package webx.huceal.services;

import org.junit.BeforeClass;
import org.junit.Test;
import webx.huceal.ErrorMessage;
import webx.huceal.domains.Film;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FilmServiceTest {

	private static FilmService filmService;
	private static Film starWarsV;
	private static List<Film> allStarWars;
	private static List<Film> allStarWarsIn2005;
	private static Response responseSW5;
	private static Response responseAllSW;
	private static Response responseAllSWIn2005;
	private static Response badId;
	private static Response badTitle;
	private static Response badYear;
	private static Response noMovie;

	@BeforeClass
	public static void setUp() throws Exception {
		// Accès au service
		filmService = new FilmService();

		// Création jeux de tests
		starWarsV = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
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

		responseSW5 = Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(starWarsV).build();

		responseAllSW = Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(allStarWars).build();

		responseAllSWIn2005 = Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(allStarWarsIn2005).build();

		badId = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Identifiant invalide !")).build();

		badTitle = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Titre invalide !")).build();

		badYear = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Année invalide !")).build();

		noMovie = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Aucun film trouvé !")).build();
	}

	@Test
	public void findById() throws Exception {
		Response response = filmService.findById("tt0080684");
		assertThat(response.getStatus(), is(responseSW5.getStatus()));
		assertThat(response.getHeaders(), is(responseSW5.getHeaders()));
		assertThat(response.getEntity(), is(responseSW5.getEntity()));
	}

	@Test
	public void findByTitle() throws Exception {
		Response response = filmService.findByTitle("star+wars");
		assertThat(response.getStatus(), is(responseAllSW.getStatus()));
		assertThat(response.getHeaders(), is(responseAllSW.getHeaders()));
		assertThat(response.getEntity(), is(responseAllSW.getEntity()));
	}

	@Test
	public void findByTitleAndYear() throws Exception {
		Response response = filmService.findByTitleAndYear("star+wars", "2005");
		assertThat(response.getStatus(), is(responseAllSWIn2005.getStatus()));
		assertThat(response.getHeaders(), is(responseAllSWIn2005.getHeaders()));
		assertThat(response.getEntity(), is(responseAllSWIn2005.getEntity()));
	}

	@Test
	public void findByIdWithBadId() throws Exception {
		Response response = filmService.findById("tt008068");
		assertThat(response.getStatus(), is(badId.getStatus()));
		assertThat(response.getHeaders(), is(badId.getHeaders()));
		assertThat(response.getEntity(), is(badId.getEntity()));
	}

	@Test
	public void findByTitleWithBadTitle() throws Exception {
		Response response = filmService.findByTitle("s");
		assertThat(response.getStatus(), is(badTitle.getStatus()));
		assertThat(response.getHeaders(), is(badTitle.getHeaders()));
		assertThat(response.getEntity(), is(badTitle.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithBadTitle() throws Exception {
		Response response = filmService.findByTitleAndYear("st", "2005");
		assertThat(response.getStatus(), is(badTitle.getStatus()));
		assertThat(response.getHeaders(), is(badTitle.getHeaders()));
		assertThat(response.getEntity(), is(badTitle.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithBadYear() throws Exception {
		Response response = filmService.findByTitleAndYear("star+wars", "20x5");
		assertThat(response.getStatus(), is(badYear.getStatus()));
		assertThat(response.getHeaders(), is(badYear.getHeaders()));
		assertThat(response.getEntity(), is(badYear.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithNoMovie() throws Exception {
		Response response =
				filmService.findByTitleAndYear("sqdlksjdqsdlsqjdlkqsdjqsldkqsd", "2005");
		assertThat(response.getStatus(), is(noMovie.getStatus()));
		assertThat(response.getHeaders(), is(noMovie.getHeaders()));
		assertThat(response.getEntity(), is(noMovie.getEntity()));
	}

}