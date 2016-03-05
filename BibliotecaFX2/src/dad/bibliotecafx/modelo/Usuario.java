package dad.bibliotecafx.modelo;

import java.util.ArrayList;

import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Usuario {

	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");
	private final StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private final StringProperty usuario = new SimpleStringProperty(this, "usuario");
	private final StringProperty password = new SimpleStringProperty(this, "password");
//	private final SetProperty<Rol> rol = new SimpleSetProperty<Rol>(
//			FXCollections.observableSet(new HashSet<Rol>()));
	private final ListProperty<Prestamo> prestamos = new SimpleListProperty<Prestamo>(
			FXCollections.observableArrayList(new ArrayList<Prestamo>()));
	private ObjectProperty<Rol> rol = new SimpleObjectProperty<Rol>();
	
	public final LongProperty codigoProperty() {
		return this.codigo;
	}	

	public final long getCodigo() {
		return this.codigoProperty().get();
	}	

	public final void setCodigo(final long codigo) {
		this.codigoProperty().set(codigo);
	}	

	public final StringProperty nombreProperty() {
		return this.nombre;
	}	

	public final java.lang.String getNombre() {
		return this.nombreProperty().get();
	}	

	public final void setNombre(final java.lang.String nombre) {
		this.nombreProperty().set(nombre);
	}	

	public final StringProperty usuarioProperty() {
		return this.usuario;
	}	

	public final java.lang.String getUsuario() {
		return this.usuarioProperty().get();
	}	

	public final void setUsuario(final java.lang.String usuario) {
		this.usuarioProperty().set(usuario);
	}	

	public final StringProperty passwordProperty() {
		return this.password;
	}	

	public final java.lang.String getPassword() {
		return this.passwordProperty().get();
	}	

	public final void setPassword(final java.lang.String password) {
		this.passwordProperty().set(password);
	}	

	public final ListProperty<Prestamo> prestamosProperty() {
		return this.prestamos;
	}	

	public final javafx.collections.ObservableList<dad.bibliotecafx.modelo.Prestamo> getPrestamos() {
		return this.prestamosProperty().get();
	}	

	public final void setPrestamos(final javafx.collections.ObservableList<dad.bibliotecafx.modelo.Prestamo> prestamos) {
		this.prestamosProperty().set(prestamos);
	}
	
	public final ObjectProperty<Rol> rolProperty() {
		return this.rol;
	}
	

	public final dad.bibliotecafx.modelo.Rol getRol() {
		return this.rolProperty().get();
	}
	

	public final void setRol(final dad.bibliotecafx.modelo.Rol rol) {
		this.rolProperty().set(rol);
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public UsuarioItem toItem(){
		UsuarioItem u = new UsuarioItem();		
		u.setCodigo(getCodigo());
		u.setNombre(getNombre());
		u.setPassword(getPassword());
		u.setUsuario(getUsuario());
		u.setRol(getRol().toItem());	
		return u;
	}


}
