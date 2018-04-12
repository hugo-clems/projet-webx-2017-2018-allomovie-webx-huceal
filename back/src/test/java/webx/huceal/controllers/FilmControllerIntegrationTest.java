package webx.huceal.controllers;

import com.google.gson.Gson;
import org.glassfish.grizzly.http.server.HttpServer;
import org.hamcrest.core.Is;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webx.huceal.Main;
import webx.huceal.domains.Film;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class FilmControllerIntegrationTest {

	private Client client;
	private WebTarget target;
	private static HttpServer server;
	private Gson transformJson = new Gson();

	private final int codeOk = Response.Status.OK.getStatusCode();
	private final int codeBadRequest = Response.Status.BAD_REQUEST.getStatusCode();
	private Film starWars5;

	@BeforeClass
	public static void startServer() {
		server = Main.startServer();
	}

	@AfterClass
	public static void stopServer() {
		server.shutdownNow();
	}

	@Before
	public void setUp() throws Exception {
		client = ClientBuilder.newClient();
		target = client.target("http://localhost:8080/allomovie");

		starWars5 = new Film("tt0080684", "Star Wars: Episode V - The Empire Strikes Back",
				"1980", "124 min", "Action, Adventure, Fantasy", "Twentieth Century Fox", "Irvin Kershner",
				"Leigh Brackett (screenplay by), Lawrence Kasdan (screenplay by), George Lucas (story by)",
				"Mark Hamill, Harrison Ford, Carrie Fisher, Billy Dee Williams", "After the rebels are brutally overpowered by the Empire on the ice planet Hoth, Luke Skywalker begins Jedi training with Yoda, while his friends are pursued by Darth Vader.",
				"https://ia.media-imdb.com/images/M/MV5BYmU1NDRjNDgtMzhiMi00NjZmLTg5NGItZDNiZjU5NTU4OTE0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg");
	}


	@Test
	public void findByIdTest() throws Exception {
		Response response = target.path("film").path("tt0080684")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertThat(response.getStatusInfo().getStatusCode(), is(codeOk));
		assertThat(response.getMediaType(), is(MediaType.APPLICATION_JSON_TYPE));
		assertThat(response.readEntity(String.class), is(transformJson.toJson(starWars5)));
	}

	@Test
	public void findByTitleTest() throws Exception {
		Response response = target.path("film").path("liste").path("star wars")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeOk, response.getStatusInfo().getStatusCode());
	}

	@Test
	public void findByTitleTestAndYear() throws Exception {
		Response response = target.path("film").path("liste").path("star wars").path("2005")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeOk, response.getStatusInfo().getStatusCode());
	}


	@Test
	public void findByIdTestWithBadId() throws Exception {
		Response response = target.path("film").path("t008s68")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeBadRequest, response.getStatusInfo().getStatusCode());
	}

	@Test
	public void findByTitleTestWithBadTitle() throws Exception {
		Response response = target.path("film").path("liste").path("st")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeBadRequest, response.getStatusInfo().getStatusCode());
	}

	@Test
	public void findByTitleTestAndYearWithBadTitle() throws Exception {
		Response response = target.path("film").path("liste").path("st").path("2005")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeBadRequest, response.getStatusInfo().getStatusCode());
	}

	@Test
	public void findByTitleTestAndYearWithBadYear() throws Exception {
		Response response = target.path("film").path("liste").path("star wars").path("2x05")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeBadRequest, response.getStatusInfo().getStatusCode());
	}

}