
$(document).ready(function () {

});

function sendContactMessage() {

    cont_name = $("#cont_name").val();
    cont_email = $("#cont_email").val();
    cont_subject = $("#cont_subject").val();
    cont_mgs = $("#cont_mgs").val();

    if (cont_name == '' || cont_email == '' || cont_subject == '' || cont_mgs == '') {
        $("#toast-mgs").text("Please fill up the all blank space and try again.");
        $('#tost').toast('show');
    } else if (!validateEmail(cont_email)) {
        $("#toast-mgs").text("Please enter valid email address and try again.");
        $('#tost').toast('show');
    } else {
        $("#overlay").show();
        
        $.post("send-contact-message",{name:cont_name, email:cont_email, subject:cont_subject, message:cont_mgs},
            function (data, status) {
                $("#overlay").hide();
                if(data){
                $("#toast-mgs2").text("Successfully message sent.");
                $('#tost2').toast('show');

                    $("#cont_name").val('');
                    $("#cont_email").val('');
                    $("#cont_subject").val('');
                    $("#cont_mgs").val('');

            }else{
                $("#toast-mgs").text("Message can't sent, Please try again later.");
                $('#tost').toast('show');
            }
        });
        
    }

}



/*
function subscribeRequest() {

    subscriberEmail = $(".subscribe-email").val();
    if (!validateEmail(subscriberEmail)) {
        $("#toast-mgs").text("Please enter valid email address and try again.");
        $('#tost').toast('show');
    } else {
        $("#overlay").show();
        $.post("send-subscribe-request",{subscriberEmail:subscriberEmail}, function (data, status) {
            $(".subscribe-email").val('');
            $("#overlay").hide();
            if(data){
                $("#toast-mgs2").text("You successfully subscribed.");
                $('#tost2').toast('show');
            }else{
                $("#toast-mgs").text("Already subscribed by this email.");
                $('#tost').toast('show');
            }
        })
    }

}
*/



function validateEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}




