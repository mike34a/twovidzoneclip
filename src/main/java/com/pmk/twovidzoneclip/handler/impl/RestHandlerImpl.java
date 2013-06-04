package com.pmk.twovidzoneclip.handler.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import org.vertx.java.core.http.HttpServerRequest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RestHandlerImpl implements RestHandler {

    @VisibleForTesting
    VidzUrlsService vidzUrlsService;
    private static final Gson gson = new Gson();

    @Inject
    public RestHandlerImpl(VidzUrlsService vidzUrlsService) {
        this.vidzUrlsService = vidzUrlsService;
    }

    @Override
    public final void handle(final HttpServerRequest req) {
        final String pageKey = "page";
        final String numberOfResultsKey = "numberOfResults";

        final String mashupTitleKey = "title";
        final String videoLinkKey = "video";
        final String soundLinkKey = "sound";

        final Map<String, String> params = req.params();

        if (params.containsKey(pageKey) && params.containsKey(numberOfResultsKey)) {

            final String pageStr = params.get(pageKey);
            final String numberOfResultsStr = params.get(numberOfResultsKey);

            final String serializedVidzUrls = vidzUrls(pageStr, numberOfResultsStr);

            req.response.putHeader("content-length", serializedVidzUrls.length());
            req.response.write(serializedVidzUrls);
            req.response.end();
        }

        if (params.containsKey(mashupTitleKey) && params.containsKey(videoLinkKey) && params.containsKey(soundLinkKey)) {
            final String title = params.get(mashupTitleKey);
            final String videoLink = params.get(videoLinkKey);
            final String soundLink = params.get(soundLinkKey);

            final String videoID = getIDFromUrl(videoLink);
            final String soundID = getIDFromUrl(soundLink);

            String reponse;

            if (vidzUrlsService.addVideo(title, videoID, soundID)) {
                reponse = "Video added successfully";
            } else {
                reponse = "There was a problem adding your video";
            }

            req.response.putHeader("content-length", reponse.length());
            req.response.write(reponse);
            req.response.end();
        }
    }

    private String vidzUrls(String pageStr, String numberOfResultsStr) {
        final boolean paramsAreCorrect = checkPageNumberFormat(pageStr) && checkPageNumberFormat(numberOfResultsStr);
        Integer page = paramsAreCorrect ? Integer.parseInt(pageStr) : 0;
        Integer numberOfPages = paramsAreCorrect ? Integer.parseInt(numberOfResultsStr) : 0;

        return paramsAreCorrect && vidzUrlsService != null
                ? gson.toJson(vidzUrlsService.findVidzUrls(page, numberOfPages))
                : "";
    }

    @VisibleForTesting
    boolean checkPageNumberFormat(final String strChecked) {
        final String pattern = "[0-9]+";
        return strChecked.matches(pattern);
    }

    private String getIDFromUrl(String url) {
        //http://www.youtube.com/watch?feature=player_embedded&v=TGspSCgvygw&blabla
        Pattern pattern = Pattern.compile("^.*&v=(.*?)&.*$");
        Matcher matcher = pattern.matcher(url);
        int i = 0;
        boolean found = matcher.find();
        if(found) {
            return matcher.group(1);
        }
        else{
            pattern = Pattern.compile("^.*&v=(.*?)$");
            matcher = pattern.matcher(url);
            found = matcher.find();
            if(found) {
                return matcher.group(1);
            }
            else return "invalid url";
        }
    }
}
