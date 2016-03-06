package dad.bibliotecafx;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import dad.bibliotecafx.controller.BibliotecaPrincipalController;
import dad.bibliotecafx.controller.PrestamoInsertarController;
import dad.bibliotecafx.controller.UsuarioAltaController;
import dad.bibliotecafx.controller.UsuarioModificarController;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage primaryStage, stage;

	private ObservableList<Libro> librosData;
	private ObservableList<Usuario> usuariosData;
	private ObservableList<Prestamo> prestamosData;
	private ObservableList<Rol> rolesData;

	@Override
	public void start(Stage primaryStage) {
		DataBase.connect();

		actualizarRoles();

		usuariosData = FXCollections.observableArrayList();
		prestamosData = FXCollections.observableArrayList();
		librosData = FXCollections.observableArrayList();
		rolesData = FXCollections.observableArrayList();

		try {
			List<UsuarioItem> usuaiosItem = ServiceLocator.getUsuarioService().listarTodosUsuarios();
			for (UsuarioItem usuarioItem : usuaiosItem) {
				usuariosData.add(usuarioItem.toModel());
			}

			List<PrestamoItem> prestamosList = ServiceLocator.getPrestamoService().listarPrestamos();
			for (PrestamoItem prestamoItem : prestamosList) {
				prestamosData.add(prestamoItem.toModel());
			}

			List<LibroItem> librosLis = ServiceLocator.getLibroService().listarLibros();
			for (LibroItem libroItem : librosLis) {
				librosData.add(libroItem.toModel());
			}

			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
			for (RolItem rolItem : rolesList) {
				rolesData.add(rolItem.toModel());
			}
		} catch (ServiceException e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al realizar consultas a la Base de Datos");
			alert.showAndWait();
			e2.printStackTrace();
		}

		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BibliotecaFX - Macarena y Joyce");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(500);
		try {
			showBibliotecaScene();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al iniciar la aplicación");
			alert.showAndWait();
			e.printStackTrace();
		}

		this.primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				DataBase.disconnect();
			}
		});
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

	private void showBibliotecaScene() throws IOException {
		URL url = getClass().getResource("/dad/bibliotecafx/views/BibliotecaUI.fxml");
		FXMLLoader loader = new FXMLLoader(url);

		Scene scene = new Scene(loader.load());

		BibliotecaPrincipalController controller = ((BibliotecaPrincipalController) loader.getController());
		controller.setMain(this);
		controller.setFilterLibros(getLibrosData());
		controller.setFilterUsuarios(getUsuariosData());
		controller.setFilterPrestamos(getPrestamosData());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void showNuevoUsuarioScene() throws IOException {
		stage = new Stage();
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

	public Stage getStage() {
		return stage;
	}
	
	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
