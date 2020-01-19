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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import playerRdF.Client;
import util.Commands;
import util.Player;
import util.User;

/**
 * The controller for sign up window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class SignUpController implements Initializable{

	@FXML private ImageView back;
	@FXML private TextField name;
	@FXML private TextField surname;
	@FXML private TextField nickname;
	@FXML private TextField email;
	@FXML private PasswordField password;
	@FXML private PasswordField passwordCheck;
	@FXML private Label errorNick;
	@FXML private Label errorEmail;
	@FXML private Label errorPsw;
	@FXML private Label title;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	//altri label per messaggi per errori, operazione non riuscita...

	/**
	 * Registers the new user. If successful sends to activation code sceen. Controls if new password and check password are equal, if nick or email already exists. 
	 * @param e Action on "Register" button.
	 * @throws IOException .
	 * @throws ClassNotFoundException .
	 */
	public void register(ActionEvent e) throws IOException, ClassNotFoundException {
		//psw sbagliata
		if (!password.getText().equals(passwordCheck.getText())) {
			//rimuovi vecchi errori
			errorNick.setVisible(false);
			nickname.setBorder(null);
			errorEmail.setVisible(false);
			email.setBorder(null);
			//error print
			errorPsw.setVisible(true);
			password.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			passwordCheck.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("Password non sono identiche");

		} else {

			User u = (User)new Player(name.getText(), surname.getText(), email.getText(), nickname.getText(), password.getText());
			Client.setUser(u);

			Commands reply = Client.getProxy().signup(u);

			if(reply == Commands.OK) {
				//rimanda al login quando hai finito con successo? NO
				//manda alla schermata di attivazione account(?)
				//Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ActivationCode.fxml"))));

			} else if(reply == Commands.EXISTMAIL){
				//rimuovi vecchi errori
				errorPsw.setVisible(false);
				password.setBorder(null);
				passwordCheck.setBorder(null);
				//
				errorNick.setVisible(false);
				nickname.setBorder(null);
				//error print
				errorEmail.setVisible(true);
				email.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));

			} else if(reply == Commands.EXISTNICK) {
				//rimuovi vecchi errori
				errorPsw.setVisible(false);
				password.setBorder(null);
				passwordCheck.setBorder(null);
				//
				errorEmail.setVisible(false);
				email.setBorder(null);
				//error print
				errorNick.setVisible(true);
				nickname.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			}
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
