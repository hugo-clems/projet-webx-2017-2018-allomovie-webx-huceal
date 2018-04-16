package webx.huceal.services;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import webx.huceal.ErrorMessage;
import webx.huceal.dao.AvisDAO;
import webx.huceal.dao.FilmDAO;
import webx.huceal.domains.Avis;
import webx.huceal.domains.Film;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class FilmServiceUnitTest {

	@Mock
	private FilmDAO filmDAO;

	@Mock
	private AvisDAO avisDAO;

	private static FilmService filmService;
	private static AvisService avisService;

	private static Film starWarsV;
	private static Film starWarsIII;
	private static Film starWarsII;
	private static List<Film> allStarWars;
	private static List<Film> allStarWarsIn2005;
	private static Response responseSW5;
	private static Response responseAllSW;
	private static Response responseAllSWIn2005;
	private static Response responseNoteSup3;
	private static Response responseCommentBien;
	private static Response badId;
	private static Response badTitle;
	private static Response badYear;
	private static Response noMovie;
	private static List<Film> emptyList;
	private static Avis avis1;
	private static Avis avis2;
	private static Avis avis3;
	private static Avis avis4;
	private static Avis avis5;
	private static Avis avis6;
	private static Avis avis7;
	private static List<Avis> avisSWV;
	private static List<Avis> avisSWIII;
	private static List<Avis> avisSWII;
	private static List<String> allFilmWithAtLeastOneNoteByFilmID;
	private static List<Film> allFilmWithAtLeastOneNote;
	private static List<Film> allFilmBien;
	private static List<Film> filmWithNoteSup3;
	private static List<Film> filmWithCommentBienAndNoteSup3;

	@BeforeClass
	public static void setUpAll() throws Exception {
		// Création jeux de tests
		starWarsV = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
				"1980", "124 min", "Action, Adventure, Fantasy", "Twentieth Century Fox", "Irvin Kershner",
				"Leigh Brackett (screenplay by), Lawrence Kasdan (screenplay by), George Lucas (story by)",
				"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams", "After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.",
				"https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", (new AvisService()).findSeuilNoteByFilmID("tt0080684"));

		starWarsIII = new Film("tt0121766", "Star Wars: Episode III - Revenge of the Sith",
				"2005", "140 min", "Action, Adventure, Fantasy", "20th Century Fox", "George Lucas",
				"George Lucas",
				"Ewan McGregor, Natalie Portman, Hayden Christensen, Ian McDiarmid", "Three years into the Clone Wars, the Jedi rescue Palpatine from Count Dooku. As Obi-Wan pursues a new threat, Anakin acts as a double agent between the Jedi Council and Palpatine and is lured into a sinister plan to rule the galaxy.",
				"https://images-na.ssl-images-amazon.com/images/M/MV5BNTc4MTc3NTQ5OF5BMl5BanBnXkFtZTcwOTg0NjI4NA@@._V1_SX300.jpg", (new AvisService()).findSeuilNoteByFilmID("tt0121766"));

		starWarsII = new Film("tt0121765", "Star Wars: Episode II - Attack of the Clones",
				"2002", "142 min", "Action, Adventure, Fantasy", "20th Century Fox", "George Lucas",
				"George Lucas (screenplay by), Jonathan Hales (screenplay by), George Lucas (story by)",
				"Ewan McGregor, Natalie Portman, Hayden Christensen, Christopher Lee", "Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé Amidala, while Obi-Wan investigates an assassination attempt on the senator and discovers a secret clone army crafted for the Jedi.",
				"https://ia.media-imdb.com/images/M/MV5BOWNkZmVjODAtNTFlYy00NTQwLWJhY2UtMmFmZTkyOWJmZjZiL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_SX300.jpg", (new AvisService()).findSeuilNoteByFilmID("tt0121766"));

		avis1 = new Avis("tt0121766", 5, "Très bon film. Xrs.");
		avis2 = new Avis("tt0121766", 4, "Très bon film !");
		avis3 = new Avis("tt0121766", 4, "Cool !");
		avis4 = new Avis("tt0080684", 3, "Bien");
		avis5 = new Avis("tt0080684", 3, "Oui c'est un bon film.");
		avis6 = new Avis("tt0121765", 2, "Bof bof");
		avis7 = new Avis("tt0121765", 2, "Bien mais long");

		avisSWIII = new ArrayList<>();
		avisSWIII.add(avis1);
		avisSWIII.add(avis2);
		avisSWIII.add(avis3);
		avisSWV = new ArrayList<>();
		avisSWV.add(avis4);
		avisSWV.add(avis5);
		avisSWII = new ArrayList<>();
		avisSWII.add(avis6);
		avisSWII.add(avis7);

		allFilmWithAtLeastOneNoteByFilmID = new ArrayList<>();
		allFilmWithAtLeastOneNoteByFilmID.add("tt0121766");
		allFilmWithAtLeastOneNoteByFilmID.add("tt0080684");
		allFilmWithAtLeastOneNoteByFilmID.add("tt0121765");

		allFilmWithAtLeastOneNote = new ArrayList<>();
		allFilmWithAtLeastOneNote.add(starWarsV);
		allFilmWithAtLeastOneNote.add(starWarsIII);
		allFilmWithAtLeastOneNote.add(starWarsII);

		allFilmBien = new ArrayList<>();
		allFilmBien.add(starWarsV);
		allFilmBien.add(starWarsII);

		filmWithNoteSup3 = new ArrayList<>();
		filmWithNoteSup3.add(starWarsV);
		filmWithNoteSup3.add(starWarsIII);

		filmWithCommentBienAndNoteSup3 = new ArrayList<>();
		filmWithCommentBienAndNoteSup3.add(starWarsV);

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

		responseNoteSup3 = Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(filmWithNoteSup3).build();

		responseCommentBien = Response.status(Response.Status.OK)
				.type(MediaType.APPLICATION_JSON)
				.entity(filmWithCommentBienAndNoteSup3).build();

		badId = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Identifiant invalide !")).build();

		badTitle = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Titre invalide !")).build();

		badYear = Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Année invalide !")).build();

		noMovie = Response.status(Response.Status.NOT_FOUND)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Aucun film trouvé !")).build();

		emptyList = new ArrayList<>();
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		filmService = new FilmService(filmDAO, avisDAO);
		avisService = new AvisService(avisDAO, filmDAO);
	}

	@Test
	public void findById() throws Exception {
		when(filmDAO.findById("tt0080684")).thenReturn(starWarsV);
		Response response = filmService.findById("tt0080684");
		assertThat(response.getStatus(), is(responseSW5.getStatus()));
		assertThat(response.getHeaders(), is(responseSW5.getHeaders()));
		assertThat(response.getEntity(), is(responseSW5.getEntity()));
	}

	@Test
	public void findByTitle() throws Exception {
		when(filmDAO.findByTitle("star+wars")).thenReturn(allStarWars);
		Response response = filmService.findByTitle("star+wars");
		assertThat(response.getStatus(), is(responseAllSW.getStatus()));
		assertThat(response.getHeaders(), is(responseAllSW.getHeaders()));
		assertThat(response.getEntity(), is(responseAllSW.getEntity()));
	}

	@Test
	public void findByTitleAndYear() throws Exception {
		when(filmDAO.findByTitleAndYear("star+wars", "2005")).thenReturn(allStarWarsIn2005);
		Response response = filmService.findByTitleAndYear("star+wars", "2005");
		assertThat(response.getStatus(), is(responseAllSWIn2005.getStatus()));
		assertThat(response.getHeaders(), is(responseAllSWIn2005.getHeaders()));
		assertThat(response.getEntity(), is(responseAllSWIn2005.getEntity()));
	}

	@Test
	public void findByAvisByNote() throws Exception {
		when(filmDAO.findById("tt0121766")).thenReturn(starWarsIII);
		when(filmDAO.findById("tt0080684")).thenReturn(starWarsV);
		when(filmDAO.findById("tt0121765")).thenReturn(starWarsII);
		when(avisDAO.findAllFilmsWithAtLeastOneNoteByFilmID()).thenReturn(allFilmWithAtLeastOneNoteByFilmID);
		when(avisDAO.findAllAvisByFilmID("tt0121766")).thenReturn(avisSWIII);
		when(avisDAO.findAllAvisByFilmID("tt0080684")).thenReturn(avisSWV);
		when(avisDAO.findAllAvisByFilmID("tt0121765")).thenReturn(avisSWII);
		when(filmDAO.findByAvis("")).thenReturn(allFilmWithAtLeastOneNote);

		Response response = filmService.findByAvis("3", "");
		assertThat(response.getStatus(), is(responseNoteSup3.getStatus()));
		assertThat(response.getHeaders(), is(responseNoteSup3.getHeaders()));
		assertThat(response.getEntity(), is(responseNoteSup3.getEntity()));
	}

	@Test
	public void findByAvisByNoteAndComment() throws Exception {
		when(filmDAO.findById("tt0121766")).thenReturn(starWarsIII);
		when(filmDAO.findById("tt0080684")).thenReturn(starWarsV);
		when(filmDAO.findById("tt0121765")).thenReturn(starWarsII);
		when(avisDAO.findAllFilmsWithAtLeastOneNoteByFilmID()).thenReturn(allFilmWithAtLeastOneNoteByFilmID);
		when(avisDAO.findAllAvisByFilmID("tt0121766")).thenReturn(avisSWIII);
		when(avisDAO.findAllAvisByFilmID("tt0080684")).thenReturn(avisSWV);
		when(avisDAO.findAllAvisByFilmID("tt0121765")).thenReturn(avisSWII);
		when(filmDAO.findByAvis("bien")).thenReturn(allFilmBien);

		Response response = filmService.findByAvis("3", "bien");
		assertThat(response.getStatus(), is(responseCommentBien.getStatus()));
		assertThat(response.getHeaders(), is(responseCommentBien.getHeaders()));
		assertThat(response.getEntity(), is(responseCommentBien.getEntity()));
	}

	@Test
	public void findByIdWithBadId() throws Exception {
		when(filmDAO.findById("tt008068")).thenReturn(null);
		Response response = filmService.findById("tt008068");
		assertThat(response.getStatus(), is(badId.getStatus()));
		assertThat(response.getHeaders(), is(badId.getHeaders()));
		assertThat(response.getEntity(), is(badId.getEntity()));
	}

	@Test
	public void findByTitleWithBadTitle() throws Exception {
		when(filmDAO.findByTitle("s")).thenReturn(emptyList);
		Response response = filmService.findByTitle("s");
		assertThat(response.getStatus(), is(badTitle.getStatus()));
		assertThat(response.getHeaders(), is(badTitle.getHeaders()));
		assertThat(response.getEntity(), is(badTitle.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithBadTitle() throws Exception {
		when(filmDAO.findByTitleAndYear("s", "2005")).thenReturn(emptyList);
		Response response = filmService.findByTitleAndYear("st", "2005");
		assertThat(response.getStatus(), is(badTitle.getStatus()));
		assertThat(response.getHeaders(), is(badTitle.getHeaders()));
		assertThat(response.getEntity(), is(badTitle.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithBadYear() throws Exception {
		when(filmDAO.findByTitleAndYear("star+wars", "2x05")).thenReturn(emptyList);
		Response response = filmService.findByTitleAndYear("star+wars", "20x5");
		assertThat(response.getStatus(), is(badYear.getStatus()));
		assertThat(response.getHeaders(), is(badYear.getHeaders()));
		assertThat(response.getEntity(), is(badYear.getEntity()));
	}

	@Test
	public void findByTitleAndYearWithNoMovie() throws Exception {
		when(filmDAO.findByTitleAndYear("sqdlksjdqsdlsqjdlkqsdjqsldkqsd", "2005"))
				.thenReturn(emptyList);
		Response response =
				filmService.findByTitleAndYear("sqdlksjdqsdlsqjdlkqsdjqsldkqsd", "2005");
		assertThat(response.getStatus(), is(noMovie.getStatus()));
		assertThat(response.getHeaders(), is(noMovie.getHeaders()));
		assertThat(response.getEntity(), is(noMovie.getEntity()));
	}

}