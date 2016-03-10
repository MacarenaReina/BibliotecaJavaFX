package dad.bibliotecafx.controller;

import java.io.IOException;
import java.util.Optional;

import dad.bibliotecafx.Main;
import dad.bibliotecafx.db.DataBase;
import dad.bibliotecafx.modelo.Rol;
import dad.bibliotecafx.service.ServiceException;
import dad.bibliotecafx.service.ServiceLocator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class GestionRolesController {

	private Main main;

	@FXML
	private TableView<Rol> rolTable;
	@FXML
	private TableColumn<Rol, Long> codigoRolColumn;
	@FXML
	private TableColumn<Rol, String> tipoRolColumn;
	@FXML
	private Button anadirButton, modificarButton, eliminarButton;

	@FXML
	private void initialize() {
		rolTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		codigoRolColumn.setCellValueFactory(cellData -> cellData.getValue().codigoProperty().asObject());
		tipoRolColumn.setCellValueFactory(cellData -> cellData.getValue().tipoProperty());
	}

	public void setMain(Main main) {
		this.main = main;
		rolTable.setItems(main.getRolesData());
	}

	@FXML
	public void onAnadirRolButton() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Insertar rol");
		dialog.setContentText("Nombre del nuevo rol:");
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Rol rol = new Rol();
			rol.setTipo(result.get());
			try {
				if (ServiceLocator.getRolService().crearRol(rol)) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("¡Atención!");
					alert.setContentText("¡El rol ya existe en la Base de Datos!");
					alert.showAndWait();
				} else{
					rolTable.setItems(main.getRolesData());
				}
			} catch (ServiceException | RuntimeException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setContentText("Ha ocurrido un error al crear el rol:\n" + e.getMessage()
						+ "\nLos datos no se guardarán en la Base de Datos");
				alert.showAndWait();
				DataBase.getSession().close();
				e.printStackTrace();
			}
		}
	}

	@FXML
	public void onModificarRolButton() {
		int rolesSeleccionados = rolTable.getSelectionModel().getSelectedItems().size();
		if (rolesSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modificar rol");
			alert.setContentText("Debe seleccionar el rol que quiere modificar");
			alert.showAndWait();
		} else if (rolesSeleccionados >= 2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Modificar rol");
			alert.setContentText("Sólo puede seleccionar un rol para modificar.");
			alert.showAndWait();
		} else {
			if (rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Administrador")
					|| rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Lector")
					|| rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Bibliotecario")) {
				Alert alertError = new Alert(AlertType.WARNING);
				alertError.setTitle("¡Atención!");
				alertError.setContentText("No puede modificar el rol Administrador, ni el Lector ni el Bibliotecario");
				alertError.showAndWait();
			} else {
				try {
					this.main.showModificarRolDialog(rolTable.getSelectionModel().getSelectedItem());
					rolTable.setItems(main.getRolesData());
				} catch (IOException e) {
					Alert alertError = new Alert(AlertType.WARNING);
					alertError.setTitle("¡Atención!");
					alertError.setContentText("Ha ocurrido un error al cargar la ventana de modificación de Rol: \n"
							+ e.getMessage() + "\n" + e.getCause());
					alertError.showAndWait();
				}
			}
		}
	}

	@FXML
	public void onEliminarButton() {
		int rolesSeleccionados = rolTable.getSelectionModel().getSelectedItems().size();
		if (rolesSeleccionados == 0) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Eliminar Rol");
			alert.setContentText("Debe seleccionar al menos un rol");
			alert.showAndWait();
		} else {
			if (rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Administrador")
					|| rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Lector")
					|| rolTable.getSelectionModel().getSelectedItem().getTipo().equals("Bibliotecario")) {
				Alert alertError = new Alert(AlertType.WARNING);
				alertError.setTitle("¡Atención!");
				alertError.setContentText("No puede eliminar el rol Administrador, ni el Lector ni el Bibliotecario");
				alertError.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Eliminar Rol");
				alert.setContentText("¿Seguro que quiere eliminar " + rolesSeleccionados + " roles seleccionados?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					for (Rol rol : rolTable.getSelectionModel().getSelectedItems()) {
						if (rol.getTipo().equals("Administrador") || rol.getTipo().equals("Lector")
								|| rol.getTipo().equals("Bibliotecario")) {
							Alert alertError = new Alert(AlertType.WARNING);
							alertError.setTitle("¡Atención!");
							alertError.setContentText(
									"No puede eliminar el rol Administrador, ni el Lector ni el Bibliotecario");
							alertError.showAndWait();
						} else {
							try {
								ServiceLocator.getRolService().eliminarRol(rol);
							} catch (ServiceException | RuntimeException e1) {
								Alert alertError = new Alert(AlertType.ERROR);
								alertError.setTitle("Error");
								alertError.setContentText("Ha ocurrido un error al eliminar el Rol:\n" + e1.getMessage()
										+ "\nLos datos no se guardarán en la Base de Datos");
								alertError.showAndWait();
								DataBase.getSession().close();
								e1.printStackTrace();
							}
						}
					}
					rolTable.setItems(main.getRolesData());
				}
			}
		}
	}

}
