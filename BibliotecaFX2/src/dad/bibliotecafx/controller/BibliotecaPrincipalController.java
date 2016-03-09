package dad.bibliotecafx.controller;

import java.io.IOException;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Autor;
import dad.bibliotecafx.modelo.Editorial;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.utils.DateUtils;
import dad.bibliotecafx.utils.ReportsUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import net.sf.jasperreports.engine.JRException;

public class BibliotecaPrincipalController {

	private Main main;
	private Usuario usuarioLogged;

	@FXML
	private Button devolucionButton;

	// PESTAÑA LIBROS:
	private FilteredList<Libro> filtroLibros;
	SortedList<Libro> sortedDataLibros;

	@FXML
	private TextField libroText;

	@FXML
	private TableView<Libro> librosTable;
	@FXML
	private TableColumn<Libro, String> isbnTableColumn;
	@FXML
	private TableColumn<Libro, String> tituloTableColumn;
	@FXML
	private TableColumn<Libro, Autor> autorTableColumn;
	@FXML
	private TableColumn<Libro, Editorial> editorialTableColumn;
	@FXML
	private TableColumn<Libro, Integer> anioTableColumn;
	@FXML
	private TableColumn<Libro, Integer> ejemplaresBiblioTableColumn;

	// PESTAÑA USUARIOS:
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
	private TableColumn<Usuario, Rol> rolTableColumn;

	// PESTAÑA PRESTAMOS
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
	private TableColumn<Prestamo, String> fechainiPresTableColumn;
	@FXML
	private TableColumn<Prestamo, String> fechadevolPresTableColumn;

	// PESTAÑA SANCIONES:
	private FilteredList<Sancion> filtroSanciones;
	SortedList<Sancion> sortedDataSanciones;

	@FXML
	private TextField sancionesText;
	@FXML
	private Button nuevaSancionButton, eliminarSancionButton, editarSancionButton;
	@FXML
	private TableView<Sancion> sancionesTable;
	@FXML
	private TableColumn<Sancion, Prestamo> codPrestamoColumn;
	@FXML
	private TableColumn<Sancion, Usuario> usuarioSancionTableColumn;
	@FXML
	private TableColumn<Sancion, String> fechainiSancionTableColumn;
	@FXML
	private TableColumn<Sancion, String> fechafinSancionTableColumn;

	ObservableList<Rol> rolesComboBox;

	@FXML
	private MenuItem gestionRolesMenuItem, gestConfigMenuItem, editarMisDatosMenuItem, cambiarUsuarioMenuItem;

	@FXML
	private Tab usuariosTabPane, prestamosTabPane, sancionesTabPane;

	public BibliotecaPrincipalController() {
	}

	@FXML
	private void initialize() {
		// Libros
		librosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		isbnTableColumn.setCellValueFactory(cellData -> cellData.getValue().ISBNProperty());
		tituloTableColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
		autorTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Autor>("autores"));
		editorialTableColumn.setCellValueFactory(cellData -> cellData.getValue().editorialProperty());
		anioTableColumn.setCellValueFactory(cellData -> cellData.getValue().anioPublicacionProperty().asObject());
		ejemplaresBiblioTableColumn.setCellValueFactory(cellData -> cellData.getValue().cantidadProperty().asObject());

		// Usuarios:
		usuariosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		nombreTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		nombreDeUsuTableColumn.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		rolTableColumn.setCellValueFactory(cellData -> cellData.getValue().rolProperty());

		// Préstamos:
		prestamosTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		codigoPresTableColumn.setCellValueFactory(cellData -> cellData.getValue().codigoProperty().asObject());
		libroPresTableColumn.setCellValueFactory(new PropertyValueFactory<Prestamo, Libro>("libro"));
		usuarioPresTableColumn.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		fechainiPresTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prestamo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prestamo, String> param) {
						return new SimpleStringProperty(DateUtils.toStringDate(param.getValue().getFechaPrestamo()));
					}
				});
		fechadevolPresTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Prestamo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Prestamo, String> param) {
						if (param.getValue().getFechaDevolucion() != null)
							return new SimpleStringProperty(
									DateUtils.toStringDate(param.getValue().getFechaDevolucion()));
						else
							return null;
					}
				});

		// Sanciones:
		sancionesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		codPrestamoColumn.setCellValueFactory(cellData -> cellData.getValue().prestamoProperty());
		usuarioSancionTableColumn
				.setCellValueFactory(cellData -> cellData.getValue().prestamoProperty().get().usuarioProperty());
		fechainiSancionTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Sancion, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Sancion, String> param) {
						return new SimpleStringProperty(DateUtils.toStringDate(param.getValue().getFechaAlta()));
					}
				});
		fechafinSancionTableColumn.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Sancion, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Sancion, String> param) {
						return new SimpleStringProperty(
								DateUtils.toStringDate(param.getValue().getFechaFinalizacion()));
					}
				});
	}

	// A partir de aquí es de la biblioteca:

	// USUARIOS:

	@FXML
	private void onGenerarCarnetUsu(ActionEvent e) {
		// TODO: Hacer en segundo plano:. Poner código de barras y foto...
		int usuariosSeleccionados = usuariosTable.getSelectionModel().getSelectedItems().size();
		if (usuariosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Generar carnet de usuario");
			alert.setContentText("Debe seleccionar un usuario");
			alert.showAndWait();
		} else {
			main.getPrimaryStage().getScene().setCursor(javafx.scene.Cursor.WAIT);
			for (Usuario usu : usuariosTable.getSelectionModel().getSelectedItems()) {
				try {
					new ReportsUtils().generarCarnet(usu);
				} catch (IOException | JRException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText(
							"Ha ocurrido un error al imprimir el carnet:\n" + e1.getMessage() + "\n" + e1.getCause());
					alert.showAndWait();
					e1.printStackTrace();
				}
			}
			main.getPrimaryStage().getScene().setCursor(javafx.scene.Cursor.DEFAULT);
		}
	}

	@FXML
	private void onAltaUsuario(ActionEvent e) {
		try {
			this.main.showNuevoUsuarioScene(usuarioLogged);
			if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
				usuariosTable.setItems(main.getUsuariosData());
			} else {
				usuariosTable.setItems(main.getUsuariosLectoresData());
			}
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de alta de usuario:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
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
			alert.setContentText("Debe seleccionar el usuario que quiere modificar");
			alert.showAndWait();
		} else if (usuariosSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modificar usuario");
			alert.setContentText("Sólo puede seleccionar un usuario para modificar.");
			alert.showAndWait();
		} else {
			try {
				this.main.showModificarUsuarioScene(usuariosTable.getSelectionModel().getSelectedItem(), usuarioLogged);
				if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
					usuariosTable.setItems(main.getUsuariosData());
				} else {
					usuariosTable.setItems(main.getUsuariosLectoresData());
				}
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la ventana de modificar usuario:\n"
						+ e1.getMessage() + "\n" + e1.getCause());
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
			alert.setContentText("Debe seleccionar al menos un usuario");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar usuario");
			alert.setContentText("¿Seguro que quiere eliminar " + usuariosSeleccionados + " usuarios seleccionados?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				for (Usuario usu : usuariosTable.getSelectionModel().getSelectedItems()) {
					try {
						ServiceLocator.getUsuarioService().eliminarUsuario(usu);
						if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
							usuariosTable.setItems(main.getUsuariosData());
						} else {
							usuariosTable.setItems(main.getUsuariosLectoresData());
						}
					} catch (ServiceException | RuntimeException e1) {
						Alert alertError = new Alert(AlertType.ERROR);
						alertError.setTitle("Error");
						alertError.setContentText("Ha ocurrido un error al eliminar el usuario:\n" + e1.getMessage()
								+ "\n" + e1.getCause() + "\nLos datos no se guardarán en la Base de Datos");
						alertError.showAndWait();
						DataBase.getSession().close();
						e1.printStackTrace();
					}
				}
			}
		}
	}

	// PRÉSTAMOS:

	@FXML
	private void onNuevoPrestamo(ActionEvent e) {
		try {
			this.main.showNuevoPrestamoScene();
			prestamosTable.setItems(main.getPrestamosData());
			librosTable.setItems(main.getLibrosData());
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de crear préstamos:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	@FXML
	private void onEliminarPrestamo(ActionEvent e) {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Eliminar préstamo");
			alert.setContentText("Debe seleccionar al menos un préstamo");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar préstamo");
			alert.setContentText("¿Seguro que quiere eliminar " + prestamosSeleccionados + " préstamos seleccionados?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				// TODO: calcular correctamente el stock
				for (Prestamo prest : prestamosTable.getSelectionModel().getSelectedItems()) {
					try {
						int cant;
						for (Libro libro : prest.getLibro()) {
							cant = libro.getCantidad() + 1;
							libro.setCantidad(cant);
							ServiceLocator.getLibroService().actualizarLibro(libro);
						}
						ServiceLocator.getPrestamoService().eliminarPrestamo(prest);
					} catch (ServiceException | RuntimeException e1) {
						Alert alertError = new Alert(AlertType.ERROR);
						alertError.setTitle("Error");
						alertError.setContentText("Ha ocurrido un error al eliminar el préstamo:\n" + e1.getMessage()
								+ "\n" + e1.getCause() + "\nLos datos no se guardarán en la Base de Datos");
						alertError.showAndWait();
						DataBase.getSession().close();
						e1.printStackTrace();
					}
				}
				prestamosTable.setItems(main.getPrestamosData());
				librosTable.setItems(main.getLibrosData());
			}
		}
	}

	@FXML
	public void onEditarPrestamoButton() {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar préstamo");
			alert.setContentText("Debe seleccionar al menos un préstamo");
			alert.showAndWait();
		} else if (prestamosSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar préstamo");
			alert.setContentText("Sólo puede modificar un préstamo a la vez");
			alert.showAndWait();
		} else {
			try {
				this.main.showModificarPrestamoScene(prestamosTable.getSelectionModel().getSelectedItem());
				prestamosTable.setItems(main.getPrestamosData());
				librosTable.setItems(main.getLibrosData());
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la ventana de modificar préstamo:\n"
						+ e1.getMessage() + "\n" + e1.getCause());
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	@FXML
	public void onDevolucionPrestamoButton() {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar préstamo");
			alert.setContentText("Debe seleccionar al menos un préstamo");
			alert.showAndWait();
		} else if (prestamosSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar préstamo");
			alert.setContentText("Sólo puede modificar un préstamo a la vez");
			alert.showAndWait();
		} else {
			try {
				this.main.showDevolucionPrestamoDialog(prestamosTable.getSelectionModel().getSelectedItem());
				prestamosTable.setItems(main.getPrestamosData());
				librosTable.setItems(main.getLibrosData());
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la ventana de alta de usuario:\n" + e1.getMessage()
						+ "\n" + e1.getCause());
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	// SANCIONES:

	@FXML
	private void onNuevaSancion() {
		try {
			this.main.showNuevaSancionScene();
			sancionesTable.setItems(main.getSancionData());
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de crear sanciones:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	@FXML
	private void onEliminarSancion() {
		int sancionesSeleccionadas = sancionesTable.getSelectionModel().getSelectedItems().size();
		if (sancionesSeleccionadas == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Eliminar sanción");
			alert.setContentText("Debe seleccionar al menos una sanción");
			alert.showAndWait();
		} else {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Eliminar sanción");
			alert.setContentText("¿Seguro que quiere eliminar " + sancionesSeleccionadas + " sanciones seleccionados?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				for (Sancion sancion : sancionesTable.getSelectionModel().getSelectedItems()) {
					try {
						ServiceLocator.getSancionService().eliminarSancion(sancion);
					} catch (ServiceException | RuntimeException e1) {
						Alert alertError = new Alert(AlertType.ERROR);
						alertError.setTitle("Error");
						alertError.setContentText("Ha ocurrido un error al eliminar la sanción:\n" + e1.getMessage()
								+ "\nLos datos no se guardarán en la Base de Datos");
						alertError.showAndWait();
						DataBase.getSession().close();
						e1.printStackTrace();
					}
				}
				sancionesTable.setItems(main.getSancionData());
			}
		}
	}

	@FXML
	public void onEditarSancion() {
		int sancionesSeleccionadas = sancionesTable.getSelectionModel().getSelectedItems().size();
		if (sancionesSeleccionadas == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar sanción");
			alert.setContentText("Debe seleccionar al menos una sanción");
			alert.showAndWait();
		} else if (sancionesSeleccionadas >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Editar sanción");
			alert.setContentText("Sólo puede modificar una sanción");
			alert.showAndWait();
		} else {
			try {
				this.main.showModificarSancionScene(sancionesTable.getSelectionModel().getSelectedItem());
				sancionesTable.setItems(main.getSancionData());
			} catch (IOException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la ventana de modificar sanción:\n"
						+ e1.getMessage() + "\n" + e1.getCause());
				alert.showAndWait();
				e1.printStackTrace();
			}
		}
	}

	// MENÚ - ROLES:

	@FXML
	public void gestionRoles() {
		try {
			this.main.showGestionRolesScene();
			if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
				usuariosTable.setItems(main.getUsuariosData());
			} else {
				usuariosTable.setItems(main.getUsuariosLectoresData());
			}
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de Gestión de roles:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	// MENÚ - CONFIGURACIONES:

	@FXML
	public void gestionConfiguraciones() {
		try {
			this.main.showGesionConfiuracionesDialog();
		} catch (IOException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de alta de usuario:\n" + e1.getMessage()
					+ "\n" + e1.getCause());
			alert.showAndWait();
			e1.printStackTrace();
		}
	}

	// FILTROS

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
				} else if (libro.getAutores().contains(lowerCaseFilter)) {
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

	public void setFilterSanciones(ObservableList<Sancion> sanciones) {
		filtroSanciones = new FilteredList<>(sanciones, p -> true);
		sancionesText.textProperty().addListener((observable, oldValue, newValue) -> {
			filtroSanciones.setPredicate(sancion -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				String nombreUsuario = sancion.getPrestamo().getUsuario().getNombre().toLowerCase();

				if (nombreUsuario.contains(lowerCaseFilter)) {
					return true;
				} else {
					try {
						if (sancion.getPrestamo().getCodigo() >= Long.parseLong(newValue)) {
							return true;
						}
						if (sancion.getPrestamo().getUsuario().getCodigo() >= Long.parseLong(newValue)) {
							return true;
						}
					} catch (NumberFormatException e) {
					}
				}

				return false;
			});
		});

		sortedDataSanciones = new SortedList<>(filtroSanciones);
		sortedDataSanciones.comparatorProperty().bind(sancionesTable.comparatorProperty());
		sancionesTable.setItems(sortedDataSanciones);
	}

	// SALIR

	@FXML
	public void salir() {
		DataBase.disconnect();
		main.getPrimaryStage().close();
	}

	// CARGAR MAIN:

	public void setMain(Main main, Usuario usuario) {
		this.main = main;

		this.usuarioLogged = usuario;

		System.out.println(usuarioLogged.getRol().getTipo());
		if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
			usuariosTable.setItems(main.getUsuariosData());
		} else {
			usuariosTable.setItems(main.getUsuariosLectoresData());
		}
		librosTable.setItems(main.getLibrosData());
		prestamosTable.setItems(main.getPrestamosData());
		sancionesTable.setItems(main.getSancionData());

		ocultarDatos();
	}

	private void ocultarDatos() {
		if (usuarioLogged.getRol().getTipo().equals("Lector")) {
			gestionRolesMenuItem.setVisible(false);
			gestConfigMenuItem.setVisible(false);
			usuariosTabPane.setDisable(true);
			prestamosTabPane.setDisable(true);
			sancionesTabPane.setDisable(true);
		}
		if (usuarioLogged.getRol().getTipo().equals("Bibliotecario")) {
			gestionRolesMenuItem.setVisible(false);
			gestConfigMenuItem.setVisible(false);
		}
	}

	@FXML
	public void onEditarMisDatosMenuItem() {
		try {
			this.main.showModificarUsuarioScene(usuarioLogged, usuarioLogged);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al cargar la ventana de modificar usuario:\n" + e.getMessage()
					+ "\n" + e.getCause());
			alert.showAndWait();
			e.printStackTrace();
		}
	}

	@FXML
	public void onCambiarUsuario() {
		try {
			this.main.showBibliotecaLoginScene();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText(
					"Ha ocurrido un error al cargar la ventana de login:\n" + e.getMessage() + "\n" + e.getCause());
			alert.showAndWait();
			e.printStackTrace();
		}
	}
}
