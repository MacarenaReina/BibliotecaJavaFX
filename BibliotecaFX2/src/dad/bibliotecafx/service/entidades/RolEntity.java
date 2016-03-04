package dad.bibliotecafx.service.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.RolItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Rol")
public class RolEntity implements Serializable {
	
	@Id
	@GeneratedValue
	private Long codigo;
	@Column(columnDefinition="VARCHAR(30)")
	private String tipo ;	
	
	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
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
		RolEntity other = (RolEntity) obj;
		if (other.codigo != codigo){
				return false;
		} 
		return true;
	}

	public RolItem toItem(){
		RolItem r = new RolItem();		
		r.setCodigo(getCodigo());
		r.setTipo(getTipo());	
		return r;
	}
}
