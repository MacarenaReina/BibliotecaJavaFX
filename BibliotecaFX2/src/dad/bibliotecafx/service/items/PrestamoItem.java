package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.service.entidades.LibroEntity;
import dad.bibliotecafx.service.entidades.PrestamoEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class PrestamoItem {

	private Long codigo;
	private Set<LibroItem> libro = new HashSet<LibroItem>();
	private UsuarioItem usuario;
	private Date fechaPrestamo;
	private Date fechaDevolucion;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Set<LibroItem> getLibro() {
		return libro;
	}

	public void setLibro(Set<LibroItem> libro) {
		this.libro = libro;
	}

	public UsuarioItem getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioItem usuario) {
		this.usuario = usuario;
	}

	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}

	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}

	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}

	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}

	public PrestamoEntity toEntity() {
		PrestamoEntity pe = new PrestamoEntity();
		pe.setCodigo(getCodigo());
		pe.setFechaDevolucion(getFechaDevolucion());
		pe.setFechaPrestamo(getFechaPrestamo());
		pe.setUsuario(getUsuario().toEntity());
		Set<LibroEntity> libros = new HashSet<LibroEntity>();
		for (LibroItem libro : getLibro()) {
			libros.add(libro.toEntity());
		}
		pe.setLibro(libros);
		return pe;
	}
	
	public Prestamo toModel() {
		Prestamo pe = new Prestamo();
		pe.setCodigo(getCodigo());
		pe.setFechaDevolucion(getFechaDevolucion());
		pe.setFechaPrestamo(getFechaPrestamo());
		pe.setUsuario(getUsuario().toModel());
		ObservableSet<Libro> libros = FXCollections.observableSet(new HashSet<Libro>());
		for (LibroItem libro : getLibro()) {
			libros.add(libro.toModel());
		}
		pe.setLibro(libros);
		return pe;
	}

}
