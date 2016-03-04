package dad.bibliotecafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.JOptionPane;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.RolUsuario;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.impl.RolService;
import dad.bibliotecafx.service.impl.UsuarioService;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class BibliotecaPruebaController {
	
	private Main main;
	
//	private StringProperty usuario, password;
	
	//PESTAÑA LIBROS:
	@FXML
	private TextField libroText;  //El nombre tiene que coindicir con el id que tiene en el XML
	@FXML
	private TableView<String> librosTable;
	@FXML
	private TableColumn<String, String> tituloTableColumn;
	@FXML
	private TableColumn<String, String> autorTableColumn;
	@FXML
	private TableColumn<String, Integer> anioTableColumn;
	@FXML
	private TableColumn<String, String> ejemplaresBiblioTableColumn;
	
	//PESTAÑA USUARIOS:
	@FXML
	private TextField usuarioText;
	@FXML
	private Button carnetButton, altaUsuButton, modificarUsuButton, bajaUsuButton;
	@FXML
	private TableView<Usuario> usuariosTable;
	@FXML
	private TableColumn<Usuario, String> nombreTableColum;
	@FXML
	private TableColumn<Usuario, List<RolUsuario>> rolTableColumn;
	
	//PESTAÑA PRESTAMOS
	@FXML
	private TextField prestamoText;
	@FXML
	private ComboBox<TableColumn<String, ?>> buscarPresComboBox;
	@FXML
	private Button nuevoPresButton, eliminarPresButton;
	@FXML
	private TableView<String> prestamosTable;
	@FXML
	private TableColumn<String, Integer> codigoPresTableColumn;
	@FXML
	private TableColumn<String, String> libroPresTableColumn;
	@FXML
	private TableColumn<String, String> usuarioPresTableColumn;
	@FXML
	private TableColumn<String, Date> fechainiPresTableColumn;
	
	//INSERTAR USUARIO
//	@FXML
//	private TextField nombreUsuText;
//	@FXML
//	private ComboBox<Rol> rolUsuComboBox;
//	@FXML
//	private Button nuevoRolButton, insertarUsuButton, cancelarUsuButton;
	
	
	private RolService rolService;
	private UsuarioService usuarioService;
	private ObservableList<Usuario> personData;
	
	ObservableList<TableColumn<String, ?>> buscarPresComboBoxOL;
	ObservableList<Rol> rolesComboBox;
//	final ComboBox comboBox = new ComboBox(options);
	
//	private Main main;
	
	
	
	public BibliotecaPruebaController() {
		
		try {
			List<UsuarioItem> usuariosList = ServiceLocator.getUsuarioService().listarTodosUsuarios();
			for (UsuarioItem usuarioItem : usuariosList) {
				personData.add(usuarioItem.toModel());
			}
			
			
//			personData = FXCollections.observableArrayList(usuariosList);
//			for(int i=0;i<usuariosList.size();i++) {
//				personData.add(usuariosList.get(i));
//			}
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		
		try {
			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
			for (RolItem rolItem : rolesList) {
				rolesComboBox.add(rolItem.toModel());
			}
			
//			rolesComboBox = FXCollections.observableArrayList(rolesList);
//			for(int i=0;i<usuariosList.size();i++) {
//				personData.add(usuariosList.get(i));
//			}
//			rolUsuComboBox.setItems(rolesComboBox);
		} catch (ServiceException e1) {
			e1.printStackTrace();
		}
		
		
//		buscarPresComboBoxOL = FXCollections.observableArrayList(prestamosTable.getColumns());
//		buscarPresComboBox.setItems(buscarPresComboBoxOL);
		
	}
	
//	@FXML
//	private void initialize(){
//		nombreTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
////		rolTableColumn.setCellValueFactory(cellData -> cellData.getValue().rolProperty());
//		
//		usuariosTable.setItems(personData);
//		
//		System.out.println("Inicializando el controlador");
//		
////		usuario = new SimpleStringProperty(); //Modelo		
////		password = new SimpleStringProperty();
//		
////		tituloText.textProperty().bindBidirectional(usuario); //Vincula el Modelo con la Vista
////		passwordText.textProperty().bindBidirectional(password);
//	}
	
//	 public void setMainApp(Main main) {
//	        this.main = main;
//
//	        // Add observable list data to the table
//	        usuariosTable.setItems(main.getUsuariosData());
//	 }
	
	//A partir de aquí es de la biblioteca:
	
	//USUARIOS:	
	
	@FXML
	private void onGenerarCarnetUsu(ActionEvent e) {
		System.out.println("GENERAR CARNET");
	}
	
	private Stage stage;
	@FXML
	private void onAltaUsuario(ActionEvent e) {
		try {
			this.main.showNuevoUsuarioScene();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		stage = new Stage();
//		stage.setTitle("Alta usuario");
//		stage.setWidth(298);
//		stage.setHeight(225);
//		stage.setResizable(false);
//		
//		URL url = getClass().getResource("/dad/bibliotecafx/docs/BibliotecaNuevoUsuario.fxml");
//		FXMLLoader loader = new FXMLLoader(url);
//		Scene scene = null;
//		try {
//			scene = new Scene(loader.load());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		stage.setScene(scene);
//		stage.show();
	}
	
	@FXML
	private void onModificarUsuario(ActionEvent e) {
		System.out.println("MODIFICAR USUARIO");
	}
	
	@FXML
	private void onBajaUsuario(ActionEvent e) {
		System.out.println("BAJA USUARIO");
	}
	
	@FXML
	private void onNuevoPrestamo(ActionEvent e) {
		System.out.println("NUEVO PRESTAMO");
	}
	
	@FXML
	private void onEliminarPrestamo(ActionEvent e) {
		System.out.println("ELIMINAR PRESTAMO");
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	
}
