package com.pmk.twovidzoneclip.handler;

import com.google.common.annotations.VisibleForTesting;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public final class WebHandler implements Handler<HttpServerRequest> {
    public final void handle(final HttpServerRequest req) {
        final String file = "/".equals(req.path) ? "index.html" : req.path;

        final String fileRequiredPath = "webroot/" + file;

        req.response.sendFile(fileRequiredPath);
    }

    @VisibleForTesting
    boolean fileExists(String path) {
        return WebHandler.class.getResourceAsStream("/" + path) != null;
    }
}
