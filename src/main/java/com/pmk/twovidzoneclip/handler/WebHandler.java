package com.pmk.twovidzoneclip.handler;

import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;

public class WebHandler implements Handler<HttpServerRequest> {
    public void handle(HttpServerRequest req) {
        String file = req.path.equals("/") ? "index.html" : req.path;

        req.response.sendFile("webroot/" + file);
    }
}
