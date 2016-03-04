package dad.bibliotecafx.service.items;

import java.util.HashSet;
import java.util.Set;

import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.service.entidades.AutorEntity;
import dad.bibliotecafx.service.entidades.LibroEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;

public class LibroItem {

	private String ISBN;
	private String titulo;
	private EditorialItem editorial;
	private Integer anioPublicacion;	
	private Set<AutorItem> autores = new HashSet<AutorItem>();
			
	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public EditorialItem getEditorial() {
		return editorial;
	}

	public void setEditorial(EditorialItem editorial) {
		this.editorial = editorial;
	}

	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public Set<AutorItem> getAutores() {
		return autores;
	}

	public void setAutores(Set<AutorItem> autores) {
		this.autores = autores;
	}	
	
	public LibroEntity toEntity(){
		LibroEntity le = new LibroEntity();		
		le.setEditorial(getEditorial().toEntity());
		le.setAnioPublicacion(getAnioPublicacion());
		le.setISBN(getISBN());
		le.setTitulo(getTitulo());		
		Set<AutorEntity> autorList = new HashSet<AutorEntity>();
		for (AutorItem a : getAutores()) {
			autorList.add(a.toEntity());
		}			
		le.setAutores(autorList);
		return le;
	}
	
	public Libro toModel(){
		Libro le = new Libro();		
		le.setEditorial(getEditorial().toModel());
		le.setAnioPublicacion(getAnioPublicacion());
		le.setISBN(getISBN());
		le.setTitulo(getTitulo());		
		ObservableSet<Autor> autorList = FXCollections.observableSet(new HashSet<Autor>());
		for (AutorItem a : getAutores()) {
			autorList.add(a.toModel());
		}			
		le.setAutores(autorList);
		return le;
	}
}
