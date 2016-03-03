package dad.bibliotecafx.modelo;

import java.util.HashSet;

import dad.bibliotecafx.service.items.AutorItem;
import dad.bibliotecafx.service.items.LibroItem;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;


public class Libro {
	
	private final StringProperty ISBN = new SimpleStringProperty(this, "ISBN");
	private final StringProperty titulo = new SimpleStringProperty(this, "titulo");
	private final ObjectProperty<Editorial> editorial = new SimpleObjectProperty<Editorial>(this, "editorial");
	private final IntegerProperty anioPublicacion = new SimpleIntegerProperty(this, "anioPublicacion");
	private final SetProperty<Autor> autores = new SimpleSetProperty<Autor>(FXCollections.observableSet(new HashSet<Autor>()));
	
	
	public final StringProperty ISBNProperty() {
		return this.ISBN;
	}	

	public final java.lang.String getISBN() {
		return this.ISBNProperty().get();
	}	

	public final void setISBN(final java.lang.String ISBN) {
		this.ISBNProperty().set(ISBN);
	}	

	public final StringProperty tituloProperty() {
		return this.titulo;
	}	

	public final java.lang.String getTitulo() {
		return this.tituloProperty().get();
	}	

	public final void setTitulo(final java.lang.String titulo) {
		this.tituloProperty().set(titulo);
	}	

	public final ObjectProperty<Editorial> editorialProperty() {
		return this.editorial;
	}	

	public final dad.bibliotecafx.modelo.Editorial getEditorial() {
		return this.editorialProperty().get();
	}	

	public final void setEditorial(final dad.bibliotecafx.modelo.Editorial editorial) {
		this.editorialProperty().set(editorial);
	}	

	public final SetProperty<Autor> autoresProperty() {
		return this.autores;
	}	

	public final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Autor> getAutores() {
		return this.autoresProperty().get();
	}	

	public final void setAutores(final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Autor> autores) {
		this.autoresProperty().set(autores);
	}	

	public final IntegerProperty anioPublicacionProperty() {
		return this.anioPublicacion;
	}	

	public final int getAnioPublicacion() {
		return this.anioPublicacionProperty().get();
	}
	

	public final void setAnioPublicacion(final int anioPublicacion) {
		this.anioPublicacionProperty().set(anioPublicacion);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN.get() == null) ? 0 : ISBN.get().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Libro other = (Libro) obj;
		if (ISBN.get() == null) {
			if (other.ISBN.get() != null)
				return false;
		} else if (!ISBN.get().equals(other.ISBN.get()))
			return false;
		return true;
	}
	
	public LibroItem toItem() {
		LibroItem l = new LibroItem();		
		l.setEditorial(getEditorial().toItem());
		l.setISBN(getISBN());
		l.setTitulo(getTitulo());
		l.setAnioPublicacion(getAnioPublicacion());
		ObservableSet<AutorItem> autoresList = FXCollections.observableSet(new HashSet<AutorItem>());
		for (Autor a : getAutores()) {
			autoresList.add(a.toItem());
		}		
		l.setAutores(autoresList);
		return l;		
	}

}
