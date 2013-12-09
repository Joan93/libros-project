package edu.upc.eetac.dsa.joan.libros.api.links;

import java.net.URI;

import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.*;
import edu.upc.eetac.dsa.joan.libros.api.model.Libro;

public class LibrosAPILinkBuilder {
	public final static Link buildURIRootAPI(UriInfo uriInfo) {
		URI uriRoot = uriInfo.getBaseUriBuilder()
				.path(LibrosRootAPIResource.class).build();// nos devuelve
															// http://localhost:8080/beeter-api
		Link link = new Link(); // utilizamos la clase link para construir el
								// enlace
		link.setUri(uriRoot.toString());
		link.setRel("self bookmark"); // decirle al cliente la pagina inicial
		link.setTitle("Beeter API"); // descripción
		link.setType(MediaType.LIBROS_API_LINK_COLLECTION); /*
															 * devolver una
															 * colección de
															 * enlaces
															 */

		return link;
	}

	// La uri se construye de diferentes maneras según los parametros que
	// tenemos

	public static final Link buildURILibros(UriInfo uriInfo, String rel) {
		return buildURILibros(uriInfo, null, null, null, rel); // uri hasta
																// Libros
	}

	public static final Link buildURILibros(UriInfo uriInfo, String offset,
			String length, String username, String rel) { // poner la uri con
															// query parámetros
		URI uriLibros;
		if (offset == null && length == null)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.build();
		else {
			if (username == null)
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length).build(); // no devuelve
																// una plantilla
																// sino una con
																// valores
																// concretos
			else
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("username", username).build();
		}

		Link self = new Link();
		self.setUri(uriLibros.toString());
		self.setRel(rel);
		self.setTitle("Libros collection");
		self.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);

		return self;
	}

	public static final Link buildTemplatedURILibros(UriInfo uriInfo, String rel) {

		return buildTemplatedURILibros(uriInfo, rel, false);
	}

	public static final Link buildTemplatedURILibros(UriInfo uriInfo,
			String rel, boolean username)
	// no toman un valor en concreto sino que es una plantilla
	{
		URI uriLibros;
		if (username)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("username", "{username}").build();
		else
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();

		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriLibros));
		link.setRel(rel);
		if (username)
			link.setTitle("Libros collection resource filtered by {username}");
		else
			link.setTitle("Libros collection resource");
		link.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);

		return link;
	}

	public final static Link buildURILibro(UriInfo uriInfo, Libro Libro) {
		URI LibroURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.build(); // http://localhost:8080/beeter-api/Libros
		Link link = new Link();
		link.setUri(LibroURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + Libro.getTitulo());
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

	public final static Link buildURILibroId(UriInfo uriInfo, String Libroid,
			String rel) {
		// http://localhost:8080/beeter-api/Libros/{valor Libro}
		URI LibroURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path(LibroResource.class, "getLibro").build(Libroid);
		Link link = new Link();
		link.setUri(LibroURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + Libroid);
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

	// Users

	public static final Link buildURIUsers(UriInfo uriInfo, String rel) {
		URI uriUsers;

		uriUsers = uriInfo.getBaseUriBuilder().path(UserResource.class).build();

		Link self = new Link();
		self.setUri(uriUsers.toString());
		self.setRel(rel);
		self.setTitle("Users collection");
		self.setType(MediaType.LIBROS_API_USER_COLLECTION);

		return self;
	}

	public static final Link buildTemplatedURIUsers(UriInfo uriInfo, String rel) {

		return buildTemplatedURIUsers(uriInfo, rel, false, false);
	}

	public static final Link buildTemplatedURIUsers(UriInfo uriInfo,
			String rel, boolean name, boolean email)
	// no toman un valor en concreto sino que es una plantilla
	{
		URI uriLibros;
		if (email && name)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("name", "{name}")
					.queryParam("email", "{email}").build();
		else if (name)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("name", "{name}").build();
		else
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("username", "{username}")
					.queryParam("email", "{email}").build();

		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriLibros));
		link.setRel(rel);

		link.setType(MediaType.LIBROS_API_USER_COLLECTION);

		return link;
	}

	public final static Link buildURIUserName(UriInfo uriInfo, String username,
			String rel) {
		URI userURI = uriInfo.getBaseUriBuilder().path(UserResource.class)
				.path(UserResource.class, "getUser").build(username);
		Link link = new Link();
		link.setUri(userURI.toString());
		link.setRel("self");
		link.setTitle("User " + username);
		link.setType(MediaType.LIBROS_API_USER);

		return link;
	}
}
