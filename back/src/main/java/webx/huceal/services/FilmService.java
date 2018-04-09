package webx.huceal.services;

import webx.huceal.ErrorMessage;
import webx.huceal.dao.FilmDAO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	private final int noteMin = 0;

	/**
	 * Note maximale.
	 */
	private final int noteMax = 5;

	/**
	 * Taille d'un String d'une année.
	 */
	private final int tailleAnnee = 4;

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
	 * @param annee année du film recherché
	 * @return Response Json
	 */
	public final Response findList(final String titre, final String annee) {
		Response.Status status = Response.Status.BAD_REQUEST;
		Object body = null;

		if (titleIsValid(titre)) {
			if (!annee.equals("")) {
				if (yearIsValid(annee)) {
					body = dao.findByTitleAndYear(titre, annee);
					status = Response.Status.OK;
				} else {
					body = new ErrorMessage("Année invalide !");
				}
			} else {
				body = dao.findByTitle(titre);
				status = Response.Status.OK;
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
		return titre.length() > 3;
		// TODO gérer too many results mieux que ça
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
			return annee.length() == tailleAnnee;
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
			return i >= noteMin && i <= noteMax;
		} catch (NumberFormatException err) {
			return false;
		}
	}

}
