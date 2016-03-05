package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.ILibroService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.AutorEntity;
import dad.bibliotecafx.service.entidades.LibroEntity;
import dad.bibliotecafx.service.items.AutorItem;
import dad.bibliotecafx.service.items.LibroItem;

public class LibroService implements ILibroService {

	@SuppressWarnings("unchecked")
	@Override
	public List<LibroItem> listarLibros() throws ServiceException {
		DataBase.begin();
		Query consultaLibros = DataBase.getSession().createQuery("FROM LibroEntity");
		List<LibroEntity> librosList = consultaLibros.list();

		List<LibroItem> libros = new ArrayList<LibroItem>();
		for (LibroEntity l : librosList) {
			libros.add(l.toItem());
		}
		DataBase.commit();
		return libros;
	}

	@Override
	public void crearLibro(LibroItem libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(libro.toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarLibro(LibroItem libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(libro.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarLibro(LibroItem libro) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(libro.toEntity());
		DataBase.commit();
	}

	//Esto no funciona...
	@SuppressWarnings("unchecked")
	@Override
	public List<LibroItem> librosPorAutor(AutorItem autor) throws ServiceException {
		DataBase.begin();
		List<AutorEntity> autoresList = new ArrayList<AutorEntity>();
		autoresList.add(autor.toEntity());

		Query consultaLibros = DataBase.getSession()
				.createQuery(
						"FROM LibroEntity WHERE autores.codigo IN :autores");
		consultaLibros.setParameterList("autores", autoresList);
		
//		Query consultaLibros = DataBase.getSession()
//				.createQuery(
//						"FROM LibroEntity WHERE autores.codigo = :cod").setLong("cod", autor.getCodigo());
		
		List<LibroEntity> librosList = consultaLibros.list();
		List<LibroItem> libros = new ArrayList<LibroItem>();
		for (LibroEntity l : librosList) {
			libros.add(l.toItem());
		}

		DataBase.commit();
		return libros;
	}
}
