package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.SancionItem;

public interface ISancionService {
	public List<SancionItem> listarSanciones() throws ServiceException;
	public void crearSancion(SancionItem sancion) throws ServiceException;
	public void actualizarSancion(SancionItem sancion) throws ServiceException;
	public void eliminarSancion(SancionItem sancion) throws ServiceException;
}
