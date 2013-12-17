package edu.upc.eetac.dsa.joan.libros.api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.links.LibrosAPILinkBuilder;


@Path("/")
public class LibrosRootAPIResource {
        @Context
        private UriInfo uriInfo;
        // TODO: Return links
        @GET
        @Produces(MediaType.LIBROS_API_LINK_COLLECTION)
        public LibrosRootAPI getLinks(){
                LibrosRootAPI root = new LibrosRootAPI();
                root.addLink(LibrosAPILinkBuilder.buildURIRootAPI(uriInfo));
                root.addLink(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo, "libros"));
                root.addLink(LibrosAPILinkBuilder.buildTemplatedURILibros(uriInfo, "libros", true, true));
                return root;
        }
}