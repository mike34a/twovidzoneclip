$(document).ready(function(){    
    /*
    var json = "[{\"imageUrl\":\"lemonde.fr\",\"soundUrl\":\"korben.info\"},{\"imageUrl\":\"google.fr\",\"soundUrl\":\"youtube.com\"}]";
    //var json=getVideos(1,3);
    var videosList = jQuery.parseJSON(json);
    for (var i = 0; i < videosList.length; i++) {
        var object = videosList[i];
        var soundID = object.soundUrl;
        var videoID = object.imageUrl; 
        alert("video : " + videoID + " sound : " + soundID);
    }
    */

   getVideos(1,2);
   function getVideos(page, numberOfResult)
   {
       var url = 'http://127.0.0.1:8182/videoresources/'+page+'/'+numberOfResult;
       $.get(url, function(json) {
        videosList = jQuery.parseJSON(json);
        if(videosList !== null){
             for (var i = 0; i < videosList.length; i++) {
                 var object = videosList[i];
                 var soundID = object.soundUrl;
                 var videoID = object.imageUrl; 
                 alert("video : " + videoID + " sound : " + soundID);
             }
         }
        else console.log("Bad json");
        });
   }
});
