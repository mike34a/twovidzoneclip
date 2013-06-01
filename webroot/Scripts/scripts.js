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
        $("#videosList").append("<div><p>" + title + "</p><a onclick=\"playVideos('" + soundID + "','" + videoID + "');\" value=\"" + title + "\"><img src=\"//i1.ytimg.com/vi/" + videoID + "/default.jpg\"></a></div>");
    });
}

function playVideos(soundID, videoID) {
    $("#soundplayer").attr("src", getEmbedUrl(soundID));
    $("#vidplayer").attr("src", getEmbedUrl(videoID));
}

function getEmbedUrl(url) {
    return 'http://www.youtube.com/embed/' + url + '?autoplay=0&amp;controls=0&amp;enablejsapi=1&amp;origin=http%3A%2F%2F127.0.0.1%3A8182';
}
   