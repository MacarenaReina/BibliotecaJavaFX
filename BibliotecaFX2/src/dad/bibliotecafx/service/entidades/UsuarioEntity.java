package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.UsuarioItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Usuario")
public class UsuarioEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name="codUsuario")	
	private Long codigo;
	@Column(columnDefinition = "VARCHAR (100)")
	private String nombre;
	@Column(columnDefinition = "VARCHAR(20)")
	private String usuario;
	@Column(columnDefinition = "VARCHAR(20)")
	private String password;
	
	@ManyToOne
	@JoinColumn(name="rol")
	private RolEntity rol;
	
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
	

	public RolEntity getRol() {
		return rol;
	}

	public void setRol(RolEntity rol) {
		this.rol = rol;
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
		u.setUsuario(getUsuario());
		u.setCodigo(getCodigo());
		u.setNombre(getNombre());
		u.setPassword(getPassword());	
		u.setRol(getRol().toItem());			
		return u;
	}
}
