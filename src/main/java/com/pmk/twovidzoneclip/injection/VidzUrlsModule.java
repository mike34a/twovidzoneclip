package com.pmk.twovidzoneclip.injection;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.impl.RestHandlerImpl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.persistence.impl.VidzUrlsDAOImpl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import com.pmk.twovidzoneclip.service.impl.VidzUrlsServiceImpl;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VidzUrlsModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(RestHandler.class).to(RestHandlerImpl.class);
        bind(VidzUrlsService.class).to(VidzUrlsServiceImpl.class);
    }

    @Provides
    VidzUrlsDAO provideVidzUrlsDao() {
        List<URI> urls = Lists.newArrayList(URI.create("http://127.0.0.1:8091/pools"));

        CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
        System.setProperty("viewmode", "development");
        cfb.setOpTimeout(10000);

        final CouchbaseConnectionFactory cf;
        try {
            cf = cfb.buildCouchbaseConnection(urls, "tvoc-videos-urls", "");
            CouchbaseClient couchbaseClient = new CouchbaseClient(cf);

            return new VidzUrlsDAOImpl(couchbaseClient);

        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
            throw new Error("Impossible de se connecter à la base de données");
        }
    }
}
