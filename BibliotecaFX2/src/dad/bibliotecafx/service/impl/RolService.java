package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.IRolService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.RolEntity;

public class RolService implements IRolService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> getRoles() throws ServiceException {
		DataBase.begin();
		Query consultaRoles = DataBase.getSession().createQuery("FROM RolEntity");
		List<RolEntity> rolListEntity = consultaRoles.list();
		List<Rol> rolList = new ArrayList<Rol>();
		for (RolEntity r : rolListEntity) {
			rolList.add(r.toItem().toModel());
		}
		DataBase.commit();
		return rolList;
	}

	@Override
	public boolean crearRol(Rol rol) throws ServiceException {
		boolean yaExiste = false;
		DataBase.begin();
		Long registros;
		registros = (Long) (DataBase.getSession().createQuery("SELECT COUNT (*) FROM RolEntity WHERE tipo = :tipo")
				.setString("tipo", rol.getTipo()).uniqueResult());
		if (registros == 0) {
			DataBase.getSession().save(rol.toItem().toEntity());
		} else {
			yaExiste = true;
		}
		DataBase.commit();
		return yaExiste;
	}

	@Override
	public void actualizarRol(Rol rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(rol.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarRol(Rol rol) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(rol.toItem().toEntity());
		DataBase.commit();

	}

//	private Long getUltimoId() {
//		Long lastId;
//		lastId = ((BigInteger) DataBase.getSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
//				.longValue();
//		return lastId;
//	}

}
