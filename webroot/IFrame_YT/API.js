// 1. This code loads the IFrame Player API code asynchronously.
var tag = document.createElement('script');

var tag = document.createElement('script');
tag.src = "http://www.youtube.com/player_api";
var firstScriptTag = document.getElementsByTagName('script')[0];
firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

// 2. This function creates an <iframe> (and YouTube player)
//    after the API code downloads.

function onYouTubeIframeAPIReady() {

    var soundUrl = 'iPrnduGtgmc';
    var videoUrl = 'TGspSCgvygw';
    
    var soundplayer;
    soundplayer = new YT.Player('soundplayer', {
      height: '0',
      width: '0',
      videoId: soundUrl,
      playerVars: { 'autoplay': 0},
      events: {
        'onReady': onSoundPlayerReady,
        'onStateChange': onPlayerStateChange
      },
      params:{ allowScriptAccess: "always" }
    });
    
    var vidplayer;
        vidplayer = new YT.Player('vidplayer', {
        height: '390',
        width: '640',
        videoId: videoUrl,
        playerVars: { 'autoplay': 0, 'controls': 1 },
        events: {
          'onReady': onVideoPlayerReady,
          'onStateChange': onPlayerStateChange
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

function onPlayerStateChange(event) {
  //alert(event.data);
  if (event.data == YT.PlayerState.PLAYING) {
    //setTimeout(stopVideo, 6000);
    //alert("play");
    callPlayer('soundplayer', 'playVideo');
    callPlayer('vidplayer', 'playVideo');
  }
  
  if ((event.data == YT.PlayerState.PAUSED || event.data == YT.PlayerState.BUFFERING )) {
    //alert("pause buffering");
    callPlayer('soundplayer', 'pauseVideo');
    callPlayer('vidplayer', 'pauseVideo');
  }
  
  if (event.data == YT.PlayerState.ENDED) {
    //alert("stop");
    callPlayer('soundplayer', 'stopVideo');
    callPlayer('vidplayer', 'stopVideo');
  }
}

function callPlayer(frame_id, func, args) {
    if (window.jQuery && frame_id instanceof jQuery) frame_id = frame_id.get(0).id;
    var iframe = document.getElementById(frame_id);
    if (iframe && iframe.tagName.toUpperCase() != 'IFRAME') {
        iframe = iframe.getElementsByTagName('iframe')[0];
    }
    
    if (!callPlayer.queue) callPlayer.queue = {};
    var queue = callPlayer.queue[frame_id],
        domReady = document.readyState == 'complete';

    if (domReady && !iframe) {
        
        window.console && console.log('callPlayer: Frame not found; id=' + frame_id);
        if (queue) clearInterval(queue.poller);
    } else if (func === 'listening') {
        
        if (iframe && iframe.contentWindow) {
            func = '{"event":"listening","id":' + JSON.stringify(''+frame_id) + '}';
            iframe.contentWindow.postMessage(func, '*');
        }
    } else if (!domReady || iframe && (!iframe.contentWindow || queue && !queue.ready)) {
        if (!queue) queue = callPlayer.queue[frame_id] = [];
        queue.push([func, args]);
        if (!('poller' in queue)) {
            
            queue.poller = setInterval(function() {
                callPlayer(frame_id, 'listening');
            }, 250);
            
            messageEvent(1, function runOnceReady(e) {
                var tmp = JSON.parse(e.data);
                if (tmp && tmp.id == frame_id && tmp.event == 'onReady') {
                    
                    clearInterval(queue.poller);
                    queue.ready = true;
                    messageEvent(0, runOnceReady);
                    
                    while (tmp = queue.shift()) {
                        callPlayer(frame_id, tmp[0], tmp[1]);
                    }
                }
            }, false);
        }
    } else if (iframe && iframe.contentWindow) {
        if (func.call) return func();
        iframe.contentWindow.postMessage(JSON.stringify({
            "event": "command",
            "func": func,
            "args": args || [],
            "id": frame_id
        }), "*");
    }
    function messageEvent(add, listener) {
        var w3 = add ? window.addEventListener : window.removeEventListener;
        w3 ?
            w3('message', listener, !1)
        :
            (add ? window.attachEvent : window.detachEvent)('onmessage', listener);
    }
}
