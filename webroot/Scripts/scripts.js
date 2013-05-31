$(document).ready(function(){    
   getVideos(1,2);
});

function getVideos(page, numberOfResult)
{
    var url = 'http://127.0.0.1:8182/videoresources/'+page+'/'+numberOfResult;
    $.get(url, function(json) {
     videosList = jQuery.parseJSON(json);
     if(videosList !== null){
          createVideosList(videosList)
      }
     else console.log("Bad json");
     });
}

function createVideosList(videosList)
{
  for (var i = 0; i < videosList.length; i++) {
              var object = videosList[i];
              var title = object.title;
              var soundID = object.soundUrl;
              var videoID = object.imageUrl;
              $("#videosList").append("<input type=\"button\" onclick=\"playVideos('"+soundID+"','"+videoID+"');\" value=\""+title+"\">");
          }  
};

function playVideos(soundID,videoID)
{
    $("#soundplayer").attr("src", getEmbedUrl(soundID));
    $("#vidplayer").attr("src", getEmbedUrl(videoID)); 
};

   function getEmbedUrl(url)
{
   return 'http://www.youtube.com/embed/'+url+'?autoplay=0&amp;controls=0&amp;enablejsapi=1&amp;origin=http%3A%2F%2F127.0.0.1%3A8182';
}
   