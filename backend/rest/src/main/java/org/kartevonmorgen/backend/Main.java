package org.kartevonmorgen.backend;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;

import org.flywaydb.core.Flyway;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main class.
 *
 */
public class Main
{

    public static void main(String[] args) throws IOException {
	flywayMigrate();
	URI BASE_URI = URI.create(Config.BASE_URI);

	try
	{
	    System.out.println("JSON with MOXy Jersey Example App");

	    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp(), false);
	    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
	    {
		@Override
		public void run() {
		    server.shutdownNow();
		}
	    }));
	    server.start();

	    System.out.println(String.format("Application started.%nStop the application using CTRL+C"));

	    Thread.currentThread().join();
	} catch (IOException | InterruptedException ex)
	{
	    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public static ResourceConfig createApp() {
	return new ResourceConfig().packages("org.kartevonmorgen.backend").register(createMoxyJsonResolver());
    }

    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
	final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
	Map<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
	namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
	moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
	return moxyJsonConfig.resolver();
    }

    public static void flywayMigrate() {
	// Create the Flyway instance
	Flyway flyway = new Flyway();

	// Point it to the database
	flyway.setDataSource(Config.DB_NAME, null, null);

	// Start the migration
	flyway.migrate();
    }
}
