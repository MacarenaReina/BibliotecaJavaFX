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

import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolUsuarioItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@SuppressWarnings("serial")
@Entity
@Table(name="Usuario")
public class UsuarioEntity implements Serializable {

	@Id
	@GeneratedValue
	private Long codigo;
	@Column(columnDefinition = "VARCHAR (100)")
	private String nombre;
	@Column(columnDefinition = "VARCHAR(20)")
	private String usuario;
	@Column(columnDefinition = "VARCHAR(20)")
	private String password;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<RolUsuarioEntity> rol = new ArrayList<RolUsuarioEntity>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<PrestamoEntity> prestamos = new ArrayList<PrestamoEntity>();

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<RolUsuarioEntity> getRol() {
		return rol;
	}

	public void setRol(List<RolUsuarioEntity> rol) {
		this.rol = rol;
	}

	public List<PrestamoEntity> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<PrestamoEntity> prestamos) {
		this.prestamos = prestamos;
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
		UsuarioEntity other = (UsuarioEntity) obj;
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
		ObservableList<RolUsuarioItem> rolList = FXCollections.observableArrayList(new ArrayList<RolUsuarioItem>());
		for (RolUsuarioEntity r : getRol()) {
			rolList.add(r.toItem());
		}		
		u.setRol(rolList);
		ObservableList<PrestamoItem> prestamoList = FXCollections.observableArrayList(new ArrayList<PrestamoItem>());
		for (PrestamoEntity p : getPrestamos()) {
			prestamoList.add(p.toItem());
		}
		u.setPrestamos(prestamoList);		
		return u;
	}

}
