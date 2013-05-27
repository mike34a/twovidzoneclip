// 1. This code loads the IFrame Player API code asynchronously.
var tag = document.createElement('script');

tag.src = "https://www.youtube.com/iframe_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

// 2. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.

function onYouTubeIframeAPIReady() {
    var soundUrl = 'iPrnduGtgmc';
    var videoUrl = 'TGspSCgvygw';
    
    var soundplayer;
    soundplayer = new YT.Player('vidplayer', {
      height: '0',
      width: '0',
      videoId: soundUrl,
      events: {
        'onReady': onSoundPlayerReady,
        'onStateChange': onPlayerStateChange
      }
    });
    
    var vidplayer;
        vidplayer = new YT.Player('soundplayer', {
        height: '390',
        width: '640',
        videoId: videoUrl,
        playerVars: { 'autoplay': 1, 'controls': 0 },
        events: {
          'onReady': onVideoPlayerReady,
          'onStateChange': onPlayerStateChange
        }
        });
}

// 3. The API will call this function when the video player is ready.
function onSoundPlayerReady(event) {
  event.target.playVideo();
}
function onVideoPlayerReady(event) {
  event.target.playVideo();
  event.target.mute();
}

// 4. The API calls this function when the player's state changes.
//    The function indicates that when playing a video (state=1),
//    the player should play for six seconds and then stop.
var done = false;
function onPlayerStateChange(event) {
  if (event.data == YT.PlayerState.PLAYING && !done) {
    setTimeout(stopVideo, 6000);
    done = true;
  }
}
function stopVideo() {
  player.stopVideo();
}