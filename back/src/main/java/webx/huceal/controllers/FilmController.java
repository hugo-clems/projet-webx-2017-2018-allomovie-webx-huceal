package webx.huceal.controllers;

import webx.huceal.ErrorMessage;
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
	public Response findFilms(@QueryParam("titre") String titre,
							  @QueryParam("annee") String annee,
							  @QueryParam("note") String note,
							  @QueryParam("commentaire") String commentaire) {
		if (titre != null && titre != "") {
			titre = titre.replaceAll("\\s", "+");
			return filmService.findByTitleAndOrYear(titre, annee);
		}

		if (note != null) {
			note = note.isEmpty() ? "0" : note;
			commentaire = commentaire == null ? "" : commentaire;
			return filmService.findByNoteAndComment(note, commentaire);
		} else if (commentaire != null) {
			return filmService.findByNoteAndComment("0", commentaire);
		}

		return Response.status(Response.Status.BAD_REQUEST)
				.type(MediaType.APPLICATION_JSON)
				.entity(new ErrorMessage("Requête invalide !")).build();
	}

}
