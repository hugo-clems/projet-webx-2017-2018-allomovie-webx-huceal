package webx.huceal.controllers;

import webx.huceal.services.FilmService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/films")
public class FilmController {

	private FilmService filmService = new FilmService();

	@GET @Path("/{titre}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findByTitle(@PathParam("titre") String titre) {
		return filmService.findByTitle(titre);
	}

}
