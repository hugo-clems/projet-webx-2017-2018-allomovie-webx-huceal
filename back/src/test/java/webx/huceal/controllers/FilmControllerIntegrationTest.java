package webx.huceal.controllers;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import webx.huceal.Main;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.*;

public class FilmControllerIntegrationTest {

	private Client client;
	private WebTarget target;
	private static HttpServer server;

	private final int codeOk = Response.Status.OK.getStatusCode();
	private final int codeBadRequest = Response.Status.BAD_REQUEST.getStatusCode();

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
	}


	@Test
	public void findByIdTest() throws Exception {
		Response response = target.path("film").path("tt0080684")
				.request(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).get();

		assertEquals(codeOk, response.getStatusInfo().getStatusCode());
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