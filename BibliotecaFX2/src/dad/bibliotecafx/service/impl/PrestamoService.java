package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.IPrestamoService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.PrestamoEntity;
import dad.bibliotecafx.service.entidades.UsuarioEntity;
public class PrestamoService implements IPrestamoService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Prestamo> getPrestamos() throws ServiceException {
		DataBase.begin();
		Query consultaPrestamos = DataBase.getSession().createQuery("FROM PrestamoEntity");
		List<PrestamoEntity> prestamosListEntity = consultaPrestamos.list();
		List<Prestamo> prestamosList = new ArrayList<Prestamo>();
		for (PrestamoEntity p : prestamosListEntity) {
			prestamosList.add(p.toItem().toModel());
		}
		DataBase.commit();
		return prestamosList;
	}

	@Override
	public void crearPrestamo(Prestamo prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(prestamo.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarPrestamo(Prestamo prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(prestamo.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarPrestamo(Prestamo prestamo) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(prestamo.toItem().toEntity());
		DataBase.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Prestamo> prestamosPorUsuario(Usuario usuario) throws ServiceException {
		DataBase.begin();
		List<UsuarioEntity> usuariosList = new ArrayList<UsuarioEntity>();
		usuariosList.add(usuario.toItem().toEntity());
		Query consultaPrestamos = DataBase.getSession().createQuery("FROM PrestamoEntity WHERE usuario IN :usuarios");
		consultaPrestamos.setParameterList("usuarios", usuariosList);
		List<PrestamoEntity> prestamosList = consultaPrestamos.list();
		List<Prestamo> prestamos = new ArrayList<Prestamo>();
		for (PrestamoEntity p : prestamosList) {
			prestamos.add(p.toItem().toModel());
		}
		DataBase.commit();
		return prestamos;
	}

//	private Long getUltimoId() {
//		Long lastId;
//		lastId = ((BigInteger) DataBase.getSession().createSQLQuery("SELECT LAST_INSERT_ID()").uniqueResult())
//				.longValue();
//		return lastId;
//	}

}
