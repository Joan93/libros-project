package edu.upc.eetac.dsa.joan.libros.api.model;

import java.sql.Date;

public class Libro {

	public String autor;
	public String lengua;
	public String edicion;
	public Date fecha_ed;
	public Date fecha_imp;
	public String editorial;
	public String titulo;

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getLengua() {
		return lengua;
	}

	public void setLengua(String lengua) {
		this.lengua = lengua;
	}

	public String getEdicion() {
		return edicion;
	}

	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}

	public Date getFecha_ed() {
		return fecha_ed;
	}

	public void setFecha_ed(Date fecha_ed) {
		this.fecha_ed = fecha_ed;
	}

	public Date getFecha_imp() {
		return fecha_imp;
	}

	public void setFecha_imp(Date fecha_imp) {
		this.fecha_imp = fecha_imp;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
