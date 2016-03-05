package dad.bibliotecafx.controller;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class UsuarioModificarController {
	
	private Main main;
	private Usuario usuario;
	
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
	private Button modificarModifUsuButton, cancelarModifUsuButton;
	
	public UsuarioModificarController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onModificarModifUsuButton() {
		usuario.setNombre(nombreModifUsuText.getText()+" "+apellidosModifUsuText.getText());
		usuario.setUsuario(nombreUsuModifUsuText.getText());
		usuario.setPassword(contraseniaModifUsuText.getText());
		usuario.setRol(rolModifUsuComboBox.getValue());
		
		try {
			ServiceLocator.getUsuarioService().actualizarUsuario(usuario.toItem());
			main.getStage().close();
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void onCancelarModifUsuButton() {
		main.getStage().close();
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	public void setRolesData(ObservableList<Rol> roles){
		rolModifUsuComboBox.setItems(roles);
//		rolModifUsuComboBox.setValue(roles.get(0));
	}
	
	//TODO: no sabía otra forma de pasarle los datos del usuario
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		
		String [] usuarioArray = usuario.getNombre().split(" ");
		if(usuarioArray.length >= 1) {
			nombreModifUsuText.setText(usuarioArray[0]);
		}
		if(usuarioArray.length >= 2) {
			for(int i=1; i< usuarioArray.length; i++) {
				apellidosModifUsuText.setText(apellidosModifUsuText.getText() + usuarioArray[i]+" ");
			}
		}
		
		
		//TODO: faltaría dividir el campo nombre en nombre y apellido
		nombreUsuModifUsuText.setText(usuario.getUsuario());
		contraseniaModifUsuText.setText(usuario.getPassword());
		rolModifUsuComboBox.setValue(usuario.getRol());
	}	
}
