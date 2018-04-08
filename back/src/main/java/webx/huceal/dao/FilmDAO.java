package webx.huceal.dao;

import webx.huceal.domains.Film;

import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;

public class FilmDAO {

	/**
	 * Lien d'accès à l'API OMDB
	 */
	private final String BASE_URL = "http://www.omdbapi.com/?apikey=5a0f558e&type=movie";

	/**
	 * Récupère la liste des films correpondant au titre renseigné
	 * @param titre titre du film recherché
	 * @return liste des films en Json
	 */
	public JsonObject findByTitle(String titre) {
		return executeRequest("&s=" + titre);
	}

	/**
	 * Récupère la liste des films correpondant au titre et à l'année renseigné
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return liste des films en Json
	 */
	public JsonObject findByTitleAndYear(String titre, String annee) {
		return executeRequest("&s=" + titre + "&y=" + annee);
	}

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return le film en Json
	 */
	public Film findById(String id) {
		JsonObject result = executeRequest("&i=" + id);
		Film leFilm = null;

		if (checkRequest(result)) {
			leFilm = new Film(result.getString("imdbID"),
					result.getString("Title"),
					result.getString("Year"),
					result.getString("Runtime"),
					result.getString("Genre"),
					result.getString("Production"),
					result.getString("Director"),
					result.getString("Writer"),
					result.getString("Actors"),
					result.getString("Plot"),
					result.getString("Poster"));
		}

		return leFilm;
	}

	/**
	 * Exécute la requête passé en pramètre
	 * @param url la requête
	 * @return résultat de la requête en Json
	 */
	private JsonObject executeRequest(String url) {
		return ClientBuilder.newClient()
				.target(BASE_URL + url)
				.request()
				.get(JsonObject.class);
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
