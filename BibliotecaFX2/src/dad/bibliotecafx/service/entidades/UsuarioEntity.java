package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;

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
	
	@ManyToMany(cascade=CascadeType.ALL,fetch =FetchType.LAZY)  
	@JoinTable(name="Usuario_Rol", joinColumns=@JoinColumn(name="rol"), inverseJoinColumns=@JoinColumn(name="usuario")) 
	private Set<RolEntity> roles = new HashSet<RolEntity>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	private List<PrestamoEntity> prestamos = new ArrayList<PrestamoEntity>();

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	public long getCodigo() {
		return codigo;
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

	public List<PrestamoEntity> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<PrestamoEntity> prestamos) {
		this.prestamos = prestamos;
	}
	
	public Set<RolEntity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolEntity> roles) {
		this.roles = roles;
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
		List<PrestamoItem> prestamoList = new ArrayList<PrestamoItem>();
		for (PrestamoEntity p : getPrestamos()) {
			prestamoList.add(p.toItem());
		}
		u.setPrestamos(prestamoList);	
		Set<RolItem> rolList = new HashSet<RolItem>();
		for (RolEntity r : getRoles()) {
			rolList.add(r.toItem());
		}				
		return u;
	}

}
