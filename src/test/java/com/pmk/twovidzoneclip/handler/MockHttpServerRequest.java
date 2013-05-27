package com.pmk.twovidzoneclip.handler;

import com.google.common.collect.Maps;
import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.HttpServerResponse;

import java.util.List;
import java.util.Map;

public class MockHttpServerRequest extends HttpServerRequest {

    private Map<String, String> params;

    public static MockHttpServerRequest getNewHttpServerRequest (String path, HttpServerResponse response) {
        return new MockHttpServerRequest(null, null, path, null, null, response);
    }

    public static MockHttpServerRequest getNewHttpServerRequest (String path, Map params, HttpServerResponse response) {
        return new MockHttpServerRequest(null, null, path, params, null, response);
    }

    private MockHttpServerRequest(String method, String uri, String path, Map params, String query, HttpServerResponse response) {
        super(method, uri, path, query, response);
        this.params = params;
    }

    @Override
    public Map<String, String> headers() {
        return null;
    }

    @Override
    public Map<String, String> params() {
        return Maps.newHashMap(params);
    }

    @Override
    public void dataHandler(Handler<Buffer> handler) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void exceptionHandler(Handler<Exception> handler) {
    }

    @Override
    public void endHandler(Handler<Void> endHandler) {
    }
}
