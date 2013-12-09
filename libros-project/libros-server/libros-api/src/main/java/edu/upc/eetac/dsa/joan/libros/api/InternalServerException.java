package edu.upc.eetac.dsa.joan.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.joan.libros.api.model.LibrosError;

public class InternalServerException extends WebApplicationException {
	public InternalServerException(String message) {
		super(Response
				.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(new LibrosError(Response.Status.INTERNAL_SERVER_ERROR
						.getStatusCode(), message))
				.type(MediaType.LIBROS_API_ERROR).build());
	}
}
