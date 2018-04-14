package webx.huceal.services;

import webx.huceal.ErrorMessage;
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
		// Empty
	}

	/**
	 * Création d'un nouveau service.
	 * @param dao le dao
	 */
	public FilmService(FilmDAO dao) {
		this.dao = dao;
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

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(body).build();
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
			} else {
				body = result;
				status = Response.Status.OK;
			}
		} else {
			body = new ErrorMessage("Titre invalide !");
		}

		return Response.status(status)
				.header("Access-Control-Allow-Origin", "*")
				.type(MediaType.APPLICATION_JSON).entity(body).build();
	}

	/**
	 * Récupère la liste des films correspondant aux critères de recherche.
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return Response Json
	 */
	public final Response findByTitleAndYear(final String titre, final String annee) {
		Response.Status status = Response.Status.BAD_REQUEST;
		Object body = null;

		if (titleIsValid(titre)) {
			if (yearIsValid(annee)) {
				List<Film> result = dao.findByTitleAndYear(titre, annee);
				if (result.equals(new ArrayList<Film>())) {
					body = new ErrorMessage("Aucun film trouvé !");
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

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(body).build();
	}

	/**
	 * Récupère la liste des films correspondant aux critères de recherche.
	 * @param note note minimal des films recherchés
	 * @param commentaire mot contenu dans les commentaires des films recherchés
	 * @return Response Json
	 */
	public final Response findByAvis(final String note, final String commentaire) {
		Response.Status status = Response.Status.BAD_REQUEST;
		Object body = null;

		if (noteIsValid(note)) {
			body = dao.findByAvis(note, commentaire);
			status = Response.Status.OK;
		} else {
			body = new ErrorMessage("Note invalide !");
		}

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(body).build();
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
