package webx.huceal.services;

import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Film;

import javax.json.JsonObject;
import javax.ws.rs.core.Response;

public class FilmService {

	private FilmDAO dao = new FilmDAO();

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return Response Json
	 */
	public Response findById(String id) {
		Response.Status status = Response.Status.BAD_REQUEST;
		JsonObject result = dao.findById(id);
		Object body = null;

		if (checkRequest(result)) {
			// Si le résultat de la requête est valide
			// On créer un objet film
			body = new Film(result.getString("imdbID"), result.getString("Title"),
					result.getString("Year"), result.getString("Poster"));

			// Et on change le status de la réponse
			status = Response.Status.OK;
		} else {
			// TODO message d'erreur
		}

		return Response.status(status).type("application/json").entity(body).build();
	}

	/**
	 * Récupère la liste des films correspondant aux critères de recherche
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return Response Json
	 */
	public Response findFilms(String titre, String annee) {
		Response.Status status = Response.Status.BAD_REQUEST;
		JsonObject result = null;
		Object body = null;

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
		body = result;

		return Response.status(status).entity(body).build();
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
	 * Vérifie la requête
	 * @param response le JsonObject à vérifier
	 * @return true si la réponse est valide, false sinon
	 */
	private boolean checkRequest(JsonObject response) {
		return response.getString("Response").equals("True");
	}

}
