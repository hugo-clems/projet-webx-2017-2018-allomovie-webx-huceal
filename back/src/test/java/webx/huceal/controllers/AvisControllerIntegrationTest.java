package webx.huceal.controllers;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import webx.huceal.Main;
import webx.huceal.dao.AvisDAO;
import webx.huceal.domains.Avis;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.equalTo;

public class AvisControllerIntegrationTest {

    private static HttpServer server;
    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target("http://localhost:8080/allomovie");
    private AvisDAO avisDAO = new AvisDAO();
    private Avis avis1 = new Avis("tt0080684", 5, "Très bon film.");
    private Avis avis2 = new Avis("tt0080684", -1, "J'adore ce film !");
    private Avis avis3 = new Avis("tt0080685", 3, "");

    @BeforeClass
    public static void startServer() {
        server = Main.startServer();
    }

    @AfterClass
    public static void stopServer() {
        server.shutdownNow();
    }

    @Before
    public void setUp() throws Exception {
        avis1.setId(avisDAO.addAvis(avis1.getFilmID(), avis1.getNote(), avis1.getCommentaire()));
        avis2.setId(avisDAO.addAvis(avis2.getFilmID(), avis2.getNote(), avis2.getCommentaire()));
        avis3.setId(avisDAO.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire()));
    }

    @After
    public void tearDown() throws Exception {
        if (avisDAO.findAvisByID(avis1.getId()) != null) {
            avisDAO.deleteAvisByID(avis1.getId());
        }
        if (avisDAO.findAvisByID(avis2.getId()) != null) {
            avisDAO.deleteAvisByID(avis2.getId());
        }
        if (avisDAO.findAvisByID(avis3.getId()) != null) {
            avisDAO.deleteAvisByID(avis3.getId());
        }
    }

    @Test
    public void deleteAvisByKeyWhenKeyExistsInCommentaires() {
        Response response = target.path("film")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .delete();
        Assert.assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    }

}
