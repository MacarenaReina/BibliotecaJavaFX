package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.EditorialItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Editorial")
public class EditorialEntity implements Serializable {
	
	@Id
	@GeneratedValue
	private Long codigo;
	@Column(columnDefinition="VARCHAR(50)")
	private String nombre;	
	@OneToMany(mappedBy="editorial", fetch=FetchType.LAZY)	
	private List<LibroEntity> libros = new ArrayList<LibroEntity>();
		
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<LibroEntity> getLibros() {
		return libros;
	}

	public void setLibros(List<LibroEntity> libros) {
		this.libros = libros;
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
		EditorialEntity other = (EditorialEntity) obj;
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
		return e;		
	}	
}
