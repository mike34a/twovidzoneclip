package com.pmk.twovidzoneclip.handler.impl;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.pmk.twovidzoneclip.handler.MockHttpServerRequest;
import com.pmk.twovidzoneclip.handler.RestHandler;
import com.pmk.twovidzoneclip.injection.VidzUrlsTestingModule;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.vertx.java.core.http.HttpServerResponse;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestHandlerImplTest {

    private RestHandlerImpl handler;

    private HttpServerResponse response;

    private static Injector injector;

    @BeforeClass
    public static void staticSetUp() {
        injector = Guice.createInjector(new VidzUrlsTestingModule());
    }

    @Before
    public void setUp() {
        response = mock(HttpServerResponse.class);
        handler = (RestHandlerImpl)injector.getInstance(RestHandler.class);
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
    public void should_send_resource_web_file_on_web_handle_call_when_file_exists() {
        //GIVEN
        final MockHttpServerRequest mockHttpServerRequest = MockHttpServerRequest.getNewHttpServerRequest("index.html", response);

        final String sendFileCalledMessage = "sendFile was sent";
        when(response.sendFile("webroot/index.html")).thenThrow(new RuntimeException(sendFileCalledMessage));

        //WHEN
        try {
            handler.handle(mockHttpServerRequest);

            //THEN
            Assert.fail("No file was sent.");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(sendFileCalledMessage);
        }
    }

}
