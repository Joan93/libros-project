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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.model.Libro;
import edu.upc.eetac.dsa.joan.libros.api.model.Resena;
import edu.upc.eetac.dsa.joan.libros.api.model.ResenaCollection;

@Path("/libros/{idlibro}/resenas")
public class ResenaResource {

	@Context
	// para que nos inyecte la uri info
	private UriInfo uriInfo;
	@Context
	private SecurityContext security;
	private DataSource ds = DataSourceSPA.getInstance().getDataSource();

	@GET
	@Produces(MediaType.LIBROS_API_RESENA_COLLECTION)
	public ResenaCollection getResenas(@PathParam("idlibro") String idlibro) {
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
			sql = "SELECT * FROM resenas where idlibro='" + idlibro + "'";

			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Resena resena = new Resena();
				resena.setUsername(rs.getString("username"));
				resena.setName(rs.getString("name"));
				resena.setFecha(rs.getDate("fecha"));
				resena.setTexto(rs.getString("texto"));
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setIdres(rs.getInt("idres"));
				resenas.add(resena);
			}
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
		return resenas;
	}

	@POST
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena createResena(@PathParam("idlibro") String idlibro,
			Resena resena) {
		String username = security.getUserPrincipal().getName();

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
			update = "INSERT INTO resenas (idlibro,username,name,fecha,texto) VALUES ('"
					+ idlibro
					+ "','"
					+ username
					+ "','"
					+ resena.getName()
					+ "','"
					+ resena.getFecha()
					+ "','"
					+ resena.getTexto() + "')";
			int rows = stmt.executeUpdate(update,
					Statement.RETURN_GENERATED_KEYS);
			if (rows != 0) {
				String sql = "SELECT * FROM resenas WHERE idlibro='" + idlibro
						+ "'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					resena.setIdres(rs.getInt("idres"));
					resena.setIdlibro(rs.getInt("idlibro"));
					resena.setUsername(rs.getString("username"));
					resena.setName(rs.getString("name"));
					resena.setFecha(rs.getDate("fecha"));
					resena.setTexto(rs.getString("texto"));
					resena.setUsername(username);
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

		return resena;
	}

	@DELETE
	@Path("/{idres}")
	public void deleteResena(@PathParam("idres") String idres) {

		try {
			Connection conn = null;
			Statement stmt = null;
			String sql;
			conn = ds.getConnection();
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas where idres='" + idres + "'";
			ResultSet rs = stmt.executeQuery(sql);
			Resena resena = new Resena();
			resena.setUsername(rs.getString("username"));

			if (security.isUserInRole("registered")) {
				if (!security.getUserPrincipal().getName()
						.equals(resena.getUsername())) {
					throw new ForbiddenException("You are not allowed");
				}
			}
		} catch (SQLException e) {
			throw new InternalServerException(e.getMessage());
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
			String sql = "DELETE FROM resenas WHERE idres='" + idres + "'";
			stmt.executeUpdate(sql);
			sql = "DELETE FROM resenas WHERE idres='" + idres + "'";
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
