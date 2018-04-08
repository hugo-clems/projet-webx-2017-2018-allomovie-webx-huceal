package webx.huceal.controllers;

import webx.huceal.services.FilmService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/film")
public class FilmController {

	private FilmService filmService = new FilmService();

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findById(@QueryParam("id") String id) {
		return filmService.findById(id);
	}

	@GET @Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findFilms(@QueryParam("titre") String titre, @QueryParam("annee") String annee) {
		titre = titre.replaceAll("\\s", "+");
		return filmService.findFilms(titre, annee);
	}

}
