package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IAutorService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.AutorEntity;
import dad.bibliotecafx.service.items.AutorItem;

public class AutorService implements IAutorService {

	@SuppressWarnings("unchecked")
	@Override
	public List<AutorItem> listarAutores() throws ServiceException {
		DataBase.begin();		
		Query consultaAutores = DataBase.getSession().createQuery("FROM AutorEntity");
		List<AutorEntity> autorList = consultaAutores.list();		
		List<AutorItem> autores = new ArrayList<AutorItem>();
		for (AutorEntity a : autorList) {
			autores.add(a.toItem());
		}
		DataBase.commit();
		return autores;
	}

	@Override
	public void crearAutor(AutorItem autor) throws ServiceException {
		DataBase.begin();	
		DataBase.getSession().save(autor.toEntity());	
		DataBase.commit();
	}

	@Override
	public void actualizarAutor(AutorItem autor) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(autor.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarAutor(AutorItem autor) throws ServiceException {
		DataBase.begin();		
		DataBase.getSession().delete(autor.toEntity());		
		DataBase.commit();
	}

}
