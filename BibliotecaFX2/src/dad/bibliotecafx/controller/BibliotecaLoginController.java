package dad.bibliotecafx.controller;

import java.io.IOException;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class BibliotecaLoginController {

	private Main main;

	@FXML
	private Button loginButton; // El nombre tiene que coindicir con el id que
								// tiene en el XML

	@FXML
	private Label usuarioLabel;

	@FXML
	private Label passwordLabel;

	@FXML
	private TextField usuarioText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private void initialize() {
	}

	@FXML
	private void onLoginHandle(ActionEvent e) {
		try {
			Usuario usuario = (ServiceLocator.getUsuarioService().loginCorrecto(usuarioText.getText(),
					passwordText.getText())).toModel();
			try {
				this.main.showBibliotecaScene(usuario);
			} catch (IOException | RuntimeException e1) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al cargar la aplicación");
				alert.showAndWait();
				e1.printStackTrace();
			}
		} catch (ServiceException | NullPointerException e1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setContentText("Los datos de acceso no son correctos");
			alert.showAndWait();
		}
	}

	public void setMain(Main main) {
		this.main = main;
	}

}
