package webx.huceal.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webx.huceal.ErrorMessage;
import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Avis;
import webx.huceal.domains.Film;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class AvisServiceUnitTest {

    @Mock
    private AvisDAO avisDAO;
    @Mock
    private FilmDAO filmDAO;
    @Mock
    private UriInfo uriInfo;
    private Response response;
    private ErrorMessage erreur = new ErrorMessage();
    private AvisService avisService;
    private Film film = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
            "1980", "124 min", "Action, Adventure, Fantasy",
            "Twentieth Century Fox", "Irvin Kershner",
            "Leigh Brackett (screenplay by), Lawrence Kasdan (screenplay by), George Lucas (story by)",
            "Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams",
            "After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.",
            "https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg");
    private Avis avis1 = new Avis("tt0080684", 5, "Très bon film.");
    private Avis avis2 = new Avis("tt0080684", -1, "J'adore ce film !");
    private Avis avis3 = new Avis("tt0080685", 3, "");

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        avisService = new AvisService(avisDAO, filmDAO);
    }

    @Test
    public void verifyFilmIdWhenIdIsValid() {
        when(filmDAO.findById(film.getFilmId())).thenReturn(film);
        assertThat(avisService.verifyFilmID(film.getFilmId()), is(true));
    }

    @Test
    public void verifyFilmIdWhenIdIsInvalid() {
        // filmID is null
        assertThat(avisService.verifyFilmID(null), is(false));
        // filmID is too long
        assertThat(avisService.verifyFilmID(film.getFilmId().concat("2")), is(false));
        // filmID is too short
        assertThat(avisService.verifyFilmID(film.getFilmId().substring(1)), is(false));
        // filmID doesn't exist
        when(filmDAO.findById(anyString())).thenReturn(null);
        assertThat(avisService.verifyFilmID("tt0000000"), is(false));
    }

    @Test
    public void verifyNoteWhenNoteIsValid() {
        // note is valid (between 0 and 5)
        assertThat(avisService.verifyNote(0), is(true));
        assertThat(avisService.verifyNote(5), is(true));
        // note not entered = -1 (optionnal param)
        assertThat(avisService.verifyNote(-1), is(true));
    }

    @Test
    public void verifyNoteWhenNoteIsInvalid() {
        // note is too high
        assertThat(avisService.verifyNote(6), is(false));
        // note is too low
        assertThat(avisService.verifyNote(-2), is(false));
    }

    @Test
    public void deleteAvisByKeyWhenAvisIsValidAndExistsInCommentaires() {
        final int affectedRows = 2;
        when(avisDAO.deleteAvisByKey(anyString())).thenReturn(affectedRows);
        response = avisService.deleteAvisByKey("bien");
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        JsonObject json = Json.createObjectBuilder()
                .add("affectedRows", affectedRows)
                .build();
        assertThat((JsonObject) response.getEntity(), is(json));
    }

    @Test
    public void deleteAvisByKeyWhenKeyIsValidAndDoesntExistsInCommentaires() {
        when(avisDAO.deleteAvisByKey(anyString())).thenReturn(0);
        response = avisService.deleteAvisByKey("nope");
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Aucun commentaire à supprimer n'a été trouvé.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void deleteAvisByKeyWhenKeyIsInvalid() {
        // key is null
        response = avisService.deleteAvisByKey(null);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Le mot-clé ne doit pas être vide ou contenir d'espace.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
        // key is empty
        response = avisService.deleteAvisByKey("");
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
        // key has space inside
        response = avisService.deleteAvisByKey("hello world");
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void findAvisByIdWhenIdIsValid() {
        when(avisDAO.findAvisByID(anyInt())).thenReturn(avis3);
        response = avisService.findAvisByID(avis3.getId());
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat((Avis) response.getEntity(), is(avis3));
    }

    @Test
    public void findAvisByIdWhenIdIsInvalid() {
        when(avisDAO.findAvisByID(anyInt())).thenReturn(null);
        response = avisService.findAvisByID(anyInt());
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id de l'avis demandé n'existe pas.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndAvisExists() {
        when(filmDAO.findById(anyString())).thenReturn(film);
        List<Avis> liste = new ArrayList<>();
        liste.add(avis1);
        liste.add(avis2);
        when(avisDAO.findAllAvisByFilmID(avis1.getFilmID())).thenReturn(liste);
        response = avisService.findAllAvisByFilmID(avis1.getFilmID());
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat((List<Avis>) response.getEntity(), is(liste));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndAvisDoesntExist() {
        when(filmDAO.findById(anyString())).thenReturn(film);
        when(avisDAO.findAllAvisByFilmID(anyString())).thenReturn(new ArrayList<Avis>());
        response = avisService.findAllAvisByFilmID("tt0080686");
        assertThat(response.getStatus(), is(Response.Status.NOT_FOUND.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Il n'y a pas d'avis pour ce film.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsInvalid() {
        when(filmDAO.findById(anyString())).thenReturn(null);
        response = avisService.findAllAvisByFilmID("tt1234567");
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id du film n'est pas valide.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void addAvisWhenAvisIsValid() {
        UriBuilder uriBuilder = UriBuilder.fromUri("http://localhost:8080/allomovie/avis");
        when(filmDAO.findById(anyString())).thenReturn(film);
        when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
        // note is between 0 and 5 (entered) and commentaire is not empty
        when(avisDAO.addAvis(avis1.getFilmID(), avis1.getNote(), avis1.getCommentaire())).thenReturn(avis3.getId());
        response = avisService.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(response.hasEntity(), is(false));
        assertThat(response.getLocation().toString(), is(uriBuilder.toString()));
        // note is not entered (= -1, optionnal parameter) and commentaire is not empty
        when(avisDAO.addAvis(avis1.getFilmID(), -1, avis1.getCommentaire())).thenReturn(avis3.getId());
        response = avisService.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(response.hasEntity(), is(false));
        assertThat(response.getLocation().toString(), is(uriBuilder.toString()));
        // note is between 0 and 5 (entered) and commentaire empty (optionnal parameter)
        when(avisDAO.addAvis(avis1.getFilmID(), avis1.getNote(), "")).thenReturn(avis3.getId());
        response = avisService.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(response.hasEntity(), is(false));
        assertThat(response.getLocation().toString(), is(uriBuilder.toString()));

    }

    @Test
    public void addAvisWhenFilmIdIsInvalid() {
        when(filmDAO.findById(anyString())).thenReturn(null);
        response = avisService.addAvis(avis1.getFilmID(), avis1.getNote(), avis1.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("L'id du film n'est pas valide.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void addAvisWhenNoteIsInvalid() {
        when(filmDAO.findById(anyString())).thenReturn(film);
        // note is too high
        response = avisService.addAvis(avis1.getFilmID(), 6, avis1.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("La note donnée n'est pas valide.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
        // note is too low
        response = avisService.addAvis(avis1.getFilmID(), -2, avis1.getCommentaire(), uriInfo);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void addAvisWhenCommentaireIsTooLong() {
        when(filmDAO.findById(anyString())).thenReturn(film);
        String comTooLong = "Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long.";
        response = avisService.addAvis(avis1.getFilmID(), avis1.getNote(), comTooLong, uriInfo);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Le commentaire est trop long.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

    @Test
    public void addAvisWhenNoNoteAndNoCommentaire() {
        when(filmDAO.findById(anyString())).thenReturn(film);
        response = avisService.addAvis(avis1.getFilmID(), -1, "", uriInfo);
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));
        assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
        erreur.setMessage("Il faut au moins une note ou un commentaire pour déposer un avis.");
        assertThat((ErrorMessage) response.getEntity(), is(erreur));
    }

}
