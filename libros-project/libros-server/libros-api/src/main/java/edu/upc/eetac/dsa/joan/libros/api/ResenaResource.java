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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import edu.upc.eetac.dsa.joan.libros.api.links.LibrosAPILinkBuilder;
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
			sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idlibro= '"
					+ idlibro + "' and users.username=resenas.username) ";
			ResultSet rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				throw new ResenaNotFoundException();
			}
			//rs.previous();
			while (rs.next()) {
				Resena resena = new Resena();
				resena.setUsername(rs.getString("username"));
				resena.setName(rs.getString("name"));
				resena.setFecha(rs.getDate("fecha"));
				resena.setTexto(rs.getString("texto"));
				resena.setIdlibro(rs.getInt("idlibro"));
				resena.setIdres(rs.getInt("idres"));
		//		resena.add(LibrosAPILinkBuilder.buildURIResenas(uriInfo, "self",idlibro));
						resenas.add(resena);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			update = "INSERT INTO resenas (idlibro,username,texto) VALUES ('"
					+ idlibro + "','" + username + "' ,'" + resena.getTexto()
					+ "')";
			stmt.executeUpdate(update, Statement.RETURN_GENERATED_KEYS);
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				int idres = rs.getInt(1);
				rs.close();
				String sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idres= '"
						+ idres + "' and users.username=resenas.username) ";
				rs = stmt.executeQuery(sql);

				if (rs.next()) {
					resena.setIdres(rs.getInt("idres"));
					resena.setIdlibro(rs.getInt("idlibro"));
					resena.setUsername(rs.getString("username"));
					resena.setName(rs.getString("name"));
					resena.setFecha(rs.getDate("fecha"));
					resena.setTexto(rs.getString("texto"));
		//			resena.add(LibrosAPILinkBuilder.buildURIResenaId(uriInfo, "self",resena.getIdres(),idlibro));
				}
			} else
				throw new ResenaNotFoundException();
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
		String username;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;
		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}
		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas WHERE idres='" + idres + "'";
			rs = stmt.executeQuery(sql);
			if (!rs.next()) {
				throw new ResenaNotFoundException();
			}
		//	rs.next();
			username = rs.getString("username");

		} catch (SQLException e) {
			throw new ResenaNotFoundException();
		}
		if (security.getUserPrincipal().getName().equals(username)) {

			try {
				stmt = conn.createStatement();
				sql = "DELETE FROM resenas WHERE idres='" + idres
						+ "'and username='" + username + "'";
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
		} else {
			throw new NotAllowedException();
		}
	}

	@PUT
	@Path("/{idres}")
	@Consumes(MediaType.LIBROS_API_RESENA)
	@Produces(MediaType.LIBROS_API_RESENA)
	public Resena updateResena(@PathParam("idres") String idres, Resena resena) {

		String username;
		Connection conn = null;
		Statement stmt = null;
		String sql;
		ResultSet rs;

		try {
			conn = ds.getConnection();
		} catch (SQLException e) {
			throw new ServiceUnavailableException(e.getMessage());
		}

		try {
			stmt = conn.createStatement();
			sql = "SELECT * FROM resenas WHERE idres='" + idres + "'";
			rs = stmt.executeQuery(sql);
			rs.next();
			username = rs.getString("username");

		} catch (SQLException e) {
			throw new ResenaNotFoundException();
		}

		if (security.getUserPrincipal().getName().equals(username)) {
			try {
				stmt = conn.createStatement();
				String update = null;
				update = "UPDATE resenas SET resenas.texto ='"
						+ resena.getTexto() + "'WHERE idres='" + idres + "'";
				int rows = stmt.executeUpdate(update,
						Statement.RETURN_GENERATED_KEYS);
				if (rows != 0) {

					sql = "SELECT resenas.*, users.name FROM users INNER JOIN resenas ON(idres= '"
							+ idres + "' and users.username=resenas.username) ";
					rs = stmt.executeQuery(sql);
					if (rs.next()) {
						resena.setIdres(rs.getInt("idres"));
						resena.setIdlibro(rs.getInt("idlibro"));
						resena.setUsername(rs.getString("username"));
						resena.setName(rs.getString("name"));
						resena.setFecha(rs.getDate("fecha"));
						resena.setTexto(rs.getString("texto"));
						
					}
				} else
					throw new ResenaNotFoundException();
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
		} else {
		}
		return resena;
	}
}
