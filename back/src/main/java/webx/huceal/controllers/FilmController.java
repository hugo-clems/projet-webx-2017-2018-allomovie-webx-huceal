package webx.huceal.controllers;

import webx.huceal.services.FilmService;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Contrôle l'accès aux Films.
 *
 * /{id} - Récupère un film via son identifiant
 * /list - Récupère une liste de film selon le titre et/ou l'année de sortie
 * /avis - Récupère une liste de film en renseignant une note minimal
 *         ou un mot contenu dans les commentaires
 */
@Path("/film")
@Produces({ MediaType.APPLICATION_JSON })
public class FilmController {

    /**
     * Le service de Film.
     */
    private FilmService filmService = new FilmService();

    /**
     * Récupère un film via son identifiant.
     * @param id l'identifiant du film à récupérer
     * @return le film sous forme de Response Json
     */
    @GET @Path("/{id}")
    public final Response findById(@PathParam("id") final String id) {
        return filmService.findById(id);
    }

    /**
     * Récupère une liste de film selon le titre.
     * @param titre titre du film recherché
     * @return le film sous forme de Response Json
     */
    @GET @Path("/liste/{titre}")
    public final Response findByTitle(@PathParam("titre") final String titre) {
        return filmService.findByTitle(titre.replaceAll("\\s", "+"));
    }

    /**
     * Récupère une liste de film selon le titre et l'année de sortie.
     * @param titre titre du film recherché
     * @param annee année du film recherché
     * @return le film sous forme de Response Json
     */
    @GET @Path("/liste/{titre}/{annee}")
    public final Response findByTitleAndYear(
            @PathParam("titre") final String titre,
            @PathParam("annee") final String annee) {
        String nouveauTitre = titre.replaceAll("\\s", "+");
        return filmService.findByTitleAndYear(nouveauTitre, annee);
    }

    /**
     * Récupère une liste de film en renseignant une note minimal
     * ou un mot contenu dans les commentaires.
     * @param note note minimal des films recherchés
     * @param comm mot contenu dans les commentaires des films recherchés
     * @return le film sous forme de Response Json
     */
    @GET @Path("/avis")
    public final Response findByAvis(
            @DefaultValue("0") @QueryParam("note") final String note,
            @DefaultValue("") @QueryParam("commentaire") final String comm) {
        return filmService.findByAvis(note, comm);
    }

}
