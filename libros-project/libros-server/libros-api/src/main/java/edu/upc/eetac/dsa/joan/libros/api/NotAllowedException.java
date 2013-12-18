package edu.upc.eetac.dsa.joan.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.joan.libros.api.model.LibrosError;

public class NotAllowedException extends WebApplicationException {
	private final static String MESSAGE = "No se te permite realizar ésta acción";

	public NotAllowedException() {
		super(Response
				.status(Response.Status.FORBIDDEN)
				.entity(new LibrosError(Response.Status.FORBIDDEN
						.getStatusCode(), MESSAGE))
				.type(MediaType.LIBROS_API_ERROR).build());
	}
}