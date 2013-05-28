$(document).ready(function(){    
    var json = "[{\"imageUrl\":\"dataimageUrl\",\"soundUrl\":\"datassound\"},{\"imageUrl\":\"datasimage2\",\"soundUrl\":\"datassound2\"}]";
    var videosList = jQuery.parseJSON(json);
    for (var i = 0; i < videosList.length; i++) {
        var object = videosList[i];
        var soundID = object.soundUrl;
        var videoID = object.imageUrl; 
        alert("video : " + videoID + " sound : " + soundID);
    }
});