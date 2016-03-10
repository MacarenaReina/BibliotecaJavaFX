package dad.bibliotecafx.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.utils.DateUtils;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SancionInsertarController {

	private Main main;

	private LocalDate fechaFin;

	@FXML
	private TableView<Prestamo> prestamosTable;
	@FXML
	private TableColumn<Prestamo, Long> prestamoTableColum;
	@FXML
	private TableColumn<Prestamo, Usuario> usuarioTableColumn;

	@FXML
	private Button insertarButton, cancelarButton;

	@FXML
	private DatePicker datePickerInicio;

	@FXML
	private TextField buscarPrestamoText;

	private FilteredList<Prestamo> filtroPrestamos;
	SortedList<Prestamo> sortedDataPrestamos;

	public SancionInsertarController() {

	}

	@FXML
	private void initialize() {
		datePickerInicio.setValue(LocalDate.now());
		datePickerInicio.setEditable(false);

		prestamoTableColum.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("codigo"));
		usuarioTableColumn.setCellValueFactory(new PropertyValueFactory<Prestamo, Usuario>("usuario"));
	}

	@FXML
	private void onInsertarButton() {
		int prestamosSeleccionados = prestamosTable.getSelectionModel().getSelectedItems().size();
		int libros;

		if (prestamosSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insertar sanción");
			alert.setHeaderText("Sanción");
			alert.setContentText("Debe seleccionar un usuario");

			alert.showAndWait();
		} else if (prestamosSeleccionados > 1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insertar sanción");
			alert.setHeaderText("Usuario");
			alert.setContentText("Debe seleccionar un sólo préstamo");
			alert.showAndWait();
		} else {
			if (prestamosTable.getSelectionModel().getSelectedItem().getFechaDevolucion() == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar sanción");
				alert.setHeaderText("Sanción");
				alert.setContentText("No se puede crear una sanción de un préstamo que no está devuelto");
				alert.showAndWait();
			} else {
				if (datePickerInicio.getValue().isBefore(prestamosTable.getSelectionModel().getSelectedItem()
						.getFechaDevolucion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Insertar sanción");
					alert.setHeaderText("Usuario");
					alert.setContentText(
							"La fecha de inicio de sanción no puede ser inferior a la fecha de devolución: " + 
					DateUtils.toStringDate(prestamosTable.getSelectionModel().getSelectedItem().getFechaDevolucion()));
					alert.showAndWait();
				} else {

					libros = prestamosTable.getSelectionModel().getSelectedItem().getLibro().size();
					fechaFin = datePickerInicio.getValue().plusDays(main.getDiasSancion() * libros);
					Sancion sancion = new Sancion();

					try {
						sancion.setFechaAlta(DateUtils.toDate(datePickerInicio.getValue()));
						sancion.setFechaFinalizacion(DateUtils.toDate(fechaFin));
					} catch (ParseException e1) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e1.getMessage()
								+ "\nCompruebe que la fecha de la sanción es correcta");
						alert.showAndWait();
						e1.printStackTrace();
					}
					sancion.setPrestamo(prestamosTable.getSelectionModel().getSelectedItem());
					try {
						ServiceLocator.getSancionService().crearSancion(sancion);
						main.getStage().close();
					} catch (ServiceException | RuntimeException e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Error");
						alert.setContentText("Ha ocurrido un error al grabar la sanción: \n" + e.getMessage()
								+ "\nLos datos no se guardarán en la Base de Datos");
						alert.showAndWait();
						DataBase.getSession().close();
						e.printStackTrace();
					}
				}
			}
		}
	}

	@FXML
	private void onCancelarButton() {
		this.main.getStage().close();
	}

	public void setMain(Main main) {
		this.main = main;
		prestamosTable.setItems(main.getPrestamosData());
	}

	public void setFilterPrestamos(ObservableList<Prestamo> prestamos) {
		filtroPrestamos = new FilteredList<>(prestamos, p -> true);
		buscarPrestamoText.textProperty().addListener((observable, oldValue, newValue) -> {
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
}
