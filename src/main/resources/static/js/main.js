$(document).ready(function () {

    $("#giftform").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {

    var search = {}
    search["username"] = $("#selectuser").val();
    search["gifttype"] = $("#selectgift").val();

    $("#giftbtn").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/getgift",
        data: JSON.stringify(search),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Response</h4>"
                + JSON.stringify(data, null, 4);
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#giftbtn").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Response</h4>"
                + e.responseText;
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#giftbtn").prop("disabled", false);

        }
    });

}