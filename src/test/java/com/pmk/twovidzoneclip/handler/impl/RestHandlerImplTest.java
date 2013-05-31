package com.pmk.twovidzoneclip.handler.impl;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.handler.MockHttpServerRequest;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.injection.VidzUrlsTestingModule;
import com.pmk.twovidzoneclip.metier.VidzUrl;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.vertx.java.core.http.HttpServerResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestHandlerImplTest {

    private RestHandlerImpl handler;

    private HttpServerResponse response;

    private VidzUrlsService mockService;

    private Gson gson;

    private static Injector injector;

    @BeforeClass
    public static void staticSetUp() {
        injector = Guice.createInjector(new VidzUrlsTestingModule());
    }

    @Before
    public void setUp() {
        response = mock(HttpServerResponse.class);
        mockService = mock(VidzUrlsService.class);
        handler = (RestHandlerImpl)injector.getInstance(RestHandler.class);
        gson = new Gson();
    }

    @Test
    public void should_not_valid_string_with_chars_other_than_numbers() {
        //GIVEN
        final String testedStr = "12Bon34";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isFalse();
    }

    @Test
    public void should_not_valid_negative_number() {
        //GIVEN
        final String testedStr = "-12";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isFalse();
    }

    @Test
    public void should_valid_positive_numbers() {
        //GIVEN
        final String testedStr = "456";

        //WHEN
        final boolean isNumber = handler.checkPageNumberFormat(testedStr);

        //THEN
        assertThat(isNumber).isTrue();
    }

    @Test
    public void should_find_links_for_request_well_formatted() {
        //GIVEN
        final HashMap<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("numberOfResults", "10");

        final MockHttpServerRequest mockHttpServerRequest = MockHttpServerRequest.getNewHttpServerRequest("/videoresources", params, response);

        final String sendFileCalledMessage = "links correctly written";

        final VidzUrl vidzUrl1 = new VidzUrl(new Date(), "image1", "sound1","title1");
        final VidzUrl vidzUrl2 = new VidzUrl(new Date(), "image2", "sound2","title2");
        final ArrayList<VidzUrl> vidzsLists = Lists.newArrayList(vidzUrl1, vidzUrl2);

        final String gsonResults = gson.toJson(vidzsLists);

        when(mockService.findVidzUrls(Mockito.anyInt(), Mockito.anyInt())).thenReturn(vidzsLists);
        when(response.write(gsonResults)).thenThrow(new RuntimeException(sendFileCalledMessage));

        //WHEN
        try {
            handler.vidzUrlsService = this.mockService;
            handler.handle(mockHttpServerRequest);

            //THEN
            Assert.fail("No file was sent.");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(sendFileCalledMessage);
        }
    }

    @Test
    public void should_return_empty_string_for_bad_request() {
        //GIVEN
        final HashMap<String, String> params = new HashMap<>();
        params.put("page", "bla");
        params.put("numberOfResults", "10");

        final MockHttpServerRequest mockHttpServerRequest = MockHttpServerRequest.getNewHttpServerRequest("/videoresources/", params, response);

        final String sendFileCalledMessage = "no links written";

        final VidzUrl vidzUrl1 = new VidzUrl(new Date(), "image1", "sound1","title1");
        final VidzUrl vidzUrl2 = new VidzUrl(new Date(), "image2", "sound2","title2");
        final ArrayList<VidzUrl> vidzsLists = Lists.newArrayList(vidzUrl1, vidzUrl2);

        when(mockService.findVidzUrls(Mockito.anyInt(), Mockito.anyInt())).thenReturn(vidzsLists);
        when(response.write("")).thenThrow(new RuntimeException(sendFileCalledMessage));

        //WHEN
        try {
            handler.vidzUrlsService = this.mockService;
            handler.handle(mockHttpServerRequest);

            //THEN
            Assert.fail("No write should be made");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(sendFileCalledMessage);
        }
    }
}
