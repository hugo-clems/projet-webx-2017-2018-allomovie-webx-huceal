package webx.huceal.services;

import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Film;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class FilmService {

	private FilmDAO dao = new FilmDAO();

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return Response Json
	 */
	public Response findById(String id) {
		Response.Status status = Response.Status.OK;
		Film result = dao.findById(id);

		if (result == null) {
			status = Response.Status.BAD_REQUEST;
		}

		return Response.status(status).type(MediaType.APPLICATION_JSON).entity(result).build();
	}

	/**
	 * Récupère la liste des films correspondant aux critères de recherche
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return Response Json
	 */
	public Response findFilms(String titre, String annee) {
		Response.Status status = Response.Status.BAD_REQUEST;
		List<Film> result = null;

		if (titleIsValid(titre)) {
			if (annee != null) {
				if (yearIsValid(annee)) {
					result = dao.findByTitleAndYear(titre, annee);
					status = Response.Status.OK;
				} else {
					// TODO message d'erreur
				}
			} else {
				result = dao.findByTitle(titre);
				status = Response.Status.OK;
			}
		} else {
			// TODO message d'erreur
		}

		return Response.status(status).entity(result).build();
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

}
