package webx.huceal.services;

import webx.huceal.ErrorMessage;
import webx.huceal.controllers.AvisController;
import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Avis;

import javax.json.Json;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

/**
 * Le service des Avis.
 */
public class AvisService {

    /**
     * La DAO d'Avis.
     */
    private AvisDAO avisDAO = new AvisDAO();
    /**
     * La DAO de Film.
     */
    private FilmDAO filmDAO = new FilmDAO();

    public AvisService() {
        // empty
    }

    public AvisService(AvisDAO avisDAO, FilmDAO filmDAO) {
        this.avisDAO = avisDAO;
        this.filmDAO = filmDAO;
    }

    /**
     * Appelle la DAO pour ajouter un avis valide.
     * @param filmID l'id du film pour quel est émis l'avis
     * @param note la note donnée, -1 si elle n'est pas renseignée
     * @param commentaire le commentaire donné, peut être vide si note existe
     * @return Response Json avec la localisation de la ressource
     */
    public final Response addAvis(final String filmID, final int note, final String commentaire, UriInfo uriInfo) {
        final int COMMENTAIRE_MAX_LENGTH = 500;
        Response.Status status = Response.Status.BAD_REQUEST;
        ErrorMessage erreur = new ErrorMessage();
        if (!verifyFilmID(filmID)) {
            erreur.setMessage("L'id du film n'est pas valide.");
        } else if (!verifyNote(note)) {
            erreur.setMessage("La note donnée n'est pas valide.");
        } else if (commentaire.length() > COMMENTAIRE_MAX_LENGTH) {
            erreur.setMessage("Le commentaire est trop long.");
        } else if (note == -1 && commentaire.isEmpty()) {
            erreur.setMessage("Il faut au moins une note ou un commentaire pour déposer un avis.");
        } else {
            long id = avisDAO.addAvis(filmID, note, commentaire);
            if (id == -1) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                erreur.setMessage("Problème de connexion avec la base de données.");
            } else {
                UriBuilder ub = uriInfo.getBaseUriBuilder();
                URI uri = ub.path(AvisController.class)
                        .path(String.valueOf(id))
                        .build();
                status = Response.Status.CREATED;
                return Response.status(status)
                        .header("Location", uri)
                        .build();
            }
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(erreur)
                .build();
    }

    /**
     * Appelle la DAO pour trouver tous les avis d'un film identifié par son id.
     * @param filmID l'id du film donné
     * @return ResponseJson avec la liste des avis du film trouvés
     */
    public final Response findAllAvisByFilmID(final String filmID) {
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

    /**
     * Appelle la DAO pour trouver un avis via son identifiant.
     * @param avisID l'id de l'avis
     * @return Response Json avec l'avis demandé s'il existe
     */
    public final Response findAvisByID(final long avisID) {
        Response.Status status = Response.Status.OK;
        Object entity = avisDAO.findAvisByID(avisID);
        if (entity == null) {
            status = Response.Status.NOT_FOUND;
            entity = new ErrorMessage("L'id de l'avis demandé n'existe pas.");
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

    /**
     * Appelle la DAO pour supprimer tous les avis contenant le mot-clé donné dans leur commentaire.
     * @param key le mot-clé
     * @return Response Json avec le nombre de commentaires supprimés
     */
    public final Response deleteAvisByKey(final String key) {
        Response.Status status = Response.Status.OK;
        ErrorMessage erreur = new ErrorMessage();
        Object entity = erreur;
        int affectedRows;
        if (key == null || key.isEmpty() || key.contains(" ")) {
            status = Response.Status.BAD_REQUEST;
            erreur.setMessage("Le mot-clé ne doit pas être vide ou contenir d'espace.");
        } else {
            affectedRows = avisDAO.deleteAvisByKey(key);
            if (affectedRows == 0) {
                status = Response.Status.NOT_FOUND;
                erreur.setMessage("Aucun commentaire à supprimer n'a été trouvé.");
            } else {
                entity = Json.createObjectBuilder().add("affectedRows", affectedRows).build();
            }
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

    /**
     * Vérifie que la note donnée est valide.
     * @param note la note reçue
     * @return Boolean à true si la note est valide, false sinon
     */
    public boolean verifyNote(final int note) {
        boolean ok = true;
        if (note < -1 || note > 5) {
            ok = false;
        }
        return ok;
    }

    /**
     * Vérifie que l'id du film donné est valide.
     * @param filmID l'id du film reçu
     * @return Boolean à true si l'id est valide, false sinon
     */
    public final boolean verifyFilmID(final String filmID) {
        final int filmIDLength = 9;
        boolean ok = true;
        if (filmID == null) {
            ok = false;
        } else if (filmID.length() != filmIDLength || filmDAO.findById(filmID) == null) {
            ok = false;
        }
        return ok;
    }

}
