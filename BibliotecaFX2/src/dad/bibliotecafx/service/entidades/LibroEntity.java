package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.AutorItem;
import dad.bibliotecafx.service.items.LibroItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Libro")
public class LibroEntity implements Serializable {
	
	@Id
	@Column(columnDefinition="VARCHAR(20)", name="ISBN")
	private String ISBN;
	@Column(columnDefinition="VARCHAR(200)")
	private String titulo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="editorial")
	private EditorialEntity editorial;
	@Column(columnDefinition="INTEGER")
	private Integer anioPublicacion;
	@Column(columnDefinition="INTEGER")
	private Integer cantidad;
	@ManyToMany(fetch =FetchType.LAZY)  
	@JoinTable(name="Autor_Libro", joinColumns=@JoinColumn(name="ISBN"), inverseJoinColumns=@JoinColumn(name="codAutor")) 
	private Set<AutorEntity> autores = new HashSet<AutorEntity>();
	
	
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

	public EditorialEntity getEditorial() {
		return editorial;
	}

	public void setEditorial(EditorialEntity editorial) {
		this.editorial = editorial;
	}

	public Set<AutorEntity> getAutores() {
		return autores;
	}

	public void setAutores(Set<AutorEntity> autores) {
		this.autores = autores;
	}

	public void setAnioPublicacion(Integer anioPublicacion) {
		this.anioPublicacion = anioPublicacion;
	}

	public Integer getAnioPublicacion() {
		return anioPublicacion;
	}
	
	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ISBN == null) ? 0 : ISBN.hashCode());
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
		LibroEntity other = (LibroEntity) obj;
		if (ISBN == null) {
			if (other.ISBN != null)
				return false;
		} else if (!ISBN.equals(other.ISBN))
			return false;
		return true;
	}
	
	public LibroItem toItem() {
		LibroItem l = new LibroItem();		
		l.setEditorial(getEditorial().toItem());
		l.setISBN(getISBN());
		l.setTitulo(getTitulo());
		l.setAnioPublicacion(getAnioPublicacion());
		l.setCantidad(getCantidad());
		Set<AutorItem> autores = new HashSet<AutorItem>();
		for (AutorEntity autor : getAutores()) {
			autores.add(autor.toItem());
		}
		l.setAutores(autores);
		return l;		
	}
	
}
