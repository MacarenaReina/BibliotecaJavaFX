package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;

public interface ISancionService {
	public List<Sancion> getSanciones() throws ServiceException;
	public void crearSancion(Sancion sancion) throws ServiceException;
	public void actualizarSancion(Sancion sancion) throws ServiceException;
	public void eliminarSancion(Sancion sancion) throws ServiceException;
	public List<Sancion> listarSancionesPorUsuario(Usuario usuario) throws ServiceException;
}
