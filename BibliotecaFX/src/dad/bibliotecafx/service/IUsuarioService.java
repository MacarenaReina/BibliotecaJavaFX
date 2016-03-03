package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.UsuarioItem;

public interface IUsuarioService {
	public List<UsuarioItem> listarTodosUsuarios() throws ServiceException;
	public List<UsuarioItem> listarUsuariosLectores() throws ServiceException;
	public void crearUsuario(UsuarioItem usuario) throws ServiceException;
	public void actualizarUsuario(UsuarioItem usuario) throws ServiceException;
	public void eliminarUsuario(UsuarioItem usuario) throws ServiceException;
}
