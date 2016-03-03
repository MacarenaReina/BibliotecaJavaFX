package dad.bibliotecafx.modelo;

import java.util.ArrayList;

import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.RolUsuarioItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rol {
	
	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");	
	private final StringProperty tipo = new SimpleStringProperty(this, "tipo");		
	private ListProperty<RolUsuario> usuario = new SimpleListProperty<RolUsuario>(FXCollections.observableArrayList(new ArrayList<RolUsuario>()));
	
	public final LongProperty codigoProperty() {
		return this.codigo;
	}	

	public final long getCodigo() {
		return this.codigoProperty().get();
	}	

	public final void setCodigo(final long codigo) {
		this.codigoProperty().set(codigo);
	}	

	public final StringProperty tipoProperty() {
		return this.tipo;
	}	

	public final java.lang.String getTipo() {
		return this.tipoProperty().get();
	}	

	public final void setTipo(final java.lang.String tipo) {
		this.tipoProperty().set(tipo);
	}	

	public final ListProperty<RolUsuario> usuarioProperty() {
		return this.usuario;
	}	

	public final javafx.collections.ObservableList<dad.bibliotecafx.modelo.RolUsuario> getUsuario() {
		return this.usuarioProperty().get();
	}	

	public final void setUsuario(final javafx.collections.ObservableList<dad.bibliotecafx.modelo.RolUsuario> usuario) {
		this.usuarioProperty().set(usuario);
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
		Rol other = (Rol) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	
	public RolItem toItem(){
		RolItem r = new RolItem();		
		r.setCodigo(getCodigo());
		r.setTipo(getTipo());
		ObservableList<RolUsuarioItem> rol_usuarioList = FXCollections.observableArrayList(new ArrayList<RolUsuarioItem>());
		for (RolUsuario rue : getUsuario()) {
			rol_usuarioList.add(rue.toItem());
		}
		r.setUsuario(rol_usuarioList);		
		return r;
	}

}
