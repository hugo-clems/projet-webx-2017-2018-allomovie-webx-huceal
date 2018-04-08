package webx.huceal.services;

import webx.huceal.ErrorMessage;
import webx.huceal.dao.FilmDAO;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class FilmService {

	private FilmDAO dao = new FilmDAO();

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return Response Json
	 */
	public Response findById(String id) {
		Response.Status status = Response.Status.OK;
		Object body = dao.findById(id);

		if (body == null) {
			status = Response.Status.BAD_REQUEST;
			body = new ErrorMessage("Identifiant invalide !");
		}

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(body).build();
	}

	/**
	 * Récupère la liste des films correspondant aux critères de recherche
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return Response Json
	 */
	public Response findByTitleAndOrYear(String titre, String annee) {
		Response.Status status = Response.Status.BAD_REQUEST;
		Object body = null;

		if (titleIsValid(titre)) {
			if (annee != null && annee != "") {
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
	 * Récupère la liste des films correspondant aux critères de recherche
	 * @param note note minimal des films recherchés
	 * @param commentaire mot contenu dans les commentaires des films recherchés
	 * @return Response Json
	 */
	public Response findByNoteAndComment(String note, String commentaire) {
		Response.Status status = Response.Status.BAD_REQUEST;
		Object body = null;

		if (noteIsValid(note)) {
			body = dao.findByNoteAndComment(note, commentaire);
			status = Response.Status.OK;
		} else {
			body = new ErrorMessage("Note invalide !");
		}

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(body).build();
	}

	/**
	 * Vérifie si un titre est valide
	 * (Non Null et d'une taille minimale de 4 caractères)
	 * @param titre le titre à valider
	 * @return boolean
	 */
	private boolean titleIsValid(String titre) {
		return titre != null && titre.length() > 3;
	}

	/**
	 * Vérifie si une année est valide
	 * (Est un entier)
	 * @param annee l'année à valider
	 * @return boolean
	 */
	private boolean yearIsValid(String annee) {
		try {
			Integer.parseInt(annee);
		} catch (NumberFormatException err) {
			return false;
		}
		return true;
	}

	/**
	 * Vérifie si la note est valide
	 * (Est un entier entre 0 et 5)
	 * @param note la note à vérifier
	 * @return boolean
	 */
	private boolean noteIsValid(String note) {
		try {
			int i = Integer.parseInt(note);
			return i >= 0 && i <= 5;
		} catch (NumberFormatException err) {
			return false;
		}
	}

}
