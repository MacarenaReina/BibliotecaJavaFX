package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Libro;

public interface ILibroService {
	public List<Libro> getLibros() throws ServiceException;
	public void crearLibro(Libro libro) throws ServiceException;
	public void actualizarLibro(Libro libro) throws ServiceException;
	public void eliminarLibro(Libro libro) throws ServiceException;
}
