package com.pmk.twovidzoneclip.persistence.impl;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.CouchbaseConnectionFactory;
import com.couchbase.client.CouchbaseConnectionFactoryBuilder;
import com.couchbase.client.protocol.views.*;
import com.google.common.collect.Lists;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class VidzUrlsDAOImplTest {

    private CouchbaseClient couchbaseClient;

    private VidzUrlsDAOImpl vidzUrlsDAO;

    @Test
    public void should_find_results_for_the_first_page() {
        //GIVEN
        List<URI> urls = Lists.newArrayList(URI.create("http://127.0.0.1:8091/pools"));

        try {
            CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
            System.setProperty("viewmode", "development");
            cfb.setOpTimeout(10000);
            final CouchbaseConnectionFactory cf = cfb.buildCouchbaseConnection(urls, "tvoc-videos-urls", "");

            couchbaseClient = new CouchbaseClient(cf);
            vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

            //WHEN
            final ViewResponse viewRows = vidzUrlsDAO.vidzUrlsViewResponse(1, 10);

            //THEN
            assertThat(viewRows).isNotEmpty();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void should_find_no_results_for_the_tenth_page() {
        //GIVEN
        List<URI> urls = Lists.newArrayList(URI.create("http://127.0.0.1:8091/pools"));

        try {
            CouchbaseConnectionFactoryBuilder cfb = new CouchbaseConnectionFactoryBuilder();
            System.setProperty("viewmode", "development");
            cfb.setOpTimeout(10000);
            final CouchbaseConnectionFactory cf = cfb.buildCouchbaseConnection(urls, "tvoc-videos-urls", "");

            final CouchbaseClient couchbaseClient = new CouchbaseClient(cf);
            vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

            //WHEN
            final ViewResponse viewRows = vidzUrlsDAO.vidzUrlsViewResponse(10, 10);

            //THEN
            assertThat(viewRows).isNullOrEmpty();
        } catch (IOException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void should_build_object_correctly_from_json() {
        //GIVEN
        couchbaseClient = mock(CouchbaseClient.class);
        Mockito.when(couchbaseClient.query(any(AbstractView.class), any(Query.class))).thenReturn(mockedViewRows());

        vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

        final Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2013, 04, 27, 15, 36);
        final Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2013, 04, 26, 16, 45);
        final VidzUrl vidzUrl1 = new VidzUrl(calendar1, "lemonde.fr", "korben.info");
        final VidzUrl vidzUrl2 = new VidzUrl(calendar2, "google.fr", "youtube.com");

        //WHEN
        final List<VidzUrl> urls = vidzUrlsDAO.getUrls(1, 10);

        //THEN
        assertThat(urls).containsExactly(vidzUrl1, vidzUrl2);
    }

    @Test
    public void should_correctly_translate_str_to_calendar() {
        //GIVEN
        couchbaseClient = mock(CouchbaseClient.class);
        Mockito.when(couchbaseClient.query(any(AbstractView.class), any(Query.class))).thenReturn(mockedViewRows());

        final String dateStr = "201305271536";
        final Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(2013, 04, 27, 15, 36);
        vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

        //WHEN
        final Calendar calculatedDate = vidzUrlsDAO.dateFromStr(dateStr);

        //THEN
        assertThat(calculatedDate.getTime().toString()).isEqualTo(calendar1.getTime().toString());
    }

    private ViewResponse mockedViewRows() {
        final ViewRowNoDocs viewRowNoDocs1 = new ViewRowNoDocs("2", "201305271536", "{\"imageUrl\":\"lemonde.fr\",\"soundUrl\":\"korben.info\"}");
        final ViewRowNoDocs viewRowNoDocs2 = new ViewRowNoDocs("1", "201305261645", "{\"imageUrl\":\"google.fr\",\"soundUrl\":\"youtube.com\"}");

        final LinkedList<ViewRow> viewRowNoDocses = Lists.newLinkedList();
        viewRowNoDocses.add(viewRowNoDocs1);
        viewRowNoDocses.add(viewRowNoDocs2);
        return new ViewResponseNoDocs(viewRowNoDocses, Collections.<RowError>emptyList());
    }
}
