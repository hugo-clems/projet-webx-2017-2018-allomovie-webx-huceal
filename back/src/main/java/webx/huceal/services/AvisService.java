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

    /**
     * Taille maximale d'un commentaire.
     */
    private static final int COMMENTAIRE_MAX_LENGTH = 500;

    /**
     * Taille d'un filmId.
     */
    private static final int FILMID_LENGTH = 9;

    /**
     * Note minimale.
     */
    private static final int NOTE_MIN = -1;

    /**
     * Note maximale.
     */
    private static final int NOTE_MAX = 5;

    /**
     * Message d'erreur.
     */
    private static final String MSG_NOTE_NOT_VALID
            = "La note donnée n'est pas valide.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_COMMENT_TO_LONG
            = "Le commentaire est trop long.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_REQUIRED
            = "Il faut au moins une note "
            + "ou un commentaire pour déposer un avis.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_ERROR_DB
            = "Problème de connexion avec la base de données.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_FILMID_NOT_VALID
            = "L'id du film n'est pas valide.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_NO_AVIS_FOUND
            = "Il n'y a pas d'avis pour ce film.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_AVISID_NOT_EXIST
            = "L'id de l'avis demandé n'existe pas.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_KEY_MUST_NO_EMPTY =
            "Le mot-clé ne doit pas être vide ou contenir d'espace.";

    /**
     * Message d'erreur.
     */
    private static final String MSG_NO_COMMENT_TO_DELETE =
            "Aucun commentaire à supprimer n'a été trouvé.";


    /**
     * Constructeur par défaut.
     */
    public AvisService() {
        // empty
    }

    /**
     * Création d'un nouveau service.
     * @param newAvisDAO nouveau avisDAO
     * @param newFilmDAO nouveau filmDAO
     */
    public AvisService(final AvisDAO newAvisDAO, final FilmDAO newFilmDAO) {
        this.avisDAO = newAvisDAO;
        this.filmDAO = newFilmDAO;
    }

    /**
     * Appelle la DAO pour ajouter un avis valide.
     * @param filmID l'id du film pour quel est émis l'avis
     * @param note la note donnée, -1 si elle n'est pas renseignée
     * @param commentaire le commentaire donné, peut être vide si note existe
     * @param uriInfo uriInfo
     * @return Response Json avec la localisation de la ressource
     */
    public final Response addAvis(final String filmID,
                                  final int note,
                                  final String commentaire,
                                  final UriInfo uriInfo) {
        Response.Status status = Response.Status.BAD_REQUEST;
        ErrorMessage erreur = new ErrorMessage();
        if (!verifyFilmID(filmID)) {
            erreur.setMessage(MSG_FILMID_NOT_VALID);
        } else if (!verifyNote(note)) {
            erreur.setMessage(MSG_NOTE_NOT_VALID);
        } else if (commentaire.length() > COMMENTAIRE_MAX_LENGTH) {
            erreur.setMessage(MSG_COMMENT_TO_LONG);
        } else if (note == -1 && commentaire.isEmpty()) {
            erreur.setMessage(MSG_REQUIRED);
        } else {
            long id = avisDAO.addAvis(filmID, note, commentaire);
            if (id == -1) {
                status = Response.Status.INTERNAL_SERVER_ERROR;
                erreur.setMessage(MSG_ERROR_DB);
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
            erreur.setMessage(MSG_FILMID_NOT_VALID);
        } else {
            liste = avisDAO.findAllAvisByFilmID(filmID);
            if (liste.isEmpty()) {
                status = Response.Status.NOT_FOUND;
                erreur.setMessage(MSG_NO_AVIS_FOUND);
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
            entity = new ErrorMessage(MSG_AVISID_NOT_EXIST);
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

    /**
     * Appelle la DAO pour supprimer tous les avis
     * contenant le mot-clé donné dans leur commentaire.
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
            erreur.setMessage(MSG_KEY_MUST_NO_EMPTY);
        } else {
            affectedRows = avisDAO.deleteAvisByKey(key);
            if (affectedRows == 0) {
                status = Response.Status.NOT_FOUND;
                erreur.setMessage(MSG_NO_COMMENT_TO_DELETE);
            } else {
                entity = Json.createObjectBuilder()
                        .add("affectedRows", affectedRows).build();
            }
        }
        return Response.status(status)
                .type(MediaType.APPLICATION_JSON)
                .entity(entity)
                .build();
    }

    /**
     * Recherche la moyenne des avis d'un film.
     * @param filmID l'id du film
     * @return la moyenne des avis du film en float, -1 s'il n'y en a pas
     */
    public final float findSeuilNoteByFilmID(final String filmID) {
        float moyenne = -1, div = 0, nb = 0;
        int note = 0;
        List<Avis> liste = avisDAO.findAllAvisByFilmID(filmID);
        if (!liste.isEmpty()) {
            for (Avis avis : liste) {
                note = avis.getNote();
                if (note != -1) {
                    nb += note;
                    div++;
                }
            }
            if (nb != 0 && div != 0) {
                moyenne = nb / div;
            }
        }
        return moyenne;
    }

    /**
     * Vérifie que la note donnée est valide.
     * @param note la note reçue
     * @return Boolean à true si la note est valide, false sinon
     */
    public final boolean verifyNote(final int note) {
        boolean ok = true;
        if (note < NOTE_MIN || note > NOTE_MAX) {
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
        boolean ok = true;
        if (filmID == null) {
            ok = false;
        } else if (filmID.length() != FILMID_LENGTH
                || filmDAO.findById(filmID) == null) {
            ok = false;
        }
        return ok;
    }

}
