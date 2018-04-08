package webx.huceal.dao;

import webx.huceal.domains.Film;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

	/**
	 * Lien d'accès à l'API OMDB
	 */
	private final String BASE_URL = "http://www.omdbapi.com/?apikey=5a0f558e&type=movie";

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return le film
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
	 * Récupère la liste des films correpondant au titre renseigné
	 * @param titre titre du film recherché
	 * @return liste des films
	 */
	public List<Film> findByTitle(String titre) {
		return createListFilms(executeRequest("&s=" + titre));
	}

	/**
	 * Récupère la liste des films correpondant au titre et à l'année renseigné
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return liste des films
	 */
	public List<Film> findByTitleAndYear(String titre, String annee) {
		return createListFilms(executeRequest("&s=" + titre + "&y=" + annee));
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

	/**
	 * Créer une liste de films à partir du résultat de la requête
	 * @param result résultat de la requête
	 * @return liste des films
	 */
	private List<Film> createListFilms(JsonObject result) {
		ArrayList<Film> lesFilms = new ArrayList<>();
		if (checkRequest(result)) {
			JsonArray listJson = result.getJsonArray("Search");
			for (int i = 0; i < listJson.size(); i++) {
				JsonObject elt = listJson.getJsonObject(i);
				lesFilms.add(new Film(elt.getString("imdbID"),
						elt.getString("Title"),
						elt.getString("Year"),
						elt.getString("Poster")));
			}
		}
		return lesFilms;
	}

}
