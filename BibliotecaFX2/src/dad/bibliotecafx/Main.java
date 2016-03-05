package dad.bibliotecafx;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import dad.bibliotecafx.controller.BibliotecaPruebaController;
import dad.bibliotecafx.controller.PrestamoInsertarController;
import dad.bibliotecafx.controller.UsuarioAltaController;
import dad.bibliotecafx.controller.UsuarioModificarController;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	
//	UsuarioService usuarioService = new UsuarioService();
	
	private Stage primaryStage;
	
	private ObservableList<LibroItem> librosData;
	private ObservableList<UsuarioItem> usuariosData;
	private ObservableList<PrestamoItem> prestamosData;
	
	private ObservableList<Rol> rolesData;

	@Override
	public void start(Stage primaryStage) {
		DataBase.connect();
		
//		try {
//			List<PrestamoItem> prestamosList = ServiceLocator.getPrestamoService().listarPrestamos();
//			prestamosData = FXCollections.observableArrayList(prestamosList);
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
		
		rolesData = FXCollections.observableArrayList();
		try {
			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
			for (RolItem rolItem : rolesList) {
				rolesData.add(rolItem.toModel());
			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		
//		try {
//			List<LibroItem> librosList = ServiceLocator.getLibroService().listarLibros();
//			librosData = FXCollections.observableArrayList(librosList);
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
		
		
		//TODO me da error con los roles, creo q al listar los usuarios, pero no pone numero de linea.
		
//		try {
//			List<UsuarioItem> usuariosList = ServiceLocator.getUsuarioService().listarTodosUsuarios();
//			usuariosData = FXCollections.observableArrayList(usuariosList);
//			
////			personData = FXCollections.observableArrayList(usuariosList);
////			for(int i=0;i<usuariosList.size();i++) {
////				personData.add(usuariosList.get(i));
////			}
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
		
//		BorderPane root = new BorderPane();
//		Scene scene = new Scene(root, 400, 400);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BibliotecaFX - Macarena y Joyce");
		this.primaryStage.setWidth(800);
		this.primaryStage.setHeight(500);
		try {
			showBibliotecaScene();
		} catch (IOException e) {
			//TODO: Mostrar mensaje, no ha sido posible iniciar la aplicación...
			e.printStackTrace();
		}
	}
	
	private void showBibliotecaScene() throws IOException{
		URL url = getClass().getResource("/dad/bibliotecafx/docs/BibliotecaUI.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		
		Scene scene = new Scene(loader.load());
		((BibliotecaPruebaController) loader.getController()).setMain(this);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void showNuevoUsuarioScene() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Alta usuario");
		stage.setWidth(300);
		stage.setHeight(225);
		stage.setResizable(false);
		
		URL url = getClass().getResource("docs/BibliotecaNuevoUsuario.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		
		Scene scene = new Scene(loader.load());
		
		UsuarioAltaController controller = ((UsuarioAltaController) loader.getController());
		controller.setMain(this);
		controller.setRolesData(getRolesData());
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void showModificarUsuarioScene() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Modificar usuario");
		stage.setWidth(300);
		stage.setHeight(225);
		stage.setResizable(false);

		URL url = getClass().getResource("docs/BibliotecaModificarUsuario.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		
		Scene scene = new Scene(loader.load());
		
		((UsuarioModificarController) loader.getController()).setMain(this);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void showNuevoPrestamoScene() throws IOException {
		Stage stage = new Stage();
		stage.setTitle("Nuevo préstamo");
		stage.setWidth(700);
		stage.setHeight(600);
		stage.setResizable(false);

		URL url = getClass().getResource("docs/BibliotecaNuevoPrestamo.fxml");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(url);
		
		Scene scene = new Scene(loader.load());
		
		((PrestamoInsertarController) loader.getController()).setMain(this);
		
		stage.setScene(scene);
		stage.show();
	}
	
//	public Stage getPrimaryStage() {
//        return primaryStage;
//   }
	
	public ObservableList<UsuarioItem> getUsuariosData() {
		return usuariosData;
	}
	
	public ObservableList<LibroItem> getLibrosData() {
		return librosData;
	}
	
	public ObservableList<Rol> getRolesData() {
		return rolesData;
	}
	
	public ObservableList<PrestamoItem> getPrestamosData() {
		return prestamosData;
	}
	

	public static void main(String[] args) {
		launch(args);
	}
}
