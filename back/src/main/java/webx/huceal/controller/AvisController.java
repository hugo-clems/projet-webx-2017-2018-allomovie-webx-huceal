package webx.huceal.controller;

import webx.huceal.dao.AvisDAO;
import webx.huceal.domain.Avis;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/avis")
public class AvisController {

	AvisDAO dao = new AvisDAO();

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Avis> findAllByFilmId(@PathParam("id") String id) {
		return dao.findAllByFilmId(id);
	}

}
