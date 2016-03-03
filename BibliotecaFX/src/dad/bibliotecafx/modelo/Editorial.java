package dad.bibliotecafx.modelo;

import java.util.ArrayList;

import dad.bibliotecafx.service.items.EditorialItem;
import dad.bibliotecafx.service.items.LibroItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Editorial {
	
	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");
	private final StringProperty nombre = new SimpleStringProperty(this, "nombre");		
	private final ListProperty<Libro> libros = new SimpleListProperty<Libro>(FXCollections.observableArrayList(new ArrayList<Libro>()));
	
	public final LongProperty codigoProperty() {
		return this.codigo;
	}
	
	public final long getCodigo() {
		return this.codigoProperty().get();
	}
	
	public final void setCodigo(final long codigo) {
		this.codigoProperty().set(codigo);
	}
	
	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	
	public final java.lang.String getNombre() {
		return this.nombreProperty().get();
	}
	
	public final void setNombre(final java.lang.String nombre) {
		this.nombreProperty().set(nombre);
	}
	
	public final ListProperty<Libro> librosProperty() {
		return this.libros;
	}
	
	public final javafx.collections.ObservableList<dad.bibliotecafx.modelo.Libro> getLibros() {
		return this.librosProperty().get();
	}
	
	public final void setLibros(final javafx.collections.ObservableList<dad.bibliotecafx.modelo.Libro> libros) {
		this.librosProperty().set(libros);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Editorial other = (Editorial) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public EditorialItem toItem() {
		EditorialItem e = new EditorialItem();		
		e.setCodigo(getCodigo());
		e.setNombre(getNombre());
		ObservableList<LibroItem> librosList = FXCollections.observableArrayList(new ArrayList<LibroItem>());
		for (Libro l : getLibros()) {
			librosList.add(l.toItem());
		}	
		e.setLibros(librosList);
		return e;		
	}
	
}
