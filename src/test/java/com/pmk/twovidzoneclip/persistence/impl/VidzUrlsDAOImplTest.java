package com.pmk.twovidzoneclip.persistence.impl;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.*;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.injection.VidzUrlsTestingModule;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.persistence.VidzUrlsDAO;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

public class VidzUrlsDAOImplTest {

    private CouchbaseClient couchbaseClient;

    private static VidzUrlsDAOImpl vidzUrlsDAO;

    private static Injector injector;

    @BeforeClass
    public static void staticSetUp() {
        injector = Guice.createInjector(new VidzUrlsTestingModule());
        vidzUrlsDAO = (VidzUrlsDAOImpl)injector.getProvider(VidzUrlsDAO.class).get();
    }

    @Test
    public void should_find_results_for_the_first_page() {
        //WHEN
        final ViewResponse viewRows = vidzUrlsDAO.vidzUrlsViewResponse(1, 10);

        //THEN
        assertThat(viewRows).isNotEmpty();
    }

    @Test
    public void should_find_no_results_for_the_tenth_page() {
        //WHEN
        final ViewResponse viewRows = vidzUrlsDAO.vidzUrlsViewResponse(10, 10);

        //THEN
        assertThat(viewRows).isNullOrEmpty();
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
        final VidzUrl vidzUrl1 = new VidzUrl(calendar1.getTime(), "lemonde.fr", "korben.info");
        final VidzUrl vidzUrl2 = new VidzUrl(calendar2.getTime(), "google.fr", "youtube.com");

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
        final Date calculatedDate = vidzUrlsDAO.dateFromStr(dateStr);

        //THEN
        assertThat(calculatedDate.toString()).isEqualTo(calendar1.getTime().toString());
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
