package com.pmk.twovidzoneclip.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public final class RestHandler implements Handler<HttpServerRequest> {
    @Override
    public final void handle(final HttpServerRequest req) {
        req.response.sendFile("webroot/route_match/index.html");
    }
}
