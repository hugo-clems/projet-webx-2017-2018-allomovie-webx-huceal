package webx.huceal.controllers;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import webx.huceal.DataSource;
import webx.huceal.ErrorMessage;
import webx.huceal.Main;
import webx.huceal.dao.AvisDAO;
import webx.huceal.domains.Avis;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AvisControllerIntegrationTest {

    private static HttpServer server;
    private Response response;
    private ErrorMessage erreur = new ErrorMessage();
    private Client client = ClientBuilder.newClient();
    private WebTarget target = client.target("http://localhost:8080/allomovie/avis");
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
    public void deleteAvisByKeyWhenKeyIsValidAndExistsInCommentaires() {
        response = target.path("film")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        JsonObject json = Json.createObjectBuilder()
                .add("affectedRows", 2)
                .build();
        assertThat(response.readEntity(JsonObject.class), is(json));
    }

    @Test
    public void deleteAvisByKeyWhenKeyIsValidAndDoesntExistsInCommentaires() {
        response = target.path("blabla")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Aucun commentaire à supprimer n'a été trouvé.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void deleteAvisByKeyWhenKeyIsInvalid() {
        response = target.path(" ")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .delete();
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Le mot-clé ne doit pas être vide ou contenir d'espace.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void findAvisByIdWhenIdIsValid() {
        response = target.path(String.valueOf(avis3.getId()))
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.readEntity(Avis.class), is(avis3));
    }

    @Test
    public void findAvisByIdWhenIdIsInvalid() {
        response = target.path("-1")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id de l'avis demandé n'existe pas.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndAvisExists() {
        response = target.path("film")
                .path(avis1.getFilmID())
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        List<Avis> liste = new ArrayList<>();
        liste.add(avis1);
        liste.add(avis2);
        assertThat(response.readEntity(new GenericType<List<Avis>>() {}), is(liste));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndAvisDoesntExist() {
        response = target.path("film")
                .path("tt0080688")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Il n'y a pas d'avis pour ce film.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsInvalid() {
        response = target.path("film")
                .path("123456")
                .request()
                .accept(MediaType.APPLICATION_JSON)
                .get();
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id du film n'est pas valide.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void addAvisWhenAvisIsValid() {
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.entity(avis3, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(response.hasEntity(), is(false));
        UriBuilder uri = UriBuilder.fromUri("http://localhost:8080/allomovie/avis")
                .path(String.valueOf(avis3.getId() + 1));
        assertThat(response.getLocation().toString(), is(uri.toString()));
        avisDAO.deleteAvisByID(avis3.getId() + 1);
    }

    @Test
    public void addAvisWhenFilmIdIsInvalid() {
        avis1.setFilmID("123456");
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.entity(avis1, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id du film n'est pas valide.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void addAvisWhenNoteIsInvalid() {
        avis1.setNote(7);
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.entity(avis1, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("La note donnée n'est pas valide.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void addAvisWhenCommentaireIsTooLong() {
        avis1.setCommentaire("Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long.");
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.entity(avis1, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Le commentaire est trop long.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

    @Test
    public void addAvisWhenNoNoteAndNoCommentaire() {
        Avis avisVide = new Avis(avis1.getFilmID(), -1, "");
        response = target.request()
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .post(Entity.entity(avisVide, MediaType.APPLICATION_JSON));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Il faut au moins une note ou un commentaire pour déposer un avis.");
        assertThat(response.readEntity(ErrorMessage.class), is(erreur));
    }

}
