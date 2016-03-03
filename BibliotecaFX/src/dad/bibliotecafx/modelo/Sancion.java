package dad.bibliotecafx.modelo;

import java.util.Date;

import dad.bibliotecafx.service.items.SancionItem;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Sancion {
	
	private final ObjectProperty<Prestamo> prestamo = new SimpleObjectProperty<Prestamo>(this, "prestamo");
	private final ObjectProperty<Libro> libro = new SimpleObjectProperty<Libro>(this, "libro");
	private final ObjectProperty<Date> fechaAlta = new SimpleObjectProperty<Date>(this, "fechaAlta");
	private final ObjectProperty<Date> fechaFinalizacion = new SimpleObjectProperty<Date>(this, "fechaFinalizacion");

	public final ObjectProperty<Prestamo> prestamoProperty() {
		return this.prestamo;
	}	

	public final dad.bibliotecafx.modelo.Prestamo getPrestamo() {
		return this.prestamoProperty().get();
	}	

	public final void setPrestamo(final dad.bibliotecafx.modelo.Prestamo prestamo) {
		this.prestamoProperty().set(prestamo);
	}	

	public final ObjectProperty<Libro> libroProperty() {
		return this.libro;
	}	

	public final dad.bibliotecafx.modelo.Libro getLibro() {
		return this.libroProperty().get();
	}	

	public final void setLibro(final dad.bibliotecafx.modelo.Libro libro) {
		this.libroProperty().set(libro);
	}	

	public final ObjectProperty<Date> fechaAltaProperty() {
		return this.fechaAlta;
	}	

	public final java.util.Date getFechaAlta() {
		return this.fechaAltaProperty().get();
	}	

	public final void setFechaAlta(final java.util.Date fechaAlta) {
		this.fechaAltaProperty().set(fechaAlta);
	}	

	public final ObjectProperty<Date> fechaFinalizacionProperty() {
		return this.fechaFinalizacion;
	}	

	public final java.util.Date getFechaFinalizacion() {
		return this.fechaFinalizacionProperty().get();
	}	

	public final void setFechaFinalizacion(final java.util.Date fechaFinalizacion) {
		this.fechaFinalizacionProperty().set(fechaFinalizacion);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((libro.get() == null) ? 0 : libro.get().hashCode());
		result = prime * result + ((prestamo.get() == null) ? 0 : prestamo.get().hashCode());
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
		Sancion other = (Sancion) obj;
		if (libro.get() == null) {
			if (other.libro.get() != null)
				return false;
		} else if (!libro.get().equals(other.libro.get()))
			return false;
		if (prestamo.get() == null) {
			if (other.prestamo.get() != null)
				return false;
		} else if (!prestamo.get().equals(other.prestamo.get()))
			return false;
		return true;
	}
	
	public SancionItem toItem(){
		SancionItem s = new SancionItem();		
		s.setFechaAlta(getFechaAlta());
		s.setFechaFinalizacion(getFechaFinalizacion());
		s.setLibro(getLibro().toItem());
		s.setPrestamo(getPrestamo().toItem());		
		return s;
	}
}
