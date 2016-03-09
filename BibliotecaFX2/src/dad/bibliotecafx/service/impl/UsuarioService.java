package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.IUsuarioService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.UsuarioEntity;

public class UsuarioService implements IUsuarioService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuarios() throws ServiceException {
		DataBase.begin();
		Query consultaUsuarios = DataBase.getSession().createQuery("FROM UsuarioEntity");
		List<UsuarioEntity> usuariosListEntity = consultaUsuarios.list();
		List<Usuario> usuariosList = new ArrayList<Usuario>();
		for (UsuarioEntity u : usuariosListEntity) {
			usuariosList.add(u.toItem().toModel());
		}
		DataBase.commit();
		return usuariosList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> getUsuariosLectores() throws ServiceException {
		DataBase.begin();
		Query consultaUsuariosLectores = DataBase.getSession()
				.createQuery("FROM UsuarioEntity u WHERE u.rol.tipo = :tipo").setString("tipo", "Lector");
		List<UsuarioEntity> usuariosLectoresListEntity = consultaUsuariosLectores.list();
		List<Usuario> usuariosLectoresList = new ArrayList<Usuario>();
		for (UsuarioEntity u : usuariosLectoresListEntity) {
			usuariosLectoresList.add(u.toItem().toModel());
		}
		DataBase.commit();
		return usuariosLectoresList;
	}

	@Override
	public boolean crearUsuario(Usuario usuario) throws ServiceException {
		boolean yaExiste = false;
		DataBase.begin();
		Long registros;
		registros = (Long) (DataBase.getSession()
				.createQuery("SELECT COUNT (*) FROM UsuarioEntity WHERE usuario = :usuario")
				.setString("usuario", usuario.getUsuario()).uniqueResult());
		DataBase.commit();
		if (registros == 0) {
			DataBase.begin();
			DataBase.getSession().save(usuario.toItem().toEntity());
			DataBase.commit();
		} else {
			yaExiste = true;
		}
		return yaExiste;
	}

	@Override
	public void actualizarUsuario(Usuario usuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(usuario.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarUsuario(Usuario usuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(usuario.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public Usuario loginCorrecto(String usuario, String password) throws ServiceException {
		DataBase.begin();
		UsuarioEntity usu;
		usu = (UsuarioEntity) (DataBase.getSession()
				.createQuery("FROM UsuarioEntity WHERE usuario = :usuario AND password = :password")
				.setString("usuario", usuario).setString("password", password).uniqueResult());
		DataBase.commit();
		return usu.toItem().toModel();
	}

//	private Long getUltimoId() {
//		Long lastId;
//		lastId = ((BigInteger) DataBase.getSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
//				.longValue();
//		return lastId;
//	}

}
