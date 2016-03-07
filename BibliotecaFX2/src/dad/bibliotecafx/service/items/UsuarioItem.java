package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.List;

import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.entidades.UsuarioEntity;

public class UsuarioItem {

	private Long codigo;
	private String nombre;
	private String usuario;
	private String password;
	private RolItem rol;
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

	public List<PrestamoItem> getPrestamos() {
		return prestamos;
	}

	public void setPrestamos(List<PrestamoItem> prestamos) {
		this.prestamos = prestamos;
	}
	
	public RolItem getRol() {
		return rol;
	}

	public void setRol(RolItem rol) {
		this.rol = rol;
	}

	public UsuarioEntity toEntity(){
		UsuarioEntity ue = new UsuarioEntity();		
		ue.setCodigo(getCodigo());
		ue.setNombre(getNombre());
		ue.setUsuario(getUsuario());
		ue.setPassword(getPassword());
		ue.setRol(getRol().toEntity());
		return ue;
	}
	
	public Usuario toModel(){
		Usuario ue = new Usuario();		
		ue.setCodigo(getCodigo());
		ue.setNombre(getNombre());
		ue.setUsuario(getUsuario());
		ue.setPassword(getPassword());
		ue.setRol(getRol().toModel());
		return ue;
	}
	
}
