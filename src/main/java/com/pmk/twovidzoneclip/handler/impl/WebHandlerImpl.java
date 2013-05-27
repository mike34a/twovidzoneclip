package com.pmk.twovidzoneclip.handler.impl;

import com.google.common.annotations.VisibleForTesting;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public final class WebHandlerImpl implements WebHandler {
    @Override
    public final void handle(final HttpServerRequest req) {
        final String file = "/".equals(req.path) ? "index.html" : req.path;

        final String fileRequiredPath = "webroot/" + file;

        if (fileExists(fileRequiredPath)) {
            req.response.sendFile(fileRequiredPath);
        } else {
            req.response.sendFile("webroot/Kickstrap/extras/root goodies/404.html");
        }
    }

    @VisibleForTesting
    boolean fileExists(String path) {
        return WebHandlerImpl.class.getResourceAsStream("/" + path) != null;
    }
}
