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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import playerRdF.Client;
import util.User;

/**
 * The controller for changePassword window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ChangePasswordController implements Initializable{

	@FXML private ImageView back;
	@FXML private PasswordField oldPwd;
	@FXML private PasswordField newPwd;
	@FXML private PasswordField confNewPwd;
	@FXML private Label errOldPwd;
	@FXML private Label errNewPwd;
	@FXML private Label errConfNewPwd;
	@FXML private Label title;

	private static User temp = Client.getUser();

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Utility method that changes password.
	 * @throws IOException .
	 */
	private void confirm() throws IOException {
		//se la password vecchia e' sbagliata
		if(!oldPwd.getText().equals(Client.getUser().getPassword())) {
			errOldPwd.setVisible(true);
			oldPwd.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("OldPassword errata");
		} else 

			//se la nuova password non e' la stessa nei due casi
			if(!newPwd.getText().equals(confNewPwd.getText())) {
				//togli gli errori per la vecchia psw
				errOldPwd.setVisible(false);
				oldPwd.setBorder(null);
				//evidenzia in rosso campo newPsw + messaggio errore
				errNewPwd.setVisible(true);
				newPwd.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				//evidenzia in rosso campo confNewPsw + messaggio errore
				errConfNewPwd.setVisible(true);
				confNewPwd.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				System.err.println("NewPassword non e' uguale");
			} else {

				temp.setPassword(confNewPwd.getText());

				//mando l'utente con la nuova pwd al server
				Client.getProxy().updateUser(temp);

				Client.setUser(temp);

				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));

			}	
	}
	/**
	 * Changes password. It controls if the old password is right, if the new password is different from the old one and if the new password is equal to confirm password , if so proceeds and sets the new one.
	 * @param e action on button "Confirm".
	 * @throws IOException .
	 */
	public void confirm(ActionEvent e) throws IOException {
		confirm();
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
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
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Settings.fxml"))));
		}
	}

}
