package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ServerStarter extends Application {

	private static Stage stage;
	private static FXMLLoader loader;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;

		stage.setTitle("Connect to database");

		loader = new FXMLLoader(getClass().getResource("DBConnection.fxml"));
		//avviamo la prima finestra, dbConnection
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

}
