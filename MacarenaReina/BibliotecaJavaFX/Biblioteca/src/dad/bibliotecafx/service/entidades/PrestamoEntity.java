package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("serial")
@Entity
@Table(name="Prestamo")
public class PrestamoEntity implements Serializable {
	
	@Id
	@GeneratedValue
	private Long codigo;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<LibroEntity> libro = new ArrayList<LibroEntity>();
	@ManyToOne
	@JoinColumn(name="usuario")
	private UsuarioEntity usuario;
	@Column(columnDefinition="DATE")
	private Date fechaPrestamo;
	@Column(columnDefinition="DATE")
	private Date fechaDevolucion;
		
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public List<LibroEntity> getLibro() {
		return libro;
	}

	public void setLibro(List<LibroEntity> libro) {
		this.libro = libro;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
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
		PrestamoEntity other = (PrestamoEntity) obj;
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
		ObservableList<LibroItem> librosList = FXCollections.observableArrayList(new ArrayList<LibroItem>());
		for (LibroEntity l : getLibro()) {
			librosList.add(l.toItem());
		}
		p.setLibro(librosList);
		return p;
	}
}
