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
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @Ignore("Should be realised with a flat file database")
    public void should_find_results_for_the_first_page() {
        //WHEN
        final ViewResponse viewRows = vidzUrlsDAO.vidzUrlsViewResponse(1, 10);

        //THEN
        assertThat(viewRows).isNotEmpty();
    }

    @Test
    @Ignore("Should be realised with a flat file database")
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
        when(couchbaseClient.query(any(AbstractView.class), any(Query.class))).thenReturn(mockedViewRows());

        vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

        final Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(2013, 04, 26, 15, 36);
        final Calendar calendar2 = GregorianCalendar.getInstance();
        calendar2.set(2013, 04, 27, 16, 46);

        final VidzUrl vidzUrl1 = new VidzUrl(calendar1.getTime(), "XsoTIvDxgZI", "FE0XcdM22Yo","Scatman Rabbi Jacob");
        final VidzUrl vidzUrl2 = new VidzUrl(calendar2.getTime(), "fCWNMWQzoz0", "dxZqHff44Js","La soupe aux choux Israelienne");

        //WHEN
        final List<VidzUrl> urls = vidzUrlsDAO.getUrlsForTheNthPage(1, 10);

        //THEN
        assertThat(urls).containsExactly(vidzUrl1, vidzUrl2);
    }

    @Test
    public void should_correctly_translate_str_to_calendar_when_date_format_is_correct() {
        //GIVEN
        couchbaseClient = mock(CouchbaseClient.class);

        final String dateStr = "201305271536";
        final Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(2013, 04, 27, 15, 36);
        vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

        //WHEN
        final Date calculatedDate = vidzUrlsDAO.dateFromStr(dateStr);

        //THEN
        assertThat(calculatedDate.toString()).isEqualTo(calendar1.getTime().toString());
    }

    @Test
    public void should_return_null_when_date_format_is_not_correct() {
        //GIVEN
        couchbaseClient = mock(CouchbaseClient.class);

        final String dateStr1 = null;
        final String dateStr2 = "654321";

        final Calendar calendar1 = GregorianCalendar.getInstance();
        calendar1.set(2013, 04, 27, 15, 36);
        vidzUrlsDAO = new VidzUrlsDAOImpl(couchbaseClient);

        //WHEN
        final Date calculatedDate1 = vidzUrlsDAO.dateFromStr(dateStr1);
        final Date calculatedDate2 = vidzUrlsDAO.dateFromStr(dateStr2);

        //THEN
        assertThat(calculatedDate1).isNull();
        assertThat(calculatedDate2).isNull();
    }

    private ViewResponse mockedViewRows() {
        final ViewRowNoDocs viewRowNoDocs1 = new ViewRowNoDocs("1", "201305261536", "{\"imageUrl\":\"XsoTIvDxgZI\",\"soundUrl\":\"FE0XcdM22Yo\",\"title\":\"Scatman Rabbi Jacob\"}");
        final ViewRowNoDocs viewRowNoDocs2 = new ViewRowNoDocs("2", "201305271646", "{\"imageUrl\":\"fCWNMWQzoz0\",\"soundUrl\":\"dxZqHff44Js\",\"title\":\"La soupe aux choux Israelienne\"}");
        final ViewRowNoDocs viewRowNoDocs3 = new ViewRowNoDocs("3", "20130527", "{\"imageUrl\":\"fCWNMWQzoz0\",\"soundUrl\":\"dxZqHff44Js\",\"title\":\"La soupe aux choux Israelienne\"}");

        final LinkedList<ViewRow> viewRowNoDocses = Lists.newLinkedList();
        viewRowNoDocses.add(viewRowNoDocs1);
        viewRowNoDocses.add(viewRowNoDocs2);
        viewRowNoDocses.add(viewRowNoDocs3);
        return new ViewResponseNoDocs(viewRowNoDocses, Collections.<RowError>emptyList());
    }
}
