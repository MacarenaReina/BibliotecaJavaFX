package dad.bibliotecafx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import dad.bibliotecafx.service.impl.RolService;
import dad.bibliotecafx.service.items.RolItem;
import dad.bibliotecafx.service.items.UsuarioItem;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class UsuarioAltaController {
	
	private Main main;
	
	//INSERTAR USUARIO
	@FXML
	private TextField nombreUsuText, apellidoUsuText, nombreUsuarioUsuText;
	@FXML
	private PasswordField contraseniaUsuText;
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
		//En mi orde da error...
		
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Insertar rol");
//		dialog.setHeaderText("");
		dialog.setContentText("Nombre del nuevo rol:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    RolItem rol = new RolItem();
			rol.setTipo(result.get());
			try {
				ServiceLocator.getRolService().crearRol(rol);
			} catch (ServiceException e) {
				e.printStackTrace();
			}
			
			//para que se actualice el comboBox de roles
			ObservableList<Rol> roles = main.getRolesData();
			rolUsuComboBox.setItems(roles);
			rolUsuComboBox.setValue(roles.get(0));
		}
		
	}
	
	@FXML
	private void onInsertarUsuButton() {
//		System.out.println(nombreUsuText.getText());
//		System.out.println(contraseniaUsuText.getCharacters().toString());
		
//		UsuarioItem usuario = new UsuarioItem();
//		usuario.setNombre(nombreUsuText.getText()+" "+apellidoUsuText.getText());
//		usuario.setUsuario(nombreUsuarioUsuText.getText());
//		usuario.setPassword(contraseniaUsuText.getCharacters().toString());
//		usuario.setRol(rol);
		
//		try {
//			ServiceLocator.getUsuarioService().crearUsuario(usuario);
//		} catch (ServiceException e) {
//			e.printStackTrace();
//		}
	}
	
	@FXML
	private void onCancelarButton() {
		
	}
	
	public void setRolesData(ObservableList<Rol> roles){
		rolUsuComboBox.setItems(roles);
		rolUsuComboBox.setValue(roles.get(0));
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
