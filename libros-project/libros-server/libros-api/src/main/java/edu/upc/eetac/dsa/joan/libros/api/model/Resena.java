package edu.upc.eetac.dsa.joan.libros.api.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.joan.libros.api.links.Link;

public class Resena {
	
	public int idres;
	public int idlibro;
	public String username;
	public String name;
	public Date fecha;
	public String texto;
	private List<Link> links = new ArrayList<Link>();
	
	public int getIdres() {
		return idres;
	}
	public void setIdres(int idres) {
		this.idres = idres;
	}
	public int getIdlibro() {
		return idlibro;
	}
	public void setIdlibro(int idlibro) {
		this.idlibro = idlibro;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}	
	public void add(Link link) {
		links.add(link);
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
}
