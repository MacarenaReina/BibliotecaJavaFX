package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Editorial;
import dad.bibliotecafx.service.IEditorialService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.EditorialEntity;

public class EditorialService implements IEditorialService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Editorial> getEditoriales() throws ServiceException {
		DataBase.begin();
		Query consultaEditoriales = DataBase.getSession().createQuery("FROM EditorialEntity");
		List<EditorialEntity> editorialListEntity = consultaEditoriales.list();
		List<Editorial> editorialList = new ArrayList<Editorial>();
		for (EditorialEntity e : editorialListEntity) {
			editorialList.add(e.toItem().toModel());
		}
		DataBase.commit();
		return editorialList;
	}

	@Override
	public void crearEditorial(Editorial editorial) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(editorial.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarEditorial(Editorial editorial) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(editorial.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarEditorial(Editorial editorial) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(editorial.toItem().toEntity());
		DataBase.commit();
	}

//	private Long getUltimoId() {
//		Long lastId;
//		lastId = ((BigInteger) DataBase.getSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
//				.longValue();
//		return lastId;
//	}

}
