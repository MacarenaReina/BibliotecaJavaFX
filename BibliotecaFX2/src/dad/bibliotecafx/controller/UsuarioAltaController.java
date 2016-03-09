package dad.bibliotecafx.controller;

import java.util.Optional;
import java.util.Random;
import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class UsuarioAltaController {

	private Main main;
	private Usuario usuarioLogged;
	Rol rol;

	// INSERTAR USUARIO
	@FXML
	private TextField nombreUsuText, apellidoUsuText, nombreUsuarioUsuText;
	@FXML
	private PasswordField contraseniaUsuText;
	@FXML
	private ComboBox<Rol> rolUsuComboBox;
	@FXML
	private Button nuevoRolButton, insertarUsuButton, cancelarUsuButton;
	@FXML
	private Label rolEtiqueta;

	public UsuarioAltaController() {
	}

	@FXML
	private void initialize() {
		nombreUsuText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				nombreUsuarioUsuText.setText(newValue.toLowerCase());
			}
		});
		nombreUsuText.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue.booleanValue()) {
					Random rnd = new Random();
					nombreUsuarioUsuText.setText(nombreUsuarioUsuText.getText() + rnd.nextInt(100));
					// contraseniaUsuText.setText(nombreUsuarioUsuText.getText());
				}
			}
		});
		nombreUsuarioUsuText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				contraseniaUsuText.setText(nombreUsuarioUsuText.getText());
			}
		});
	}

	@FXML
	private void onNuevoRolButton() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Insertar rol");
		dialog.setContentText("Nombre del nuevo rol:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Rol rol = new Rol();
			rol.setTipo(result.get());
			try {
				ServiceLocator.getRolService().crearRol(rol);
			} catch (ServiceException | RuntimeException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al crear el rol:\n" + e.getMessage()
						+ "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onInsertarUsuButton() {
		String nombre = nombreUsuText.getText();
		String apellidos = apellidoUsuText.getText();
		String nombreUsuario = nombreUsuarioUsuText.getText();
		String contrasenia = contraseniaUsuText.getCharacters().toString();
		rol = rolUsuComboBox.getSelectionModel().getSelectedItem();

		if (nombre.isEmpty() || apellidos.isEmpty() || nombreUsuario.isEmpty() || contrasenia.isEmpty()
				|| rol.equals(null)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Alta usuario");
			alert.setContentText("Debe rellenar todos los campos");
			alert.showAndWait();
		} else {
			Usuario usuario = new Usuario();
			usuario.setNombre(nombre + " " + apellidos);
			usuario.setUsuario(nombreUsuario);
			usuario.setPassword(contrasenia);
			usuario.setRol(rol);
			main.getStage().close();
			try {
				if (ServiceLocator.getUsuarioService().crearUsuario(usuario)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("¡Atención!");
					alert.setContentText("¡El usuario ya existe en la Base de Datos!");
					alert.showAndWait();
				} else {
					main.getStage().close();
				}
			} catch (ServiceException | RuntimeException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al crear el usuario:\n" + e.getMessage()
						+ "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		}
	}

	@FXML
	private void onCancelarButton() {
		main.getStage().close();
	}

	public void setMain(Main main, Usuario usuario) {
		this.main = main;
		this.usuarioLogged = usuario;
		rolUsuComboBox.setItems(main.getRolesData());
		Rol r = null;
		for (Rol rol : main.getRolesData()) {
			if (rol.getTipo().equals("Lector")) {
				r = rol;
			}
		}
		rolUsuComboBox.setValue(r);
		ocultarDatos();
	}

	private void ocultarDatos() {
		if (usuarioLogged.getRol().getTipo().equals("Bibliotecario")) {
			nuevoRolButton.setVisible(false);
			rolUsuComboBox.setVisible(false);
			rolEtiqueta.setVisible(false);
		}
	}
}
