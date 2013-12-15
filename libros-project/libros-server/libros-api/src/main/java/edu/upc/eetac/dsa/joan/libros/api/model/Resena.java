package edu.upc.eetac.dsa.joan.libros.api.model;

import java.sql.Date;

public class Resena {

	public String username;
	public String name;
	public Date fecha;
	public String texto;
	public String titulolibro;
	
	public String getTitulolibro() {
		return titulolibro;
	}
	public void setTitulolibro(String titulolibro) {
		this.titulolibro = titulolibro;
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

	
}
