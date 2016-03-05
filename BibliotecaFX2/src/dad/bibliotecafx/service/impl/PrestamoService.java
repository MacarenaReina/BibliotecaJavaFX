package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.service.IPrestamoService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.PrestamoEntity;
import dad.bibliotecafx.service.entidades.UsuarioEntity;
import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.UsuarioItem;

public class PrestamoService implements IPrestamoService {

	@SuppressWarnings("unchecked")
	@Override
	public List<PrestamoItem> listarPrestamos() throws ServiceException {
		DataBase.begin();
		Query consultaPrestamos = DataBase.getSession().createQuery("FROM PrestamoEntity");
		List<PrestamoEntity> prestamosList = consultaPrestamos.list();
		List<PrestamoItem> prestamos = new ArrayList<PrestamoItem>();
		for (PrestamoEntity p : prestamosList) {
			prestamos.add(p.toItem());
		}
		DataBase.commit();
		return prestamos;
	}

	@Override
	public void crearPrestamo(PrestamoItem prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(prestamo.toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarPrestamo(PrestamoItem prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(prestamo.toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarPrestamo(PrestamoItem prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(prestamo.toEntity());
		DataBase.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PrestamoItem> prestamosPorUsuario(UsuarioItem usuario) throws ServiceException {
		DataBase.begin();		
		List<UsuarioEntity> usuariosList = new ArrayList<UsuarioEntity>();
		usuariosList.add(usuario.toEntity());
		Query consultaPrestamos = DataBase.getSession()
				.createQuery(
						"FROM PrestamoEntity WHERE usuario IN :usuarios");
		consultaPrestamos.setParameterList("usuarios", usuariosList);
		List<PrestamoEntity> prestamosList = consultaPrestamos.list();
		List<PrestamoItem> prestamos = new ArrayList<PrestamoItem>();
		for (PrestamoEntity p : prestamosList) {
			prestamos.add(p.toItem());
		}
		DataBase.commit();
		return prestamos;
	}

}
