package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IRolService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.RolEntity;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.RolUsuarioItem;

public class RolService implements IRolService {

	@SuppressWarnings("unchecked")
	@Override
	public List<RolItem> listarRoles() throws ServiceException {
		DataBase.begin();
		Query consultaRoles = DataBase.getSession().createQuery("FROM RolEntity");
		List<RolEntity> rolList = consultaRoles.list();		
		List<RolItem> roles = new ArrayList<RolItem>();		
		for (RolEntity r : rolList) {
			roles.add(r.toItem());
		}		
		DataBase.commit();
		return roles;
	}

	@Override
	public void crearRol(RolItem rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(rol.toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarRol(RolItem rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(rol.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarRol(RolItem rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(rol.toEntity());
		DataBase.commit();
	}

	@Override
	public void asignarRol(RolUsuarioItem rolUsuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(rolUsuario.toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarRolUsuario(RolUsuarioItem rolUsuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(rolUsuario.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarRolUsuario(RolUsuarioItem rolUsuario) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(rolUsuario.toEntity());
		DataBase.commit();
	}

}
