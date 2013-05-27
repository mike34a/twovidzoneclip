package com.pmk.twovidzoneclip.handler.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public final class RestHandlerImpl implements RestHandler {

    @VisibleForTesting
    VidzUrlsService vidzUrlsService;

    @Inject
    public RestHandlerImpl(VidzUrlsService vidzUrlsService) {
        this.vidzUrlsService = vidzUrlsService;
    }

    @Override
    public final void handle(final HttpServerRequest req) {
        req.response.sendFile("webroot/route_match/index.html");

        final String pageKey = "page";
        if(req.params().containsKey(pageKey)) {

            final String pageNumber = req.params().get(pageKey);
            if (checkPageNumberFormat(pageNumber)) {

            }
        }
    }

    @VisibleForTesting
    boolean checkPageNumberFormat(final String strChecked) {
        final String pattern = "[0-9]+";
        return strChecked.matches(pattern);
    }
}
