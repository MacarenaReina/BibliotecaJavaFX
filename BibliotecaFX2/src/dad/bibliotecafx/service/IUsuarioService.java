package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Usuario;

public interface IUsuarioService {
	public List<Usuario> getUsuarios() throws ServiceException;
	public List<Usuario> getUsuariosLectores() throws ServiceException;
	public boolean crearUsuario(Usuario usuario) throws ServiceException;
	public void actualizarUsuario(Usuario usuario) throws ServiceException;
	public void eliminarUsuario(Usuario usuario) throws ServiceException;
	public Usuario loginCorrecto(String usuario, String password) throws ServiceException;
}
