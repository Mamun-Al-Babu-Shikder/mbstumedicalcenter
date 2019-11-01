/**
 * Created by A.A.MAMUN on 10/11/2019.
 */


$(document).ready(function () {

    ids = $("#optSectionHolder").children("section");
    for (i = 0; i < ids.length; i++) {
        $(ids[i]).hide();
    }
    $("#homeSection").show();
    $("#overlay").show();

    getDoctor();
    getPatient();
    fetchMessage();
    fetchHealthTips();
    fetchContactMessage();

});


function getDoctor() {

    $.post("fetch-doctor-info", {email: $("#dr-email").text()}, function (doctor, status) {

        doctorInfo = doctor;
        //alert(doctor["email"]+" "+doctor["name"]);
        $("#drName").val(doctor["name"]);
        $("#drDegree").val(doctor["degree"]);
        $("#drPhone").val(doctor["phone"]);
        $("#drPassword").val(doctor["password"]);

    });
}


function getPatient() {

    $.post("fetch-patient", function (data, status) {

        $("#overlay").hide();

        patientList = Object.values(data);
        tblRow = "";
        numOfTeacher = 0;
        numOfStudent = 0;
        numOfStaff = 0;

        for(i=0;i<patientList.length;i++) {

            p = patientList[i];

            if(p["ptype"]=="Teacher"){
                numOfTeacher++;
            }else  if(p["ptype"]=="Student"){
                numOfStudent++;
            }else  if(p["ptype"]=="Staff"){
                numOfStaff++;
            }


            tblRow+="<tr id="+p["id"]+">";
            tblRow+='<td style="text-align: center"> <div> <i class="fa fa-ellipsis-v opt-menu-btn" onclick="openOptMenu(this)" aria-hidden="true"></i> <div class="sub_menu"> ' +
                '<ul style="list-style: none; list-style-type: none; padding:0px;"> ' +
                '<li onclick="prescriptionHistory(this,'+i+')"> <a> <span class="fa fa-bar-chart "aria-hidden="true"></span> History </a> </li> ' +
                '<li onclick="editPatientInfo(this,'+i+')"> <a> <span class="fa fa-pencil "aria-hidden="true"></span> Update </a> </li> ' +
                '<li onclick="deletePatientInfo(this,'+i+')"> <a><span class="fa fa-trash" aria-hidden="true"></span> Delete </a> </li> ' +
                '</ul> ' +
                '</div> </div> </td>';

            tblRow+="<td onclick='selectedPatient("+p["id"]+")'>"+p["ptype"]+"</td>";
            tblRow+="<td onclick='selectedPatient("+p["id"]+")'>"+p["pid"]+"</td>";
            tblRow+="<td onclick='selectedPatient("+p["id"]+")'>"+p["pname"]+"</td>";
            tblRow+="<td onclick='selectedPatient("+p["id"]+")'>"+p["pfather"]+"</td>";
            tblRow+="<td onclick='selectedPatient("+p["id"]+")'>"+p["pmother"]+"</td>";
            tblRow+='<td onclick="selectedPatient('+p["id"]+')">'+p['paddress']+'</td>';
            tblRow+='<td onclick="selectedPatient('+p["id"]+')">'+p['psex']+'</td>';
            tblRow+='<td onclick="selectedPatient('+p["id"]+')">'+p['pdob']+'</td>';
            tblRow+='<td onclick="selectedPatient('+p["id"]+')" style="text-align: center">'+p['pblood']+'</td>';
            tblRow+='<td onclick="selectedPatient('+p["id"]+')" style="text-align: center">'+p['pdept']+'</td>';
            tblRow+='<td onclick="selectedPatient('+p["id"]+')" >'+p['pcontact']+'</td>';


            tblRow+="</tr>";

        }


        $("#numOfTeacher").text(numOfTeacher);
        $("#numOfStudent").text(numOfStudent);
        $("#numOfStaff").text(numOfStaff);


        totalPatient = numOfTeacher + numOfStudent + numOfStaff;
        if(totalPatient==0){
            totalPatient = 1;
        }

        $("#percentageOfTeacher").text(  Math.floor((numOfTeacher/totalPatient)*100) + "%");
        $("#percentageOfStudent").text(  Math.floor((numOfStudent/totalPatient)*100) + "%");
        $("#percentageOfStaff").text(  Math.floor((numOfStaff/totalPatient)*100) + "%");


        $("#patientTbl").empty();
        $("#patientTbl").append(tblRow);


    });

}

function selectOption(ctx, opt) {

    $(".active-menu").addClass("non-active-menu");
    $(".active-menu").removeClass("active-menu");

    ids = $("#optSectionHolder").children("section");
    for (i = 0; i < ids.length; i++) {
        $(ids[i]).hide();
    }
    $(ctx).removeClass("non-active-menu");
    $(ctx).addClass("active-menu");
    $("#" + opt).show();

}

function displayDrProfile() {


    $("#drName").removeClass("efw");
    $("#drDegree").removeClass("efw");
    $("#drPhone").removeClass("efw");
    $("#drPassword").removeClass("efw");

    doctor = doctorInfo ;
    //alert(doctor["email"]+" "+doctor["name"]);
    $("#drName").val(doctor["name"]);
    $("#drDegree").val(doctor["degree"]);
    $("#drPhone").val(doctor["phone"]);
    $("#drPassword").val(doctor["password"]);

    $("#drModal").modal("show");

}

function updateDrInfo() {

    $("#drName").removeClass("efw");
    $("#drDegree").removeClass("efw");
    $("#drPhone").removeClass("efw");
    $("#drPassword").removeClass("efw");

    if($("#drName").val()==''){
        $("#drName").addClass("efw");
    }else if( $("#drDegree").val()==''){
        $("#drDegree").addClass("efw");
    }else if($("#drPhone").val()==''){
        $("#drPhone").addClass("efw");
    }else if($("#drPassword").val()==''){
        $("#drPassword").addClass("efw");
    }else{

        $("#drModal").modal("hide");

        var doctor = {
            'email': doctorInfo["email"],
            'name': $("#drName").val(),
            'degree': $("#drDegree").val(),
            'phone': $("#drPhone").val(),
            'password': $("#drPassword").val()
        };

        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: "update-doctor-info",
            data: JSON.stringify(doctor), // Note : it is important
            success: function (result) {
                if(result){
                    alert("Successfully updated.");
                    getDoctor();
                }else{
                    alert("Can't update! Please try again.");
                }
            },
            error: function (e) {
                alert("Internal error occurred.");
            }
        });


    }

}


function fetchMessage() {

    drEmail = $("#dr-email").text();
    $.post("fetch-message-by-doctor", {dremail: drEmail}, function (data, status) {

        messageList = data;
        tblRow = "";
        for(i=0;i<messageList.length;i++)
        {
            message = messageList[i];

            if(message["status"]=="unseen"){
                tblRow+='<tr id="mgsTr'+i+'" class="boldFont" onclick="viewMessage('+i+','+message["id"]+')">';
            }else{
                tblRow+='<tr id="mgsTr'+i+'" onclick="viewMessage('+i+')">';
            }
            tblRow+='<td>'+message["date"]+'</td>';

            if(message["sender"]==drEmail){
                tblRow+='<td style="text-align: center"> <i class="fa fa-arrow-right" aria-hidden="true"></i> </td>';
                tblRow+='<td>You</td>';
            }else{
                tblRow+='<td style="text-align: center"> <i class="fa fa-arrow-left" aria-hidden="true"></i> </td>';
                tblRow+='<td>'+message["sender"]+'</td>';
            }

            tblRow+='<td>'+message["subject"]+' - ('+message["message"]+')'+'</td>';
            tblRow+='<td>'+message["status"]+'</td>';
            tblRow+='</tr>';
        }

        $("#messageTblBody").empty();
        $("#messageTblBody").append(tblRow);

    });

}



function viewMessage(i, id) {

    mgs = messageList[i];
    if(mgs["sender"]==drEmail){
        send = "Send by You"
        $("#replyMessageBtn").hide();
    }else{
        send = "Send by "+mgs["patient"];
        $("#replyMessageBtn").show();
    }

    $("#mgsDate").text("Date : "+mgs["date"]);
    $("#mgsSender").text("Sender : "+send);
    $("#mgsPatient").text("Patient : "+mgs["patient"]);
    $("#mgsSubject").text("Subject : "+mgs["subject"]);
    $("#messageBody").text("Message : "+mgs["message"]);



    if(mgs["sender"]!=drEmail && mgs["status"]=="unseen"){
        $.post("message-status-to-seen",{id:id}, function (data, status) {
            if(data){
                $("#mgsTr"+i).removeClass("boldFont");
                $("#statusTd"+i).text("seen");
            }
        });
    }

    $("#messageDisplayModal").modal('show');

    $("#replyMessageBtn").click(function () {
        $("#patientId").val(mgs["sender"]);
        $("#subject").val(mgs["subject"]);
        $("#sendMessageModal").modal('show');
    })
}

function sendMessage() {


    $(".input-box").removeClass("efw2");

    cmPid = $("#patientId").val();
    cmSub = $("#subject").val();
    cmBody = $("#message").val();

    if(cmPid==''){
        $("#patientId").addClass("efw2");
    }else if(cmSub==''){
        $("#subject").addClass("efw2");
    }else if(cmBody==''){
        $("#message").addClass("efw2");
    }else{


        $("#sendMessageModal").modal('hide');
        $("#overlay").show();

        $(".input-box").val('');
        /*
         $("#patientId").val();
         $("#subject").val();
         $("#message").val();
         */

        $.post("send-message-to-patient",{
            pid: cmPid,
            dremail: doctorInfo["email"],
            drname: doctorInfo["name"],
            subject: cmSub,
            message: cmBody
        }, function (data, status) {

            $("#overlay").hide();
            if(data){
                fetchMessage();
            }else{
                alert("Message can't send, Please try again with existing patient id");
            }

        });

    }

   // alert("Sent to : "+cmPid+"\n"+cmSub+"\n"+cmBody);

}


/* HEALTH TIPS SECTION HERE */
// function fetchHealthTips() {
//     alert("Fetch Health Tips");
// }


function fetchContactMessage() {

    $.post("fetch-contact-message", function (data, status) {

        contactMessageList = data;

        tblRow = "";
        for(i = 0; i<contactMessageList.length; i++)
        {
            contactMgs = contactMessageList[i];
            if(contactMgs["status"]=='unseen')
                tblRow+='<tr id="contMgsTr'+i+'" class="boldFont" >';
            else
                tblRow+='<tr id="contMgsTr'+i+'">';
            tblRow+='<td onclick="viewContactMessage('+i+')">'+contactMgs["date"]+'</td>';
            tblRow+='<td onclick="viewContactMessage('+i+')">'+contactMgs["name"]+'</td>';
            tblRow+='<td onclick="viewContactMessage('+i+')">'+contactMgs["email"]+'</td>';
            tblRow+='<td onclick="viewContactMessage('+i+')">'+contactMgs["subject"]+' - ('+contactMgs["message"]+') </td>';
            tblRow+='<td> <button style="font-size: 11px;" type="button"  onclick="deleteContactMessage('+i+','+contactMgs["id"]+')" class="btn btn-danger"> <i class="fa fa-trash-o" aria-hidden="true"></i> </button></td>';
            tblRow+='</tr>';
        }

        $("#contactTblBody").empty();
        $("#contactTblBody").append(tblRow);
    });

}



function viewContactMessage(i) {

    contactMgs = contactMessageList[i];
    if(contactMgs["status"]=='unseen'){

        $.post("update-contact-message-status",{id:contactMgs["id"]}, function (data, status) {
            if(data){
                $("#contMgsTr"+i).removeClass("boldFont");
            }
        });
    }

    $("#contMgsDate").text("Date : "+contactMgs["date"]);
    $("#contMgsSender").text("Sender : "+contactMgs["name"]);
    $("#contMgsEmail").text("Eamil : "+contactMgs["email"]);
    $("#contMgsSubject").text("Subject : "+contactMgs["subject"]);
    $("#contMgsBody").text("Message : "+contactMgs["message"]);

    $("#contactMessageDisplayModal").modal('show');

    $("#replyContMessageBtn").click(function () {
        $("#replyEmailAddress").val("To : "+contactMgs["email"]);
        $("#sendReplyByEmailModal").modal('show');
    });

}


function deleteContactMessage(i,id) {

    $("#overlay").show();
    $.post("delete-contact-message", {id:id}, function (data, status) {
        $("#overlay").hide();
        if(data){
            //fetchContactMessage();
            $("#contMgsTr"+i).remove();
        }else{
            alert("Message can't delete, Please try again later.");
        }
    });

    //alert("delete id : "+id);
}

function sendReplyMessageByEmail() {

    replySub = $("#replySubject").val();
    replyMgs = $("#replyMessage").val();

    if(replySub!='' && replyMgs!=''){

        $("#sendReplyByEmailModal").modal('hide');
        $("#overlay").show();
        $.post("reply-message-by-email",{email:contactMgs["email"], subject:replySub, message:replyMgs},
            function (data, status) {
                $("#overlay").hide();
                if(data){
                    $("#replySubject").val('');
                    $("#replyMessage").val('');

                    $("#success-mgs").text("Message successfully sent to : "+contactMgs["email"]);
                    $("#succeessToast").toast('show');

                }else{
                    // alert("Message can't send, Please try again later.");
                    $("#failedToast").text("Message can't send, Please try again later.");
                    $("#failedToast").toast('show');
                }
            });
    }

}





$(document).ready(function () {

    $("#searchPatient").keyup(function () {
        var sv = $(this).val().toLowerCase();
        $("#patientTbl tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(sv) > -1);
        });
    });

    $("#searchMessage").keyup(function () {
        var sv = $(this).val().toLowerCase();
        $("#messageTblBody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(sv) > -1);
        });
    });

    $("#searchHealthTips").keyup(function () {
        var sv = $(this).val().toLowerCase();
        $("#healthTipsTblBody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(sv) > -1);
        });
    });

    $("#searchHealthTipsBook").keyup(function () {
        var sv = $(this).val().toLowerCase();
        $("#healthTipsBookTblBody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(sv) > -1);
        });
    });

    $("#searchContactMessage").keyup(function () {
        var sv = $(this).val().toLowerCase();
        $("#contactTblBody tr").filter(function () {
            $(this).toggle($(this).text().toLowerCase().indexOf(sv) > -1);
        });
    });

    $(".vp").click(function () {
        alert("ID");
        //alert("ID : " + $(this).parent("tr").attr("id"));
    });

});

function openOptMenu(ctx) {
    $(".sub_menu").hide(100);
    $(ctx).parent("div").children("div").show();
}


function selectedPatient(id) {
    //  alert("ID -> "+id);
    sessionStorage.setItem("key", id);
    sessionStorage.setItem("dr-email", $("#dr-email").text());
    window.open("prescription","_self");
}

function clearWarning() {
    for (i = 1; i < 15; i++) {
        $("#pInfo" + i).removeClass("efw");
    }
}

function addPatient() {

    $(".modal-title").text("Add New Patient");
    clearWarning();
    optType = 101;
    for (i = 1; i < 15; i++) {
        if (i == 1 || i == 2 || i == 8 || i == 10 || i == 14) {
        } else {
            $("#pInfo" + i).val("");
        }
    }
}

function editPatientInfo(ctx, v) {

    $(".modal-title").text("Update Patient Info");
    clearWarning();


    p = patientList[v];

    $('#pInfo1').val(p["ptype"]),
        $('#pInfo2').val(p["pdept"]),
        $('#pInfo3').val(p["pid"]),
        $('#pInfo4').val(p["pname"]),
        $('#pInfo5').val(p["pfather"]),
        $('#pInfo6').val(p["pmother"]),
        $('#pInfo7').val(p["paddress"]),
        $('#pInfo8').val(p["psex"]),
        $('#pInfo9').val(p["pdob"]),
        $('#pInfo10').val(p["pblood"]),
        $('#pInfo11').val(p["pheight"]),
        $('#pInfo12').val(p["pweight"]),
        $('#pInfo13').val(p["pcontact"]),
        $('#pInfo14').val(p["pmaritalstatus"]);

    optType = 102;
    $(".sub_menu").hide(100);
    $("#myModal").modal('show');

}

function saveOrUpdate() {

    saveAble = true;
    for (i = 1; i < 15; i++) {
        if ($("#pInfo" + i).val() == '' && (i == 3 || i == 4 || i == 7 || i==9)) {
            saveAble = false;
            $("#pInfo" + i).addClass("efw");
        } else {
            $("#pInfo" + i).removeClass("efw");
        }
    }

    var  patient = {

        'id': 0,
        'dd': 0,
        'mm': 0,
        'yyyy': 0,
        'ptype': $('#pInfo1').val(),
        'pdept': $('#pInfo2').val(),
        'pid': $('#pInfo3').val(),
        'pname': $('#pInfo4').val(),
        'pfather': $('#pInfo5').val(),
        'pmother': $('#pInfo6').val(),
        'paddress': $('#pInfo7').val(),
        'psex': $('#pInfo8').val(),
        'pdob': $('#pInfo9').val(),
        'pblood': $('#pInfo10').val(),
        'pheight': $('#pInfo11').val(),
        'pweight': $('#pInfo12').val(),
        'pcontact': $('#pInfo13').val(),
        'pmaritalstatus': $('#pInfo14').val()

    };

    if (optType === 101 && saveAble) {

        $("#overlay").show();

        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: "save-patient",
            data: JSON.stringify(patient), // Note : it is important
            success: function (result) {
                $("#overlay").hide();
                if(result){
                    $("#myModal").modal("hide");
                    getPatient();
                }else{
                    alert("Can't save! Please try again.");
                }
            },
            error: function (e) {
                $("#overlay").hide();
                alert("Internal error occurred.");
            }
        });


    } else if(optType == 102 && saveAble) {

        $("#overlay").show();

        patient.id = p["id"];
        patient.dd = p["dd"];
        patient.mm = p["mm"];
        patient.yyyy = p["yyyy"];

        $.ajax({
            type: "POST",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            url: "update-patient",
            data: JSON.stringify(patient), // Note : it is important
            success: function (result) {
                $("#overlay").hide();
                if(result){
                    $("#myModal").modal("hide");
                    getPatient();
                }else{
                    alert("Can't update! Please try again.");
                }
            },
            error: function (e) {
                $("#overlay").hide();
                alert("Internal error occurred.");
            }
        });
    }
}



function deletePatientInfo(ctx,v) {


    $("#deletePatientInfoModal").modal('show');

    delete_patient_id = v;

    $(".sub_menu").hide(100);
    //alert("Delete : "+v);

}

function deletePatient() {

    $("#overlay").show();
    patient = patientList[delete_patient_id];

    $.post("delete-patient",{id: patient["id"]},function (data, status) {
        $("#overlay").hide();
        if(data){
            getPatient();
        }else{
            alert("Can't delete, please try again.")
        }
    });
}

function prescriptionHistory(ctx, v) {

    $("#overlay").show();
    $(".sub_menu").hide(100);
    patient = patientList[v];
    $.post("history-by-pid",{pid:patient["pid"]}, function (data, status) {

        // alert(Object.values(data));
        $("#overlay").hide();

        hlist = "";
        historyList = Object.values(data);
        for(i=0;i<historyList.length;i++ )
        {
            phistory = historyList[i];

            hlist+='<div style="margin: 10px 0px" class="card">';
            hlist+='<div style="padding: 10px; background: #eee;" class="d-flex">';
            hlist+='<img src="images/dr.png" style="width: 40px; height:40px" />';
            hlist+='<div style="padding: 0px 10px;">';
            hlist+='<h6 style="margin: 0px; padding:0px"> '+phistory["drname"]+' </h6>';
            hlist+='<p style="font-size: 12px; margin: 0px;"> '+phistory["date"]+' </p>';
            hlist+='</div>';
            hlist+='</div>';
            hlist+='<div style="padding: 15px 15px;">';
            hlist+='<p style="margin: 0px; font-size: 14px"> '+phistory["body"]+' </p>';
            hlist+='</div>';
            hlist+='</div>';
        }
        if(hlist!=''){
            $("#historyBody").append(hlist);
            $("#prescriptionHistoryModal").modal('show');
        }


    });

}
