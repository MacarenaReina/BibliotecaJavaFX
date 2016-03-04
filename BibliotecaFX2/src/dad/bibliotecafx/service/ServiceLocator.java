package dad.bibliotecafx.service;

import dad.bibliotecafx.service.impl.AutorService;
import dad.bibliotecafx.service.impl.EditorialService;
import dad.bibliotecafx.service.impl.LibroService;
import dad.bibliotecafx.service.impl.PrestamoService;
import dad.bibliotecafx.service.impl.RolService;
import dad.bibliotecafx.service.impl.SancionService;
import dad.bibliotecafx.service.impl.UsuarioService;

public class ServiceLocator {
	private static ILibroService libroService;
	private static IEditorialService editorialService;
	private static IRolService rolService;
	private static ISancionService sancionService;
	private static IUsuarioService usuarioService;
	private static IPrestamoService prestamoService;
	private static IAutorService autorService;
	
	static {
		libroService = new LibroService();
		editorialService = new EditorialService();
		rolService = new RolService();
		sancionService = new SancionService();
		usuarioService = new UsuarioService();
		prestamoService = new PrestamoService();
		autorService = new AutorService();
	}

	public static ILibroService getLibroService() {
		return libroService;
	}
	
	public static IEditorialService getEditorialService() {
		return editorialService;
	}
	
	public static IRolService getRolService() {
		return rolService;
	}
	
	public static ISancionService getSancionService() {
		return sancionService;
	}
	
	public static IUsuarioService getUsuarioService() {
		return usuarioService;
	}
	
	public static IPrestamoService getPrestamoService() {
		return prestamoService;
	}
	
	public static IAutorService getAutorService() {
		return autorService;
	}
	
}
