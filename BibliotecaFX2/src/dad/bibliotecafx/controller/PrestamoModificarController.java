package dad.bibliotecafx.controller;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.utils.DateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PrestamoModificarController {

	private Main main;

	private FilteredList<Libro> filtroLibros;
	SortedList<Libro> sortedDataLibros;
	private FilteredList<Usuario> filtroUsuarios;
	SortedList<Usuario> sortedDataUsuarios;
	private Usuario usuarioLogged;

	private Boolean modoAnadir;

	private List<Libro> libros;

	@FXML
	private StackPane prestamoStackPane;

	@FXML
	private StackPane stackPane;

	@FXML
	private GridPane gridPaneInfo;
	@FXML
	private BorderPane usuariosBorderPane, librosBorderPane;

	@FXML
	private TextField usuarioText, libroText, buscarUsuarioText, buscarLibroText;

	@FXML
	private TableView<Libro> librosPrestTable;
	@FXML
	private TableColumn<Libro, String> tituloPrestTableColumn;
	@FXML
	private TableColumn<Libro, String> autorPrestTableColumn;
	@FXML
	private TableColumn<Libro, Integer> anioPrestTableColumn;

	@FXML
	private TableView<Usuario> usuariosPrestTable;
	@FXML
	private TableColumn<Usuario, String> nombrePrestTableColum;
	@FXML
	private TableColumn<Usuario, String> rolPrestTableColumn;

	@FXML
	private ScrollPane prestamoScrollPane;

	@FXML
	private TextArea prestamosTextArea;

	@FXML
	private Button siguientePrestButton, atrasPrestButton, anadirLibroButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private GridPane gridPaneDatosUsuario;

	@FXML
	private Button aceptarButton, cancelarButton, usuarioButton, libroButton;

	// @FXML
	// private Label codigoPrestamoText;

	private Usuario usuario;
	private Prestamo prestamo;

	@FXML
	private void initialize() {
		datePicker.setValue(LocalDate.now());
		libros = new ArrayList<Libro>();
		librosPrestTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		nombrePrestTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		rolPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));

		tituloPrestTableColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
		autorPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("autores"));
		anioPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anioPublicacion"));
	}

	@FXML
	private void onAceptarButton() {
		// TODO: Revisar la lista de los libros para prestar...

		if (usuariosBorderPane.isVisible()) {
			usuario = usuariosPrestTable.getSelectionModel().getSelectedItem();
			usuarioText.setText(usuario.getNombre());
			gridPaneInfo.setVisible(true);
			usuariosBorderPane.setVisible(false);
		} else if (librosBorderPane.isVisible()) {
			if (modoAnadir || librosPrestTable.getSelectionModel().getSelectedItems().size() == 0) {
				for (Libro libro : prestamo.getLibro()) {
					libros.add(libro);
				}
			}
			for (Libro libro : librosPrestTable.getSelectionModel().getSelectedItems()) {
				libros.add(libro);
			}

			// for (Libro libro :
			// librosPrestTable.getSelectionModel().getSelectedItems()) {
			// libros.add(libro);
			// }
			libroText.setText(libros + "");
			gridPaneInfo.setVisible(true);
			librosBorderPane.setVisible(false);
		} else if (gridPaneInfo.isVisible()) {
			List<Sancion> usuarioSancionado = null;
			List<Prestamo> prestamosUsuario = null;
			try {
				if (usuario == null) {
					usuario = prestamo.getUsuario();
				}
				usuarioSancionado = ServiceLocator.getSancionService().listarSancionesPorUsuario(usuario);
				prestamosUsuario = ServiceLocator.getPrestamoService().prestamosPorUsuario(usuario);
			} catch (ServiceException e2) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al listar las sanciones del usuario:\n" + e2.getMessage()
						+ "\n" + e2.getCause() + "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				e2.printStackTrace();
			}
			Date fecha = null, fechaTmp = null;
			if (usuarioSancionado.size() > 0) {
				fecha = usuarioSancionado.get(0).getFechaFinalizacion();
				for (Sancion s : usuarioSancionado) {
					fechaTmp = s.getFechaFinalizacion();
					if (fechaTmp.after(fecha)) {
						fecha = fechaTmp;
					}
				}
				Date fechaHoy = null;
				try {
					fechaHoy = DateUtils.toDate(LocalDate.now());
				} catch (ParseException e2) {
					Alert alertError = new Alert(AlertType.ERROR);
					alertError.setTitle("Error");
					alertError.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e2.getMessage()
							+ "\n" + e2.getCause() + "\nCompruebe que la fecha de la sanción es correcta");
					alertError.showAndWait();
					e2.printStackTrace();
				}
				if (fecha.after(fechaHoy)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("¡Atención!");
					alert.setContentText("El usuario tiene una sanción vigente y no puede realizar préstamos");
					alert.showAndWait();
				} else {
					modificarPrestamo(prestamosUsuario);
				}
			} else {
				modificarPrestamo(prestamosUsuario);
			}
		}
	}

	@FXML
	private void onCancelarButton() {
		if (gridPaneInfo.isVisible()) {
			this.main.getStage().close();
		} else {
			gridPaneInfo.setVisible(true);
			usuariosBorderPane.setVisible(false);
			librosBorderPane.setVisible(false);
		}
	}

	@FXML
	private void onUsuarioButton() {
		gridPaneInfo.setVisible(false);
		usuariosBorderPane.setVisible(true);
	}

	@FXML
	private void onLibroButton() {
		gridPaneInfo.setVisible(false);
		librosBorderPane.setVisible(true);
		modoAnadir = false;
	}

	@FXML
	private void onAnadirLibroButton() {
		gridPaneInfo.setVisible(false);
		librosBorderPane.setVisible(true);
		modoAnadir = true;
	}

	private void modificarPrestamo(List<Prestamo> prestamosUsuario) {
		List<Libro> librosEnPrestamos = new ArrayList<Libro>();
		for (Prestamo prestamo : prestamosUsuario) {
			if (prestamo.getFechaDevolucion() == null) {
				for (Libro libro : prestamo.getLibro()) {
					librosEnPrestamos.add(libro);
				}
			}
		}

		List<Libro> librosParaPrestamoSinComprobarCantidad = new ArrayList<Libro>();
		for (Libro l : this.libros) {
			librosParaPrestamoSinComprobarCantidad.add(l);
		}

		// if(librosParaPrestamoSinComprobarCantidad.size() == 0){
		// for (Libro libro : prestamo.getLibro()) {
		// librosParaPrestamoSinComprobarCantidad.add(libro);
		// }
		// }

		int totalLibros = librosEnPrestamos.size() + librosParaPrestamoSinComprobarCantidad.size();

		if (totalLibros > main.getMaxLibrosPrestamo()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¡Atención!");
			alert.setContentText("El usuario ya tiene prestados " + librosEnPrestamos.size() + " libros.\n"
					+ "No puede tener más de " + main.getMaxLibrosPrestamo() + " libros en préstamo.\n"
					+ "En este préstamo sólo puede llevarse " + (main.getMaxLibrosPrestamo() - librosEnPrestamos.size())
					+ " libros.");
			alert.showAndWait();
		} else {
			normalizarStock();

			QuitarStockLibros(librosParaPrestamoSinComprobarCantidad);

			if (libros.size() > 0) {
				try {
					prestamo.setFechaPrestamo(DateUtils.toDate(datePicker.getValue()));
				} catch (ParseException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e1.getMessage() + "\n"
							+ e1.getCause() + "\nCompruebe que la fecha del préstamo es correcta");
					alert.showAndWait();
					e1.printStackTrace();
				}
				ObservableSet<Libro> l = FXCollections.observableSet(new HashSet<Libro>());
				for (Libro libro : libros) {
					l.add(libro);
				}
				prestamo.setLibro(l);
				prestamo.setUsuario(usuario);
				try {
					ServiceLocator.getPrestamoService().actualizarPrestamo(prestamo);
					main.getStage().close();
				} catch (ServiceException | RuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al grabar el péstamo:\n" + e.getMessage() + "\n"
							+ e.getCause() + "\nLos datos no se guardarán en la Base de Datos");
					alert.showAndWait();
					DataBase.rollback();
					DataBase.getSession().close();
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("No hay libros que añadir al préstamo. No se grabará en la Base de Datos");
				alert.showAndWait();
				DataBase.rollback();
				DataBase.getSession().close();
			}
		}
	}

	private void normalizarStock() {
		for (Libro libro : prestamo.getLibro()) {
			libro.setCantidad(libro.getCantidad() + 1);
			try {
				ServiceLocator.getLibroService().actualizarLibro(libro);
			} catch (ServiceException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText(
						"Ha ocurrido un error al actualizar el libro:\n" + e.getMessage() + "\n" + e.getCause());
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		}
	}

	private void QuitarStockLibros(List<Libro> librosParaPrestamoSinComprobarCantidad) {
		int cant;

		for (Libro libro : librosParaPrestamoSinComprobarCantidad) {
			cant = libro.getCantidad() - 1;
			if (cant >= 0) {
				libros.add(libro);
				libro.setCantidad(cant);
				try {
					ServiceLocator.getLibroService().actualizarLibro(libro);
				} catch (ServiceException e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("¡Atención!");
					alert.setContentText("Ha ocurrido un error al actualizar el stock de ejemplares:\n" + e.getMessage()
							+ "\n" + e.getCause());
					alert.showAndWait();
					DataBase.getSession().close();
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("¡Atención!");
				alert.setContentText("No hay suficientes ejemplares del libro " + libro.getTitulo() + ".\n"
						+ "Este libro no se añadirá al préstamo.");
				alert.showAndWait();
			}
		}
	}

	public void setMain(Main main, Usuario usuarioLogged) {
		this.main = main;

		librosPrestTable.setItems(main.getLibrosData());
		this.usuarioLogged = usuarioLogged;
		if (usuarioLogged.getRol().getTipo().equals("Administrador")) {
			usuariosPrestTable.setItems(main.getUsuariosData());
		} else {
			usuariosPrestTable.setItems(main.getUsuariosLectoresData());
		}
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;

		datePicker.setValue(DateUtils.toLocalDate(prestamo.getFechaPrestamo()));
		usuarioText.setText(prestamo.getUsuario().getNombre());
		libroText.setText(prestamo.getLibro() + "");

	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setFilterLibros(ObservableList<Libro> libros) {
		filtroLibros = new FilteredList<>(libros, p -> true);
		buscarLibroText.textProperty().addListener((observable, oldValue, newValue) -> {
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
		sortedDataLibros.comparatorProperty().bind(librosPrestTable.comparatorProperty());
		librosPrestTable.setItems(sortedDataLibros);
	}

	public void setFilterUsuarios(ObservableList<Usuario> usuarios) {
		filtroUsuarios = new FilteredList<>(usuarios, p -> true);
		buscarUsuarioText.textProperty().addListener((observable, oldValue, newValue) -> {
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
		sortedDataUsuarios.comparatorProperty().bind(usuariosPrestTable.comparatorProperty());
		usuariosPrestTable.setItems(sortedDataUsuarios);
	}

}
