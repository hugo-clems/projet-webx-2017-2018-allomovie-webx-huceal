package webx.huceal.controllers;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import webx.huceal.ErrorMessage;
import webx.huceal.Main;
import webx.huceal.domains.Film;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FilmControllerIntegrationTest {

	private static Client client;
	private static WebTarget target;
	private static HttpServer server;

	private static int codeOk;
	private static int codeBadRequest;

	private static ErrorMessage idInvalid;
	private static ErrorMessage titleInvalid;
	private static ErrorMessage yearInvalid;
	private static ErrorMessage noMovie;

	private static Film starWars5;
	private static List<Film> allStarWars;
	private static List<Film> allStarWarsIn2005;

	@BeforeClass
	public static void startServer() {
		// Initialisation du client
		client = ClientBuilder.newClient();
		target = client.target(Main.BASE_URI);

		// Lancement du serveur
		server = Main.startServer();

		// Création des jeux de tests
		codeOk = Response.Status.OK.getStatusCode();
		codeBadRequest = Response.Status.BAD_REQUEST.getStatusCode();

		idInvalid = new ErrorMessage("Identifiant invalide !");
		titleInvalid = new ErrorMessage("Titre invalide !");
		yearInvalid = new ErrorMessage("Année invalide !");
		noMovie = new ErrorMessage("Aucun film trouvé !");

		starWars5 = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
				"1980", "124 min", "Action, Adventure, Fantasy", "Twentieth Century Fox", "Irvin Kershner",
				"Leigh Brackett (screenplay by), Lawrence Kasdan (screenplay by), George Lucas (story by)",
				"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams", "After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.",
				"https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg");

		allStarWars = new ArrayList<>();
		allStarWars.add(new Film("tt0076759", "Star Wars: Episode IV - A New Hope", "1977", "https://ia.media-imdb.com/images/M/MV5BNzVlY2MwMjktM2E4OS00Y2Y3LWE3ZjctYzhkZGM3YzA1ZWM2XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back", "1980", "https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt0086190", "Star Wars: Episode VI - Return of the Jedi", "1983", "https://images-na.ssl-images-amazon.com/images/M/MV5BOWZlMjFiYzgtMTUzNC00Y2IzLTk1NTMtZmNhMTczNTk0ODk1XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt2488496", "Star Wars: The Force Awakens", "2015", "https://ia.media-imdb.com/images/M/MV5BOTAzODEzNDAzMl5BMl5BanBnXkFtZTgwMDU1MTgzNzE@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt0120915", "Star Wars: Episode I - The Phantom Menace", "1999", "https://ia.media-imdb.com/images/M/MV5BYTRhNjcwNWQtMGJmMi00NmQyLWE2YzItODVmMTdjNWI0ZDA2XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt0121766", "Star Wars: Episode III - Revenge of the Sith", "2005", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTc4MTc3NTQ5OF5BMl5BanBnXkFtZTcwOTg0NjI4NA@@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt0121765", "Star Wars: Episode II - Attack of the Clones", "2002", "https://ia.media-imdb.com/images/M/MV5BOWNkZmVjODAtNTFlYy00NTQwLWJhY2UtMmFmZTkyOWJmZjZiL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt3748528", "Rogue One: A Star Wars Story", "2016", "https://ia.media-imdb.com/images/M/MV5BMjEwMzMxODIzOV5BMl5BanBnXkFtZTgwNzg3OTAzMDI@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt2527336", "Star Wars: The Last Jedi", "2017", "https://ia.media-imdb.com/images/M/MV5BMjQ1MzcxNjg4N15BMl5BanBnXkFtZTgwNzgwMjY4MzI@._V1_SX300.jpg"));
		allStarWars.add(new Film("tt1185834", "Star Wars: The Clone Wars", "2008", "https://ia.media-imdb.com/images/M/MV5BMTI1MDIwMTczOV5BMl5BanBnXkFtZTcwNTI4MDE3MQ@@._V1_SX300.jpg"));

		allStarWarsIn2005 = new ArrayList<>();
		allStarWarsIn2005.add(new Film("tt0121766", "Star Wars: Episode III - Revenge of the Sith", "2005", "https://images-na.ssl-images-amazon.com/images/M/MV5BNTc4MTc3NTQ5OF5BMl5BanBnXkFtZTcwOTg0NjI4NA@@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt0457489", "Star Wars: Revelations", "2005", "https://ia.media-imdb.com/images/M/MV5BMTYxNzU0NTc3MF5BMl5BanBnXkFtZTcwNTM1MzEzMQ@@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt0459164", "Star Wars: A Musical Journey", "2005", "https://images-na.ssl-images-amazon.com/images/M/MV5BYWYxODQwMDQtMTY4ZS00ZWNhLWFmNDktNGQ1YWE1ODQzYmI1XkEyXkFqcGdeQXVyMTMxMjkxOTU@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt2008565", "Lego Star Wars: Revenge of the Brick", "2005", "https://images-na.ssl-images-amazon.com/images/M/MV5BMTQxMzdiYTgtNmVkNC00ZGM3LWFkZmItZjNiNDNkNzdmNmFmXkEyXkFqcGdeQXVyNDQ2OTk4MzI@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt0462554", "'Star Wars': Feel the Force", "2005", "N/A"));
		allStarWarsIn2005.add(new Film("tt1970025", "Star Wars Heroes & Villains", "2005", "https://images-na.ssl-images-amazon.com/images/M/MV5BNGQ5Zjk1ZjEtYTY4YS00N2ZkLWJiZDAtNTgxNTgwMzg5MmUyL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNzA4NDk0NjQ@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt0790824", "World Premiere 'Star Wars III: Revenge of the Sith'", "2005", "N/A"));
		allStarWarsIn2005.add(new Film("tt0469106", "How to Stand in Line for Star Wars", "2005", "http://ia.media-imdb.com/images/M/MV5BMTMyMTQwNjEzOV5BMl5BanBnXkFtZTcwODg4MDI1MQ@@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt4273912", "Star Wars Episode III: Becoming Obi-Wan", "2005", "https://ia.media-imdb.com/images/M/MV5BNzExYzA4ODctMDA0Yy00M2RhLTgyNzQtM2MyMzlhNmEzZTYyXkEyXkFqcGdeQXVyMzYyMzU2OA@@._V1_SX300.jpg"));
		allStarWarsIn2005.add(new Film("tt4528700", "Star Wars Epizod III - Imladris", "2005", "N/A"));
	}

	@AfterClass
	public static void stopServer() {
		server.shutdownNow();
	}


	@Test
	public void findByIdTest() throws Exception {
		Response response = target.path("film").path("tt0080684")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeOk));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(Film.class), is(starWars5));
	}

	@Test
	public void findByTitleTest() throws Exception {
		Response response = target.path("film").path("liste").path("star wars")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeOk));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(new GenericType<List<Film>>() {}), is(allStarWars));
	}

	@Test
	public void findByTitleTestAndYear() throws Exception {
		Response response = target.path("film").path("liste").path("star wars").path("2005")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeOk));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(new GenericType<List<Film>>() {}), is(allStarWarsIn2005));
	}


	@Test
	public void findByIdTestWithBadId() throws Exception {
		Response response = target.path("film").path("t008s68")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeBadRequest));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(ErrorMessage.class), is(idInvalid));
	}

	@Test
	public void findByTitleTestWithBadTitle() throws Exception {
		Response response = target.path("film").path("liste").path("st")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeBadRequest));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(ErrorMessage.class), is(titleInvalid));
	}

	@Test
	public void findByTitleTestAndYearWithBadTitle() throws Exception {
		Response response = target.path("film").path("liste").path("st").path("2005")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeBadRequest));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(ErrorMessage.class), is(titleInvalid));
	}

	@Test
	public void findByTitleTestAndYearWithBadYear() throws Exception {
		Response response = target.path("film").path("liste").path("star wars").path("2x05")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeBadRequest));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(ErrorMessage.class), is(yearInvalid));
	}

	@Test
	public void findByTitleTestAndYearWithNoMovie() throws Exception {
		Response response = target.path("film").path("liste").path("sdflkjdsjdsfsdfkldsdf").path("2005")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatus(), is(codeBadRequest));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(ErrorMessage.class), is(noMovie));
	}

}