package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IUsuarioService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.UsuarioEntity;
import dad.bibliotecafx.service.items.UsuarioItem;

public class UsuarioService implements IUsuarioService {


	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioItem> listarTodosUsuarios() throws ServiceException {
		DataBase.begin();
		Query consultaUsuarios = DataBase.getSession().createQuery("FROM UsuarioEntity");
		List<UsuarioEntity> usuariosList = consultaUsuarios.list();		
		List<UsuarioItem> usuarios = new ArrayList<UsuarioItem>();
		for (UsuarioEntity u : usuariosList) {
			usuarios.add(u.toItem());
		}	
		DataBase.commit();
		return usuarios;
	}

	@Override
	public boolean crearUsuario(UsuarioItem usuario) throws ServiceException {
		boolean yaExiste = false;
		DataBase.begin();
		Long registros;
		registros = (Long)(DataBase.getSession().createQuery("SELECT COUNT (*) FROM UsuarioEntity WHERE usuario = :usuario").setString("usuario", usuario.getUsuario()).uniqueResult());
		DataBase.commit();
		if(registros == 0){
			DataBase.begin();
			DataBase.getSession().save(usuario.toEntity());
			DataBase.commit();
		} else{
			yaExiste = true;
		}
		return yaExiste;
	}

	@Override
	public void actualizarUsuario(UsuarioItem usuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(usuario.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarUsuario(UsuarioItem usuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(usuario.toEntity());
		DataBase.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioItem> listarUsuariosLectores() throws ServiceException {
		DataBase.begin();
		
		Query consultaUsuarios = DataBase.getSession()
				.createQuery(
						"FROM UsuarioEntity u  WHERE u.rol.tipo = :tipo")
				.setString("tipo", "Lector");
		List<UsuarioEntity> usuariosList = consultaUsuarios.list();
		
		List<UsuarioItem> usuarios = new ArrayList<UsuarioItem>();
		for (UsuarioEntity u : usuariosList) {
			usuarios.add(u.toItem());
		}
		DataBase.commit();
		return usuarios;
	}

}
