package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.UsuarioItem;

public interface IPrestamoService {
	public List<PrestamoItem> listarPrestamos() throws ServiceException;
	public void crearPrestamo(PrestamoItem prestamo) throws ServiceException;
	public void actualizarPrestamo(PrestamoItem prestamo) throws ServiceException;
	public void eliminarPrestamo(PrestamoItem prestamo) throws ServiceException;
	public List<PrestamoItem> prestamosPorUsuario(UsuarioItem usuario) throws ServiceException;
}
