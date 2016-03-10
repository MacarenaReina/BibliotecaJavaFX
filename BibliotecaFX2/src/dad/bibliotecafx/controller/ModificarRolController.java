package dad.bibliotecafx.controller;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ModificarRolController {

	private Main main;
	private Rol rol;
	
	@FXML
	private TextField nuevoNombreRolText;
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	public void onCancelarButton(){
		main.getStage().close();
	}
	
	@FXML
	public void onAceptarButton(){
		if(!nuevoNombreRolText.getText().isEmpty()){
			rol.setTipo(nuevoNombreRolText.getText());
			try {
				ServiceLocator.getRolService().actualizarRol(rol);	
//				(BibliotecaPrincipalController).refrescarTablaUsuarios();
				main.getStage().close();
			} catch (ServiceException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al actualizar el rol: " + e.getMessage() + "\n" +
				e.getCause());
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		} else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("¡Atención!");
			alert.setContentText("El nombre del rol no puede estar vacío");
			alert.showAndWait();
		}
	}
	
	public void setMain(Main main) {
		this.main = main;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
		nuevoNombreRolText.setText(rol.getTipo());
	}

}
