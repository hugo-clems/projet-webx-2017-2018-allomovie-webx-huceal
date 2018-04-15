package webx.huceal.dao;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.*;
import webx.huceal.DataSource;
import webx.huceal.Main;
import webx.huceal.domains.Avis;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class AvisDAOUnitTest {

    private static HttpServer server;
    private AvisDAO avisDAO = new AvisDAO();
    private Avis avis1 = new Avis("tt0120916", 5, "Très bon film. Xrs.");
    private Avis avis2 = new Avis("tt0120916", -1, "J'adore ce film ! Xrs.");
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
    public void deleteAvisByIdWhenIdIsValid() {
        long validId = avis1.getId();
        assertThat(avisDAO.deleteAvisByID(validId), is(true));
    }

    @Test
    public void deleteAvisByIdWhenIdDoesntExist() {
        long invalidId = -1;
        assertThat(avisDAO.deleteAvisByID(invalidId), is(false));
    }

    @Test
    public void deleteAvisByKeyWhenKeyExistsInCommentaires() {
        String existingKey = "Xrs";
        assertThat(avisDAO.deleteAvisByKey(existingKey), is(2));
    }

    @Test
    public void deleteAvisByKeyWhenKeyDoesNotExistInCommentaires() {
        String falseKey = "coucou";
        assertThat(avisDAO.deleteAvisByKey(falseKey), is(0));
    }

    @Test
    public void findAvisByIdWhenIdIsValid() {
        long validId = avis1.getId();
        assertThat(avisDAO.findAvisByID(validId), is(avis1));
    }

    @Test
    public void findAvisByIdWhenIdIsInvalid() {
        long invalidId = -1;
        assertThat(avisDAO.findAvisByID(invalidId), is(nullValue()));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndCommentairesExist() {
        String validId = avis1.getFilmID();
        List<Avis> liste = new ArrayList<>();
        liste.add(avis1);
        liste.add(avis2);
        assertThat(avisDAO.findAllAvisByFilmID(validId), is(liste));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsValidAndCommentairesDoesNotExist() {
        String validId = "tt0080686";
        List<Avis> liste = new ArrayList<>();
        assertThat(avisDAO.findAllAvisByFilmID(validId), is(liste));
    }

    @Test
    public void findAllAvisByFilmIdWhenFilmIdIsInvalid() {
        // does not have to respect regex -> will just return empty list because it is a search
        // works the same for too long, too short, not right regex...
        String tooLongId = "tt00806869";
        List<Avis> liste = new ArrayList<>();
        assertThat(avisDAO.findAllAvisByFilmID(tooLongId), is(liste));
    }

    @Test
    public void addAvisWhenAvisIsValidWithNoteAndCommentaire() {
        long newId = avis3.getId() + 1;
        assertThat(avisDAO.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire()), is(newId));
        avisDAO.deleteAvisByID(newId);
    }

    @Test
    public void addAvisWhenAvisIsValidWithNoteOnly() {
        long newId = avis3.getId() + 1;
        assertThat(avisDAO.addAvis(avis3.getFilmID(), avis3.getNote(), ""), is(newId));
        avisDAO.deleteAvisByID(newId);
    }

    @Test
    public void addAvisWhenAvisIsValidWithCommentaireOnly() {
        long newId = avis3.getId() + 1;
        avis3.setCommentaire("Bien.");
        assertThat(avisDAO.addAvis(avis3.getFilmID(), -1, avis3.getCommentaire()), is(newId));
        avisDAO.deleteAvisByID(newId);
    }

    @Test
    public void addAvisWhenNoteIsTooHigh() {
        assertThat(avisDAO.addAvis(avis3.getFilmID(), 6, avis3.getCommentaire()), is(-1L));
    }

    @Test
    public void addAvisWhenNoteIsTooLow() {
        assertThat(avisDAO.addAvis(avis3.getFilmID(), -2, avis3.getCommentaire()), is(-1L));
    }

    @Test
    public void addAvisWhenCommentaireIsTooLong() {
        String comTooLong = "Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long."
                + " Le commentaire est trop long. Le commentaire est trop long. Le commentaire est trop long.";
        assertThat(avisDAO.addAvis(avis3.getFilmID(), avis3.getNote(), comTooLong), is(-1L));
    }

    @Test
    public void addAvisWhenNoNoteAndNoCommentaire() {
        assertThat(avisDAO.addAvis(avis3.getFilmID(), -1, ""), is(-1L));
    }

    @Test
    public void addAvisWhenFilmIdIsTooShort() {
        assertThat(avisDAO.addAvis("tt008068", avis1.getNote(), avis1.getCommentaire()), is(-1L));
    }

    @Test
    public void addAvisWhenFilmIdIsTooLong() {
        assertThat(avisDAO.addAvis("tt00806865", avis1.getNote(), avis1.getCommentaire()), is(-1L));
    }

    @Test
    public void addAvisWhenFilmIdDoesNotRespectRegex() {
        assertThat(avisDAO.addAvis("120080686", avis1.getNote(), avis1.getCommentaire()), is(-1L));
    }

    @Test
    public void findAllFilmsWithAtLeastOneNoteByFilmIDWhenExist() {
        List<String> liste = new ArrayList<>();
        liste.add(avis3.getFilmID());
        liste.add(avis1.getFilmID());
        assertThat(avisDAO.findAllFilmsWithAtLeastOneNoteByFilmID(), is(liste));
    }

    @Test
    public void findAllFilmsWithAtLeastOneNoteByFilmIDWhenNoFilm() {
        avisDAO.deleteAvisByID(avis1.getId());
        avisDAO.deleteAvisByID(avis3.getId());
        List<String> liste = new ArrayList<>();
        assertThat(avisDAO.findAllFilmsWithAtLeastOneNoteByFilmID(), is(liste));
    }

    @Test
    public void findAllAvisWhenExist() {
        List<Avis> liste = new ArrayList<>();
        liste.add(avis1);
        liste.add(avis2);
        liste.add(avis3);
        assertThat(avisDAO.findAllAvis(), is(liste));
    }

    @Test
    public void findAllAvisWhenNoAvis() {
        avisDAO.deleteAvisByID(avis1.getId());
        avisDAO.deleteAvisByID(avis2.getId());
        avisDAO.deleteAvisByID(avis3.getId());
        List<Avis> liste = new ArrayList<>();
        assertThat(avisDAO.findAllAvis(), is(liste));
    }

}
