package webx.huceal;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/**
 * Gestion des filtres CORS.
 */
@Provider
public class CORSFilter implements ContainerResponseFilter {

    /**
     * Filtre les entrées dans l'API.
     * @param request la requête
     * @param response la réponse
     * @throws IOException IOException
     */
    @Override
    public final void filter(final ContainerRequestContext request,final ContainerResponseContext response)
            throws IOException {
        response.getHeaders().add("Access-Control-Allow-Origin", "*");
<<<<<<< HEAD
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
        //response.getHeaders().add("Access-Control-Allow-Credentials", "true");
=======
        response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept");
        response.getHeaders().add("Access-Control-Allow-Credentials", "true");
>>>>>>> 250028a423f5e938fcc31489cc168bf6b97bc79d
        response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE");
    }

}
