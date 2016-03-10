package dad.bibliotecafx.controller;

import dad.bibliotecafx.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GestionConfiguracionesController {

	private Main main;

	@FXML
	private TextField diasPrestamoText, diasSancionText, maximoLibrosPrestamoText;
	@FXML
	private Button aceptarButton, cancelarButton;

	@FXML
	private void initialize() {

	}

	public void setMain(Main main) {
		this.main = main;
	}

	@FXML
	public void onCancelarButton() {
		main.getStage().close();
	}

	@FXML
	public void onAceptarButton() {
		if (!diasPrestamoText.getText().isEmpty() && !diasSancionText.getText().isEmpty()
				&& !maximoLibrosPrestamoText.getText().isEmpty()) {
			Integer dP = null, dS = null, mL = null;
			try {
				dP = Integer.parseInt(diasPrestamoText.getText());
				dS = Integer.parseInt(diasSancionText.getText());
				mL = Integer.parseInt(maximoLibrosPrestamoText.getText());
			} catch (NumberFormatException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error. Asegúrese de que los datos son correctos");
				alert.showAndWait();
			}
			main.setDiasPrestamo(dP);
			main.setDiasSancion(dS);
			main.setMaxLibrosPrestamo(mL);
			main.getStage().close();
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¡Atención!");
			alert.setContentText("Ninguno de los campos puede estar vacío");
			alert.showAndWait();
			if (diasPrestamoText.getText().isEmpty()) {
				diasPrestamoText.requestFocus();
			} else if(diasSancionText.getText().isEmpty()) {
				diasSancionText.requestFocus();
			} else if(maximoLibrosPrestamoText.getText().isEmpty()){
				maximoLibrosPrestamoText.requestFocus();
			}
		}
	}

	public void setData(Integer diasP, Integer diasS, Integer maxLibros) {
		diasPrestamoText.setText(String.valueOf(diasP));
		diasSancionText.setText(String.valueOf(diasS));
		maximoLibrosPrestamoText.setText(String.valueOf(maxLibros));
	}
}
