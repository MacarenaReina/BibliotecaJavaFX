package dad.bibliotecafx.controller;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Sancion;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.utils.DateUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class DevolucionPrestamoController {
	private Main main;

	private Date fechaDevolucion, fechaPrestamo;
	private Prestamo prestamo;

	@FXML
	private DatePicker devolucionDatePicker;

	@FXML
	private Button cancelarButton, aceptarButton;

	@FXML
	private void initialize() {}

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	public void onAceptarButton() {
		try {
			fechaDevolucion = DateUtils.toDate(devolucionDatePicker.getValue());
		} catch (ParseException e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e1.getMessage()
					+ "\nCompruebe que la fecha del préstamo es correcta");
			alert.showAndWait();
			e1.printStackTrace();
		}

		if (fechaDevolucion.before(fechaPrestamo)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¡Atención!");
			alert.setContentText("La fecha de devolución debe ser superior a la fecha del préstamo");
			alert.showAndWait();
		} else {
			prestamo.setFechaDevolucion(fechaDevolucion);
			try {
				for(Libro libro : prestamo.getLibro()){
					libro.setCantidad(libro.getCantidad() + 1);
					ServiceLocator.getLibroService().actualizarLibro(libro);
				}	
				ServiceLocator.getPrestamoService().actualizarPrestamo(prestamo);
				LocalDate fechaTemp = DateUtils.toLocalDate(fechaPrestamo).plusDays(main.getDiasPrestamo());
				LocalDate fFin = DateUtils.toLocalDate(fechaDevolucion);
				long dif = ChronoUnit.DAYS.between(fechaTemp, fFin);
				if (fechaTemp.isBefore(fFin)) {
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("¡Préstamo con retraso!");
					alert.setContentText("La devolución de este préstamo se ha retrasado " + dif + " días.\n"
							+ "¿Desea crear una sanción para este préstamo?");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.isPresent() && result.get() == ButtonType.OK) {
						Sancion s = new Sancion();
						s.setPrestamo(prestamo);
						s.setFechaAlta(fechaDevolucion);
						LocalDate fechaFin = fFin.plusDays(main.getDiasSancion() * prestamo.getLibro().size());
						try {
							s.setFechaFinalizacion(DateUtils.toDate(fechaFin));
						} catch (ParseException e) {
							Alert alertError = new Alert(AlertType.ERROR);
							alertError.setTitle("Error");
							alertError.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n"
									+ e.getMessage() + "\nCompruebe que la fecha de la sanción es correcta");
							alertError.showAndWait();
							e.printStackTrace();
						}
						try {
							ServiceLocator.getSancionService().crearSancion(s);							
						} catch (ServiceException e) {
							Alert alertErrorCrear = new Alert(AlertType.ERROR);
							alertErrorCrear.setTitle("Error");
							alertErrorCrear.setContentText("Ha ocurrido un error al grabar la sanción: \n"
									+ e.getMessage() + "\nLos datos no se guardarán en la Base de Datos");
							alertErrorCrear.showAndWait();
							DataBase.getSession().close();
							e.printStackTrace();
						}
					}
				}
				main.getStage().close();
			} catch (ServiceException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al modificar el préstamo:\n" + e.getMessage()
						+ "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void onCancelarButton() {
		main.getStage().close();
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
		fechaDevolucion = prestamo.getFechaDevolucion();
		fechaPrestamo = prestamo.getFechaPrestamo();
		if (fechaDevolucion == null) {
			devolucionDatePicker.setValue(LocalDate.now());
		} else {
			devolucionDatePicker.setValue(DateUtils.toLocalDate(fechaDevolucion));
		}
	}
}
