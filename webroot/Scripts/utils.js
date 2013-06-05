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

//Description : Encode and url to be passed to the server
//args
//input url : The url to be encoded
function getYoutubeLink(url) {
    var link = url.replace(/\//g, "&");
    link = link.replace(/\?/g, "&");
    return link;
}

//Description : Check if the input is a valid youtube url
//args
//input : Input text
function isInputAYoutubeUrl(input) {
    var urlregex = new RegExp("^(http:\/\/www.youtube.com|www.youtube.com){1}([0-9A-Za-z]+\.)?");
    if (urlregex.test(input)) {
        return (true);
    }
    else
        return (false);
}


//Description : Dynamic scroll
function autoScroll(){  
    var scrolltop=$('#scrollbox').scrollTop();  
    var scrollheight=$('#scrollbox').prop("scrollHeight");  
    var windowheight=$('#scrollbox').height();
    var scrolloffset=1;  
    if(scrolltop>=(scrollheight-(windowheight+scrolloffset)))  
    {  
        var newPageNumber = parseInt($('#pageNumber').val())+1;
        $('#pageNumber').val(newPageNumber);
        feedVideosList(newPageNumber,5);
    }  
    setTimeout('autoScroll();', 1500);  
}  