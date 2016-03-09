package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.modelo.Autor;

public interface IAutorService {
	public List<Autor> getAutores() throws ServiceException;
	public void crearAutor(Autor autor) throws ServiceException;
	public void actualizarAutor(Autor autor) throws ServiceException;
	public void eliminarAutor(Autor autor) throws ServiceException;
}
