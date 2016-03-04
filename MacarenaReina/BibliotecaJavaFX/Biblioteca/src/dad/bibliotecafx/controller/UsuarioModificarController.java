package dad.bibliotecafx.controller;

import java.awt.Button;
import java.awt.TextField;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Rol;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;

public class UsuarioModificarController {
	
	private Main main;
	
	@FXML
	private TextField nombreModifUsuText;
	@FXML
	private PasswordField contraseniaModifUsuText;
	@FXML
	private ComboBox<Rol> rolModifUsuComboBox;
	@FXML
	private Button modificarModifUsuButton, cancelarModifUsuButton;
	
	public UsuarioModificarController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onModificarModifUsuButton() {
		
	}
	
	@FXML
	private void onCancelarModifUsuButton() {
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
}
