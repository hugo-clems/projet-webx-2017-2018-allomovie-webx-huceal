package webx.huceal.services;

import webx.huceal.ErrorMessage;
import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Avis;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class AvisService {

    private AvisDAO avisDAO = new AvisDAO();
    private FilmDAO filmDAO = new FilmDAO();

    public Response addAvis(String filmID, int note, String commentaire) {
        final int COMMENTAIRE_MAX_LENGTH = 500;
        Response.Status status = Response.Status.CREATED;
        ErrorMessage erreur = new ErrorMessage();
        if (!verifyFilmID(filmID)) {
            status = Response.Status.BAD_REQUEST;
            erreur.setMessage("L'id du film n'est pas valide.");
        } else if (!verifyNote(note)) {
            status = Response.Status.BAD_REQUEST;
            erreur.setMessage("La note donnée n'est pas valide.");
        } else if (commentaire.length() > COMMENTAIRE_MAX_LENGTH) {
            status = Response.Status.BAD_REQUEST;
            erreur.setMessage("Le commentaire est trop long.");
        } else {
            long id = avisDAO.addAvis(filmID, note, commentaire);
            if (id == -1) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                erreur.setMessage("Problème de connexion avec la base de données.");
            } else {
                return Response.status(status)
                        .header("Location", "/avis/" + id)
                        .build();
            }
        }
        return Response.status(status)
                .entity(erreur)
                .build();
    }

    public Response findAllAvisByFilmID(String filmID) {
        Response.Status status = Response.Status.OK;
        List<Avis> liste;
        ErrorMessage erreur = new ErrorMessage();
        Object entity = erreur;
        if (!verifyFilmID(filmID)) {
            status = Response.Status.BAD_REQUEST;
            erreur.setMessage("L'id du film n'est pas valide.");
        } else {
            liste = avisDAO.findAllAvisByFilmID(filmID);
            if (liste.isEmpty()) {
                status = Response.Status.NOT_FOUND;
                erreur.setMessage("Il n'y a pas d'avis pour ce film.");
            } else {
                entity = liste;
            }
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

    public Response findAvisByID(String avisID) {
        Response.Status status = Response.Status.OK;
        Object avis = avisDAO.findAvisByID(avisID);
        if (avis == null) {
            status = Response.Status.NOT_FOUND;
            avis = new ErrorMessage("L'id de l'avis demandé n'existe pas.");
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(avis)
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
        final int FILM_ID_LENGTH = 9;
        boolean ok = true;
        if (filmID == null) {
            ok = false;
        } else if (filmID.length() != FILM_ID_LENGTH || filmDAO.findById(filmID) == null) {
            ok = false;
        }
        return ok;
    }

}
