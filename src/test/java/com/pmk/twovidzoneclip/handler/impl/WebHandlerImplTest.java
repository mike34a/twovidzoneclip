package com.pmk.twovidzoneclip.handler.impl;

import org.junit.Before;
import org.junit.Test;
import org.vertx.java.core.http.HttpServerResponse;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class WebHandlerImplTest {

    private HttpServerResponse response;

    private WebHandlerImpl webHandler;

    @Before
    public void setUp() {
        response = mock(HttpServerResponse.class);
        webHandler = new WebHandlerImpl();
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
