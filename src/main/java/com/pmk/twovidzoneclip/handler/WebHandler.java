package com.pmk.twovidzoneclip.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public final class WebHandler implements Handler<HttpServerRequest> {
    public final void handle(final HttpServerRequest req) {
        final String file = req.path.equals("/") ? "index.html" : req.path;

        req.response.sendFile("webroot/" + file);
    }
}
