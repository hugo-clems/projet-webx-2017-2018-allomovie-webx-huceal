package webx.huceal.dao;

import webx.huceal.domains.Film;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

	/**
	 * Lien d'accès à l'API OMDB
	 */
	private final String BASE_URL = "http://www.omdbapi.com/?apikey=5a0f558e";

	/**
	 * Récupère la liste des films correpondant au titre renseigné
	 * @param titre titre du film recherché
	 * @return liste des films en Json
	 */
	public JsonArray findByTitle(String titre) {
		return executeRequest("&s=" + titre);
	}

	/**
	 * Exécute la requête passé en pramètre
	 * @param url la requête
	 * @return résultat de la requête en Json
	 */
	private JsonArray executeRequest(String url) {
		return ClientBuilder.newClient()
				.target(BASE_URL + url)
				.request()
				.get(JsonObject.class)
				.getJsonArray("Search");
	}

}
