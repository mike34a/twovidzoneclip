// 1. This code loads the IFrame Player API code asynchronously.
var tag = document.createElement('script');

var tag = document.createElement('script');
tag.src = "http://www.youtube.com/player_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

// 2. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.

function onYouTubeIframeAPIReady() {

    //var soundUrl = 'iPrnduGtgmc';
    //var videoUrl = 'TGspSCgvygw';
    
    var vid = getFirstVideo();
    
    var soundUrl = vid.sound;
    var videoUrl = vid.video;
    $("#title").text(vid.title);
    
    var soundplayer;
    soundplayer = new YT.Player('soundplayer', {
      height: '200',
      width: '200',
      videoId: soundUrl,
      playerVars: { 'autoplay': 0, 'controls': 0 },
      events: {
        'onReady': onSoundPlayerReady,
        'onStateChange': onSoundPlayerStateChange
      },
      params:{ allowScriptAccess: "always" }
    });
    
    var vidplayer;
        vidplayer = new YT.Player('vidplayer', {
        height: '390',
        width: '640',
        videoId: videoUrl,
        playerVars: { 'autoplay': 0, 'controls': 0 },
        events: {
          'onReady': onVideoPlayerReady,
          'onStateChange': onVideoPlayerStateChange
        },
        params:{ allowScriptAccess: "always" }
        });
}

// 3. The API will call this function when the video player is ready.
function onSoundPlayerReady(event) {
  event.target.pauseVideo();
}
function onVideoPlayerReady(event) {
  event.target.pauseVideo();
  event.target.mute();
}

// 4. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.

function onVideoPlayerStateChange(event) {
  //alert(event.data);
  if (event.data == YT.PlayerState.PLAYING) {
    //callPlayer("soundplayer","playVideo");
  }
  
  if (event.data == YT.PlayerState.PAUSED || event.data == YT.PlayerState.BUFFERING) {
    //callPlayer('soundplayer', 'pauseVideo');
  }
  
  if (event.data == YT.PlayerState.ENDED) {
    //callPlayer('soundplayer', 'stopVideo');
  }
}

function onSoundPlayerStateChange(event) {
  //alert(event.data);
  if (event.data == YT.PlayerState.PLAYING) {
      //callPlayer("vidplayer","playVideo");
  }
  
  if (event.data == YT.PlayerState.PAUSED || event.data == YT.PlayerState.BUFFERING) {
    //callPlayer('vidplayer', 'pauseVideo');
  }
  
  if (event.data == YT.PlayerState.ENDED) {
    //callPlayer('vidplayer', 'stopVideo');
  }
}

function playPlayers() {
    callPlayer("vidplayer","playVideo");
    callPlayer("soundplayer","playVideo");
}

function pausePlayers() {
    callPlayer("vidplayer","pauseVideo");
    callPlayer("soundplayer","pauseVideo");    
}

function stopPlayers() {
    callPlayer("vidplayer","stopVideo");
    callPlayer("soundplayer","stopVideo");    
}
/*
 * @author       Rob W (http://stackoverflow.com/a/7513356/938089
 * @description  Executes function on a framed YouTube video (see previous link)
 *               For a full list of possible functions, see:
 *               http://code.google.com/apis/youtube/js_api_reference.html
 * @param String frame_id The id of (the div containing) the frame
 * @param String func     Desired function to call, eg. "playVideo"
 * @param Array  args     (optional) List of arguments to pass to function func*/
function callPlayer(frame_id, func, args) {
    if (window.jQuery && frame_id instanceof jQuery) frame_id = frame_id.get(0).id;
    var iframe = document.getElementById(frame_id);
    if (iframe && iframe.tagName.toUpperCase() != 'IFRAME') {
        iframe = iframe.getElementsByTagName('iframe')[0];
    }
    if (iframe) {
        // Frame exists, 
        iframe.contentWindow.postMessage(JSON.stringify({
            "event": "command",
            "func": func,
            "args": args || [],
            "id": frame_id
        }), "*");
    }
}
