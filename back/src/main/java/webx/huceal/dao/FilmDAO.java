package webx.huceal.dao;

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
	public JsonObject findById(String id) {
		return executeRequest("&i=" + id);
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

}
