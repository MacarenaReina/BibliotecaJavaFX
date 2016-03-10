package dad.bibliotecafx.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class PrestamoInsertarController {

	private Main main;

	private FilteredList<Libro> filtroLibros;
	SortedList<Libro> sortedDataLibros;
	private FilteredList<Usuario> filtroUsuarios;
	SortedList<Usuario> sortedDataUsuarios;

	private Usuario usuarioLogged;

	@FXML
	private StackPane prestamoStackPane;

	@FXML
	private Label fechaPrestamoLabel;

	@FXML
	private TextField buscarUsuarioText, buscarLibroText;

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
	private BorderPane usuariosBorderPane, librosBorderPane;

	@FXML
	private ScrollPane prestamoScrollPane;

	@FXML
	private TextArea prestamosTextArea;

	@FXML
	private Button siguientePrestButton, atrasPrestButton, finalizarPresButton;

	@FXML
	private DatePicker datePicker;

	@FXML
	private void initialize() {
		datePicker.setValue(LocalDate.now());
		librosPrestTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

		if (usuariosPrestTable.isVisible()) {
			atrasPrestButton.setDisable(true);
			finalizarPresButton.setDisable(true);
		}

		nombrePrestTableColum.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		rolPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));

		tituloPrestTableColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
		autorPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("autores"));
		anioPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anioPublicacion"));
	}

	@FXML
	private void onSiguientePrestButton() {
		int usuariosSeleccionados = usuariosPrestTable.getSelectionModel().getSelectedItems().size();
		int librosSeleccionados = librosPrestTable.getSelectionModel().getSelectedItems().size();

		if (usuariosBorderPane.isVisible()) {
			if (usuariosSeleccionados == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Usuario");
				alert.setContentText("Debe seleccionar un usuario");

				alert.showAndWait();
			} else if (usuariosSeleccionados > 1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Usuario");
				alert.setContentText("Debe seleccionar un sólo usuario");

				alert.showAndWait();
			} else {
				usuariosBorderPane.setVisible(false);
				librosBorderPane.setVisible(true);
				atrasPrestButton.setDisable(false);
			}

		} else if (librosBorderPane.isVisible()) {
			if (librosSeleccionados == 0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Libro");
				alert.setContentText("Debe seleccionar un libro");

				alert.showAndWait();
			} else if (librosSeleccionados > main.getMaxLibrosPrestamo()) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("¡Atención!");
				alert.setContentText("No puede seleccionar más de " + main.getMaxLibrosPrestamo() + " libros");
				alert.showAndWait();
			} else {
				usuariosBorderPane.setVisible(false);
				librosBorderPane.setVisible(false);

				prestamosTextArea.setText(
						"Usuario: " + usuariosPrestTable.getSelectionModel().getSelectedItem().getUsuario() + "\n"
								+ "Libro: " + librosPrestTable.getSelectionModel().getSelectedItems() + "\n" + "Autor: "
								+ librosPrestTable.getSelectionModel().getSelectedItem().getAutores().toString() + "\n"
								+ "Fecha del préstamo: " + DateUtils.format(datePicker.getValue()));

				prestamoScrollPane.setVisible(true);
				prestamosTextArea.setVisible(true);
				siguientePrestButton.setDisable(true);

				finalizarPresButton.setDisable(false);
			}
		}
	}

	private void crearPrestamo(ObservableSet<Libro> libros,	List<Prestamo> prestamosUsuario) {
		List<Libro> librosPrestamo = new ArrayList<Libro>();
		for (Prestamo prestamo : prestamosUsuario) {
			if (prestamo.getFechaDevolucion() == null) {
				for (Libro libro : prestamo.getLibro()) {
					librosPrestamo.add(libro);
				}
			}
		}

		int totalLibros = librosPrestamo.size() + librosPrestTable.getSelectionModel().getSelectedItems().size();

		if (totalLibros > main.getMaxLibrosPrestamo()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¡Atención!");
			alert.setContentText("El usuario ya tiene prestados " + librosPrestamo.size() + " libros.\n"
					+ "No puede tener más de " + main.getMaxLibrosPrestamo() + " libros en préstamo.\n"
					+ "En este préstamo sólo puede llevarse " + (main.getMaxLibrosPrestamo() - librosPrestamo.size())
					+ " libros.");
			alert.showAndWait();
		} else {

			int cant;
			for (Libro libro : librosPrestTable.getSelectionModel().getSelectedItems()) {
				cant = libro.getCantidad() - 1;
				if (cant >= 0) {
					libros.add(libro);
					libro.setCantidad(cant);
					try {
						ServiceLocator.getLibroService().actualizarLibro(libro);
					} catch (ServiceException e) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("¡Atención!");
						alert.setContentText("Ha ocurrido un error al aactualizar el stock de ejemplares:\n"
								+ e.getMessage() + "\n" + e.getCause());
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

			if (libros.size() > 0) {

				Prestamo prestamo = new Prestamo();

				prestamo.setUsuario(usuariosPrestTable.getSelectionModel().getSelectedItem());
				// for (Libro libro :
				// librosPrestTable.getSelectionModel().getSelectedItems()) {
				// libros.add(libro);
				// }
				prestamo.setLibro(libros);

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
				prestamo.getFechaPrestamo();
				try {
					ServiceLocator.getPrestamoService().crearPrestamo(prestamo);
					main.getStage().close();
				} catch (ServiceException | RuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al grabar el péstamo:\n" + e.getMessage() + "\n"
							+ e.getCause() + "\nLos datos no se guardarán en la Base de Datos");
					alert.showAndWait();
					DataBase.getSession().close();
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText(
						"No hay libros que añadir al préstmo. Este préstamo no se grabará en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				main.getStage().close();
			}
		}
	}

	@FXML
	private void onAtrasPrestButton() {
		if (librosBorderPane.isVisible()) {
			usuariosBorderPane.setVisible(true);
			librosBorderPane.setVisible(false);
			prestamoScrollPane.setVisible(false);
			prestamosTextArea.setVisible(false);
			atrasPrestButton.setDisable(true);
		} else if (prestamoScrollPane.isVisible()) {
			usuariosBorderPane.setVisible(false);
			librosBorderPane.setVisible(true);
			prestamoScrollPane.setVisible(false);
			prestamosTextArea.setVisible(false);
			siguientePrestButton.setDisable(false);
		}
	}

	@FXML
	private void onFinalizarPresButton() {
		ObservableSet<Libro> libros = FXCollections.observableSet();

		Usuario usu = usuariosPrestTable.getSelectionModel().getSelectedItem();
		List<Sancion> usuarioSancionado = null;
		List<Prestamo> prestamosUsuario = null;
		try {
			usuarioSancionado = ServiceLocator.getSancionService().listarSancionesPorUsuario(usu);
			prestamosUsuario = ServiceLocator.getPrestamoService().prestamosPorUsuario(usu);
		} catch (ServiceException e2) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al listar las sanciones del usuario: \n" + e2.getMessage()
					+ "\nLos datos no se guardarán en la Base de Datos");
			alert.showAndWait();
			e2.printStackTrace();
		} finally {
			DataBase.getSession().clear();
		}
		Date fecha = null, fechaTmp = null;
		if (usuarioSancionado.size() > 0) {
			fecha = usuarioSancionado.get(0).getFechaFinalizacion();
			for (Sancion sancionItem : usuarioSancionado) {
				fechaTmp = sancionItem.getFechaFinalizacion();
				if (fechaTmp.after(fecha)) {
					fecha = fechaTmp;
				}
			}
			Date fecha2 = null;
			try {
				fecha2 = DateUtils.toDate(LocalDate.now());
			} catch (ParseException e2) {
				Alert alertError = new Alert(AlertType.ERROR);
				alertError.setTitle("Error");
				alertError.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e2.getMessage()
						+ "\nCompruebe que la fecha de la sanción es correcta");
				alertError.showAndWait();
				e2.printStackTrace();
			}
			if (fecha.after(fecha2)) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("¡Atención!");
				alert.setContentText("El usuario tiene una sanción vigente y no puede realizar préstamos");
				alert.showAndWait();
				main.getStage().close();
			} else {
				crearPrestamo(libros, prestamosUsuario);
			}
		} else {
			crearPrestamo(libros, prestamosUsuario);
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
