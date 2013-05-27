package com.pmk.twovidzoneclip.main;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import com.pmk.twovidzoneclip.handler.impl.RestHandlerImpl;
import com.pmk.twovidzoneclip.handler.impl.WebHandlerImpl;
import com.pmk.twovidzoneclip.injection.VidzUrlsModule;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.deploy.Verticle;

public final class VertxServer extends Verticle {

    private static final Integer PORT_NUMBER = 8182;

    @Override
    public final void start() throws Exception {
        final RouteMatcher routeMatcher = new RouteMatcher();

        Injector injector = Guice.createInjector(new VidzUrlsModule());

        routeMatcher.get("/videoresources/:page", injector.getInstance(RestHandler.class));

        // Catch all - serve the index page
        routeMatcher.getWithRegEx(".*", injector.getInstance(WebHandler.class));

        vertx.createHttpServer().requestHandler(
                routeMatcher).listen(PORT_NUMBER);
    }
}
