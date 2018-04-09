package webx.huceal.dao;

import webx.huceal.DataSource;
import webx.huceal.domains.Film;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * La DAO du Film.
 */
public class FilmDAO {

	/**
	 * Lien d'accès à l'API OMDB.
	 */
	private static final String BASE_URL = "http://www.omdbapi.com/?apikey=5a0f558e&type=movie";

	/**
	 * Récupère le film correspondant à l'identifiant.
	 * @param id identifiant du film recherché
	 * @return le film
	 */
	public final Film findById(final String id) {
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
	 * Récupère la liste des films correpondant au titre renseigné.
	 * @param titre titre du film recherché
	 * @return liste des films
	 */
	public final List<Film> findByTitle(final String titre) {
		return createListFilms(executeRequest("&s=" + titre));
	}

	/**
	 * Récupère la liste des films correpondant au titre et à l'année renseigné.
	 * @param titre titre du film recherché
	 * @param annee année du film recherché
	 * @return liste des films
	 */
	public final List<Film> findByTitleAndYear(final String titre, final String annee) {
		return createListFilms(executeRequest("&s=" + titre + "&y=" + annee));
	}

	/**
	 * Récupère la liste des films correpondant à la note et/ou un mot dans le commentaire.
	 * @param note note minimal des films recherchés
	 * @param commentaire mot contenu dans les commentaires des films recherchés
	 * @return liste des films
	 */
	public final List<Film> findByAvis(final String note, final String commentaire) {
		List<Film> listFilm = new ArrayList<>();
		List<String> listId = new ArrayList<>();
		Connection con = null;
		Statement stmt = null;
		String query = "SELECT FilmID FROM Avis WHERE Note >= " + note
				+ "AND LOWER(Commentaire) LIKE '%" + commentaire.toLowerCase() + "%'";

		// On récupère la liste des ID des films ayant une note supérieur à @param
		try {
			con = DataSource.getDBConnection();
			stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(query);
			while (res.next()) {
				listId.add(res.getString("FilmID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConAndStmt(con, stmt);
		}

		// On récupère l'objet film pour chaque film présent dans la liste d'ID
		for (int i = 0; i < listId.size(); i++) {
			listFilm.add(findById(listId.get(i)));
		}

		return listFilm;
	}

	/**
	 * Exécute la requête passé en paramètre.
	 * @param url la requête
	 * @return résultat de la requête en Json
	 */
	private JsonObject executeRequest(final String url) {
		return ClientBuilder.newClient()
				.target(BASE_URL + url)
				.request()
				.get(JsonObject.class);
	}

	/**
	 * Vérifie la requête.
	 * @param response le JsonObject à vérifier
	 * @return true si la réponse est valide, false sinon
	 */
	private boolean checkRequest(final JsonObject response) {
		return response.getString("Response").equals("True");
	}

	/**
	 * Créer une liste de films à partir du résultat de la requête.
	 * @param result résultat de la requête
	 * @return liste des films
	 */
	private List<Film> createListFilms(final JsonObject result) {
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
