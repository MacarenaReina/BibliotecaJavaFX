package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.ISancionService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.SancionEntity;
import dad.bibliotecafx.service.items.SancionItem;

public class SancionService implements ISancionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<SancionItem> listarSanciones() throws ServiceException {
		DataBase.begin();		
		Query consultaSanciones = DataBase.getSession().createQuery("FROM SancionEntity");
		List<SancionEntity> sancionList = consultaSanciones.list();		
		List<SancionItem> sanciones = new ArrayList<SancionItem>();
		for (SancionEntity s : sancionList) {
			sanciones.add(s.toItem());
		}
		DataBase.commit();
		return sanciones;		
	}

	@Override
	public void crearSancion(SancionItem sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(sancion.toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarSancion(SancionItem sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(sancion.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarSancion(SancionItem sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(sancion.toEntity());
		DataBase.commit();
	}

}
