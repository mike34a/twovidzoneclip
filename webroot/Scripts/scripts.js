$(document).ready(function () {
    getVideos(1, 10);
});

function getVideos(page, numberOfResult) {
    var url = '/videoresources/' + page + '/' + numberOfResult;

    $.getJSON(url, function (videosList) {
        if (videosList != null) {
            createVideosList(videosList)
        }
        else console.log("Bad json");
    });
}

function createVideosList(videosList) {
    $.each(videosList, function (key, value) {
        var title = value.title;
        var soundID = value.soundUrl;
        var videoID = value.imageUrl;
        $("#videosList").append("<div><p>"+title+"</p><a href=\"#\" onclick=\"playVideos('"+title+"','"+soundID+"','"+videoID+"');\" value=\""+title+"\"><img class=\"miniature\" src=\"//i2.ytimg.com//vi/"+videoID+"/mqdefault.jpg\" width=\"90%\" /></a></div>");
    });
}

function playVideos(title, soundID, videoID) {
    $("#title").text(title);
    $("#soundplayer").attr("src", getEmbedUrl(soundID));
    $("#vidplayer").attr("src", getEmbedUrl(videoID));
}

function getEmbedUrl(url) {
    return 'http://www.youtube.com/embed/' + url + '?autoplay=0&amp;controls=0&amp;enablejsapi=1&amp;%3A8182';
}
   