package com.pmk.twovidzoneclip.persistence.impl;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.google.common.annotations.VisibleForTesting;
import com.google.gson.Gson;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;

import java.util.*;

public class VidzUrlsDAOImpl implements VidzUrlsDAO {

    private final CouchbaseClient couchbaseClient;

    private final View urlsByDateView;

    private final Gson gson;

    public static final String URLS_BY_DATE_VIEW_NAME = "tvoc_urls_bydate";

    public VidzUrlsDAOImpl(final CouchbaseClient couchbaseClient) {
        this.couchbaseClient = couchbaseClient;
        urlsByDateView = couchbaseClient.getView("bydate", URLS_BY_DATE_VIEW_NAME);
        gson = new Gson();
    }

    public List<VidzUrl> getUrls(final Integer page, final Integer numberOfResults) {
        final ViewResponse viewResponse = vidzUrlsViewResponse(page, numberOfResults);

        final List<VidzUrl> results = new LinkedList<>();
        for (ViewRow viewRow : viewResponse) {
            final VidzUrl vidzUrl = gson.fromJson(viewRow.getValue(), VidzUrl.class);
            vidzUrl.setDate(dateFromStr(viewRow.getKey()));
            results.add(vidzUrl);
        }

        return results;
    }

    @VisibleForTesting
    ViewResponse vidzUrlsViewResponse(final Integer page, final Integer numberOfResults) {
        final Query getLimitedResultsQuery = new Query();
        final int numberSkipped = (page - 1) * numberOfResults;

        getLimitedResultsQuery.setLimit(numberOfResults);
        getLimitedResultsQuery.setDescending(true);
        getLimitedResultsQuery.setSkip(numberSkipped);

        return couchbaseClient.query(urlsByDateView, getLimitedResultsQuery);
    }

    @VisibleForTesting
    Date dateFromStr(String dateStr) {
        final String year = dateStr.substring(0, 4);
        final String month = dateStr.substring(4, 6);
        final String day = dateStr.substring(6, 8);
        final String hour = dateStr.substring(8, 10);
        final String minute = dateStr.substring(10, 12);

        final Calendar calendar = GregorianCalendar.getInstance();

        calendar.set(Integer.parseInt(year), (Integer.parseInt(month) - 1), Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));

        return calendar.getTime();
    }
}
