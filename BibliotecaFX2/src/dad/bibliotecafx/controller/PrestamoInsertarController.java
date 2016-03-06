package dad.bibliotecafx.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Libro;
import dad.bibliotecafx.modelo.Prestamo;
import dad.bibliotecafx.modelo.Usuario;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Usuario> usuariosPrestTable;
	@FXML
	private TableColumn<Usuario, String> nombrePrestTableColum;
	@FXML
	private TableColumn<Usuario, String> rolPrestTableColumn;
	
	@FXML
	private ScrollPane prestamoScrollPane;
	
	@FXML
	private TextArea prestamosTextArea;
	
	@FXML
	private Button siguientePrestButton, atrasPrestButton;
	
	@FXML
	private DatePicker datePicker;
	
	@FXML
	private void initialize() {
		datePicker.setValue(LocalDate.now());
		librosPrestTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		if(usuariosPrestTable.isVisible()) {
			atrasPrestButton.setDisable(true);
		}
		
		nombrePrestTableColum.setCellValueFactory(cellData -> cellData.getValue().usuarioProperty());
		rolPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Usuario, String>("rol"));

		tituloPrestTableColumn.setCellValueFactory(cellData -> cellData.getValue().tituloProperty());
		autorPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, String>("autores"));
		anioPrestTableColumn.setCellValueFactory(new PropertyValueFactory<Libro, Integer>("anioPublicacion"));		
		
//		anioPrestTableColumn.setCellValueFactory(cellData -> cellData.getValue().anioPublicacionProperty());
	}
	
	@FXML
	private void onSiguientePrestButton() {
		int usuariosSeleccionados = usuariosPrestTable.getSelectionModel().getSelectedItems().size();
		int librosSeleccionados = librosPrestTable.getSelectionModel().getSelectedItems().size();
		ObservableSet<Libro> libros = FXCollections.observableSet();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTimeInMillis(System.currentTimeMillis());
//		Date fecha = cal.getTime();
		
		if(usuariosPrestTable.isVisible()) {
				
			if(usuariosSeleccionados==0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Usuario");
				alert.setContentText("Debe seleccionar un usuario");
	
				alert.showAndWait();
			} else if(usuariosSeleccionados>1) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Usuario");
				alert.setContentText("Debe seleccionar un sólo usuario");
	
				alert.showAndWait();
			} else {
				usuariosPrestTable.setVisible(false);
				librosPrestTable.setVisible(true);
				atrasPrestButton.setDisable(false);
			}
			
		} else if(librosPrestTable.isVisible()) {
			if(librosSeleccionados==0) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Insertar préstamo");
				alert.setHeaderText("Libro");
				alert.setContentText("Debe seleccionar un libro");
	
				alert.showAndWait();
			} else {
				
				usuariosPrestTable.setVisible(false);
				librosPrestTable.setVisible(false);		
				prestamosTextArea.setText("Usuario: "+usuariosPrestTable.getSelectionModel().getSelectedItem().getUsuario()+"\n"
						+ "Libro: "+ librosPrestTable.getSelectionModel().getSelectedItems() +"\n"
						+ "Autor: "+ librosPrestTable.getSelectionModel().getSelectedItem().getAutores().toString()+"\n"
						+ "Fecha del préstamo: " + datePicker.getValue().format(formatter));
				
				prestamoScrollPane.setVisible(true);
				prestamosTextArea.setVisible(true);
				siguientePrestButton.setText("Finalizar");
			}
		} else if(prestamoScrollPane.isVisible()) {
			
			for (Libro libro : libros) {
				System.out.println(libro.getTitulo());
			}
			
				Prestamo prestamo = new Prestamo();
				
				prestamo.setUsuario(usuariosPrestTable.getSelectionModel().getSelectedItem());
				System.out.println(prestamo.getUsuario().getNombre());
				for (Libro libro : librosPrestTable.getSelectionModel().getSelectedItems()) {
					libros.add(libro);
				}				
				prestamo.setLibro(libros);		
				
				String formattedValue = (datePicker.getValue()).format(formatter);
				try {
					prestamo.setFechaPrestamo(sdf.parse(formattedValue));
				} catch (ParseException e1) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al intentar parsear la fecha:\n" + e1.getMessage()
							+ "\nCompruebe que la fecha del préstamo es correcta");
					alert.showAndWait();
					e1.printStackTrace();
				}
				prestamo.getFechaPrestamo();
				
				//guardar el prestamo y cerrar la ventana
				try {
					ServiceLocator.getPrestamoService().crearPrestamo(prestamo.toItem());					
					main.getStage().close();
				} catch (ServiceException | RuntimeException e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error");
					alert.setContentText("Ha ocurrido un error al grabar el péstamo: \n" + e.getMessage()
							+ "\nLos datos no se guardarán en la Base de Datos");
					alert.showAndWait();
					DataBase.rollback();
					e.printStackTrace();
				}
			
			
			
		}
		
		
		
		
		
		
//		if(usuariosPrestTable.isVisible()) {
//			usuariosPrestTable.setVisible(false);
//			librosPrestTable.setVisible(true);
//			atrasPrestButton.setDisable(false);
//		} else if(librosPrestTable.isVisible()) {
//			usuariosPrestTable.setVisible(false);
//			librosPrestTable.setVisible(false);
//			
//			
//			prestamosTextArea.setText("Usuario: \nLibro: \nAutor: \nFecha del préstamo: ");
//			
//			prestamoScrollPane.setVisible(true);
//			prestamosTextArea.setVisible(true);
//			siguientePrestButton.setText("Finalizar");
//		} else if(prestamoScrollPane.isVisible()) {
//			//guardar el prestamo y cerrar la ventana
//			System.out.println("SALIR");
//		}
	}
	
	@FXML
	private void onAtrasPrestButton() {
		if(librosPrestTable.isVisible()) {
			usuariosPrestTable.setVisible(true);
			librosPrestTable.setVisible(false);
			prestamoScrollPane.setVisible(false);
			atrasPrestButton.setDisable(true);
		} else if(prestamoScrollPane.isVisible()) {
			usuariosPrestTable.setVisible(false);
			librosPrestTable.setVisible(true);
			prestamoScrollPane.setVisible(false);
			siguientePrestButton.setText("Siguiente");
		}
	}
	
	public void setMain(Main main) {
		this.main = main;
		
		usuariosPrestTable.setItems(main.getUsuariosData());
		librosPrestTable.setItems(main.getLibrosData());
	}
	
}
