package dad.bibliotecafx.modelo;

import dad.bibliotecafx.service.items.RolUsuarioItem;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

public class RolUsuario{
	
	private final ObjectProperty<Rol> permiso = new SimpleObjectProperty<Rol>(this, "permiso");
	private final ObjectProperty<Usuario> usuario = new SimpleObjectProperty<Usuario>(this, "usuario");
	private final BooleanProperty activado = new SimpleBooleanProperty(this, "activado");
	
	public final ObjectProperty<Rol> permisoProperty() {
		return this.permiso;
	}	

	public final dad.bibliotecafx.modelo.Rol getPermiso() {
		return this.permisoProperty().get();
	}	

	public final void setPermiso(final dad.bibliotecafx.modelo.Rol permiso) {
		this.permisoProperty().set(permiso);
	}	

	public final ObjectProperty<Usuario> usuarioProperty() {
		return this.usuario;
	}	

	public final dad.bibliotecafx.modelo.Usuario getUsuario() {
		return this.usuarioProperty().get();
	}	

	public final void setUsuario(final dad.bibliotecafx.modelo.Usuario usuario) {
		this.usuarioProperty().set(usuario);
	}	

	public final BooleanProperty activadoProperty() {
		return this.activado;
	}	

	public final boolean isActivado() {
		return this.activadoProperty().get();
	}	

	public final void setActivado(final boolean activado) {
		this.activadoProperty().set(activado);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((permiso.get() == null) ? 0 : permiso.get().hashCode());
		result = prime * result + ((usuario.get() == null) ? 0 : usuario.get().hashCode());
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
		RolUsuario other = (RolUsuario) obj;
		if (permiso.get() == null) {
			if (other.permiso.get() != null)
				return false;
		} else if (!permiso.get().equals(other.permiso.get()))
			return false;
		if (usuario.get() == null) {
			if (other.usuario.get() != null)
				return false;
		} else if (!usuario.get().equals(other.usuario.get()))
			return false;
		return true;
	}
	
	public RolUsuarioItem toItem(){
		RolUsuarioItem ru = new RolUsuarioItem();		
		ru.setActivado(isActivado());
		ru.setPermiso(getPermiso().toItem());
		ru.setUsuario(getUsuario().toItem());		
		return ru;
	}

}
