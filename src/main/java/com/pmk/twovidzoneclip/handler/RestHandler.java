package com.pmk.twovidzoneclip.handler;

import com.google.common.annotations.VisibleForTesting;
import org.fest.assertions.IntAssert;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

import java.text.NumberFormat;

public final class RestHandler implements Handler<HttpServerRequest> {
    @Override
    public final void handle(final HttpServerRequest req) {
        req.response.sendFile("webroot/route_match/index.html");
    }

    @VisibleForTesting
    boolean checkPageNumberFormat(final String strChecked) {
        final String pattern = "[0-9]+";
        return strChecked.matches(pattern);
    }
}
