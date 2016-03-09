package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.service.ILibroService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.LibroEntity;

public class LibroService implements ILibroService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Libro> getLibros() throws ServiceException {
		DataBase.begin();
		Query consultaLibros = DataBase.getSession().createQuery("FROM LibroEntity");
		List<LibroEntity> librosListEntity = consultaLibros.list();
		List<Libro> librosList = new ArrayList<Libro>();
		for (LibroEntity l : librosListEntity) {
			librosList.add(l.toItem().toModel());
		}
		DataBase.commit();
		return librosList;
	}

	@Override
	public void crearLibro(Libro libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(libro.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarLibro(Libro libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(libro.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarLibro(Libro libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(libro.toItem().toEntity());
		DataBase.commit();
	}
}
