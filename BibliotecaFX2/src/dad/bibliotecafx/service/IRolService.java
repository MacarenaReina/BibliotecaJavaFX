package dad.bibliotecafx.service;

import java.util.List;
import dad.bibliotecafx.service.items.RolItem;

public interface IRolService {
	public List<RolItem> listarRoles() throws ServiceException;
	public boolean crearRol(RolItem rol) throws ServiceException;
	public boolean actualizarRol(RolItem rol) throws ServiceException;
	public void eliminarRol(RolItem rol) throws ServiceException;
}
