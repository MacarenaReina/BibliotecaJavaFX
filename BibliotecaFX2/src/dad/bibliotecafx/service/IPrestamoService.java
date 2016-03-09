package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Usuario;

public interface IPrestamoService {
	public List<Prestamo> getPrestamos() throws ServiceException;
	public void crearPrestamo(Prestamo prestamo) throws ServiceException;
	public void actualizarPrestamo(Prestamo prestamo) throws ServiceException;
	public void eliminarPrestamo(Prestamo prestamo) throws ServiceException;
	public List<Prestamo> prestamosPorUsuario(Usuario usuario) throws ServiceException;
}
