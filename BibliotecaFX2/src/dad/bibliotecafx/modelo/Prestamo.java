package dad.bibliotecafx.modelo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

public class Prestamo {
	
	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");
	private final SetProperty<Libro> libro = new SimpleSetProperty<Libro>(FXCollections.observableSet(new HashSet<Libro>()));
	private final ObjectProperty<Usuario> usuario = new SimpleObjectProperty<Usuario>(this, "usuario");
	private final ObjectProperty<Date> fechaPrestamo = new SimpleObjectProperty<Date>(this, "fechaPrestamo");
	private final ObjectProperty<Date> fechaDevolucion = new SimpleObjectProperty<Date>(this, "fechaDevolucion");

	public final LongProperty codigoProperty() {
		return this.codigo;
	}	

	public final long getCodigo() {
		return this.codigoProperty().get();
	}	

	public final void setCodigo(final long codigo) {
		this.codigoProperty().set(codigo);
	}

	public final SetProperty<Libro> libroProperty() {
		return this.libro;
	}	

	public final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Libro> getLibro() {
		return this.libroProperty().get();
	}	

	public final void setLibro(final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Libro> libro) {
		this.libroProperty().set(libro);
	}	

	public final ObjectProperty<Usuario> usuarioProperty() {
		return this.usuario;
	}	

	public final dad.bibliotecafx.modelo.Usuario getUsuario() {
		return this.usuarioProperty().get();
	}	

	public final void setUsuario(final dad.bibliotecafx.modelo.Usuario usuario) {
		this.usuarioProperty().set(usuario);
	}	

	public final ObjectProperty<Date> fechaPrestamoProperty() {
		return this.fechaPrestamo;
	}	

	public final java.util.Date getFechaPrestamo() {
		return this.fechaPrestamoProperty().get();
	}	

	public final void setFechaPrestamo(final java.util.Date fechaPrestamo) {
		this.fechaPrestamoProperty().set(fechaPrestamo);
	}	

	public final ObjectProperty<Date> fechaDevolucionProperty() {
		return this.fechaDevolucion;
	}
	
	public final java.util.Date getFechaDevolucion() {
		return this.fechaDevolucionProperty().get();
	}	

	public final void setFechaDevolucion(final java.util.Date fechaDevolucion) {
		this.fechaDevolucionProperty().set(fechaDevolucion);
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
		Prestamo other = (Prestamo) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	public PrestamoItem toItem() {
		PrestamoItem p = new PrestamoItem();
		p.setCodigo(getCodigo());
		p.setFechaDevolucion(getFechaDevolucion());
		p.setFechaPrestamo(getFechaPrestamo());
		p.setUsuario(getUsuario().toItem());
		Set<LibroItem> libros = new HashSet<LibroItem>();
		for (Libro libro : getLibro()) {
			libros.add(libro.toItem());
		}
		p.setLibro(libros);
		return p;
	}	
	
	@Override
	public String toString() {
		return String.valueOf(codigo.get());
	}
}
