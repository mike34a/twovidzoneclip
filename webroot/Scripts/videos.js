//Description : Video Class
function cVideo( title, video, sound ){
	this.title = title;
	this.video = video;
        this.sound = sound;

	$( this ).bind( "getTitle", function(){
		return this.title;
	});
        
        $( this ).bind( "getVideo", function(){
		return this.video;
	});
        
        $( this ).bind( "getSound", function(){
        return this.sound;
	});
}

////Description : Loads a list of videos
//args
//page : number of the page to reach
//numberOfResult : number of videos to get
function feedVideosList(page, numberOfResult) {

    var url = '/videoresources/' + page + '/' + numberOfResult;

    $.getJSON(url, function(videosList) {
        if (videosList !== null) {
            $.each(videosList, function(key, value) {
                var title = value.title;
                var soundID = value.soundUrl;
                var videoID = value.imageUrl;
                $("#videosList").append("<p>" + title + "</p><a href=\"#\" onclick=\"playVideos('" + title + "','" + soundID + "','" + videoID + "');\" value=\"" + title + "\"><img class=\"miniature\" src=\"//i2.ytimg.com//vi/" + videoID + "/mqdefault.jpg\" width=\"90%\" /></a>");
            });
        }
        else
            console.log("Bad json");
    });
}

function getFirstVideo() {
    var url = '/videoresources/1/1';

    $.getJSON(url, function(videosList) {
        if (videosList !== null) {
            $.each(videosList, function(key, value) {
                var title = value.title;
                var soundID = value.soundUrl;
                var videoID = value.imageUrl;
                $("#videosList").append("<p>" + title + "</p><a href=\"#\" onclick=\"playVideos('" + title + "','" + soundID + "','" + videoID + "');\" value=\"" + title + "\"><img class=\"miniature\" src=\"//i2.ytimg.com//vi/" + videoID + "/mqdefault.jpg\" width=\"90%\" /></a>");
            });
        }
        else
            console.log("Bad json");
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