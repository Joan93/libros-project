package edu.upc.eetac.dsa.joan.libros.api.model;
import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.joan.libros.api.links.Link;

public class ResenaCollection {

	private List<Resena> resenas = new ArrayList<Resena>();
	private List<Link> links = new ArrayList<Link>();
	
	public List<Resena> getResena() {
		return resenas;
	}
	public void setReseas(List<Resena> resenas) {
		this.resenas = resenas;
	}
	public void add(Resena resena) {
		resenas.add(resena);
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
