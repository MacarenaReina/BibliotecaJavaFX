package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.UsuarioItem;

public interface IUsuarioService {
	public List<UsuarioItem> listarTodosUsuarios() throws ServiceException;
	public List<UsuarioItem> listarUsuariosLectores() throws ServiceException;
	public boolean crearUsuario(UsuarioItem usuario) throws ServiceException;
	public void actualizarUsuario(UsuarioItem usuario) throws ServiceException;
	public void eliminarUsuario(UsuarioItem usuario) throws ServiceException;
	public UsuarioItem loginCorrecto(String usuario, String password) throws ServiceException;
}
