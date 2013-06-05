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