package dad.bibliotecafx.service.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import dad.bibliotecafx.service.items.SancionItem;

@SuppressWarnings("serial")
@Entity
@Table(name="Sancion")
public class SancionEntity implements Serializable {
	
	@Id
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE})
	@JoinColumn(name="prestamo")
	private PrestamoEntity prestamo;
	@Column(columnDefinition="DATE")
	private Date fechaAlta;
	@Column(columnDefinition="DATE")
	private Date fechaFinalizacion;

	public PrestamoEntity getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(PrestamoEntity prestamo) {
		this.prestamo = prestamo;
	}

	public Date getFechaAlta() {
		return fechaAlta;
	}

	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}

	public Date getFechaFinalizacion() {
		return fechaFinalizacion;
	}

	public void setFechaFinalizacion(Date fechaFinalizacion) {
		this.fechaFinalizacion = fechaFinalizacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((prestamo == null) ? 0 : prestamo.hashCode());
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
		SancionEntity other = (SancionEntity) obj;
		if (prestamo == null) {
			if (other.prestamo != null)
				return false;
		} else if (!prestamo.equals(other.prestamo))
			return false;
		return true;
	}
	
	public SancionItem toItem(){
		SancionItem s = new SancionItem();		
		s.setFechaAlta(getFechaAlta());
		s.setFechaFinalizacion(getFechaFinalizacion());
		s.setPrestamo(getPrestamo().toItem());		
		return s;
	}
	

}
