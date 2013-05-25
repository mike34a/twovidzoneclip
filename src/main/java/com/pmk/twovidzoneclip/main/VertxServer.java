package com.pmk.twovidzoneclip.main;

import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;

public final class VertxServer extends Verticle {

    private static final Integer portNumber = 8182;

    @Override
    public final void start() throws Exception {
        final RouteMatcher routeMatcher = new RouteMatcher();

        // Catch all - serve the index page
        routeMatcher.get("/videoresources/:user/:id", new RestHandler());

        routeMatcher.getWithRegEx(".*", new WebHandler());

        vertx.createHttpServer().requestHandler(
                routeMatcher).listen(portNumber);
    }
}
