package webx.huceal;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

/**
 * Class de lancement de l'application.
 */
public final class Main {

    /**
     * URI de base de l'application.
     */
    public static final String BASE_URI = "http://localhost:8081/allomovie";

    /**
     * Constructeur privé.
     */
    private Main() {
        // not called
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("webx.huceal");
        rc.register(new CORSFilter());
        return GrizzlyHttpServerFactory
                .createHttpServer(URI.create(BASE_URI), rc);
    }

    /**
     * Méthode de lancement de l'application.
     * @param args non utilisé
     * @throws IOException if an I/O exception of some sort has occurred
     */
    public static void main(final String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println(String.format("Lien API %s/application.wadl"
                + "%nHit enter to stop it...", BASE_URI));
        System.in.read();
        server.shutdownNow();
    }

}
