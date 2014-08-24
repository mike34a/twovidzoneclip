package com.pmk.twovidzoneclip.injection;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.pmk.twovidzoneclip.environment.DBAccess;
import com.pmk.twovidzoneclip.exception.DbException;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.handler.WebHandler;
import com.pmk.twovidzoneclip.handler.impl.RestHandlerImpl;
import com.pmk.twovidzoneclip.handler.impl.WebHandlerImpl;
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
        bind(WebHandler.class).to(WebHandlerImpl.class);
        bind(VidzUrlsService.class).to(VidzUrlsServiceImpl.class);
    }

    @Provides
    VidzUrlsDAO provideVidzUrlsDao() {

        final String addrStr = DBAccess.getDBUrl();
        final String dbPort = DBAccess.getDbPort();
        final String dbPassword = DBAccess.getDbPassword();

        final String uriStr = addrStr != null && dbPort != null ?
                String.format("http://%s:%s/pools", addrStr, dbPort) :
                "http://127.0.0.1:8091/pools";

        final URI uri = URI.create(uriStr);
        List<URI> urls = Lists.newArrayList(uri);

        CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();

        cfb.setOpTimeout(10000);

        final CouchbaseConnectionFactory cf;
        try {
            cf = cfb.buildCouchbaseConnection(urls, "tvoc-videos-urls", dbPassword);
            CouchbaseClient couchbaseClient = new CouchbaseClient(cf);

            return new VidzUrlsDAOImpl(couchbaseClient);

        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "Could not access to the database", e);
            throw new DbException();
        }
    }

}
