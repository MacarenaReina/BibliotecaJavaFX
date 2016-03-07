package dad.bibliotecafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Properties;

import dad.bibliotecafx.controller.BibliotecaLoginController;
import dad.bibliotecafx.controller.BibliotecaPrincipalController;
import dad.bibliotecafx.controller.DevolucionPrestamoController;
import dad.bibliotecafx.controller.GestionConfiguracionesController;
import dad.bibliotecafx.controller.GestionRolesController;
import dad.bibliotecafx.controller.PrestamoInsertarController;
import dad.bibliotecafx.controller.PrestamoModificarController;
import dad.bibliotecafx.controller.SancionInsertarController;
import dad.bibliotecafx.controller.SancionModificarController;
import dad.bibliotecafx.controller.UsuarioAltaController;
import dad.bibliotecafx.controller.UsuarioModificarController;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.SancionItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage primaryStage, stage;
	private Usuario usuarioLogged;

	private Properties property;
	private Integer diasPrestamo, diasSancion;

	private ObservableList<Libro> librosData;
	private ObservableList<Usuario> usuariosData;
	private ObservableList<Prestamo> prestamosData;
	private ObservableList<Rol> rolesData;
	private ObservableList<Sancion> sancionesData;

	@Override
	public void start(Stage primaryStage) {
		DataBase.connect();
		Rol rolAdmin = new Rol();
		rolAdmin.setTipo("Administrador");
		try {
			if(!ServiceLocator.getRolService().crearRol(rolAdmin.toItem())){
				rolAdmin.setCodigo(1);
				Usuario usuarioAdmin = new Usuario();
				usuarioAdmin.setNombre("admin");
				usuarioAdmin.setUsuario("admin");
				usuarioAdmin.setPassword("admin");
				usuarioAdmin.setRol(rolAdmin);
				ServiceLocator.getUsuarioService().crearUsuario(usuarioAdmin.toItem());
			}
		} catch (ServiceException e2) {
			e2.printStackTrace();
		}
		property = new Properties();
		try {
			property.load(Main.class.getResourceAsStream("/dad/bibliotecafx/config/config.properties"));
			diasPrestamo = Integer.parseInt(property.getProperty("biblioteca.diasprestamo"));
			diasSancion = Integer.parseInt(property.getProperty("biblioteca.diassancion"));
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar el fichero de configuración");
			alert.showAndWait();
			e1.printStackTrace();
		}

		actualizarRoles();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BibliotecaFX - Macarena y Joyce");
		this.primaryStage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		try {
			showBibliotecaLoginScene();
		} catch (IOException e2) {
			e2.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al iniciar la aplicación");
			alert.showAndWait();
			e2.printStackTrace();
		}

		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				DataBase.disconnect();
				URI uri = null;
				try {
					uri = Main.class.getResource("/dad/bibliotecafx/config/config.properties").toURI();
				} catch (URISyntaxException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error con el fichero de propiedades");
					alert.showAndWait();
					e1.printStackTrace();
				}
				File f = new File(uri);
				FileOutputStream file = null;

				try {
					file = new FileOutputStream(f);
				} catch (FileNotFoundException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("No se ha encontrado el fichero de configuración");
					alert.showAndWait();
					e.printStackTrace();
				}

				property.setProperty("biblioteca.diasprestamo", String.valueOf(diasPrestamo));
				property.setProperty("biblioteca.diassancion", String.valueOf(diasSancion));
				try {
					property.store(file, null);

				} catch (IOException io) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al guardar los datos en el fichero de propiedades");
					alert.showAndWait();
					io.printStackTrace();
				} finally {
					if (file != null) {
						try {
							file.close();
						} catch (IOException io) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Error");
							alert.setContentText("Ha ocurrido un error al cerrar el fichero de propiedades");
							alert.showAndWait();
							io.printStackTrace();
						}
					}
				}
			}
		});
	}
	
	public void showBibliotecaLoginScene() throws IOException {
		URL url = getClass().getResource("/dad/bibliotecafx/views/BibliotecaLogin.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		this.primaryStage.setWidth(300);
		this.primaryStage.setHeight(200);
		this.primaryStage.setResizable(false);

		Scene scene = new Scene(loader.load());

		BibliotecaLoginController controller = ((BibliotecaLoginController) loader.getController());
		controller.setMain(this);
		
		this.primaryStage.setScene(scene);
		this.primaryStage.show();
	}

	public void showBibliotecaScene(Usuario usuarioLogged) throws IOException {
		URL url = getClass().getResource("/dad/bibliotecafx/views/BibliotecaUI.fxml");
		FXMLLoader loader = new FXMLLoader(url);

		Scene scene = new Scene(loader.load());
		
		this.usuarioLogged = usuarioLogged;

		BibliotecaPrincipalController controller = ((BibliotecaPrincipalController) loader.getController());
		controller.setMain(this);
		controller.setFilterLibros(getLibrosData());
		controller.setFilterUsuarios(getUsuariosData());
		controller.setFilterPrestamos(getPrestamosData());
		controller.setFilterSanciones(getSancionesData());
//		controller.setUsuarioLogged(usuarioLogged);
		
		this.primaryStage.setScene(scene);
		this.primaryStage.setResizable(true);
		this.primaryStage.setMaximized(true);
		this.primaryStage.show();
	}

	public void showNuevoUsuarioScene() throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Alta usuario");
		stage.setWidth(400);
		stage.setHeight(300);
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaNuevoUsuario.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		UsuarioAltaController controller = ((UsuarioAltaController) loader.getController());
		controller.setMain(this);
		controller.setRolesData(getRolesData());

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showModificarUsuarioScene(Usuario usuario) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Modificar usuario");
		stage.setWidth(400);
		stage.setHeight(300);
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaModificarUsuario.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		UsuarioModificarController controller = ((UsuarioModificarController) loader.getController());
		controller.setMain(this);
		controller.setRolesData(getRolesData());
		controller.setUsuario(usuario);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showNuevoPrestamoScene() throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Nuevo préstamo");
		stage.setWidth(700);
		stage.setHeight(600);
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaNuevoPrestamo.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		PrestamoInsertarController controller = ((PrestamoInsertarController) loader.getController());
		controller.setMain(this);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showGesionConfiuracionesDialog() throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Configuración");
		stage.setResizable(false);

		URL url = getClass().getResource("views/GestionConfiguracionesDialog.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		GestionConfiguracionesController controller = ((GestionConfiguracionesController) loader.getController());
		controller.setMain(this);
		controller.setData(diasPrestamo, diasSancion);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showDevolucionPrestamoDialog(Prestamo prestamo) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Configuración");
		stage.setResizable(false);

		URL url = getClass().getResource("views/DevolucionPrestamo.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		DevolucionPrestamoController controller = ((DevolucionPrestamoController) loader.getController());
		controller.setMain(this);
		controller.setPrestamo(prestamo);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showGestionRolesScene() throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Gestión de Roles");

		URL url = getClass().getResource("views/BibliotecaGestionRoles.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		GestionRolesController controller = ((GestionRolesController) loader.getController());
		controller.setMain(this);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showNuevaSancionScene() throws IOException {
		stage = new Stage();
		stage.setTitle("Nueva sanción");
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setWidth(700);
		stage.setHeight(600);
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaNuevaSancion.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		SancionInsertarController controller = ((SancionInsertarController) loader.getController());
		controller.setMain(this);

		stage.setScene(scene);
		stage.showAndWait();
	}
	
	public void showModificarSancionScene(Sancion sancion) throws IOException {
		stage = new Stage();
		stage.setTitle("Modificar sanción");
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaModificarSancion.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		SancionModificarController controller = ((SancionModificarController) loader.getController());
		controller.setMain(this);
		controller.setSancion(sancion);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showModificarPrestamoScene(Prestamo prestamo) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Configuración");
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaModificarPrestamo.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		PrestamoModificarController controller = ((PrestamoModificarController) loader.getController());
		controller.setMain(this);
		controller.setPrestamo(prestamo);
		stage.setScene(scene);
		stage.showAndWait();
	}

	public ObservableList<Usuario> getUsuariosData() {
		actualizarUsuarios();
		return usuariosData;
	}

	public ObservableList<Libro> getLibrosData() {
		actualizarLibros();
		return librosData;
	}

	public ObservableList<Rol> getRolesData() {
		actualizarRoles();
		return rolesData;
	}

	public ObservableList<Prestamo> getPrestamosData() {
		actualizarPrestamos();
		return prestamosData;
	}

	public ObservableList<Sancion> getSancionesData() {
		actualizarSanciones();
		return sancionesData;
	}

	private void actualizarUsuarios() {
		usuariosData = FXCollections.observableArrayList();
		try {
			List<UsuarioItem> usuariosList = ServiceLocator.getUsuarioService().listarTodosUsuarios();
			for (UsuarioItem usuarioItem : usuariosList) {
				usuariosData.add(usuarioItem.toModel());
			}
		} catch (ServiceException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar los usuarios");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}
	
	private void actualizarLibros() {
		librosData = FXCollections.observableArrayList();
		try {
			List<LibroItem> librosList = ServiceLocator.getLibroService().listarLibros();
			for (LibroItem libroItem : librosList) {
				librosData.add(libroItem.toModel());
			}
		} catch (ServiceException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar los libros");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	private void actualizarPrestamos() {
		prestamosData = FXCollections.observableArrayList();
		try {
			List<PrestamoItem> prestamosList = ServiceLocator.getPrestamoService().listarPrestamos();
			for (PrestamoItem prestamoItem : prestamosList) {
				prestamosData.add(prestamoItem.toModel());
			}
		} catch (ServiceException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar los préstamos");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	private void actualizarRoles() {
		rolesData = FXCollections.observableArrayList();
		try {
			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
			for (RolItem rolItem : rolesList) {
				rolesData.add(rolItem.toModel());
			}
		} catch (ServiceException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar los préstamos");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	private void actualizarSanciones() {
		sancionesData = FXCollections.observableArrayList();
		try {
			List<SancionItem> sancionesList = ServiceLocator.getSancionService().listarSanciones();
			for (SancionItem sancionItem : sancionesList) {
				sancionesData.add(sancionItem.toModel());
			}
		} catch (ServiceException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar las sanciones");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	public Integer getDiasPrestamo() {
		return diasPrestamo;
	}

	public Integer getDiasSancion() {
		return diasSancion;
	}

	public void setDiasPrestamo(Integer diasPrestamo) {
		this.diasPrestamo = diasPrestamo;
	}

	public void setDiasSancion(Integer diasSancion) {
		this.diasSancion = diasSancion;
	}

	public Properties getProperty() {
		return property;
	}

	public Stage getStage() {
		return stage;
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public Usuario getUsuarioLogged(){
		return usuarioLogged;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
