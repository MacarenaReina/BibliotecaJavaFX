package dad.bibliotecafx;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;

import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.modelo.Editorial;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;

public class Main_HQL {

	public static void main(String[] args) {
		DataBase.connect();
//		ObservableList<Rol> rolesData = FXCollections.observableArrayList(new ArrayList<Rol>());
		try {
//			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
//			for (RolItem rolItem : rolesList) {
//				System.out.println(rolItem.getTipo());
//			}
//			for (RolItem rolItem : rolesList) {
//				rolesData.add(rolItem.toModel());
//			}
//			for (Rol rol : rolesData) {
//				System.out.println(rol.getTipo());
//			}
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
//		
//		
//		
//		try {
//			ObservableList<Editorial> edit = FXCollections.observableArrayList(new ArrayList<Editorial>());
//			List<EditorialItem> editorialList = ServiceLocator.getEditorialService().listarEditoriales();
//			for (EditorialItem edItem : editorialList) {
//				edit.add(edItem.toModel());
//			}
//			
//			for (Editorial editorial : edit) {
//				System.out.println(editorial.getNombre());
//			}
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			ObservableList<Sancion> sancion = FXCollections.observableArrayList(new ArrayList<Sancion>());
//			List<SancionItem> sancionList = ServiceLocator.getSancionService().listarSanciones();
//			for (SancionItem sanItem : sancionList) {
//				sancion.add(sanItem.toModel());
//			}
//			
//			for (Sancion s : sancion) {
//				System.out.println(s.getFechaAlta());
//			}
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			AutorItem autor = new AutorItem();
//			Autor autor = new Autor();
//			autor.setNombre("Federico García Lorca");
//			ServiceLocator.getAutorService().crearAutor(autor.toItem());
//			
//			System.out.println("Todos los autores de la BD:");
//			
//			
//			try {
//				List<AutorItem> autores = ServiceLocator.getAutorService().listarAutores();
////				for (AutorItem autorItem : autores) {
////					System.out.println(autorItem.getNombre());
////				}
//				List<Autor> autor = new ArrayList<Autor>();
//				for (AutorItem a : autores) {
//					autor.add(a.toModel());
//				}
//				
//				for (Autor au : autor) {
//					System.out.println(au.getNombre());
//				}
//				
//			} catch (ServiceException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			AutorItem autorBorrar = new AutorItem();
//			Autor autorBorrar = new Autor();
//			Long cod = (long) 6;
//			autorBorrar.setCodigo(cod);
//			ServiceLocator.getAutorService().eliminarAutor(autorBorrar.toItem());
//			
//			System.out.println("Después de borrar:");
//			List<AutorItem> autores2 = ServiceLocator.getAutorService().listarAutores();
//			for (AutorItem autorItem : autores2) {
//				System.out.println(autorItem.getNombre());
//			}
//			
//			AutorItem autorAtualizar = new AutorItem();
//			Autor autorAtualizar = new Autor();
//			Long cod2 = (long) 2;
//			autorAtualizar.setCodigo(cod2);
//			autorAtualizar.setNombre("Pepe Lumbreras");
//			ServiceLocator.getAutorService().actualizarAutor(autorAtualizar.toItem());
//			
//			System.out.println("Después de actualizar:");
//			List<AutorItem> autores3 = ServiceLocator.getAutorService().listarAutores();
//			for (AutorItem autorItem : autores3) {
//				System.out.println(autorItem.getNombre());
//			}
//			
//			
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//
//		try {
			Rol rolAdmin = new Rol();
			rolAdmin.setTipo("Administrador");
			rolAdmin.setCodigo(1);
//			ServiceLocator.getRolService().crearRol(rolAdmin.toItem());
//
//			Usuario usuarioAdmin = new Usuario();
//			usuarioAdmin.setNombre("admin");
//			usuarioAdmin.setPassword("admin");
//			usuarioAdmin.setUsuario("admin");
//			usuarioAdmin.setRol(rolAdmin);
//			
//			ServiceLocator.getUsuarioService().crearUsuario(usuarioAdmin.toItem());
//			
//			usuarioAdmin.setCodigo(1);
//			
//			RolUsuario ru = new RolUsuario();
//			ru.setPermiso(rolAdmin);
//			ru.setUsuario(usuarioAdmin);
//			ru.setActivado(true);
//			
//			ServiceLocator.getRolService().asignarRol(ru.toItem());
//
			Editorial editorial = new Editorial();
			editorial.setNombre("Anaya");
//			ServiceLocator.getEditorialService().crearEditorial(editorial.toItem());
//
			editorial.setCodigo(1);
//			
//			ServiceLocator.getEditorialService().actualizarEditorial(editorial.toItem());
//			
//			List<EditorialItem> editorialList = ServiceLocator.getEditorialService().listarEditoriales();
//			List<Editorial> editoriales = new ArrayList<Editorial>();
//			for (EditorialItem e : editorialList) {
//				editoriales.add(e.toModel());
//			}
//			for (Editorial e : editoriales) {
//				System.out.println(e.getNombre());
//			}
//			
			ObservableSet<Autor> autores = FXCollections.observableSet(new HashSet<Autor>());  
			
			Autor autor1 = new Autor();
			autor1.setNombre("Gustavo Adolfo Béquer");
//			ServiceLocator.getAutorService().crearAutor(autor1.toItem());
			autor1.setCodigo(1);
			
			Autor autor2 = new Autor();
			autor2.setNombre("Miguel de Cervantes");
//			ServiceLocator.getAutorService().crearAutor(autor2.toItem());
			autor2.setCodigo(2);

			autores.add(autor1);
			autores.add(autor2);
			
//			List<AutorItem> autorItems = ServiceLocator.getAutorService().listarAutores();
//			List<Autor> autoresConsulta = new ArrayList<Autor>();
//			for (AutorItem a : autorItems) {
//				autoresConsulta.add(a.toModel());
//			}
//			for (Autor autor : autoresConsulta) {
//				System.out.println(autor.getNombre());
//			}

			Libro libro1 = new Libro();
			libro1.setAutores(autores);
			libro1.setEditorial(editorial);
			libro1.setAnioPublicacion(2016);
			libro1.setISBN("111111111111");
			libro1.setTitulo("Don Quijote de la Mancha");

//			ServiceLocator.getLibroService().crearLibro(libro1.toItem());			

			Libro libro2 = new Libro();
			libro2.setAutores(autores);
			libro2.setEditorial(editorial);
			libro2.setAnioPublicacion(1990);
			libro2.setISBN("2222222222222");
			libro2.setTitulo("Crónicas de una muerte anunciada");

//			ServiceLocator.getLibroService().crearLibro(libro2.toItem());			

			ObservableSet<Libro> libros = FXCollections.observableSet();
			libros.add(libro1);
			libros.add(libro2);
			
//			List<LibroItem> libroItems = ServiceLocator.getLibroService().listarLibros();
//			
//			List<LibroItem> libroItemsAutor = ServiceLocator.getLibroService().librosPorAutor(autor1.toItem());
//			
//			List<Libro> lib = new ArrayList<Libro>();
//			for (LibroItem libro : libroItems) {
//				lib.add(libro.toModel());
//			}
//			List<Libro> lib2 = new ArrayList<Libro>();
//			for (LibroItem libro : libroItemsAutor) {
//				lib2.add(libro.toModel());
//			}
//			
//			for (Libro libro : lib) {
//				System.out.println(libro.getTitulo());
//			}
//			for (Libro libro : lib2) {
//				System.out.println(libro.getTitulo());
//			}

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			Prestamo prestamo = new Prestamo();
			try {
				prestamo.setFechaPrestamo(format.parse("01/03/2016"));
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			
			Rol r = new Rol();
			r.setTipo("Lector");
//			ServiceLocator.getRolService().crearRol(r.toItem());
			r.setCodigo(1);
			
			Usuario u2 = new Usuario();
			u2.setNombre("lector");
			u2.setUsuario("lector2");
			u2.setPassword("lector");
			u2.setRol(rolAdmin);
			
//			ServiceLocator.getUsuarioService().crearUsuario(u2.toItem());
		
			u2.setCodigo(1);
			
			prestamo.setUsuario(u2);
			prestamo.setLibro(libros);

//			ServiceLocator.getPrestamoService().crearPrestamo(prestamo.toItem());
//			
//			prestamo.setCodigo(1);
//			
//			List<PrestamoItem> prestItems = ServiceLocator.getPrestamoService().listarPrestamos();
//			List<Prestamo> prestamos = new ArrayList<Prestamo>();
//			for (PrestamoItem p : prestItems) {
//				prestamos.add(p.toModel());
//			}
//			for (Prestamo p : prestamos) {
//				System.out.println(p.getFechaPrestamo());
//				System.out.println(p.getUsuario().getNombre());
//				for (Libro l : p.getLibro()) {
//					System.out.println(l.getTitulo());
//				}
//			}
//			
//			List<PrestamoItem> prestItemsUsu = ServiceLocator.getPrestamoService().prestamosPorUsuario(u2.toItem());
//			List<Prestamo> prestamoUsu = new ArrayList<Prestamo>();
//			for (PrestamoItem p : prestItemsUsu) {
//				prestamoUsu.add(p.toModel());
//			}
//			for (Prestamo p : prestamoUsu) {
//				System.out.println(p.getFechaPrestamo());
//				System.out.println(p.getUsuario().getNombre());
//				for (Libro l : p.getLibro()) {
//					System.out.println(l.getTitulo());
//				}
//			}
//			
//			
//			Sancion sancion = new Sancion();
//			try {
//				sancion.setFechaAlta(format.parse("01/03/2016"));
//				sancion.setFechaFinalizacion(format.parse("03/03/2016"));
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//			sancion.setLibro(libro1);
//			sancion.setPrestamo(prestamo);
//
//			List<SancionItem> sancionItems = ServiceLocator.getSancionService().listarSanciones();
//			List<Sancion> sanciones = new ArrayList<Sancion>();
//			for (SancionItem s : sancionItems) {
//				sanciones.add(s.toModel());
//			}
//			for (Sancion s : sanciones) {
//				System.out.println(s.getFechaAlta());
//				System.out.println(s.getPrestamo().getUsuario().getNombre());
//			}
//			
//			List<SancionItem> sancionItems = ServiceLocator.getSancionService().listarSancionesPorUsuario(u2.toItem());
//			List<Sancion> sanciones = new ArrayList<Sancion>();
//			for (SancionItem s : sancionItems) {
//				sanciones.add(s.toModel());
//			}
//			for (Sancion s : sanciones) {
//				System.out.println(s.getFechaAlta());
//				System.out.println(s.getPrestamo().getUsuario().getNombre());
//			}
//			
//			ServiceLocator.getSancionService().crearSancion(sancion.toItem());
//
//			Rol r = new Rol();
//			r.setTipo("Administrador");
//			ServiceLocator.getRolService().crearRol(r.toItem());
//			
//			r.setCodigo(1);
//			
//			Rol rLector = new Rol();
//			rLector.setTipo("Lector");		
//			
//			Rol rBi = new Rol();
//			rBi.setTipo("Bibliotecario");	
//			rBi.setCodigo(2);
//			
//			ServiceLocator.getRolService().actualizarRol(rBi.toItem());
//			
//			List<RolItem> rolItems = ServiceLocator.getRolService().listarRoles();
//			ObservableList<Rol> roles = FXCollections.observableArrayList(new ArrayList<Rol>());
//			for (RolItem rol : rolItems) {
//				roles.add(rol.toModel());
//			}
//			for (Rol rol : roles) {
//				System.out.println(rol.getTipo());
//			}
//			
//			ServiceLocator.getRolService().crearRol(rLector.toItem());
//			
//			rLector.setCodigo(4);
//			
//			Usuario u = new Usuario();
//			u.setNombre("admin");
//			u.setUsuario("admin");
//			u.setPassword("admin");
//			u.setRol(r);
//			u.setCodigo(1);
//			
//			ServiceLocator.getUsuarioService().actualizarUsuario(u.toItem());
//			ServiceLocator.getUsuarioService().crearUsuario(u.toItem());
//			
//			Usuario u2 = new Usuario();
//			u2.setNombre("lector");
//			u2.setUsuario("lector");
//			u2.setPassword("lector");
//			u2.setRol(r);
//			u2.setCodigo(4);
//			
//			ServiceLocator.getUsuarioService().actualizarUsuario(u2.toItem());			
//			ServiceLocator.getUsuarioService().crearUsuario(u2.toItem());
//			
//			List<UsuarioItem> usuarioItem = ServiceLocator.getUsuarioService().listarTodosUsuarios();
//			ObservableList<Usuario> usuarios = FXCollections.observableArrayList(new ArrayList<Usuario>());
//			for (UsuarioItem usuario : usuarioItem) {
//				usuarios.add(usuario.toModel());
//			}
//			for (Usuario usuario : usuarios) {
//				System.out.println("Usuario: " + usuario.getNombre());
//				System.out.println("Rol: " + usuario.getRol().getTipo());
//			}	
			
			List<UsuarioItem> usuarioItem = ServiceLocator.getUsuarioService().listarUsuariosLectores();
			ObservableList<Usuario> usuarios = FXCollections.observableArrayList(new ArrayList<Usuario>());
			for (UsuarioItem usuario : usuarioItem) {
				usuarios.add(usuario.toModel());
			}
			for (Usuario usuario : usuarios) {
				System.out.println("Usuario: " + usuario.getNombre());
				System.out.println("Rol: " + usuario.getRol().getTipo());
			}
			
			
		} catch (ServiceException e) {
			e.printStackTrace();
		}

		DataBase.getSession().close();
	}
}
