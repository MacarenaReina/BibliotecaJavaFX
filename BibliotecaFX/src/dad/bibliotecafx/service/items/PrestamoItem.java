package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dad.bibliotecafx.service.entidades.LibroEntity;
import dad.bibliotecafx.service.entidades.PrestamoEntity;

public class PrestamoItem {

	private Long codigo;
	private List<LibroItem> libro = new ArrayList<LibroItem>();
	private UsuarioItem usuario;
	private Date fechaPrestamo;
	private Date fechaDevolucion;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List<LibroItem> getLibro() {
		return libro;
	}

	public void setLibro(List<LibroItem> libro) {
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
		List<LibroEntity> libros = new ArrayList<LibroEntity>();
		for (LibroItem l : getLibro()) {
			libros.add(l.toEntity());
		}
		pe.setLibro(libros);
		return pe;
	}

}
