package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IRolService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.RolEntity;
import dad.bibliotecafx.service.items.RolItem;

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
	public boolean crearRol(RolItem rol) throws ServiceException {
		boolean yaExiste = false;
		DataBase.begin();
		Long registros;
		registros = (Long)(DataBase.getSession().createQuery("SELECT COUNT (*) FROM RolEntity WHERE tipo = :tipo").setString("tipo", rol.getTipo()).uniqueResult());
		DataBase.commit();
		if(registros == 0){
			DataBase.begin();
			DataBase.getSession().save(rol.toEntity());
			DataBase.commit();
		} else{
			yaExiste = true;
		}
		return yaExiste;
	}

	@Override
	public boolean actualizarRol(RolItem rol) throws ServiceException {		
		boolean yaExiste = false;
		DataBase.begin();
		Long registros;
		registros = (Long)(DataBase.getSession().createQuery("SELECT COUNT (*) FROM RolEntity WHERE tipo = :tipo").setString("tipo", rol.getTipo()).uniqueResult());
		DataBase.commit();
		if(registros == 0){
			DataBase.begin();
			DataBase.getSession().update(DataBase.getSession().merge(rol.toEntity()));
			DataBase.commit();
		} else{
			yaExiste = true;
		}
		return yaExiste;
	}

	@Override
	public void eliminarRol(RolItem rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(rol.toEntity());
		DataBase.commit();
	}
}
