package dad.bibliotecafx.service.items;

import dad.bibliotecafx.modelo.RolUsuario;
import dad.bibliotecafx.service.entidades.RolUsuarioEntity;

public class RolUsuarioItem {

	private RolItem permiso;
	private UsuarioItem usuario;
	private Boolean activado;
	
	public RolItem getPermiso() {
		return permiso;
	}

	public void setPermiso(RolItem permiso) {
		this.permiso = permiso;
	}

	public UsuarioItem getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioItem usuario) {
		this.usuario = usuario;
	}

	public Boolean getActivado() {
		return activado;
	}

	public void setActivado(Boolean activado) {
		this.activado = activado;
	}

	public RolUsuarioEntity toEntity(){
		RolUsuarioEntity rue = new RolUsuarioEntity();		
		rue.setActivado(getActivado());
		rue.setPermiso(getPermiso().toEntity());
		rue.setUsuario(getUsuario().toEntity());		
		return rue;
	}
	
	public RolUsuario toModel(){
		RolUsuario rue = new RolUsuario();		
		rue.setActivado(getActivado());
		rue.setPermiso(getPermiso().toModel());
		rue.setUsuario(getUsuario().toModel());		
		return rue;
	}
	
}
