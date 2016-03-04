package dad.bibliotecafx.service;

import java.util.List;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.RolUsuarioItem;

public interface IRolService {
	public List<RolItem> listarRoles() throws ServiceException;
	public void crearRol(RolItem rol) throws ServiceException;
	public void actualizarRol(RolItem rol) throws ServiceException;
	public void eliminarRol(RolItem rol) throws ServiceException;
	public void asignarRol(RolUsuarioItem rolUsuario) throws ServiceException;
	public void actualizarRolUsuario(RolUsuarioItem rolUsuario) throws ServiceException;
	public void eliminarRolUsuario(RolUsuarioItem rolUsuario) throws ServiceException;
}
