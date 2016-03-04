package dad.bibliotecafx.service.items;

import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.entidades.RolEntity;

public class RolItem {
	
	private Long codigo;
	private String tipo ;	
	
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
