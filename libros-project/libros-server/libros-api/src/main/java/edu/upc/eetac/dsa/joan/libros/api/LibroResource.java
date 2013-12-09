package edu.upc.eetac.dsa.joan.libros.api;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.model.Libro;
import edu.upc.eetac.dsa.joan.libros.api.model.LibroCollection;


@Path("/libros")
public class LibroResource {

	@Context
	// para que nos inyecte la uri info
	private UriInfo uriInfo;

	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();
	
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
				libro.setAutor(rs.getString("autor"));	
				libro.setEdicion(rs.getString("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setLengua(rs.getString("lengua"));
				libro.setTitulo(rs.getString("titulo"));
			//	libro.add(LibrosAPILinkBuilder.buildURILibroId(uriInfo,rs.getString("username"), rel));
				libros.add(libro);	
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}
		finally {
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
				libro.setAutor(rs.getString("autor"));	
				libro.setEdicion(rs.getString("edicion"));
				libro.setEditorial(rs.getString("editorial"));
				libro.setFecha_ed(rs.getDate("fecha_ed"));
				libro.setFecha_imp(rs.getDate("fecha_imp"));
				libro.setLengua(rs.getString("lengua"));
				libro.setTitulo(rs.getString("titulo"));
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		finally {
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
	@Path("/{titulo}")
	public void deleteLibro(@PathParam("titulo") String titulo) {
	
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM libros WHERE titulo='" + titulo + "'";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
		}

		finally {
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
}