package com.pmk.twovidzoneclip.handler;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.http.HttpServerResponse;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WebHandlerTest {

    private HttpServerResponse response;

    private WebHandler webHandler;

    @Before
    public void setUp() {
        response = mock(HttpServerResponse.class);
        webHandler = new WebHandler();
    }

    @Test
    public void should_send_resource_web_file_on_web_handle_call() {
        //GIVEN
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

    @Test
    public void should_return_false_when_file_does_not_exist() {
        //GIVEN
        final String existingPath = "webroot/blabla.php";

        //WHEN
        final boolean fileExists = webHandler.fileExists(existingPath);

        //THEN
        assertThat(fileExists).isFalse();

    }

    @Test
    public void should_return_true_when_file_exists() {
        //GIVEN
        final String existingPath = "webroot/index.html";

        //WHEN
        final boolean fileExists = webHandler.fileExists(existingPath);

        //THEN
        assertThat(fileExists).isTrue();
    }
}
