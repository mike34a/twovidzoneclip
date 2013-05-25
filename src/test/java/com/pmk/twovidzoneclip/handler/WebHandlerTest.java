package com.pmk.twovidzoneclip.handler;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.Handler;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebHandlerTest {

    private HttpServerResponse response;

    @Before
    public void setUp() {
        response = mock(HttpServerResponse.class);
    }

    @Test
    public void should_send_resource_web_file_on_web_handle_call() {
        //GIVEN
        final Handler<HttpServerRequest> webHandler = new WebHandler();
        final MockHttpServerRequest mockHttpServerRequest = MockHttpServerRequest.getNewHttpServerRequest("index.html", response);

        final String sendFileCalledMessage = "sendFile was sent";
        when(response.sendFile("webroot/index.html")).thenThrow(new RuntimeException(sendFileCalledMessage));

        //WHEN
        try {
            webHandler.handle(mockHttpServerRequest);

            //THEN
            Assert.fail("No file was sent.");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(sendFileCalledMessage);
        }
    }

    @Test
    public void should_send_indexhtml_on_web_handle_call_for_root_context() {
        //GIVEN
        final Handler<HttpServerRequest> webHandler = new WebHandler();
        final MockHttpServerRequest mockHttpServerRequest = MockHttpServerRequest.getNewHttpServerRequest("/", response);

        final String sendFileCalledMessage = "sendFile was sent";
        when(response.sendFile("webroot/index.html")).thenThrow(new RuntimeException(sendFileCalledMessage));

        //WHEN
        try {
            webHandler.handle(mockHttpServerRequest);

            //THEN
            Assert.fail("No file was sent.");
        } catch (RuntimeException e) {
            assertThat(e.getMessage()).isEqualTo(sendFileCalledMessage);
        }
    }
}
