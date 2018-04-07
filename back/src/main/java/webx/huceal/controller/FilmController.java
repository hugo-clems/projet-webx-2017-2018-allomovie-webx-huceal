package webx.huceal.controller;

import webx.huceal.dao.FilmDAO;
import webx.huceal.domain.Film;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/films")
public class FilmController {

	FilmDAO dao = new FilmDAO();

	@GET @Path("/{titre}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Film> findByTitle(@PathParam("titre") String titre) {
		return dao.findByTitle(titre);
	}

}
