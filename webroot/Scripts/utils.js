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