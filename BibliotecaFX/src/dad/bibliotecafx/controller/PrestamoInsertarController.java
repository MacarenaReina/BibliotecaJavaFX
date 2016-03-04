package dad.bibliotecafx.controller;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.modelo.Libro;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public class PrestamoInsertarController {
	
	private Main main;
	
	@FXML
	private StackPane prestamoStackPane;
	
	@FXML
	private TableView<Libro> librosPrestTable;
	@FXML
	private TableColumn<Libro, String> tituloPrestTableColumn;
	@FXML
	private TableColumn<Libro, String> autorPrestTableColumn;
	@FXML
	private TableColumn<Libro, Integer> anioPrestTableColumn;
	
	@FXML
	private TableView<Libro> usuariosPrestTable;
	@FXML
	private TableColumn<Libro, String> nombrePrestTableColum;
	@FXML
	private TableColumn<Libro, String> rolPrestTableColumn;
	
	@FXML
	private Button siguientePrestButton, cancelarPrestButton;
	
	public PrestamoInsertarController() {
		
	}
	
	@FXML
	private void initialize() {
		
	}
	
	@FXML
	private void onSiguientePrestButton() {
		if(librosPrestTable.isVisible()) {
			librosPrestTable.setVisible(false);
			usuariosPrestTable.setVisible(true);
		}
	}
	
	@FXML
	private void onCancelarPrestButton() {
		
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
}
