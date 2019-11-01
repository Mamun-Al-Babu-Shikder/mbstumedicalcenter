/**
 * Created by A.A.MAMUN on 10/7/2019.
 */
$(document).ready(function () {
    //alert("I am ready to load data...");
    $("#overlay").show();

    pid = $("#patientId").text();

    fetchMessages();
    fetchTreatmentHistory();

});

function fetchTreatmentHistory() {

    $.post("prescription-list-by-id",{
            pid:pid
        },
        function (data, status) {

            $("#overlay").hide();

            prescriptionLiist = data;
            tblRow = "";


            for(i=0;i<prescriptionLiist.length;i++){
                prescription = prescriptionLiist[i];
                //alert(Object.values(prescription));
                tblRow+="<tr >";
                tblRow+="<td>"+prescription["date"]+"</td>";
                tblRow+="<td>"+prescription["drname"]+"</td>";
                tblRow+="<td onclick='displayPrescription("+i+")'><i style='color: #10afd6; font-size:16px' class='fa fa-eye' aria-hidden='true'></i> </td>";
                tblRow+="<td onclick='printPrescription("+i+")'><i style='color: #e60a0a; font-size:16px' class='fa fa-print' aria-hidden='true'></i> </td>";
                tblRow+="</tr>";
            }

            $("#prescriptionListTblBody").empty();
            $("#prescriptionListTblBody").append(tblRow);

       // alert("Data : "+Object.values(data));
    });
}



function displayPrescription(i) {

    prescription = prescriptionLiist[i];
    //alert(Object.values(prescription));

    $("#presDoctor").text("Dr. "+prescription["drname"]);
    $("#presDoctorDegree").text(prescription["drDegree"]);
    $("#presDoctorPhone").text("Phone : "+prescription["drPhone"]);
    $("#presPatientId").text("ID : "+prescription["pid"]);
    $("#presPatientName").text("Name : "+prescription["pname"]);
    $("#presPatientSex").text("Gender : "+prescription["psex"]);
    $("#presPatientAge").text("Age : "+prescription["page"]);
    $("#prescriptionBody").text(prescription["body"]);

    $("#prescriptionModal").modal('show');
}


function printPrescription(i) {

    prescription = prescriptionLiist[i];
    //alert(Object.values(prescription));

    $("#presDoctor").text("Dr. "+prescription["drname"]);
    $("#presDoctorDegree").text(prescription["drDegree"]);
    $("#presDoctorPhone").text("Phone : "+prescription["drPhone"]);
    $("#presPatientId").text("ID : "+prescription["pid"]);
    $("#presPatientName").text("Name : "+prescription["pname"]);
    $("#presPatientSex").text("Gender : "+prescription["psex"]);
    $("#presPatientAge").text("Age : "+prescription["page"]);
    $("#prescriptionBody").text(prescription["body"]);

    var hrx = $(".text-area").val();

    $("#rx").text(hrx);
    $("#rx").hide();

    var divToPrint = document.getElementById('prescription-content');
    var newWin = window.open('', 'Print-Window');
    newWin.document.open();

    var arr = hrx.split("\n");
    hrx = "";
    for (i = 0; i < arr.length; i++) {
        hrx += arr[i] + "<br/>";
    }
    drprescription = "<p style='text-align:left;padding: 0px 100px'>" + hrx + "</p>";
    newWin.document.write('<html><body onload="window.print()"> <center>' + divToPrint.innerHTML + drprescription + ' </center> </body></html>');
    newWin.document.close();
    setTimeout(function () { newWin.close(); }, 1);

}


function  fetchMessages() {

    $.post("fetch-message-by-id",{id: pid}, function (data, status) {
        messageList = data;//Object.values(data);
        //alert(Object.values(messageList));
        tblRow = "";

        for(i=0;i<messageList.length;i++){

            message = messageList[i];

            if(message["status"]=="unseen"){
                tblRow+='<tr id="mgsTr'+i+'" class="boldFont" onclick="viewMessage('+i+','+message["id"]+')">';
            }else{
                tblRow+='<tr id="mgsTr'+i+'" onclick="viewMessage('+i+','+message["id"]+')">';
            }

            tblRow+='<td>'+message["date"]+'</td>';
            if(message["sender"]==pid)
                tblRow+='<td style="text-align: center"> <i class="fa fa-arrow-right" aria-hidden="true"></i> </td>';
            else
                tblRow+='<td style="text-align: center"> <i class="fa fa-arrow-left" aria-hidden="true"></i> </td>';
            tblRow+='<td>'+message["doctor"]+'</td>';
            tblRow+='<td>'+message["subject"]+' - ('+message["message"]+')'+'</td>';
            tblRow+='<td id="statusTd'+i+'">'+message["status"]+'</td>';
            tblRow+='</tr>';
            
        }

        $("#messageTblBody").empty();
        $("#messageTblBody").append(tblRow);

    });
}



function viewMessage(i, id) {

    mgs = messageList[i];

    if(mgs["sender"]==pid){
        send = "Send by You"
    }else{
        send = "Send by Doctor"
    }

    //alert("Date : "+mgs["date"]+"\nSender : "+send+"\nSubject : "+mgs["subject"]+"\nMessage : "+mgs["message"]);

    $("#mgsDate").text("Date : "+mgs["date"]);
    $("#mgsSender").text("Sender : "+send);
    $("#mgsSubject").text("Subject : "+mgs["subject"]);
    $("#messageBody").text("Message : "+mgs["message"]);

    $("#messageDisplayModal").modal('show');

    if(mgs["sender"]!=pid && mgs["status"]=="unseen"){
        $.post("message-status-to-seen",{id:id}, function (data, status) {
            if(data){
                $("#mgsTr"+i).removeClass("boldFont");
                $("#statusTd"+i).text("seen");
            }
        });
    }


}



function sendMessage() {

   $(".input-box").removeClass("efw");

    doctorEmail = $("#selectedDoctor option:selected").attr("id");
    doctorName = $("#selectedDoctor").val();
    subject = $("#subject").val();
    message = $("#message").val();

    if(subject==''){
        $("#subject").addClass("efw");
    }else if(message==''){
        $("#message").addClass("efw");
    }else{
       // alert(doctorEmail +", "+doctorName+ ", " + subject + ", " + message);

        $("#overlay").show();

        $.post("send-message-to-doctor",{
            sender: pid,
            receiver: doctorEmail,
            patient: $("#pname").text(),
            doctor: doctorName,
            subject: subject,
            message: message
        }, function (data, status) {

            $("#overlay").hide();
            if(data){
                $("#subject").val('');
                $("#message").val('');
                fetchMessages();
            }else{
                alert("Sorry, Message can't send. Please try again later.")
            }
        });
    }

}

