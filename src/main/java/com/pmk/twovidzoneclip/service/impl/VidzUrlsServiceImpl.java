package com.pmk.twovidzoneclip.service.impl;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.google.common.collect.Lists;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.persistence.impl.VidzUrlsDAOImpl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VidzUrlsServiceImpl implements VidzUrlsService {

    private VidzUrlsDAO vidzUrlsDAO;

    public VidzUrlsServiceImpl(VidzUrlsDAO vidzUrlsDAO) {
        this.vidzUrlsDAO = vidzUrlsDAO;
    }

    public VidzUrlsServiceImpl() { List<URI> urls = Lists.newArrayList(URI.create("http://127.0.0.1:8091/pools"));

            CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
            System.setProperty("viewmode", "development");
            cfb.setOpTimeout(10000);
        final CouchbaseConnectionFactory cf;
        try {
            cf = cfb.buildCouchbaseConnection(urls, "tvoc-videos-urls", "");
            CouchbaseClient couchbaseClient = new CouchbaseClient(cf);

            vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public final List<VidzUrl> findVidzUrls(final Integer page, Integer numberOfResults) {
        return vidzUrlsDAO.getUrls(page, numberOfResults);
    }
}
