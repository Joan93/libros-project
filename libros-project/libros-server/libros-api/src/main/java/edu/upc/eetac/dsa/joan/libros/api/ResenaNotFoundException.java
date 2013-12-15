package edu.upc.eetac.dsa.joan.libros.api;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import edu.upc.eetac.dsa.joan.libros.api.model.LibrosError;

public class ResenaNotFoundException extends WebApplicationException {
	private final static String MESSAGE = "Reseña not found";

	public ResenaNotFoundException() {
		super(Response
				.status(Response.Status.NOT_FOUND)
				.entity(new LibrosError(Response.Status.NOT_FOUND
						.getStatusCode(), MESSAGE))
				.type(MediaType.LIBROS_API_ERROR).build());
	}
}