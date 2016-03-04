package dad.bibliotecafx.service.items;

import java.util.Date;

import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.service.entidades.SancionEntity;

public class SancionItem {
	
	private PrestamoItem prestamo;
	private LibroItem libro;
	private Date fechaAlta;
	private Date fechaFinalizacion;
	
	public PrestamoItem getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(PrestamoItem prestamo) {
		this.prestamo = prestamo;
	}

	public LibroItem getLibro() {
		return libro;
	}

	public void setLibro(LibroItem libro) {
		this.libro = libro;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	public SancionEntity toEntity(){
		SancionEntity se = new SancionEntity();		
		se.setFechaAlta(getFechaAlta());
		se.setFechaFinalizacion(getFechaFinalizacion());
		se.setLibro(getLibro().toEntity());
		se.setPrestamo(getPrestamo().toEntity());
		return se;
	}
	
	public Sancion toModel(){
		Sancion se = new Sancion();		
		se.setFechaAlta(getFechaAlta());
		se.setFechaFinalizacion(getFechaFinalizacion());
		se.setLibro(getLibro().toModel());
		se.setPrestamo(getPrestamo().toModel());
		return se;
	}	
}
