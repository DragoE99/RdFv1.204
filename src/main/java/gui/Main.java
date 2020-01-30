package gui;

import adminRdF.AdminClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import playerRdF.Client;
import playerRdF.Proxy;

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
	private static boolean isAdmin;

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
	
	@Override
	public void stop() throws Exception {
		super.stop();
		Client.getProxy().removeMe();
		Client.getProxy().setRunning(false);
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

	public static void setIsAdmin(boolean userType) {
		Main.isAdmin = userType;
	}

	public static boolean getIsAdmin() {
		return isAdmin;
	}

	/**
	 * Setter
	 * @param stage
	 */
	/*public void setStage(Stage stage) {
		this.stage = stage;
	}*/

}
