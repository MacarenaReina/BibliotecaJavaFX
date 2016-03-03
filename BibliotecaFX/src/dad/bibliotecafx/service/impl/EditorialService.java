package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IEditorialService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.EditorialEntity;
import dad.bibliotecafx.service.items.EditorialItem;

public class EditorialService implements IEditorialService {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<EditorialItem> listarEditoriales() throws ServiceException {
		DataBase.begin();	
		Query consultaEditoriales = DataBase.getSession().createQuery("FROM EditorialEntity");
		List<EditorialEntity> editorialList = consultaEditoriales.list();		
		List<EditorialItem> editoriales = new ArrayList<EditorialItem>();		
		for (EditorialEntity e : editorialList) {
			editoriales.add(e.toItem());
		}		
		DataBase.commit();
		return editoriales;
	}

	@Override
	public void crearEditorial(EditorialItem editorial) throws ServiceException {
		DataBase.begin();	
		DataBase.getSession().save(editorial.toEntity());	
		DataBase.commit();
	}

	@Override
	public void actualizarEditorial(EditorialItem editorial) throws ServiceException {
		DataBase.begin();	
		DataBase.getSession().update(DataBase.getSession().merge(editorial.toEntity()));	
		DataBase.commit();
	}

	@Override
	public void eliminarEditorial(EditorialItem editorial) throws ServiceException {
		DataBase.begin();	
		DataBase.getSession().delete(editorial.toEntity());	
		DataBase.commit();
	}

}
