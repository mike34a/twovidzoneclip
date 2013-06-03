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
//videoLink : Link to the youtube video as video
//soundLink : Link to the youtube video as sound
function addVideo(videoLink,soundLink) {    
    alert(videoLink+' '+soundLink);
}