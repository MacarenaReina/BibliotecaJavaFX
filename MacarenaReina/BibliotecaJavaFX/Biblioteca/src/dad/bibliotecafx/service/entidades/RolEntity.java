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
import dad.bibliotecafx.service.items.RolUsuarioItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("serial")
@Entity
@Table(name="Rol")
public class RolEntity implements Serializable {
	
	@Id
	@GeneratedValue
	private Long codigo;
	@Column(columnDefinition="VARCHAR(30)")
	private String tipo ;	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="permiso")
	private List<RolUsuarioEntity> usuario = new ArrayList<RolUsuarioEntity>();

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

	public List<RolUsuarioEntity> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<RolUsuarioEntity> usuario) {
		this.usuario = usuario;
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
		ObservableList<RolUsuarioItem> rol_usuarioList = FXCollections.observableArrayList(new ArrayList<RolUsuarioItem>());
		for (RolUsuarioEntity rue : getUsuario()) {
			rol_usuarioList.add(rue.toItem());
		}
		r.setUsuario(rol_usuarioList);		
		return r;
	}
}
