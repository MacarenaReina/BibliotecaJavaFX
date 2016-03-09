package dad.bibliotecafx.service;


import java.util.List;

import dad.bibliotecafx.modelo.Editorial;

public interface IEditorialService {
	public List<Editorial> getEditoriales() throws ServiceException;
	public void crearEditorial(Editorial editorial) throws ServiceException;
	public void actualizarEditorial(Editorial editorial) throws ServiceException;
	public void eliminarEditorial(Editorial editorial) throws ServiceException;
}
