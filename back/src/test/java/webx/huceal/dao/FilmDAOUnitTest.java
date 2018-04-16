package webx.huceal.dao;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import webx.huceal.domains.Avis;
import webx.huceal.domains.Film;
import webx.huceal.services.AvisService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FilmDAOUnitTest {

	private static FilmDAO filmDAO;
	private static AvisDAO avisDAO;
	private static Film starWarsV;
	private static List<Film> allStarWars;
	private static List<Film> allStarWarsIn2005;
	private static List<Film> commentBien;
	private static List<Film> emptyList;
	private static Avis avis1;
	private static Avis avis2;
	private static Avis avis3;
	private static Avis avis4;
	private static Avis avis5;
	private static Avis avis6;
	private static Avis avis7;

	@BeforeClass
	public static void setUp() throws Exception {
		// Accès dao
		filmDAO = new FilmDAO();
		avisDAO = new AvisDAO();

		// Création jeux de tests
		avis1 = new Avis("tt0121766", 5, "Très bon film. Xrs.");
		avis2 = new Avis("tt0121766", 4, "Très bon film !");
		avis3 = new Avis("tt0121766", 4, "Cool !");
		avis4 = new Avis("tt0080684", 3, "Bien");
		avis5 = new Avis("tt0080684", 3, "Oui c'est un bon film.");
		avis6 = new Avis("tt0121765", 2, "Bof bof");
		avis7 = new Avis("tt0121765", 2, "Bien mais long");

		avis1.setId(avisDAO.addAvis(avis1.getFilmID(), avis1.getNote(), avis1.getCommentaire()));
		avis2.setId(avisDAO.addAvis(avis2.getFilmID(), avis2.getNote(), avis2.getCommentaire()));
		avis3.setId(avisDAO.addAvis(avis3.getFilmID(), avis3.getNote(), avis3.getCommentaire()));
		avis4.setId(avisDAO.addAvis(avis4.getFilmID(), avis4.getNote(), avis4.getCommentaire()));
		avis5.setId(avisDAO.addAvis(avis5.getFilmID(), avis5.getNote(), avis5.getCommentaire()));
		avis6.setId(avisDAO.addAvis(avis6.getFilmID(), avis6.getNote(), avis6.getCommentaire()));
		avis7.setId(avisDAO.addAvis(avis7.getFilmID(), avis7.getNote(), avis7.getCommentaire()));

		starWarsV = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
				"1980", "124 min", "Action, Adventure, Fantasy", "Twentieth Century Fox", "Irvin Kershner",
				"Leigh Brackett (screenplay by), Lawrence Kasdan (screenplay by), George Lucas (story by)",
				"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams", "After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.",
				"https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg", (new AvisService()).findSeuilNoteByFilmID("tt0080684"));

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

		commentBien = new ArrayList<>();
		commentBien.add(filmDAO.findById("tt0080684"));
		commentBien.add(filmDAO.findById("tt0121765"));

		emptyList = new ArrayList<>();
	}

	@AfterClass
	public static void endTest() {
		if (avisDAO.findAvisByID(avis1.getId()) != null) {
			avisDAO.deleteAvisByID(avis1.getId());
		}
		if (avisDAO.findAvisByID(avis2.getId()) != null) {
			avisDAO.deleteAvisByID(avis2.getId());
		}
		if (avisDAO.findAvisByID(avis3.getId()) != null) {
			avisDAO.deleteAvisByID(avis3.getId());
		}
		if (avisDAO.findAvisByID(avis4.getId()) != null) {
			avisDAO.deleteAvisByID(avis4.getId());
		}
		if (avisDAO.findAvisByID(avis5.getId()) != null) {
			avisDAO.deleteAvisByID(avis5.getId());
		}
		if (avisDAO.findAvisByID(avis6.getId()) != null) {
			avisDAO.deleteAvisByID(avis6.getId());
		}
		if (avisDAO.findAvisByID(avis7.getId()) != null) {
			avisDAO.deleteAvisByID(avis7.getId());
		}
	}

	@Test
	public void findById() throws Exception {
		Film result = filmDAO.findById("tt0080684");
		assertThat(result, is(starWarsV));
	}

	@Test
	public void findByTitle() throws Exception {
		List<Film> result = filmDAO.findByTitle("star+wars");
		assertThat(result, is(allStarWars));
	}

	@Test
	public void findByTitleAndYear() throws Exception {
		List<Film> result = filmDAO.findByTitleAndYear("star+wars", "2005");
		assertThat(result, is(allStarWarsIn2005));
	}

	@Test
	public void findByAvis() throws Exception {
		List<Film> result = filmDAO.findByAvis("bien");
		assertThat(result, is(commentBien));
	}


	@Test
	public void findByIdWithBadID() throws Exception {
		Film result = filmDAO.findById("tt008068");
		assertThat(result, is(nullValue()));
	}

	@Test
	public void findByTitleWithBadTitle() throws Exception {
		List<Film> result = filmDAO.findByTitle("s");
		assertThat(result, is(emptyList));
	}

	@Test
	public void findByTitleAndYearWithBadTitle() throws Exception {
		List<Film> result = filmDAO.findByTitleAndYear("s", "2005");
		assertThat(result, is(emptyList));
	}

	@Test
	public void findByTitleAndYearWithBadYear() throws Exception {
		List<Film> result = filmDAO.findByTitleAndYear("star+wars", "20x5");
		assertThat(result, is(allStarWars));
	}

	@Test
	public void findByTitleAndYearWithBadTitleAndYear() throws Exception {
		List<Film> result = filmDAO.findByTitleAndYear("s", "20x5");
		assertThat(result, is(emptyList));
	}

}