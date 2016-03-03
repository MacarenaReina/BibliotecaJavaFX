package dad.bibliotecafx;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;

import dad.bibliotecafx.db.DataBase;
//import dad.bibliotecafx.modelo.Autor;
//import dad.bibliotecafx.modelo.Editorial;
//import dad.bibliotecafx.modelo.Libro;
//import dad.bibliotecafx.modelo.Prestamo;
//import dad.bibliotecafx.modelo.Rol;
//import dad.bibliotecafx.modelo.RolUsuario;
//import dad.bibliotecafx.modelo.Sancion;
//import dad.bibliotecafx.modelo.Usuario;
//import dad.bibliotecafx.service.ServiceException;
//import dad.bibliotecafx.service.ServiceLocator;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.collections.ObservableSet;

public class Main_HQL {

	public static void main(String[] args) {
		DataBase.connect();
		
		//HOLA MACARENAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
		
//		try {
//			AutorItem autor = new AutorItem();
//			Autor autor = new Autor();
//			autor.setNombre("Federico García Lorca");
//			ServiceLocator.getAutorService().crearAutor(autor.toItem());
//			
//			System.out.println("Todos los autores de la BD:");
//			
//			List<AutorItem> autores = ServiceLocator.getAutorService().listarAutores();
//			for (AutorItem autorItem : autores) {
//				System.out.println(autorItem.getNombre());
//			}
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
//			Rol rolAdmin = new Rol();
//			rolAdmin.setTipo("Administrador");
//
//			ServiceLocator.getRolService().crearRol(rolAdmin.toItem());
//
//			Usuario usuarioAdmin = new Usuario();
//			usuarioAdmin.setNombre("admin");
//			usuarioAdmin.setPassword("admin");
//			usuarioAdmin.setUsuario("admin");
//
//			ServiceLocator.getUsuarioService().crearUsuario(usuarioAdmin.toItem());
//			
//			usuarioAdmin.setCodigo(1);
//			rolAdmin.setCodigo(1);
//			
//			RolUsuario ru = new RolUsuario();
//			ru.setPermiso(rolAdmin);
//			ru.setUsuario(usuarioAdmin);
//			ru.setActivado(true);
//			
//			ServiceLocator.getRolService().asignarRol(ru.toItem());
//
//			Editorial editorial = new Editorial();
//			editorial.setNombre("Anaya");
//			ServiceLocator.getEditorialService().crearEditorial(editorial.toItem());
//
//			editorial.setCodigo(1);
//			
//			ObservableSet<Autor> autores = FXCollections.observableSet(new HashSet<Autor>());  
//			
//			Autor autor1 = new Autor();
//			autor1.setNombre("Gustavo Adolfo Béquer");
//			ServiceLocator.getAutorService().crearAutor(autor1.toItem());
//			autor1.setCodigo(1);
//			
//			Autor autor2 = new Autor();
//			autor2.setNombre("Miguel de Cervantes");
//			ServiceLocator.getAutorService().crearAutor(autor2.toItem());
//			autor2.setCodigo(2);
//
//			autores.add(autor1);
//			autores.add(autor2);
//
//
//			Libro libro1 = new Libro();
//			libro1.setAutores(autores);
//			libro1.setEditorial(editorial);
//			libro1.setAnioPublicacion(2016);
//			libro1.setISBN("1111111111111");
//			libro1.setTitulo("Don Quijote de la Mancha");
//
//			ServiceLocator.getLibroService().actualizarLibro(libro1.toItem());			
//
//			Libro libro2 = new Libro();
//			libro2.setAutores(autores);
//			libro2.setEditorial(editorial);
//			libro2.setAnioPublicacion(1990);
//			libro2.setISBN("2222222222222");
//			libro2.setTitulo("Crónicas de una muerte anunciada");
//
//			ServiceLocator.getLibroService().actualizarLibro(libro2.toItem());			
//
//			ObservableList<Libro> libros = FXCollections.observableArrayList(new ArrayList<Libro>());
//			libros.add(libro1);
//			libros.add(libro2);
//
//			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//
//			Prestamo prestamo = new Prestamo();
//			try {
//				prestamo.setFechaPrestamo(format.parse("01/03/2016"));
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//			prestamo.setUsuario(usuarioAdmin);
//			prestamo.setLibro(libros);
//
//			ServiceLocator.getPrestamoService().crearPrestamo(prestamo.toItem());
//			prestamo.setCodigo(2);
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
//			ServiceLocator.getSancionService().crearSancion(sancion.toItem());
//
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
//
//		DataBase.getSession().close();
	}
}
