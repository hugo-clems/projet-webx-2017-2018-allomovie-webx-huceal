package webx.huceal.services;

import webx.huceal.dao.AvisDAO;

import javax.ws.rs.core.Response;

public class AvisService {

    private AvisDAO dao = new AvisDAO();

    public Response addAvis(String filmID, String note, String commentaire) {
        Response.Status status = Response.Status.OK;
        if (filmID == null || note.isEmpty() || Integer.valueOf(note) < 0 || Integer.valueOf(note) > 5) {
            /* TODO
                Vérifier l'id du film
                Verifier à part la note (peut être null)
                Si non null conversion String -> Integer et vérification
                Exceptions SQL à gérer
             */
            status = Response.Status.BAD_REQUEST;
        } else {
            dao.addAvis(filmID, Integer.valueOf(note), commentaire);
        }
        return Response.status(status).build();
    }

}
