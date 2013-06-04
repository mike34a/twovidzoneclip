$(document).ready(function() {
    getVideos(1, 10);
});

//Description : Calls a json file containing a list of videos
//args
//page : the page number
//numberOfResult : the number of results per page
function getVideos(page, numberOfResult) {
    var url = '/videoresources/' + page + '/' + numberOfResult;

    $.getJSON(url, function(videosList) {
        if (videosList !== null) {
            createVideosList(videosList);
        }
        else
            console.log("Bad json");
    });
}

//Description : Loads a list of videos
//args
//videoList : json List of the videos
function createVideosList(videosList) {
    $.each(videosList, function(key, value) {
        var title = value.title;
        var soundID = value.soundUrl;
        var videoID = value.imageUrl;
        $("#videosList").append("<div><p>" + title + "</p><a href=\"#\" onclick=\"playVideos('" + title + "','" + soundID + "','" + videoID + "');\" value=\"" + title + "\"><img class=\"miniature\" src=\"//i2.ytimg.com//vi/" + videoID + "/mqdefault.jpg\" width=\"90%\" /></a></div>");
    });
}

//Description : Loads the embedded players
//args
//title : The title to display
//soundID : The id of the sound player you want to load
//videoID : The id of the video player you want to load
function playVideos(title, soundID, videoID) {
    $("#title").text(title);
    $("#soundplayer").attr("src", getEmbedUrl(soundID));
    $("#vidplayer").attr("src", getEmbedUrl(videoID));
}

//Description : Returns a youtube embedded url
//args
//id : The id of the youtube video
function getEmbedUrl(id) {
    return 'http://www.youtube.com/embed/' + id + '?autoplay=0&amp;controls=0&amp;enablejsapi=1&amp;%3A8182';
}

//Description : Tabs management
//args
//divid : id of the div to show
//linkid : id of the tab to activate
function tabs(divid, linkid) {
    if (linkid !== "link-view")
        $("#link-view").removeClass("active");
    if (linkid !== "link-add")
        $("#link-add").removeClass("active");
    if (linkid !== "link-about")
        $("#link-about").removeClass("active");
    $('#' + linkid).addClass("active");

    if (divid !== "tabs-view")
        $("#tabs-view").hide();
    if (divid !== "tabs-add")
        $("#tabs-add").hide();
    if (divid !== "tabs-about")
        $("#tabs-about").hide();
    $('#' + divid).show();
}

//Description : Adds a mashup to the database
//args
//title : Title of the mashup
//videoLink : Link to the youtube video as video
//soundLink : Link to the youtube video as sound
function addVideo(title, videoLink, soundLink) {
    if (validForm()) {
        var encodedVideoLink = getYoutubeLink(videoLink);
        var encodedSoundLink = getYoutubeLink(soundLink);
        $.get('/addvideo/' + title + '/' + encodedVideoLink + '/' + encodedSoundLink, function(data) {
            $("#validation").show();
            $("#add_form").hide();
            if (data === "success") {
                $("#success_add_message").show();
                $("#add_video_link").val("");
                $("#add_sound_link").val("");
                $("#add_video_title").val("");
            }
            else
                $("#fail_add_message").show();
        });
    }
}

//Description : Check if the input is a valid youtube url
//args
//input : Input text
function isInputAYoutubeUrl(input) {
    var urlregex = new RegExp("^(http:\/\/www.youtube.com|www.youtube.com){1}([0-9A-Za-z]+\.)?");
    if (urlregex.test(input)) {
        return (true);
    }
    else
        return (false);
}

//Description : Encode and url to be passed to the server
//args
//input url : The url to be encoded
function getYoutubeLink(url) {
    var link = url.replace(/\//g, "&");
    link = link.replace(/\?/g, "&");
    return link;
}

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

//Description : Hides the validation div and displays add form
function displayAddForm() {
    $("#validation").hide();
    $("#add_form").show();
}