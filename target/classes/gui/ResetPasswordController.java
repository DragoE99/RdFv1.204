package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.Commands;

/**
 * The controller for reset password window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ResetPasswordController implements Initializable{

	@FXML private TextField email;
	@FXML private ImageView back;
	@FXML private Label title;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Resets password and sends an email with the new generated one, than if successful sends to login screen.
	 * @param e Action on "Send" button.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void enter(ActionEvent e) throws IOException, ClassNotFoundException {

		Commands reply = Client.getProxy().resetPwd(email.getText());

		if(reply == Commands.OK) {
			//rimanda al login quando hai finito con successo
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));

		} else if (reply == Commands.NO){
			//TODO
			System.err.println("Utente non esiste");
		}
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
		}
	}

}
