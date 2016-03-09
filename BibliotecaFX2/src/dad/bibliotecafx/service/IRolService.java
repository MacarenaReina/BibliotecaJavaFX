package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Rol;

public interface IRolService {
	public List<Rol> getRoles() throws ServiceException;
	public boolean crearRol(Rol rol) throws ServiceException;
	public void actualizarRol(Rol rol) throws ServiceException;
	public void eliminarRol(Rol rol) throws ServiceException;
}
