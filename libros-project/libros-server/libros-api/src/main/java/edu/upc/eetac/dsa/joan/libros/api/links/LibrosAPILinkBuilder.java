package edu.upc.eetac.dsa.joan.libros.api.links;

import java.net.URI;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.LibroResource;
import edu.upc.eetac.dsa.joan.libros.api.LibrosRootAPIResource;
import edu.upc.eetac.dsa.joan.libros.api.MediaType;
import edu.upc.eetac.dsa.joan.libros.api.ResenaResource;
import edu.upc.eetac.dsa.joan.libros.api.UserResource;
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
		link.setTitle("Libros API"); // descripción
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
		return buildURILibros(uriInfo, null, null, null, null, rel);
	}

	public static final Link buildURILibros(UriInfo uriInfo, String offset,
			String length, String titulo, String autor, String rel) {
		URI uriLibros = null;
		if (offset == null && length == null)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.build();
		else {
			if (titulo == null && autor == null)
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length).build();
			else if (titulo == null)
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("autor", autor).build();
			else if (autor == null)
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("titulo", titulo).build();
			else
				uriLibros = uriInfo.getBaseUriBuilder()
						.path(LibroResource.class).queryParam("offset", offset)
						.queryParam("length", length)
						.queryParam("titulo", titulo)
						.queryParam("autor", autor).build();
		}
		Link self = new Link();
		self.setUri(uriLibros.toString());
		self.setRel(rel);
		self.setTitle("Libros collection");
		self.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);
		return self;
	}

	public static final Link buildTemplatedURILibros(UriInfo uriInfo, String rel) {
		return buildTemplatedURILibros(uriInfo, rel, false, false);
	}

	public static final Link buildTemplatedURILibros(UriInfo uriInfo,
			String rel, boolean titulo, boolean autor) {
		URI uriLibros;
		if (titulo && autor)
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("titulo", "{titulo}")
					.queryParam("autor", "{autor}").build();
		else if (titulo && !autor) {
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("titulo", "{titulo}").build();
		} else if (!titulo && autor) {
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}")
					.queryParam("autor", "{autor}").build();
		} else
			uriLibros = uriInfo.getBaseUriBuilder().path(LibroResource.class)
					.queryParam("offset", "{offset}")
					.queryParam("length", "{length}").build();

		Link link = new Link();
		link.setUri(URITemplateBuilder.buildTemplatedURI(uriLibros));
		link.setRel(rel);
		if (titulo && autor)
			link.setTitle("Libros collection resource filtered by {titulo} or {autor}");
		else if (titulo && !autor)
			link.setTitle("Libros collection resource filtered by {titulo}");
		else if (!titulo && autor)
			link.setTitle("Libros collection resource filtered by {autor}");
		else
			link.setTitle("Libros collection resource");
		link.setType(MediaType.LIBROS_API_LIBRO_COLLECTION);
		return link;
	}

	public final static Link buildURILibro(UriInfo uriInfo, Libro Libro) {
		URI stingURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.build();
		Link link = new Link();
		link.setUri(stingURI.toString());
		link.setRel("self");
		link.setTitle("Libro " + Libro.getId());
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

	public final static Link buildURILibroId(UriInfo uriInfo, int i, String rel) {
		URI LibroURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path(LibroResource.class, "getLibro").build(i);
		Link link = new Link();
		link.setUri(LibroURI.toString());
		link.setRel(rel);
		link.setTitle("Libro " + i);
		link.setType(MediaType.LIBROS_API_LIBRO);

		return link;
	}

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

	public static final Link buildURIResenas(UriInfo uriInfo, String rel) {
		URI uriResenas;

		uriResenas = uriInfo.getBaseUriBuilder().path(ResenaResource.class)
				.build();

		Link self = new Link();
		self.setUri(uriResenas.toString());
		self.setRel(rel);
		self.setTitle("Resenas collection");
		self.setType(MediaType.LIBROS_API_RESENA_COLLECTION);

		return self;
	}

	public final static Link buildURIResenas(UriInfo uriInfo, String idlibro,
			String rel) {
		URI resenaURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path("/" + idlibro).path("/resenas").build();

		Link link = new Link();
		link.setUri(resenaURI.toString());
		link.setRel("self");
		link.setTitle("Idresena " + idlibro);
		link.setType(MediaType.LIBROS_API_RESENA);

		return link;
	}

	public final static Link buildURIResenaId(UriInfo uriInfo, String rel,
			int idres, String idlibro) {

		URI resenaURI = uriInfo.getBaseUriBuilder().path(LibroResource.class)
				.path("/" + idlibro).path("/resenas").path("/" + idres).build();
		Link link = new Link();
		link.setUri(resenaURI.toString());
		link.setRel(rel);
		link.setTitle("Idresena " + idres);
		link.setType(MediaType.LIBROS_API_RESENA);
		return link;
	}

}