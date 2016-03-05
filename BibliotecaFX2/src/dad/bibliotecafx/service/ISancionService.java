package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.SancionItem;
import dad.bibliotecafx.service.items.UsuarioItem;

public interface ISancionService {
	public List<SancionItem> listarSanciones() throws ServiceException;
	public void crearSancion(SancionItem sancion) throws ServiceException;
	public void actualizarSancion(SancionItem sancion) throws ServiceException;
	public void eliminarSancion(SancionItem sancion) throws ServiceException;
	public List<SancionItem> listarSancionesPorUsuario(UsuarioItem usuario) throws ServiceException;
}
