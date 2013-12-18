package edu.upc.eetac.dsa.joan.libros.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.links.LibrosAPILinkBuilder;
import edu.upc.eetac.dsa.joan.libros.api.model.Libro;
import edu.upc.eetac.dsa.joan.libros.api.model.LibroCollection;

@Path("/libros")
public class LibroResource {

	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	@Context
	private UriInfo uriInfo;
	@Context
	private SecurityContext security;

	@GET
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getLibros() {

		LibroCollection libros = new LibroCollection();
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM libros";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setId(rs.getInt("id"));
				libro.setAutor(rs.getString("autor"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setLengua(rs.getString("lengua"));
				libro.setTitulo(rs.getString("titulo"));
				libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
                         libro.getId(), "self"));
				libros.add(libro);
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libros;
	}

	@GET
	@Path("/search/titulo/{titulo}")
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getLibrostitulo(@PathParam("titulo") String titulo) {
		LibroCollection libros = new LibroCollection();
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			// String titulo = "llibre";
			sql = "SELECT * FROM libros WHERE titulo LIKE '%" + titulo + "%'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Libro libro = new Libro();
				libro.setId(rs.getInt("id"));
				libro.setAutor(rs.getString("autor"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setLengua(rs.getString("lengua"));
				libro.setTitulo(rs.getString("titulo"));
				libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
                        libro.getId(), "self"));
				libros.add(libro);
			}		
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libros;
	}

	@GET
	@Path("/search")
	@Produces(MediaType.LIBROS_API_LIBRO_COLLECTION)
	public LibroCollection getLibrosautor(@QueryParam("autor") String autor,
			@QueryParam("titulo") String titulo) {
		LibroCollection libros = new LibroCollection();
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			// String autor = "autor3";

			if (autor != null) {
				sql = "SELECT * FROM libros WHERE autor LIKE '%" + autor + "%'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Libro libro = new Libro();
					libro.setId(rs.getInt("id"));
					libro.setAutor(rs.getString("autor"));
					libro.setEdicion(rs.getString("edicion"));
					libro.setEditorial(rs.getString("editorial"));
					libro.setFecha_ed(rs.getDate("fecha_ed"));
					libro.setFecha_imp(rs.getDate("fecha_imp"));
					libro.setLengua(rs.getString("lengua"));
					libro.setTitulo(rs.getString("titulo"));
					libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
	                        libro.getId(), "self"));
					libros.add(libro);
				}
			} else if (titulo != null) {
				sql = "SELECT * FROM libros WHERE titulo LIKE '%" + titulo
						+ "%'";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					Libro libro = new Libro();
					libro.setId(rs.getInt("id"));
					libro.setAutor(rs.getString("autor"));
					libro.setEdicion(rs.getString("edicion"));
					libro.setEditorial(rs.getString("editorial"));
					libro.setFecha_ed(rs.getDate("fecha_ed"));
					libro.setFecha_imp(rs.getDate("fecha_imp"));
					libro.setLengua(rs.getString("lengua"));
					libro.setTitulo(rs.getString("titulo"));
					libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
	                        libro.getId(), "self"));
					libros.add(libro);
				}
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libros;
	}

	@GET
	@Path("/{titulo}")
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro getLibro(@PathParam("titulo") String titulo) {

		Libro libro = new Libro();
		Connection conn = null;
		Statement stmt = null;
		String sql;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM libros WHERE titulo= '" + titulo + "'";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				libro.setId(rs.getInt("id"));
				libro.setAutor(rs.getString("autor"));
				libro.setEdicion(rs.getString("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setLengua(rs.getString("lengua"));
				libro.setTitulo(rs.getString("titulo"));
				libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
                        libro.getId(), "self"));
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libro;
	}

	@DELETE
	@Path("/{id}")
	public void deleteLibro(@PathParam("id") int id) {
		if (security.isUserInRole("registered")) {
			throw new NotAllowedException();
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			String sql2 = "DELETE FROM resenas WHERE idlibro='" + id + "'";
			stmt.executeUpdate(sql2);
			String sql = "DELETE FROM libros WHERE id='" + id + "'";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@POST
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro createLibro(Libro libro) {
		if (security.isUserInRole("registered")) {
			throw new NotAllowedException();
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			String update = null; // TODO: create update query
			update = "INSERT INTO libros (titulo,autor,lengua,edicion,fecha_ed,fecha_imp,editorial) VALUES ('"
					+ libro.getTitulo()
					+ "','"
					+ libro.getAutor()
					+ "','"
					+ libro.getLengua()
					+ "','"
					+ libro.getEdicion()
					+ "','"
					+ libro.getFecha_ed()
					+ "','"
					+ libro.getFecha_imp()
					+ "','" + libro.getEditorial() + "')";
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);
			if (rows != 0) {
				String sql = "SELECT * FROM libros WHERE titulo='"
						+ libro.getTitulo() + "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					libro.setId(rs.getInt("id"));
					libro.setAutor(rs.getString("autor"));
					libro.setEdicion(rs.getString("edicion"));
					libro.setEditorial(rs.getString("editorial"));
					libro.setFecha_ed(rs.getDate("fecha_ed"));
					libro.setFecha_imp(rs.getDate("fecha_imp"));
					libro.setLengua(rs.getString("lengua"));
					libro.setTitulo(rs.getString("titulo"));
					libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
	                        libro.getId(), "self"));
				}
			} else
				throw new UserNotFoundException();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libro;
	}

	@PUT
	@Path("/{id}")
	@Consumes(MediaType.LIBROS_API_LIBRO)
	@Produces(MediaType.LIBROS_API_LIBRO)
	public Libro updateLibro(@PathParam("id") int id, Libro libro) {
		if (security.isUserInRole("registered")) {
			throw new NotAllowedException();
		}
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			String update = null; // TODO: create update query
			update = "UPDATE libros SET libros.autor='" + libro.getAutor()
					+ "', libros.titulo= '" + libro.getTitulo()
					+ "', libros.lengua= '" + libro.getLengua()
					+ "', libros.edicion= '" + libro.getEdicion()
					+ "', libros.fecha_ed= '" + libro.getFecha_ed()
					+ "', libros.fecha_imp= '" + libro.getFecha_imp()
					+ "', libros.editorial= '" + libro.getEditorial()
					+ "' WHERE id='" + id + "'";
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);
			if (rows != 0) {
				String sql = "SELECT * FROM libros WHERE id='" + id + "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					libro.setId(rs.getInt("id"));
					libro.setAutor(rs.getString("autor"));
					libro.setEdicion(rs.getString("edicion"));
					libro.setEditorial(rs.getString("editorial"));
					libro.setFecha_ed(rs.getDate("fecha_ed"));
					libro.setFecha_imp(rs.getDate("fecha_imp"));
					libro.setLengua(rs.getString("lengua"));
					libro.setTitulo(rs.getString("titulo"));
					libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,
	                        libro.getId(), "self"));
				}
			} else
				throw new LibroNotFoundException();
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return libro;
	}
}
