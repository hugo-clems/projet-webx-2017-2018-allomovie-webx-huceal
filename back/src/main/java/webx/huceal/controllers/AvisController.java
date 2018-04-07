package webx.huceal.controllers;

import webx.huceal.services.AvisService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/avis")
public class AvisController {

	private AvisService service = new AvisService();

	/*@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Avis> findAllAvisByFilmId(@PathParam("id") String id) {
		return dao.findAllAvisByFilmId(id);
	}*/

	@POST
	@Path("/add")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addAvis(@QueryParam("id") String filmID, @QueryParam("note") int note, @QueryParam("commentaire") String commentaire) {
		return Response.ok().build();
	}

}
