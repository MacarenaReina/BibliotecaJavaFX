package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.AutorItem;
import dad.bibliotecafx.service.items.LibroItem;

public interface ILibroService {
	public List<LibroItem> listarLibros() throws ServiceException;
	public void crearLibro(LibroItem libro) throws ServiceException;
	public void actualizarLibro(LibroItem libro) throws ServiceException;
	public void eliminarLibro(LibroItem libro) throws ServiceException;
	public List<LibroItem> librosPorAutor(AutorItem autor) throws ServiceException;
}
