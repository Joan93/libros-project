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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.model.Libro;
import edu.upc.eetac.dsa.joan.libros.api.model.Resena;
import edu.upc.eetac.dsa.joan.libros.api.model.ResenaCollection;

@Path("/resenas")
public class ResenaResource {


	@Context
	// para que nos inyecte la uri info
	private UriInfo uriInfo;
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@GET
	@Produces(MediaType.LIBROS_API_RESENA_COLLECTION)
	public ResenaCollection getResenas() {
		
		ResenaCollection resenas = new ResenaCollection();

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
			sql = "SELECT * FROM resenas";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Resena resena = new Resena();
				resena.setUsername(rs.getString("username"));	
				resena.setName(rs.getString("name"));
				resena.setFecha(rs.getDate("fecha"));
				resena.setTexto(rs.getString("texto"));
				resena.setIdlibro(rs.getInt("idlibro"));
		//		resena.setTitulolibro(rs.getString("titulolibro"));
				resenas.add(resena);	
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
		return resenas;
	}
	
	
	
	@GET
	@Path("/{titulolibro}")
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena getResena(@PathParam("titulolibro") String titulolibro) {
		Resena resena = new Resena();
		
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
			sql = "SELECT * FROM resenas WHERE titulolibro= '" + titulolibro + "'";

			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				resena.setUsername(rs.getString("username"));
				resena.setName(rs.getString("name"));
				resena.setFecha(rs.getDate("fecha"));
				resena.setTexto(rs.getString("texto"));
				resena.setIdlibro(rs.getInt("idlibro"));
			//	resena.setTitulolibro(rs.getString("titulolibro"));
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
		return resena;
	}
	
	@POST
	@Path("/{idlibro}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena createLibro(@PathParam("idlibro") String idlibro,Resena resena) {
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
			
			/*
			update = "INSERT INTO resenas (username,name,fecha,texto) VALUES ('"
					+ resena.getUsername() + "','" + resena.getName() + "','" + resena.getFecha() + "','"
					+ resena.getTexto() + "')";
			*/ 
			
			
			update = "INSERT INTO resenas (username,name,fecha,texto,idlibro) VALUES ('"
					+ resena.getUsername() + "','" + resena.getName() + "','" + resena.getFecha() +  "','" + resena.getTexto() + "','"
					+ idlibro + "')";
			
			
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);

			if (rows != 0) {
				String sql = "SELECT * FROM resenas WHERE idlibro='"
						+ resena.getIdlibro() + "'";
				ResultSet rs = stmt.executeQuery(sql);
				rs.next();
				
			} else
				throw new ResenaNotFoundException();
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
		return resena;
	}
	
	
	
	@DELETE
	@Path("/{idlibro}")
	public void deleteResena(@PathParam("idlibro") String idlibro) {

		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			String sql = "DELETE FROM resenas WHERE idlibro='" + idlibro + "'";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM resenas WHERE idlibro='" + idlibro + "'";
			stmt.executeUpdate(sql);

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
	}

}
