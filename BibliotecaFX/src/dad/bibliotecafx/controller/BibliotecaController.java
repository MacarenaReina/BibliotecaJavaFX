package dad.bibliotecafx.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class BibliotecaController {
	
	private StringProperty usuario, password;
		
	@FXML
	private Button loginButton; //El nombre tiene que coindicir con el id que tiene en el XML

	@FXML
	private Label usuarioLabel;
	
	@FXML
	private Label passwordLabel;
	
	@FXML
	private TextField usuarioText;
	
	@FXML
	private PasswordField passwordText;
	
	@FXML
	private void initialize(){
		System.out.println("Inicializando el controlador");
		
		usuario = new SimpleStringProperty(); //Modelo		
		password = new SimpleStringProperty();
		
		usuarioText.textProperty().bind(usuario); //Vincula el Modelo con la Vista
		passwordText.textProperty().bindBidirectional(password);
	}
	
	@FXML
	private void onLoginHandle(ActionEvent e){
		System.out.println("Me acaban de pulsar");
		usuario.set("Pwd: " + password.get());
	}
		
}
