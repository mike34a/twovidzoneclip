package com.pmk.twovidzoneclip.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public class RestHandler implements Handler<HttpServerRequest> {
    @Override
    public void handle(HttpServerRequest req) {
        req.response.sendFile("webroot/route_match/index.html");
    }
}
