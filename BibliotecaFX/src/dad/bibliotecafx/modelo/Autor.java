package dad.bibliotecafx.modelo;

import dad.bibliotecafx.service.items.AutorItem;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Autor {
	
	private final LongProperty codigo = new SimpleLongProperty(this, "codigo");
	private final StringProperty nombre = new SimpleStringProperty(this, "nombre");
	
	public final LongProperty codigoProperty() {
		return this.codigo;
	}	

	public final long getCodigo() {
		return this.codigoProperty().get();
	}	

	public final void setCodigo(final long codigo) {
		this.codigoProperty().set(codigo);
	}	

	public final StringProperty nombreProperty() {
		return this.nombre;
	}	

	public final java.lang.String getNombre() {
		return this.nombreProperty().get();
	}	

	public final void setNombre(final java.lang.String nombre) {
		this.nombreProperty().set(nombre);
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
		Autor other = (Autor) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}	

	public AutorItem toItem() {
		AutorItem autor = new AutorItem();
		autor.setCodigo(this.getCodigo());
		autor.setNombre(this.getNombre());
		return autor;
	}
}
	
