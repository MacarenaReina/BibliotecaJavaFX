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

import dad.bibliotecafx.service.items.RolItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Rol")
public class RolEntity implements Serializable {
	
	@Id
	@GeneratedValue
	@Column(name="codRol")	
	private Long codigoRol;
	@Column(columnDefinition="VARCHAR(30)")
	private String tipo;	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rol")
	private List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
	
	public Long getCodigo() {
		return codigoRol;
	}

	public void setCodigo(Long codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public List<UsuarioEntity> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioEntity> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigoRol == null) ? 0 : codigoRol.hashCode());
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
		if (other.codigoRol != codigoRol){
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
