package dad.bibliotecafx;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;

import dad.bibliotecafx.controller.BibliotecaLoginController;
import dad.bibliotecafx.controller.BibliotecaPrincipalController;
import dad.bibliotecafx.controller.DevolucionPrestamoController;
import dad.bibliotecafx.controller.GestionConfiguracionesController;
import dad.bibliotecafx.controller.GestionRolesController;
import dad.bibliotecafx.controller.ModificarRolController;
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
	private Integer diasPrestamo, diasSancion, maxLibrosPrestammo;

	private ObservableList<Usuario> usuariosData = FXCollections.observableArrayList();
	private ObservableList<Usuario> usuariosLectoresData = FXCollections.observableArrayList();
	private ObservableList<Libro> librosData = FXCollections.observableArrayList();
	private ObservableList<Prestamo> prestamosData = FXCollections.observableArrayList();
	private ObservableList<Sancion> sancionData = FXCollections.observableArrayList();
	private ObservableList<Rol> rolesData = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		DataBase.connect();
		
		cargarPropiedades();

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BibliotecaFX - Macarena y Joyce");
		this.primaryStage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		try {
			showBibliotecaLoginScene();
		} catch (IOException e2) {
			e2.printStackTrace();
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(
					"Ha ocurrido un error al iniciar la aplicación:\n" + e2.getMessage() + "\n" + e2.getCause());
			alert.showAndWait();
			e2.printStackTrace();
		}

		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				DataBase.disconnect();
				guardarPropiedades();
			}
		});
	}

	public void showBibliotecaLoginScene() throws IOException {
		URL url = getClass().getResource("/dad/bibliotecafx/views/BibliotecaLogin.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		this.primaryStage.setWidth(300);
		this.primaryStage.setHeight(200);
		this.primaryStage.centerOnScreen();
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
		controller.setMain(this, usuarioLogged);
		controller.setFilterLibros(getLibrosData());
		controller.setFilterPrestamos(getPrestamosData());
		controller.setFilterSanciones(getSancionData());
		if(usuarioLogged.getRol().getTipo().equals("Administrador")){
			controller.setFilterUsuarios(getUsuariosData());
		} else{
			controller.setFilterUsuarios(getUsuariosLectoresData());
		}

		this.primaryStage.setScene(scene);
		this.primaryStage.setResizable(true);
		this.primaryStage.setMaximized(true);
		// this.primaryStage.centerOnScreen();
		this.primaryStage.show();
	}

	public void showNuevoUsuarioScene(Usuario usuario) throws IOException {
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
		controller.setMain(this, usuario);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showModificarUsuarioScene(Usuario usuario, Usuario usuarioLogged) throws IOException {
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
		controller.setMain(this, usuarioLogged);
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
		controller.setMain(this, usuarioLogged);
		if(usuarioLogged.getRol().getTipo().equals("Administrador")){
			controller.setFilterUsuarios(getUsuariosData());
		} else{
			controller.setFilterUsuarios(getUsuariosLectoresData());
		}
		controller.setFilterLibros(getLibrosData());

		stage.setScene(scene);
		stage.showAndWait();

	}

	public void showDevolucionPrestamoDialog(Prestamo prestamo) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Devolución de préstamo");
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

	public void showModificarPrestamoScene(Prestamo prestamo) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Modificar préstamo");

		URL url = getClass().getResource("views/BibliotecaModificarPrestamo.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		PrestamoModificarController controller = ((PrestamoModificarController) loader.getController());
		controller.setMain(this, usuarioLogged);
		controller.setPrestamo(prestamo);
		if(usuarioLogged.getRol().getTipo().equals("Administrador")){
			controller.setFilterUsuarios(getUsuariosData());
		} else{
			controller.setFilterUsuarios(getUsuariosLectoresData());
		}
		controller.setFilterLibros(getLibrosData());

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
		controller.setData(diasPrestamo, diasSancion, maxLibrosPrestammo);

		stage.setScene(scene);
		stage.showAndWait();
	}

	public void showModificarRolDialog(Rol rol) throws IOException {
		stage = new Stage();
		stage.getIcons().add(new Image("dad/bibliotecafx/images/biblioteca.png"));
		stage.setTitle("Modificar Rol");
		stage.setResizable(false);

		URL url = getClass().getResource("views/BibliotecaModificarRol.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);

		Scene scene = new Scene(loader.load());

		ModificarRolController controller = ((ModificarRolController) loader.getController());
		controller.setMain(this);
		controller.setRol(rol);
		stage.setScene(scene);
		stage.showAndWait();
		// this.primaryStage.refrescarTablaUsuarios();
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
		controller.setFilterPrestamos(getPrestamosData());

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

	public ObservableList<Usuario> getUsuariosData() {
		actualizarUsuarios();
		return usuariosData;
	}

	private void actualizarUsuarios() {
		usuariosData.clear();
		try {
			for (Usuario usuario : ServiceLocator.getUsuarioService().getUsuarios()) {
				usuariosData.add(usuario);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Usuario> getUsuariosLectoresData() {
		actualizarUsuariosLectores();
		return usuariosLectoresData;
	}

	private void actualizarUsuariosLectores() {
		usuariosLectoresData.clear();
		try {
			for (Usuario usuario : ServiceLocator.getUsuarioService().getUsuariosLectores()) {
				usuariosLectoresData.add(usuario);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Libro> getLibrosData() {
		actualizarLibros();
		return librosData;
	}

	private void actualizarLibros() {
		librosData.clear();
		try {
			for (Libro libro : ServiceLocator.getLibroService().getLibros()) {
				librosData.add(libro);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Prestamo> getPrestamosData() {
		actualizarPrestamos();
		return prestamosData;
	}

	private void actualizarPrestamos() {
		prestamosData.clear();
		try {
			for (Prestamo prestamo : ServiceLocator.getPrestamoService().getPrestamos()) {
				prestamosData.add(prestamo);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Sancion> getSancionData() {
		actualizarSanciones();
		return sancionData;
	}

	private void actualizarSanciones() {
		sancionData.clear();
		try {
			for (Sancion sancion : ServiceLocator.getSancionService().getSanciones()) {
				sancionData.add(sancion);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<Rol> getRolesData() {
		actualizarRoles();
		return rolesData;
	}

	private void actualizarRoles() {
		rolesData.clear();
		try {
			for (Rol rol : ServiceLocator.getRolService().getRoles()) {
				rolesData.add(rol);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

	public Integer getDiasPrestamo() {
		return diasPrestamo;
	}

	public Integer getDiasSancion() {
		return diasSancion;
	}

	public Integer getMaxLibrosPrestamo() {
		return maxLibrosPrestammo;
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

	public void setMaxLibrosPrestamo(Integer maxDiasLibrosPrestamo) {
		this.maxLibrosPrestammo = maxDiasLibrosPrestamo;
	}

	public Stage getStage() {
		return stage;
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}

	public Usuario getUsuarioLogged() {
		return usuarioLogged;
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void cargarPropiedades() {
		property = new Properties();
		try {
			property.load(Main.class.getResourceAsStream("/dad/bibliotecafx/config/config.properties"));
			diasPrestamo = Integer.parseInt(property.getProperty("biblioteca.diasprestamo"));
			diasSancion = Integer.parseInt(property.getProperty("biblioteca.diassancion"));
			maxLibrosPrestammo = Integer.parseInt(property.getProperty("biblioteca.maxlibrosprestamo"));
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar el fichero de configuración:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	private void guardarPropiedades() {
		URI uri = null;
		try {
			uri = Main.class.getResource("/dad/bibliotecafx/config/config.properties").toURI();
		} catch (URISyntaxException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(
					"Ha ocurrido un error con el fichero de propiedades:\n" + e1.getMessage() + "\n" + e1.getCause());
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
			alert.setContentText(
					"No se ha encontrado el fichero de configuración:\n" + e.getMessage() + "\n" + e.getCause());
			alert.showAndWait();
			e.printStackTrace();
		}

		property.setProperty("biblioteca.diasprestamo", String.valueOf(diasPrestamo));
		property.setProperty("biblioteca.diassancion", String.valueOf(diasSancion));
		property.setProperty("biblioteca.maxlibrosprestamo", String.valueOf(maxLibrosPrestammo));
		try {
			property.store(file, null);

		} catch (IOException io) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al guardar los datos en el fichero de propiedades:\n"
					+ io.getMessage() + "\n" + io.getCause());
			alert.showAndWait();
			io.printStackTrace();
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException io) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al cerrar el fichero de propiedades:\n" + io.getMessage()
							+ "\n" + io.getCause());
					alert.showAndWait();
					io.printStackTrace();
				}
			}
		}
	}
}
