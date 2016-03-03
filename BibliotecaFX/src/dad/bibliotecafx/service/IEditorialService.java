package dad.bibliotecafx.service;

import java.util.List;

import dad.bibliotecafx.service.items.EditorialItem;

public interface IEditorialService {
	public List<EditorialItem> listarEditoriales() throws ServiceException;
	public void crearEditorial(EditorialItem editorial) throws ServiceException;
	public void actualizarEditorial(EditorialItem editorial) throws ServiceException;
	public void eliminarEditorial(EditorialItem editorial) throws ServiceException;
}
