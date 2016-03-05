package dad.bibliotecafx.controller;

import java.io.IOException;
import java.util.Date;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class BibliotecaPruebaController {
	
	private Main main;
	
//	private StringProperty usuario, password;
	
	//PESTAÑA LIBROS:
	private FilteredList<Libro> filtroLibros;
	SortedList<Libro> sortedDataLibros;
	
	@FXML
	private TextField libroText;  //El nombre tiene que coindicir con el id que tiene en el XML
	@FXML
	private TableView<Libro> librosTable;
	@FXML
	private TableColumn<Libro, String> isbnTableColumn;
	@FXML
	private TableColumn<Libro, String> tituloTableColumn;
	@FXML
	private TableColumn<Libro, Autor> autorTableColumn;
	@FXML
	private TableColumn<Libro, Integer> anioTableColumn;
	@FXML
	private TableColumn<Libro, String> ejemplaresBiblioTableColumn;
	
	//PESTAÑA USUARIOS:
	private FilteredList<Usuario> filtroUsuarios;
	SortedList<Usuario> sortedDataUsuarios;
	@FXML
	private TextField usuarioText;
	@FXML
	private Button carnetButton, altaUsuButton, modificarUsuButton, bajaUsuButton;
	@FXML
	private TableView<Usuario> usuariosTable;
	@FXML
	private TableColumn<Usuario, String> nombreTableColum;
	@FXML
	private TableColumn<Usuario, String> rolTableColumn;
	
	//PESTAÑA PRESTAMOS
	private FilteredList<Prestamo> filtroPrestamos;
	SortedList<Prestamo> sortedDataPrestamos;
	
	@FXML
	private TextField prestamoText;
	@FXML
	private ComboBox<TableColumn<String, ?>> buscarPresComboBox;
	@FXML
	private Button nuevoPresButton, eliminarPresButton;
	@FXML
	private TableView<Prestamo> prestamosTable;
	@FXML
	private TableColumn<Long, Long> codigoPresTableColumn;
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
	ObservableList<Rol> rolesComboBox;
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
//		isbnTableColumn.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
		isbnTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("ISBN"));
		tituloTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
		autorTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Autor>("autores"));
		anioTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anioPublicacion"));
		
		codigoPresTableColumn.setCellValueFactory(new PropertyValueFactory<Long, Long>("codigo"));
		
//		libroText.textProperty().addListener((observable, oldValue, newValue) -> {
//            filtroLibros.setPredicate(libro -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                String lowerCaseFilter = newValue.toLowerCase();
//
//                if (libro.getTitulo().toLowerCase().contains(lowerCaseFilter)) {
//                    return true; 
//                } 
//                return false;
//            });
//        });
//		
//        sortedData.comparatorProperty().bind(librosTable.comparatorProperty());
//        librosTable.setItems(sortedData);
		
//		libroBuscar = new SimpleStringProperty();
//		libroText.textProperty().bind(libroBuscar);
		
//		tituloTableColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
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
	
	public void setFilterLibros(ObservableList<Libro> libros){
		filtroLibros = new FilteredList<>(libros, p -> true);
		libroText.textProperty().addListener((observable, oldValue, newValue) -> {
            filtroLibros.setPredicate(libro -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (libro.getTitulo().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (libro.getISBN().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (libro.getEditorial().getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                }
                return false;
            });
        });
		
		sortedDataLibros = new SortedList<>(filtroLibros);
        sortedDataLibros.comparatorProperty().bind(librosTable.comparatorProperty());
        librosTable.setItems(sortedDataLibros);		
	}
	
	public void setFilterUsuarios(ObservableList<Usuario> usuarios){
		filtroUsuarios = new FilteredList<>(usuarios, p -> true);
		usuarioText.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroUsuarios.setPredicate(usuario -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                
                String lowerCaseFilter = newValue.toLowerCase();
                Long cod = Long.parseLong(newValue);
                
                if (usuario.getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (usuario.getUsuario().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (usuario.getRol().getTipo().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (usuario.getCodigo() >= cod) {
                    return true; 
                }
                return false;
            });
        });
		
		sortedDataUsuarios = new SortedList<>(filtroUsuarios);
		sortedDataUsuarios.comparatorProperty().bind(usuariosTable.comparatorProperty());
        usuariosTable.setItems(sortedDataUsuarios);		
	}
	
	public void setFilterPrestamos(ObservableList<Prestamo> prestamos){
		filtroPrestamos = new FilteredList<>(prestamos, p -> true);
		prestamoText.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroPrestamos.setPredicate(prestamo -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                Long cod = Long.parseLong(newValue);
                
                if (prestamo.getUsuario().getNombre().toLowerCase().contains(lowerCaseFilter)) {
                    return true; 
                } else if (prestamo.getLibro().contains(newValue)) {
                    return true; 
                } else if (prestamo.getCodigo() >= cod) {
                    return true; 
                }
                return false;
            });
        });
		
		sortedDataPrestamos = new SortedList<>(filtroPrestamos);
		sortedDataPrestamos.comparatorProperty().bind(prestamosTable.comparatorProperty());
		prestamosTable.setItems(sortedDataPrestamos);		
	}
	
}
