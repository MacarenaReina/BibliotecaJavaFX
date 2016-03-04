package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.AutorItem;

public interface IAutorService {
	public List<AutorItem> listarAutores() throws ServiceException;
	public void crearAutor(AutorItem autor) throws ServiceException;
	public void actualizarAutor(AutorItem autor) throws ServiceException;
	public void eliminarAutor(AutorItem autor) throws ServiceException;
}
