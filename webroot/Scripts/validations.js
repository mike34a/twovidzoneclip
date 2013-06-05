//Description : field validator for youtube urls
//args
//msgID : Id of the error message
//url : The url to check
function checkYoutubeUrl(msgID, url) {
    if (!isInputAYoutubeUrl(url)) {
        msgID.show();
    }
    else
        msgID.hide();
}

//Description : field validator for title
//args
//msgID : Id of the error message
//text : the text to check
function isEmpty(msgID, text) {
    if (text === "")
        msgID.show();
    else
        msgID.hide();
}

//Description : Hides the validation div and displays add form
function displayAddForm() {
    $("#validation").hide();
    $("#add_form").show();
}

//Description : Valids the input fields for the add form. TODO : refactor
function validForm() {
    var valid = true;

    if ($("#add_video_title").val === "") {
        valid = false;
        $("#title_error").show();
    }

    if (!isInputAYoutubeUrl($("#add_video_link").val())) {
        valid = false;
        $("#video_link_error").show();
    }

    if (!isInputAYoutubeUrl($("#add_sound_link").val())) {
        valid = false;
        $("#sound_link_error").show();
    }

    return valid;
}