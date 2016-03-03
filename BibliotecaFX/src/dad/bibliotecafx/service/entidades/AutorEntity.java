package dad.bibliotecafx.service.entidades;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.AutorItem;


@SuppressWarnings("serial")
@Entity
@Table(name="Autor")
public class AutorEntity implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="codAutor")	
	private Long codigo;
	@Column(columnDefinition="VARCHAR(100)")
	private String nombre;
	
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
		AutorEntity other = (AutorEntity) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public AutorItem toItem() {
		AutorItem autor = new AutorItem();
		autor.setCodigo(this.getCodigo());
		autor.setNombre(this.getNombre());
		return autor;
	}
	
}
	
