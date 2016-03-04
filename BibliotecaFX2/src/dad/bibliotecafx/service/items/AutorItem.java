package dad.bibliotecafx.service.items;

import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.service.entidades.AutorEntity;

public class AutorItem {
	private Long codigo;
	private String nombre;

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
	
	public AutorEntity toEntity() {
		AutorEntity ae = new AutorEntity();
		ae.setCodigo(getCodigo());
		ae.setNombre(getNombre());
		return ae;
	}
	
	public Autor toModel() {
		Autor ae = new Autor();
		ae.setCodigo(getCodigo());
		ae.setNombre(getNombre());
		return ae;
	}
}
