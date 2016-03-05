package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.List;

import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.entidades.RolEntity;

public class RolItem {
	
	private Long codigo;
	private String tipo ;	
	private List<UsuarioItem> usuarios= new ArrayList<UsuarioItem>();
	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<UsuarioItem> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioItem> usuarios) {
		this.usuarios = usuarios;
	}

	public RolEntity toEntity(){
		RolEntity re = new RolEntity();		
		re.setCodigo(getCodigo());
		re.setTipo(getTipo());	
		return re;
	}
	
	public Rol toModel(){
		Rol re = new Rol();		
		re.setCodigo(getCodigo());
		re.setTipo(getTipo());
		return re;
	}
	
}
