package dad.bibliotecafx.controller;

import java.io.IOException;
import java.util.Date;
import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.impl.RolService;
import dad.bibliotecafx.service.impl.UsuarioService;
import dad.bibliotecafx.service.items.LibroItem;
import dad.bibliotecafx.service.items.PrestamoItem;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BibliotecaPruebaController {
	
	private Main main;
	
//	private StringProperty usuario, password;
	
	//PESTAÑA LIBROS:
	@FXML
	private TextField libroText;  //El nombre tiene que coindicir con el id que tiene en el XML
	@FXML
	private TableView<LibroItem> librosTable;
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
	private TableView<UsuarioItem> usuariosTable;
	@FXML
	private TableColumn<Usuario, String> nombreTableColum;
	@FXML
	private TableColumn<Usuario, String> rolTableColumn;
	
	//PESTAÑA PRESTAMOS
	@FXML
	private TextField prestamoText;
	@FXML
	private ComboBox<TableColumn<String, ?>> buscarPresComboBox;
	@FXML
	private Button nuevoPresButton, eliminarPresButton;
	@FXML
	private TableView<PrestamoItem> prestamosTable;
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
//	
//	
//	private RolService rolService;
//	private UsuarioService usuarioService;
//	private ObservableList<UsuarioItem> personData;
	
	ObservableList<TableColumn<String, ?>> buscarPresComboBoxOL;
	ObservableList<RolItem> rolesComboBox;
//	final ComboBox comboBox = new ComboBox(options);
	
//	private Main main;
	
	
	
	public BibliotecaPruebaController() {
//		usuarioService = new UsuarioService();
//		rolService = new RolService();
//		
//		try {
//			List<UsuarioItem> usuariosList = ServiceLocator.getUsuarioService().listarTodosUsuarios();
//			personData = FXCollections.observableArrayList(usuariosList);
////			for(int i=0;i<usuariosList.size();i++) {
////				personData.add(usuariosList.get(i));
////			}
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
//		
//		try {
//			List<RolItem> rolesList = ServiceLocator.getRolService().listarRoles();
//			rolesComboBox = FXCollections.observableArrayList(rolesList);
////			for(int i=0;i<usuariosList.size();i++) {
////				personData.add(usuariosList.get(i));
////			}
////			rolUsuComboBox.setItems(rolesComboBox);
//		} catch (ServiceException e1) {
//			e1.printStackTrace();
//		}
		
		
//		buscarPresComboBoxOL = FXCollections.observableArrayList(prestamosTable.getColumns());
//		buscarPresComboBox.setItems(buscarPresComboBoxOL);
		
	}
	
	@FXML
	private void initialize(){
		nombreTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
//		rolTableColumn.setCellValueFactory(cellData -> cellData.getValue().rolProperty());
		
//		usuariosTable.setItems(personData);
		
		System.out.println("Inicializando el controlador");
		
//		usuario = new SimpleStringProperty(); //Modelo		
//		password = new SimpleStringProperty();
		
//		tituloText.textProperty().bindBidirectional(usuario); //Vincula el Modelo con la Vista
//		passwordText.textProperty().bindBidirectional(password);
	}
	

	
	//A partir de aquí es de la biblioteca:
	
	//USUARIOS:	
	
	@FXML
	private void onGenerarCarnetUsu(ActionEvent e) {
		System.out.println("GENERAR CARNET");
	}
	
	@FXML
	private void onAltaUsuario(ActionEvent e) {
		try {
			this.main.showNuevoUsuarioScene();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void onModificarUsuario(ActionEvent e) {
		try {
			this.main.showModificarUsuarioScene();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void onBajaUsuario(ActionEvent e) {
		System.out.println("BAJA USUARIO");
	}
	
	@FXML
	private void onNuevoPrestamo(ActionEvent e) {
		try {
			this.main.showNuevoPrestamoScene();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@FXML
	private void onEliminarPrestamo(ActionEvent e) {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		
//		if(prestamosSeleccionados == 0) {
//			Alert alert = new Alert(AlertType.WARNING);
//			alert.setTitle("Información");
////			alert.setHeaderText("Look, a Warning Dialog");
//			alert.setContentText("Debe seleccionar al menos un préstamo");
//			alert.showAndWait();
//		} else if(prestamosSeleccionados == 1) {
//			ServiceLocator.getPrestamoService().eliminarPrestamo(prestamosTable.getSelectionModel().getSelectedItem());
//			main.getPrestamosData().remove(prestamosTable.getSelectionModel().getSelectedItem());
//		} else {
////			for(int i;i<prestamosSeleccionados;i++) {
////				//para eliminar todos los prestamos;
////				ServiceLocator.getPrestamoService().eliminarPrestamo(prestamosTable.getSelectionModel().getSelectedIndices().get(i));
////			}
//			
//		}
	}
	
	public void setMain(Main main) {
		this.main = main;
		
		
		librosTable.setItems(main.getLibrosData());
		usuariosTable.setItems(main.getUsuariosData()); //lo vi en internet
		prestamosTable.setItems(main.getPrestamosData());
	}
	
	
}
