package webx.huceal.services;

import webx.huceal.ErrorMessage;
import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Film;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Le service de Film.
 */
public class FilmService {

    /**
     * Le DAO de Film.
     */
    private FilmDAO dao = new FilmDAO();

    /**
     * Service Avis.
     */
    private AvisService avisService;

    /**
     * Note minimale.
     */
    private static final int NOTE_MIN = 0;

    /**
     * Note maximale.
     */
    private static final int NOTE_MAX = 5;

    /**
     * Taille d'un titre.
     */
    private static final int TAILLE_TITRE = 4;

    /**
     * Taille d'un String d'une année.
     */
    private static final int TAILLE_ANNEE = 4;

    /**
     * Constructeur par défaut.
     */
    public FilmService() {
        avisService = new AvisService();
    }

    /**
     * Création d'un nouveau service.
     * @param filmDAO la dao des films
     * @param avisDAO la dao des avis
     */
    public FilmService(final FilmDAO filmDAO, final AvisDAO avisDAO) {
        this.dao = filmDAO;
        this.avisService = new AvisService(avisDAO, filmDAO);
    }

    /**
     * Récupère le film correspondant à l'identifiant.
     * @param id identifiant du film recherché
     * @return Response Json
     */
    public final Response findById(final String id) {
        Response.Status status = Response.Status.OK;
        Object body = dao.findById(id);

        if (body == null) {
            status = Response.Status.BAD_REQUEST;
            body = new ErrorMessage("Identifiant invalide !");
        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).entity(body).build();
    }

    /**
     * Récupère la liste des films correspondant aux critères de recherche.
     * @param titre titre du film recherché
     * @return Response Json
     */
    public final Response findByTitle(final String titre) {
        Response.Status status = Response.Status.BAD_REQUEST;
        Object body = null;

        if (titleIsValid(titre)) {
            List<Film> result = dao.findByTitle(titre);
            if (result.equals(new ArrayList<Film>())) {
                body = new ErrorMessage("Aucun film trouvé !");
                status = Response.Status.NOT_FOUND;
            } else {
                body = result;
                status = Response.Status.OK;
            }
        } else {
            body = new ErrorMessage("Titre invalide !");
        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).entity(body).build();
    }

    /**
     * Récupère la liste des films correspondant aux critères de recherche.
     * @param titre titre du film recherché
     * @param annee année du film recherché
     * @return Response Json
     */
    public final Response findByTitleAndYear(final String titre,
                                             final String annee) {
        Response.Status status = Response.Status.BAD_REQUEST;
        Object body = null;

        if (titleIsValid(titre)) {
            if (yearIsValid(annee)) {
                List<Film> result = dao.findByTitleAndYear(titre, annee);
                if (result.equals(new ArrayList<Film>())) {
                    body = new ErrorMessage("Aucun film trouvé !");
                    status = Response.Status.NOT_FOUND;
                } else {
                    body = result;
                    status = Response.Status.OK;
                }
            } else {
                body = new ErrorMessage("Année invalide !");
            }
        } else {
            body = new ErrorMessage("Titre invalide !");
        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).entity(body).build();
    }

    /**
     * Récupère la liste des films correspondant aux critères de recherche.
     * @param note note minimal des films recherchés
     * @param commentaire mot contenu dans les commentaires des films recherchés
     * @return Response Json
     */
    public final Response findByAvis(final String note,
                                     final String commentaire) {
        Response.Status status = Response.Status.BAD_REQUEST;
        Object body = null;

        if (noteIsValid(note)) {
            body = fusionList(getFilmWithMinNote(note),
                    dao.findByAvis(commentaire));
            status = Response.Status.OK;
        } else {
            body = new ErrorMessage("Note invalide !");
        }

        return Response.status(status)
                .type(MediaType.APPLICATION_JSON).entity(body).build();
    }

    /**
     * Récupère la liste des films
     * ayant une moyenne supérieur à une note donéne.
     * @param note moyenne minimale
     * @return liste des films
     */
    private List<Film> getFilmWithMinNote(final String note) {
        List<Film> result = new ArrayList<>();
        List<String> filmWithNote;

        // On récupère tous les films qui ont des avis
        filmWithNote = avisService.findAllFilmsWithAtLeastOneNoteByFilmID();

        // Pour chaque film avec un avis
        // Si sa moyenne est supérieur à la note
        // Alors on l'ajoute à la liste
        for (int i = 0; i < filmWithNote.size(); i++) {
            String filmId = filmWithNote.get(i);
            float moy = avisService.findSeuilNoteByFilmID(filmId);
            if (moy >= Integer.parseInt(note)) {
                result.add(dao.findById(filmId));
            }
        }

        return result;
    }

    /**
     * Fusione 2 listes.
     * @param l1 liste à fusioner
     * @param l2 liste à fusioner
     * @return listes fusionées
     */
    private List<Film> fusionList(final List<Film> l1, final List<Film> l2) {
        List<Film> result = new ArrayList<>();

        // Pour chaque film de l2
        // S'il est présent dans l1
        // On l'ajoute à résult
        for (int i = 0; i < l2.size(); i++) {
            Film f = l2.get(i);
            if (l1.contains(f)) {
                result.add(f);
            }
        }

        return result;
    }

    /**
     * Vérifie si un titre est valide.
     * (Taille minimale de 4 caractères)
     * @param titre le titre à valider
     * @return boolean
     */
    private boolean titleIsValid(final String titre) {
        return titre.length() >= TAILLE_TITRE;
    }

    /**
     * Vérifie si une année est valide.
     * (Est un entier)
     * @param annee l'année à valider
     * @return boolean
     */
    private boolean yearIsValid(final String annee) {
        try {
            Integer.parseInt(annee);
            return annee.length() == TAILLE_ANNEE;
        } catch (NumberFormatException err) {
            return false;
        }
    }

    /**
     * Vérifie si la note est valide.
     * (Est un entier entre 0 et 5)
     * @param note la note à vérifier
     * @return boolean
     */
    private boolean noteIsValid(final String note) {
        try {
            int i = Integer.parseInt(note);
            return i >= NOTE_MIN && i <= NOTE_MAX;
        } catch (NumberFormatException err) {
            return false;
        }
    }

}
