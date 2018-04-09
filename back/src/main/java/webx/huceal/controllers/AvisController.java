package webx.huceal.controllers;

import webx.huceal.domains.Avis;
import webx.huceal.services.AvisService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/avis")
@Produces({ MediaType.APPLICATION_JSON })
public class AvisController {

	private AvisService service = new AvisService();

	/**
	 * Récupère tous les avis par film.
	 * @param id identifiant du film dont on veut les avis
	 * @return Response Json
	 */
	@GET
	@Path("/film/{id}")
	public Response findAllAvisByFilmID(@PathParam("id") String id) {
		return service.findAllAvisByFilmID(id);
	}

	/**
	 * Récupère un avis grâce à son id.
	 * @param id identifiant de l'avis demandé
	 * @return Response Json
	 */
	@GET
	@Path("/{id}")
	public Response findAvisByID(@PathParam("id") long id) {
		return service.findAvisByID(id);
	}

	/**
	 * Ajoute un avis à un film donné (note et/ou commentaire).
	 * @param avis objet de type Avis qui doit contenir un id de film, une note et un commentaire
	 * @return Response Json avec la location de la ressource dans le header si créée
	 */
	@POST
	@Path("/add")
	@Consumes({ MediaType.APPLICATION_JSON })
	public Response addAvis(Avis avis) {
		return service.addAvis(avis.getFilmID(), avis.getNote(), avis.getCommentaire());
	}

	/**
	 * Supprime un ou des avis par mot-clé dans les commentaires.
	 * @param key le mot-clé
	 * @return Response Json
	 */
	@DELETE
	@Path("/delete")
	public Response deleteAvisByKey(@QueryParam("key") String key) {
		return service.deleteAvisByKey(key);
	}

}
