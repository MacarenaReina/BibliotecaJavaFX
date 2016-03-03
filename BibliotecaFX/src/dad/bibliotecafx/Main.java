package dad.bibliotecafx;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
//		BorderPane root = new BorderPane();
//		Scene scene = new Scene(root, 400, 400);
//		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		primaryStage.setScene(scene);
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("BibliotecaFX - Macarena y Joyce");
		this.primaryStage.setWidth(320);
		this.primaryStage.setHeight(200);
		try {
			showBibliotecaScene();
		} catch (IOException e) {
			//TODO: Mostrar mensaje, no ha sido posible iniciar la aplicación...
			e.printStackTrace();
		}
	}
	
	private void showBibliotecaScene() throws IOException{
		URL url = getClass().getResource("/dad/bibliotecafx/docs/Biblioteca.fxml");
		FXMLLoader loader = new FXMLLoader(url);
		Scene scene = new Scene(loader.load());
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
