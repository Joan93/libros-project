package edu.upc.eetac.dsa.joan.libros.api.model;

import java.util.ArrayList;
import java.util.List;

import edu.upc.eetac.dsa.joan.libros.api.links.Link;

public class LibrosRootAPI {
	
	private List<Link> links = new ArrayList<Link>();
	// creamos una lista de links y la llamamos links
	public void addLink(Link l) {
		links.add(l);
	}
	//método para añadir un link "l" a la lista de links
	public List<Link> getLinks() {
		return links;
	}
	//método para devolver los links de la lista 
	public void setLinks(List<Link> l) {
		this.links = l;
	}
	//dar valores a los links de la lista
}
