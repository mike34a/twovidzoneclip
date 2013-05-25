package com.pmk.twovidzoneclip.main;

import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;
import org.vertx.java.deploy.impl.cli.Starter;

public class VertxServer extends Verticle {

    @Override
    public void start() throws Exception {
        RouteMatcher routeMatcher = new RouteMatcher();

        // Catch all - serve the index page
        routeMatcher.get("/videoresources/:user/:id", new RestHandler());

        routeMatcher.getWithRegEx(".*", new WebHandler());

        vertx.createHttpServer().requestHandler(
                routeMatcher).listen(8182);
    }
}
