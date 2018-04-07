package webx.huceal.services;

import webx.huceal.dao.FilmDAO;

import javax.ws.rs.core.Response;

public class FilmService {

	private FilmDAO dao = new FilmDAO();

	/**
	 * Récupère la liste des films correpondant au titre renseigné
	 * @param titre titre du film recherché
	 * @return Response Json
	 */
	public Response findByTitle(String titre) {
		Response.Status status = Response.Status.OK;
		Object body = null;
		if (titre.length() < 4) {
			status = Response.Status.BAD_REQUEST;
		} else {
			body = dao.findByTitle(titre);
		}
		return Response.status(status).entity(body).build();
	}

	/**
	 * Récupère le film correspondant à l'identifiant
	 * @param id identifiant du film recherché
	 * @return Response Json
	 */
	public Response findById(String id) {
		Response.Status status = Response.Status.OK;
		Object body = dao.findById(id);
		// TODO if id is not valid
		return Response.status(status).entity(body).build();
	}

}
