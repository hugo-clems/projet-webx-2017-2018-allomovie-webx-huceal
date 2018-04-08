package webx.huceal.controllers;

import webx.huceal.domains.Avis;
import webx.huceal.services.AvisService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/avis")
public class AvisController {

	private AvisService service = new AvisService();

	@GET
	@Path("/film/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAllAvisByFilmID(@PathParam("id") String id) {
		return service.findAllAvisByFilmID(id);
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAvisByID(@PathParam("id") long id) {
		return service.findAvisByID(id);
	}

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addAvis(Avis avis) {
		return service.addAvis(avis.getFilmID(), avis.getNote(), avis.getCommentaire());
	}

	@DELETE
	@Path("/delete")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response deleteAvisByKey(@QueryParam("key") String key) {
		return service.deleteAvisByKey(key);
	}

}
