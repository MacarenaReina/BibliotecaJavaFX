package dad.bibliotecafx.controller;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class UsuarioModificarController {

	private Main main;
	private Usuario usuario, usuarioLogged;

	@FXML
	private TextField nombreModifUsuText;
	@FXML
	private TextField apellidosModifUsuText;
	@FXML
	private TextField nombreUsuModifUsuText;
	@FXML
	private PasswordField contraseniaModifUsuText;
	@FXML
	private ComboBox<Rol> rolModifUsuComboBox;
	@FXML
	private Button modificarModifUsuButton, cancelarModifUsuButton, restablecerButton;
	@FXML
	private Label rolEtiqueta;

	@FXML
	private void initialize() {
		nombreModifUsuText.requestFocus();
		nombreUsuModifUsuText.setEditable(false);
	}

	public UsuarioModificarController() {

	}

	@FXML
	private void onModificarModifUsuButton() {
		usuario.setNombre(nombreModifUsuText.getText() + " " + apellidosModifUsuText.getText());
		usuario.setUsuario(nombreUsuModifUsuText.getText());
		usuario.setPassword(contraseniaModifUsuText.getText());
		usuario.setRol(rolModifUsuComboBox.getSelectionModel().getSelectedItem());

		try {
			ServiceLocator.getUsuarioService().actualizarUsuario(usuario.toItem());
			main.getStage().close();
		} catch (ServiceException | RuntimeException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Ha ocurrido un error al actualizar el usuario:\n" + e.getMessage()
					+ "\nLos datos no se guardarán en la Base de Datos");
			alert.showAndWait();
			DataBase.rollback();
			e.printStackTrace();
		}
	}

	@FXML
	private void onCancelarModifUsuButton() {
		main.getStage().close();
	}

	public void setMain(Main main, Usuario usuarioLogged) {
		this.main = main;
		this.usuarioLogged = usuarioLogged;
		ocultarDatos();
	}

	public void setRolesData(ObservableList<Rol> roles) {
		rolModifUsuComboBox.setItems(roles);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;

		String[] usuarioArray = usuario.getNombre().split(" ");
		if (usuarioArray.length >= 1) {
			nombreModifUsuText.setText(usuarioArray[0]);
			nombreModifUsuText.selectAll();
			nombreModifUsuText.requestFocus();
		}
		if (usuarioArray.length >= 2) {
			for (int i = 1; i < usuarioArray.length; i++) {
				apellidosModifUsuText.setText(apellidosModifUsuText.getText() + usuarioArray[i] + " ");
			}
		}
		nombreUsuModifUsuText.setText(usuario.getUsuario());
		contraseniaModifUsuText.setText(usuario.getPassword());
		rolModifUsuComboBox.setValue(usuario.getRol());
	}

	@FXML
	private void onRestablecerButton() {
		contraseniaModifUsuText.setText(nombreUsuModifUsuText.getText());
	}

	private void ocultarDatos() {
		if (usuarioLogged.getRol().getTipo().equals("Bibliotecario")
				|| usuarioLogged.getRol().getTipo().equals("Lector")) {
			rolModifUsuComboBox.setVisible(false);
			rolEtiqueta.setVisible(false);
		}
	}
}
