package com.pmk.twovidzoneclip.main;

import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.deploy.Verticle;
import org.vertx.java.deploy.impl.cli.Starter;

public class VertxServer extends Verticle {

    @Override
    public void start() throws Exception {
        vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
            public void handle(HttpServerRequest req) {
                String file = req.path.equals("/") ? "index.html" : req.path;
                req.response.sendFile("webroot/" + file);
            }
        }).listen(8182);
    }
}
