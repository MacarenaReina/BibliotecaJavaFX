package dad.bibliotecafx.service.entidades;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.RolUsuarioItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Rol_Usuario")
public class RolUsuarioEntity implements Serializable{
	
	@Id
	@ManyToOne
	@JoinColumn(name="permiso")	
	private RolEntity permiso;
	@Id
	@ManyToOne
	@JoinColumn(name="usuario")
	private UsuarioEntity usuario;
	@Column(columnDefinition="BOOLEAN", name="activado")
	private Boolean activado;
	
	public RolEntity getPermiso() {
		return permiso;
	}

	public void setPermiso(RolEntity permiso) {
		this.permiso = permiso;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public Boolean getActivado() {
		return activado;
	}

	public void setActivado(Boolean activado) {
		this.activado = activado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permiso == null) ? 0 : permiso.hashCode());
		result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
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
		RolUsuarioEntity other = (RolUsuarioEntity) obj;
		if (permiso == null) {
			if (other.permiso != null)
				return false;
		} else if (!permiso.equals(other.permiso))
			return false;
		if (usuario == null) {
			if (other.usuario != null)
				return false;
		} else if (!usuario.equals(other.usuario))
			return false;
		return true;
	}
	
	public RolUsuarioItem toItem(){
		RolUsuarioItem ru = new RolUsuarioItem();		
		ru.setActivado(getActivado());
		ru.setPermiso(getPermiso().toItem());
		ru.setUsuario(getUsuario().toItem());		
		return ru;
	}

}
