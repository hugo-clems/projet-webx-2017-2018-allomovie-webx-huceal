package webx.huceal.services;

import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Avis;

import javax.ws.rs.core.Response;
import java.util.List;

public class AvisService {

    private AvisDAO avisDAO = new AvisDAO();
    private FilmDAO filmDAO = new FilmDAO();
    private final int FILM_ID_LENGTH = 9;
    private final int COMMENTAIRE_MAX_LENGTH = 500;

    public Response addAvis(String filmID, int note, String commentaire) {
        Response.Status status = Response.Status.CREATED;
        if (!verifyFilmID(filmID) || !verifyNote(note) || commentaire.length() > COMMENTAIRE_MAX_LENGTH) {
            status = Response.Status.BAD_REQUEST;
        } else {
            long id = avisDAO.addAvis(filmID, note, commentaire);
            if (id == -1) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
            } else {
                System.out.println(id);
            }
        }
        return Response.status(status)
                .build();
    }

    public Response findAllAvisByFilmID(String filmID) {
        Response.Status status = Response.Status.OK;
        if (!verifyFilmID(filmID)) {
            status = Response.Status.BAD_REQUEST;
        }
        List<Avis> liste = avisDAO.findAllAvisByFilmID(filmID);
        if (liste.isEmpty()) {
            status = Response.Status.NOT_FOUND;
        }
        return Response.status(status)
                .type("application/json")
                .entity(liste)
                .build();
    }

    private boolean verifyNote(int note) {
        boolean ok = true;
        if (note < -1 || note > 5) {
            ok = false;
        }
        return ok;
    }

    private boolean verifyFilmID(String filmID) {
        boolean ok = true;
        if (filmID == null && filmID.length() != FILM_ID_LENGTH || filmDAO.findById(filmID) == null) {
            ok = false;
        }
        return ok;
    }

}
