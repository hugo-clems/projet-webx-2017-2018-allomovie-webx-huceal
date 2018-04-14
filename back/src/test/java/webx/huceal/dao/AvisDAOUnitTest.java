package webx.huceal.dao;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import webx.huceal.DataSource;
import webx.huceal.Main;
import webx.huceal.domains.Avis;

public class AvisDAOUnitTest {

    private static HttpServer server;
    private AvisDAO avisDAO = new AvisDAO();
    private Avis avis1 = new Avis("tt0080684", 5, "Très bon film.");
    private Avis avis2 = new Avis("tt0080684", -1, "J'adore ce film !");
    private Avis avis3 = new Avis("tt0080685", 3, "");

    @BeforeClass
    public static void startServer() {
        DataSource.setDbConnection("jdbc:h2:~/allomovieDBTests");
        server = Main.startServer();
    }

    @AfterClass
    public static void stopServer() {
        server.shutdownNow();
    }



}
