package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.List;

import dad.bibliotecafx.service.entidades.RolEntity;
import dad.bibliotecafx.service.entidades.RolUsuarioEntity;

public class RolItem {
	
	private Long codigo;
	private String tipo ;	
	private List<RolUsuarioItem> usuario = new ArrayList<RolUsuarioItem>();
	
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

	public List<RolUsuarioItem> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<RolUsuarioItem> usuario) {
		this.usuario = usuario;
	}

	public RolEntity toEntity(){
		RolEntity re = new RolEntity();		
		re.setCodigo(getCodigo());
		re.setTipo(getTipo());
		List<RolUsuarioEntity> ru = new ArrayList<RolUsuarioEntity>();
		for (RolUsuarioItem r : getUsuario()) {
			ru.add(r.toEntity());			
		}
		re.setUsuario(ru);		
		return re;
	}
	
}
