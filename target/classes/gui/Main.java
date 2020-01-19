package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main javafx class
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Main extends Application {

	//TODO forse e' meglio cambiare il nome da main a qualcosa tipo starterWindow
	private static Stage stage;
	private static FXMLLoader loader;

	/**
	 * Opens Login screen. Sets window title to "Ruota della Fortuna" and resizable is false.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {

		Main.stage = primaryStage;

		stage.setTitle("Ruota della Fortuna");

		loader = new FXMLLoader(getClass().getResource("Login.fxml"));
		//avviamo la prima finestra, Login
		Parent root = loader.load();

		stage.setScene(new Scene(root));

		stage.setResizable(false);
		stage.requestFocus();
		stage.show();	

	}

	/**
	 * Returns current Stage.
	 * @return stage Returns current Stage.
	 */
	public static Stage getStage() {
		return stage;
	}

	/**
	 * Returns current FXMLLoader.
	 * @return Returns current FXMLLoader.
	 */
	public static FXMLLoader getLoader() {
		return loader;
	}

	/**
	 * Sets the FXMLLoader with the one in parameter.
	 * @param newLoader the new loader you want to set.
	 */
	public static void setLoader(FXMLLoader newLoader) {
		loader = newLoader;
	}

	/**
	 * Setter
	 * @param stage
	 */
	/*public void setStage(Stage stage) {
		this.stage = stage;
	}*/

}
