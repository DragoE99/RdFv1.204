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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import playerRdF.Client;
import util.Commands;

/**
 * The controller for activation code window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ActivationCodeController implements Initializable{ 

	@FXML private TextField code;
	@FXML private Label errLabel;
	@FXML private Label title;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Utility method that activate account.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	private void confirm() throws IOException, ClassNotFoundException {
		Commands reply = Client.getProxy().activationCode(Client.getUser(), code.getText());
		//successful
		if(reply == Commands.OK) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));			

		} else if(reply == Commands.NO) {
			errLabel.setVisible(true);
			code.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("Codice errato");
		}
	}
	/**
	 * Controls if the inserted activation code is right, if is wrong displays an error message, if is right sends to Login screen.
	 * @param e Action on button "Confirm".
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void confirm(ActionEvent e) throws IOException, ClassNotFoundException {
		confirm();
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws ClassNotFoundException .
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException, ClassNotFoundException {
		if(e.getCode().toString().equals("ENTER")) {
			confirm();
		}
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SignUp.fxml"))));
		}
	}

	/**
	 * Sends a new mail with a new activation code.
	 * @param e Action on "send again" hypertext.
	 * @throws IOException .
	 */
	public void sendAgain(MouseEvent e) throws IOException {
		//TODO invia mail di nuovo
		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
		}
	}
}
