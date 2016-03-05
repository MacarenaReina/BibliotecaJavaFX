package dad.bibliotecafx.service.items;

import java.util.ArrayList;
import java.util.List;

import dad.bibliotecafx.modelo.Editorial;
import dad.bibliotecafx.service.entidades.EditorialEntity;
import dad.bibliotecafx.service.entidades.LibroEntity;

public class EditorialItem {
	
	private Long codigo;
	private String nombre;	
	private List<LibroItem> libros = new ArrayList<LibroItem>();

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

	public List<LibroItem> getLibros() {
		return libros;
	}

	public void setLibros(List<LibroItem> libros) {
		this.libros = libros;
	}
	
	public EditorialEntity toEntity(){
		EditorialEntity ee = new EditorialEntity();
		ee.setCodigo(getCodigo());
		ee.setNombre(getNombre());
		
		List<LibroEntity> libros = new ArrayList<LibroEntity>();
		for (LibroItem l : getLibros()) {
			libros.add(l.toEntity());			
		}		
		ee.setLibros(libros);		
		return ee;
	}
	
	public Editorial toModel(){
		Editorial ee = new Editorial();
		ee.setCodigo(getCodigo());
		ee.setNombre(getNombre());		
		return ee;
	}
}
