package dad.bibliotecafx.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ISancionService;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.entidades.SancionEntity;
import dad.bibliotecafx.service.entidades.UsuarioEntity;

public class SancionService implements ISancionService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Sancion> getSanciones() throws ServiceException {
		DataBase.begin();
		Query consultaSanciones = DataBase.getSession().createQuery("FROM SancionEntity");
		List<SancionEntity> sancionListEntity = consultaSanciones.list();
		List<Sancion> sancionList = new ArrayList<Sancion>();
		for (SancionEntity s : sancionListEntity) {
			sancionList.add(s.toItem().toModel());
		}
		DataBase.commit();
		return sancionList;
	}

	@Override
	public void crearSancion(Sancion sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().save(sancion.toItem().toEntity());
		DataBase.commit();
	}

	@Override
	public void actualizarSancion(Sancion sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().update(DataBase.getSession().merge(sancion.toItem().toEntity()));
		DataBase.commit();
	}

	@Override
	public void eliminarSancion(Sancion sancion) throws ServiceException {
		DataBase.begin();
		DataBase.getSession().delete(sancion.toItem().toEntity());
		DataBase.commit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sancion> listarSancionesPorUsuario(Usuario usuario) throws ServiceException {
		DataBase.begin();
		List<UsuarioEntity> usuariosList = new ArrayList<UsuarioEntity>();
		usuariosList.add(usuario.toItem().toEntity());
		Query consultaSanciones = DataBase.getSession()
				.createQuery("FROM SancionEntity WHERE prestamo.usuario IN :usuarios");
		consultaSanciones.setParameterList("usuarios", usuariosList);
		List<SancionEntity> sancionList = consultaSanciones.list();
		List<Sancion> sanciones = new ArrayList<Sancion>();
		for (SancionEntity p : sancionList) {
			sanciones.add(p.toItem().toModel());
		}
		DataBase.commit();
		return sanciones;
	}

}
