package com.pmk.twovidzoneclip.main;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Created with IntelliJ IDEA.
 * User: dolounet
 * Date: 30/04/13
 * Time: 07:58
 * To change this template use File | Settings | File Templates.
 */
public class ServerRunner extends ServerResource {
    public static void main(String args []) throws Exception {
        new Server(Protocol.HTTP, 8182, ServerRunner.class).start();
    }

    @Get
    public String toString() {
        return "Hello World !";
    }
}
