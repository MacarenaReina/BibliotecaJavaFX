package dad.bibliotecafx.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class BibliotecaPrincipalController {

	private Main main;

	// private StringProperty usuario, password;
	
	@FXML
	private Button devolucionButton;

	// PESTA�A LIBROS:
	private FilteredList<Libro> filtroLibros;
	SortedList<Libro> sortedDataLibros;

	@FXML
	private TextField libroText; // El nombre tiene que coindicir con el id que
									// tiene en el XML
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

	// PESTA�A USUARIOS:
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
	private TableColumn<Usuario, String> nombreDeUsuTableColumn;
	@FXML
	private TableColumn<Usuario, String> rolTableColumn;

	// PESTA�A PRESTAMOS
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
	private TableColumn<Prestamo, Long> codigoPresTableColumn;
	@FXML
	private TableColumn<Prestamo, Libro> libroPresTableColumn;
	@FXML
	private TableColumn<Prestamo, Usuario> usuarioPresTableColumn;
	@FXML
	private TableColumn<Prestamo, Date> fechainiPresTableColumn;
	@FXML
	private TableColumn<Prestamo, Date> fechadevolPresTableColumn;

	ObservableList<TableColumn<String, ?>> buscarPresComboBoxOL;
	ObservableList<Rol> rolesComboBox;

	public BibliotecaPrincipalController() {
	}

	@FXML
	private void initialize() {
		// Libros
		librosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		isbnTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("ISBN"));
		tituloTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("titulo"));
		autorTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Autor>("autores"));
		anioTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anioPublicacion"));

		// Usuarios:
		usuariosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nombreTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		nombreDeUsuTableColumn.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		// nombreDeUsuTableColumn.setCellValueFactory(new
		// PropertyValueFactory<Usuario, String>("usuario"));

		rolTableColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));

		// Pr�stamos:
		prestamosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		codigoPresTableColumn.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("codigo"));
		libroPresTableColumn.setCellValueFactory(new PropertyValueFactory<Prestamo, Libro>("libro"));
		usuarioPresTableColumn.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		fechainiPresTableColumn.setCellValueFactory(cellData -> cellData.getValue().fechaPrestamoProperty());
		fechadevolPresTableColumn.setCellValueFactory(cellData -> cellData.getValue().fechaDevolucionProperty());
	}

	// A partir de aqu� es de la biblioteca:

	// USUARIOS:

	@FXML
	private void onGenerarCarnetUsu(ActionEvent e) {
		System.out.println("GENERAR CARNET");
	}

	@FXML
	private void onAltaUsuario(ActionEvent e) {
		try {
			this.main.showNuevoUsuarioScene();
			usuariosTable.setItems(main.getUsuariosData());
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de alta de usuario");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	@FXML
	private void onModificarUsuario(ActionEvent e) {
		int usuariosSeleccionados = usuariosTable.getSelectionModel().getSelectedItems().size();

		if (usuariosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modificar usuario");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("Debe seleccionar el usuario que quiere modificar");
			alert.showAndWait();
		} else if (usuariosSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modificar usuario");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("S�lo puede seleccionar un usuario para modificar.");
			alert.showAndWait();
		} else {
			try {
				this.main.showModificarUsuarioScene(usuariosTable.getSelectionModel().getSelectedItem());
				usuariosTable.setItems(main.getUsuariosData());
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la ventana de modificar usuario.");
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	@FXML
	private void onBajaUsuario(ActionEvent e) {
		int usuariosSeleccionados = usuariosTable.getSelectionModel().getSelectedItems().size();
		if (usuariosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Eliminar usuario");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("Debe seleccionar al menos un usuario");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar usuario");
			alert.setContentText("�Seguro que quiere eliminar " + usuariosSeleccionados + " usuarios seleccionados?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				for (Usuario usu : usuariosTable.getSelectionModel().getSelectedItems()) {
					try {
						System.out.println(usu.getNombre());
						ServiceLocator.getUsuarioService().eliminarUsuario(usu.toItem());
						usuariosTable.setItems(main.getUsuariosData());

						// Esta l�nea no funciona porque estoy actualizando los
						// datos al llamar a getUsuariosData y ya no encuentra
						// el q
						// elimine, por eso puse la anterior:
						// main.getUsuariosData().remove(usuariosTable.getSelectionModel().getSelectedItem());
					} catch (ServiceException | RuntimeException e1) {
						Alert alertError = new Alert(AlertType.ERROR);
						alertError.setTitle("Error");
						alertError.setContentText("Ha ocurrido un error al eliminar el usuario:\n" + e1.getMessage()
								+ "\nLos datos no se guardar�n en la Base de Datos");
						alertError.showAndWait();
						DataBase.rollback();
						e1.printStackTrace();
					}
				}
			}
		}
	}

	@FXML
	private void onNuevoPrestamo(ActionEvent e) {
		try {
			this.main.showNuevoPrestamoScene();
			prestamosTable.setItems(main.getPrestamosData());
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de crear pr�stamos.");
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	@FXML
	private void onEliminarPrestamo(ActionEvent e) {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Eliminar pr�stamo");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("Debe seleccionar al menos un pr�stamo");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar pr�stamo");
			alert.setContentText("�Seguro que quiere eliminar " + prestamosSeleccionados + " pr�stamos seleccionados?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {

				for (Prestamo prest : prestamosTable.getSelectionModel().getSelectedItems()) {
					try {
						ServiceLocator.getPrestamoService().eliminarPrestamo(prest.toItem());
						prestamosTable.setItems(main.getPrestamosData());
						// main.getPrestamosData().remove(prestamosTable.getSelectionModel().getSelectedItem());
					} catch (ServiceException | RuntimeException e1) {
						Alert alertError = new Alert(AlertType.ERROR);
						alertError.setTitle("Error");
						alertError.setContentText("Ha ocurrido un error al eliminar el pr�stamo:\n" + e1.getMessage()
								+ "\nLos datos no se guardar�n en la Base de Datos");
						alertError.showAndWait();
						DataBase.rollback();
						e1.printStackTrace();
					}
				}
			}
		}
	}

	public void setMain(Main main) {
		this.main = main;

		librosTable.setItems(main.getLibrosData());
		usuariosTable.setItems(main.getUsuariosData()); // lo vi en internet
		prestamosTable.setItems(main.getPrestamosData());
	}

	public void setFilterLibros(ObservableList<Libro> libros) {
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

	public void setFilterUsuarios(ObservableList<Usuario> usuarios) {
		filtroUsuarios = new FilteredList<>(usuarios, p -> true);
		usuarioText.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroUsuarios.setPredicate(usuario -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (usuario.getNombre().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (usuario.getUsuario().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (usuario.getRol().getTipo().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else {
					try {
						if (usuario.getCodigo() >= Long.parseLong(newValue)) {
							return true;
						}
					} catch (NumberFormatException e) {
					}
				}
				return false;
			});
		});

		sortedDataUsuarios = new SortedList<>(filtroUsuarios);
		sortedDataUsuarios.comparatorProperty().bind(usuariosTable.comparatorProperty());
		usuariosTable.setItems(sortedDataUsuarios);
	}

	public void setFilterPrestamos(ObservableList<Prestamo> prestamos) {
		filtroPrestamos = new FilteredList<>(prestamos, p -> true);
		prestamoText.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroPrestamos.setPredicate(prestamo -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (prestamo.getUsuario().getNombre().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				} else if (prestamo.getLibro().contains(newValue)) {
					return true;
				} else {
					try {
						if (prestamo.getCodigo() >= Long.parseLong(newValue)) {
							return true;
						}
					} catch (NumberFormatException e) {
					}
				}

				return false;
			});
		});

		sortedDataPrestamos = new SortedList<>(filtroPrestamos);
		sortedDataPrestamos.comparatorProperty().bind(prestamosTable.comparatorProperty());
		prestamosTable.setItems(sortedDataPrestamos);
	}

	@FXML
	public void gestionRoles() {

	}

	@FXML
	public void gestionConfiguraciones() {

	}

	@FXML
	public void salir() {
		DataBase.disconnect();
		main.getPrimaryStage().close();
	}

	@FXML
	public void onEditarPrestamoButton() {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar pr�stamo");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("Debe seleccionar al menos un pr�stamo");
			alert.showAndWait();
		} else if(prestamosSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar pr�stamo");
			// alert.setHeaderText("Look, a Warning Dialog");
			alert.setContentText("S�lo puede modificar un pr�stamo a la vez");
			alert.showAndWait();
		} else{
			//TODO Editar pr�stamo
		}
	}
	
	@FXML
	public void onDevolucionPrestamoButton(){
		
	}

}
