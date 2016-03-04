package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.entidades.PrestamoEntity;
import dad.bibliotecafx.service.entidades.RolEntity;
import dad.bibliotecafx.service.entidades.UsuarioEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class UsuarioItem {

	private Long codigo;
	private String nombre;
	private String usuario;
	private String password;
	private Set<RolItem> rol = new HashSet<RolItem>();
	private List<PrestamoItem> prestamos = new ArrayList<PrestamoItem>();

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
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

	public Set<RolItem> getRol() {
		return rol;
	}

	public void setRol(Set<RolItem> rol) {
		this.rol = rol;
	}
	
	public List<PrestamoItem> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<PrestamoItem> prestamos) {
		this.prestamos = prestamos;
	}

	public UsuarioEntity toEntity(){
		UsuarioEntity ue = new UsuarioEntity();		
		ue.setCodigo(getCodigo());
		ue.setNombre(getNombre());
		ue.setUsuario(getUsuario());
		ue.setPassword(getPassword());
		List<PrestamoEntity> prestamos = new ArrayList<PrestamoEntity>();
		for (PrestamoItem p : getPrestamos()) {
			prestamos.add(p.toEntity());			
		}
		ue.setPrestamos(prestamos);
		Set<RolEntity> roles = new HashSet<RolEntity>();
		for (RolItem r : getRol()) {
			roles.add(r.toEntity());			
		}		
		ue.setRoles(roles);
		return ue;
	}
	
	public Usuario toModel(){
		Usuario ue = new Usuario();		
		ue.setCodigo(getCodigo());
		ue.setNombre(getNombre());
		ue.setUsuario(getUsuario());
		ue.setPassword(getPassword());
		ObservableList<Prestamo> prestamos = FXCollections.observableArrayList(new ArrayList<Prestamo>());
		for (PrestamoItem p : getPrestamos()) {
			prestamos.add(p.toModel());			
		}
		ue.setPrestamos(prestamos);
		ObservableSet<Rol> roles = FXCollections.observableSet(new HashSet<Rol>());
		for (RolItem r : getRol()) {
			roles.add(r.toModel());			
		}		
		ue.setRol(roles);
		return ue;
	}
	
}
