package dad.bibliotecafx.controller;

import java.util.Optional;
import java.util.Random;

import org.apache.poi.ss.formula.udf.UDFFinder;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

public class UsuarioAltaController {

	private Main main;
	private StringProperty usuario;

	// INSERTAR USUARIO
	@FXML
	private TextField nombreUsuText, apellidoUsuText, nombreUsuarioUsuText;
	@FXML
	private PasswordField contraseniaUsuText;
	@FXML
	private ComboBox<Rol> rolUsuComboBox;
	@FXML
	private Button nuevoRolButton, insertarUsuButton, cancelarUsuButton;

	public UsuarioAltaController() {
	}

	@FXML
	private void initialize() {
		usuario = new SimpleStringProperty();
		nombreUsuText.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String newValue) {
		    	nombreUsuarioUsuText.setText(newValue);
		    }
//		    @Override
//		    protected void finalize() throws Throwable {
//		    	Random rnd = new Random();
//		    	nombreUsuarioUsuText.appendText(String.valueOf(rnd.nextInt(100)));
//		    }
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
				ServiceLocator.getRolService().crearRol(rol.toItem());
			} catch (ServiceException | RuntimeException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al crear el rol:\n" + e.getMessage()
						+ "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.rollback();
				e.printStackTrace();
			}

			ObservableList<Rol> roles = main.getRolesData();
			rolUsuComboBox.setItems(roles);
			rolUsuComboBox.setValue(roles.get(0));
		}
	}

	@FXML
	private void onInsertarUsuButton() {
		String nombre = nombreUsuText.getText();
		String apellidos = apellidoUsuText.getText();
		String nombreUsuario = nombreUsuarioUsuText.getText();
		String contrasenia = contraseniaUsuText.getCharacters().toString();
		Rol rol = rolUsuComboBox.getValue();

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

			try {
				if (ServiceLocator.getUsuarioService().crearUsuario(usuario.toItem())) {
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
				DataBase.rollback();
				e.printStackTrace();
			}
		}

	}

	@FXML
	private void onCancelarButton() {
		main.getStage().close();
	}

	public void setRolesData(ObservableList<Rol> roles) {
		rolUsuComboBox.setItems(roles);
		rolUsuComboBox.setValue(roles.get(0));
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
