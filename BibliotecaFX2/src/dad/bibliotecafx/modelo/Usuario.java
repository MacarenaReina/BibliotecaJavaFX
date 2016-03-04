package dad.bibliotecafx.modelo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Usuario {

	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");
	private final StringProperty nombre = new SimpleStringProperty(this, "nombre");
	private final StringProperty usuario = new SimpleStringProperty(this, "usuario");
	private final StringProperty password = new SimpleStringProperty(this, "password");
	private final SetProperty<Rol> rol = new SimpleSetProperty<Rol>(
			FXCollections.observableSet(new HashSet<Rol>()));
	private final ListProperty<Prestamo> prestamos = new SimpleListProperty<Prestamo>(
			FXCollections.observableArrayList(new ArrayList<Prestamo>()));

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

	public final SetProperty<Rol> rolProperty() {
		return this.rol;
	}	

	public final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Rol> getRol() {
		return this.rolProperty().get();
	}	

	public final void setRol(final javafx.collections.ObservableSet<dad.bibliotecafx.modelo.Rol> rol) {
		this.rolProperty().set(rol);
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (usuario.get() == null) {
			if (other.usuario.get() != null)
				return false;
		} else if (!usuario.get().equals(other.usuario.get()))
			return false;
		return true;
	}
	
	public UsuarioItem toItem(){
		UsuarioItem u = new UsuarioItem();		
		u.setCodigo(getCodigo());
		u.setNombre(getNombre());
		u.setPassword(getPassword());
		u.setUsuario(getUsuario());
		Set<RolItem> rolList = new HashSet<RolItem>();
		for (Rol r : getRol()) {
			rolList.add(r.toItem());
		}		
		u.setRol(rolList);
		List<PrestamoItem> prestamoList = new ArrayList<PrestamoItem>();
		for (Prestamo p : getPrestamos()) {
			prestamoList.add(p.toItem());
		}
		u.setPrestamos(prestamoList);		
		return u;
	}
	
}
