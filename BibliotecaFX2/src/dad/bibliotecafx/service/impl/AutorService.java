package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.service.IAutorService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.AutorEntity;

public class AutorService implements IAutorService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> getAutores() throws ServiceException {
		DataBase.begin();
		Query consultaAutores = DataBase.getSession().createQuery("FROM AutorEntity");
		List<AutorEntity> autorList = consultaAutores.list();
		List<Autor> autoresList = new ArrayList<Autor>();
		for (AutorEntity a : autorList) {
			autoresList.add(a.toItem().toModel());
		}
		DataBase.commit();
		return autoresList;
	}

	@Override
	public void crearAutor(Autor autor) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(autor.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarAutor(Autor autor) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(autor.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarAutor(Autor autor) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(autor.toItem().toEntity());
		DataBase.commit();

	}

//	private Long getUltimoId() {
//		Long lastId;
//		lastId = ((BigInteger) DataBase.getSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
//				.longValue();
//		return lastId;
//	}

}
