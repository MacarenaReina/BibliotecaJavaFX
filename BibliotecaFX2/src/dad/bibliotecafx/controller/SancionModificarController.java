package dad.bibliotecafx.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

public class SancionModificarController {

	private Main main;

	private Date fechaSancion;
	private Sancion sancion;

	@FXML
	private DatePicker sancionDatePicker;

	@FXML
	private Button cancelarButton, aceptarButton;

	@FXML
	private void initialize() {
		
	}

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	public void onAceptarButton() {
		int libros;

		if (sancion.getPrestamo().getFechaDevolucion() == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Insertar sanci�n");
			alert.setHeaderText("Sanci�n");
			alert.setContentText("No se puede crear una sanci�n de un pr�stamo que no est� devuelto");
			alert.showAndWait();
		} else {
			if (sancionDatePicker.getValue().isBefore(DateUtils.toLocalDate(sancion.getPrestamo().getFechaDevolucion()))) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar sanci�n");
				alert.setHeaderText("Usuario");
				alert.setContentText("La fecha de inicio de sanci�n no puede ser inferior a la fecha de devoluci�n: "
						+ DateUtils.toStringDate(sancion.getPrestamo().getFechaDevolucion()));
				alert.showAndWait();
			} else {
				libros = sancion.getPrestamo().getLibro().size();
				LocalDate fechaFin = sancionDatePicker.getValue().plusDays(main.getDiasSancion() * libros);
				Sancion sancion = new Sancion();
				try {
					sancion.setFechaAlta(DateUtils.toDate(sancionDatePicker.getValue()));
					sancion.setFechaFinalizacion(DateUtils.toDate(fechaFin));
				} catch (ParseException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e1.getMessage()
							+ "\nCompruebe que la fecha de la sanci�n es correcta");
					alert.showAndWait();
					e1.printStackTrace();
				}
				sancion.setPrestamo(this.sancion.getPrestamo());
				try {
					ServiceLocator.getSancionService().actualizarSancion(sancion);
					main.getStage().close();
				} catch (ServiceException | RuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al grabar la sanci�n: \n" + e.getMessage()
							+ "\nLos datos no se guardar�n en la Base de Datos");
					alert.showAndWait();
					DataBase.getSession().close();
					e.printStackTrace();
				}
			}
		}
	}

	@FXML
	public void onCancelarButton() {
		main.getStage().close();
	}

	public void setSancion(Sancion sancion) {
		this.sancion = sancion;
		fechaSancion = sancion.getFechaAlta();
		// fechaPrestamo = sancion.getFechaPrestamo();
		if (fechaSancion != null) {
			sancionDatePicker.setValue(DateUtils.toLocalDate(fechaSancion));
		}
	}

}
