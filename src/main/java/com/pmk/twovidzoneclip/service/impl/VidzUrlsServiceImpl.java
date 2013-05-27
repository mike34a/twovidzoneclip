package com.pmk.twovidzoneclip.service.impl;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.google.common.collect.Lists;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import com.pmk.twovidzoneclip.persistence.impl.VidzUrlsDAOImpl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VidzUrlsServiceImpl implements VidzUrlsService {

    private VidzUrlsDAO vidzUrlsDAO;

    @Inject
    public VidzUrlsServiceImpl(VidzUrlsDAO vidzUrlsDAO) {
        this.vidzUrlsDAO = vidzUrlsDAO;
    }

    @Override
    public final List<VidzUrl> findVidzUrls(final Integer page, Integer numberOfResults) {
        return vidzUrlsDAO.getUrls(page, numberOfResults);
    }
}
