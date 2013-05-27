package com.pmk.twovidzoneclip.handler.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

import java.util.List;
import java.util.Map;

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
        final Map<String,String> params = req.params();

        if(params.containsKey(pageKey) && params.containsKey(numberOfResultsKey)) {

            final String pageStr = params.get(pageKey);
            final String numberOfResultsStr = params.get(numberOfResultsKey);

            final String serializedVidzUrls = vidzUrls(pageStr, numberOfResultsStr);
            
            //final String serializedVidzUrls = "[{\"imageUrl\":\"<datasimage>\",\"soundUrl\":\"<datassound>\"},{\"imageUrl\":\"<datasimage2>\",\"soundUrl\":\"<datassound2>\"}]";
 
            req.response.write(serializedVidzUrls);
        }
    }

    private String vidzUrls(String pageStr, String numberOfResultsStr) {
        final boolean paramsAreCorrect = checkPageNumberFormat(pageStr) && checkPageNumberFormat(numberOfResultsStr);
        Integer page = paramsAreCorrect ? Integer.parseInt(pageStr) : 0;
        Integer numberOfPages = paramsAreCorrect ? Integer.parseInt(numberOfResultsStr) : 0;

        return paramsAreCorrect ? gson.toJson(vidzUrlsService.findVidzUrls(page, numberOfPages)) : "";
    }


    @VisibleForTesting
    boolean checkPageNumberFormat(final String strChecked) {
        final String pattern = "[0-9]+";
        return strChecked.matches(pattern);
    }
}
