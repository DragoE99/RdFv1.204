package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Our main javafx class
 * 
 * @author gruppo aelv
 *
 */
public class Main extends Application {

	private static Stage stage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		this.stage = primaryStage;
		
		stage.setTitle("Ruota della Fortuna");
		
		//avviamo la prima finestra, Login
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

		stage.setScene(new Scene(root));

		//stage.setResizable(false);
		stage.requestFocus();
		stage.show();

	}
	
	/**
	 * Getter
	 * @return stage
	 */
	public static Stage getStage() {
		return stage;
	}

	/**
	 * Setter
	 * @param stage
	 */
	/*public void setStage(Stage stage) {
		this.stage = stage;
	}*/

}
