package dad.bibliotecafx.controller;

import java.awt.Button;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.impl.RolService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class UsuarioAltaController {
	
	private Main main;
	
	//INSERTAR USUARIO
	@FXML
	private TextField nombreUsuText;
	@FXML
	private ComboBox<Rol> rolUsuComboBox;
	@FXML
	private Button nuevoRolButton, insertarUsuButton, cancelarUsuButton;
	
	public UsuarioAltaController() {
//		Stage stage = new Stage();
//		stage.setTitle("Alta usuario");
//		stage.setWidth(298);
//		stage.setHeight(154);
//		stage.setResizable(false);
//		
//		URL url = getClass().getResource("/dad/bibliotecafx/docs/BibliotecaNuevoUsuario.fxml");
//		FXMLLoader loader = new FXMLLoader(url);
//		Scene scene = null;
//		try {
//			scene = new Scene(loader.load());
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		
//		stage.setScene(scene);
//		stage.show();
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onNuevoRolButton() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Insertar rol");
//		dialog.setHeaderText("");
		dialog.setContentText("Nombre del nuevo rol:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    Rol rol = new Rol();
			rol.setTipo(result.get());
			try {
				new RolService().crearRol(rol.toItem());
			} catch (ServiceException e) {
				e.printStackTrace();
			}
		}
	}
	
	@FXML
	private void onInsertarUsuButton() {
		
	}
	
	@FXML
	private void onCancelarButton() {
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
