package org.kartevonmorgen.backend;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.kartevonmorgen.backend.domain.Address;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;
import java.util.List;

public class TestAddressResource {

    private HttpServer server;
    private WebTarget target;

    
    @Before
    public void setUp() throws Exception {
        
	// start the server
	
        // create a resource config that scans for JAX-RS resources and providers
        // in org.kartevonmorgen.backend package
        final ResourceConfig rc = new ResourceConfig().packages("org.kartevonmorgen.backend");

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        server = GrizzlyHttpServerFactory.createHttpServer(URI.create(Config.BASE_URI), rc);
	
        // create the client
        Client c = ClientBuilder.newClient();

        target = c.target(Config.BASE_URI);
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }

    /**
     * 
     */
	@Test
	public void testAddressResource() {
		Address in = new Address();
		in.firstName = "Fred";
		in.lastName = "Hauschel";
		in.street = "Linnen";

		javax.ws.rs.core.Response response = target.path("addresses").request()
				.post(Entity.entity(in, MediaType.APPLICATION_JSON));
		
		Address address = response.readEntity(Address.class);
		Address responseMsg = target.path("addresses/" + address.id).request().get(Address.class);

		assertEquals(in.firstName, responseMsg.firstName);
		assertEquals(in.lastName, responseMsg.lastName);
		assertEquals(in.street, responseMsg.street);
		assertTrue(responseMsg.id > 0);
		
		target.path("addresses/" + responseMsg.id).request().delete();
		
		Response listResponse = target.path("addresses/list").request().get();
		List<Address> addresses = listResponse.readEntity(new GenericType<List<Address>>(){});
		assertTrue( addresses.isEmpty() );
	}
	
//	@Test
//	public void testDelete() {
//
//		
//		Response responseMsg = target.path("addresses/list").request().get();
//		
//		List<Address> addresses = responseMsg.readEntity(new GenericType<List<Address>>(){});
//		
//		for( Address adr : addresses )
//		{
//			System.out.println("deleting " + adr.getId()); 
//			javax.ws.rs.core.Response response = target.path("addresses/" + adr.getId()).request().delete();
//		}
//	}
	
	
}
