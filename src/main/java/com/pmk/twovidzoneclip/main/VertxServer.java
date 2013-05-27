package com.pmk.twovidzoneclip.main;

import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;

public final class VertxServer extends Verticle {

    private static final Integer PORT_NUMBER = 8182;

    @Override
    public final void start() throws Exception {
        final RouteMatcher routeMatcher = new RouteMatcher();

        routeMatcher.get("/videoresources/:page", new RestHandler());

        // Catch all - serve the index page
        routeMatcher.getWithRegEx(".*", new WebHandler());

        vertx.createHttpServer().requestHandler(
                routeMatcher).listen(PORT_NUMBER);
    }
}
