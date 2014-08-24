package com.pmk.twovidzoneclip.handler.impl;

import com.google.gson.Gson;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.injection.VidzUrlsTestingModule;
import com.pmk.twovidzoneclip.service.VidzUrlsService;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vertx.java.core.http.HttpServerResponse;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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

}
