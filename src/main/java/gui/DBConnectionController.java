package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import adminRdF.AdminClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import serverRdF.DataBaseConnection;
import serverRdF.ServerListener;

/**
 * The controller for DBConnection window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class DBConnectionController implements Initializable{

	@FXML private Label title;
	@FXML private Label errorLabel;
	
	@FXML private TextField ip;
	@FXML private TextField port;
	@FXML private TextField name;
	@FXML private TextField user;
	@FXML private TextField pwd;
	@FXML private TextField email;
	@FXML private TextField emailPwd;

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Utility method that starts DB.
	 * @throws IOException .
	 */
	private void login() throws IOException {
		//TODO fare il login parte admin
		DataBaseConnection DB = new DataBaseConnection(ip.getText(),port.getText(), name.getText(), user.getText(), pwd.getText());
		ServerListener.setDB(DB);
		ServerListener.setMailAndPassword(email.getText(), emailPwd.getText());
		if(!DB.testConnection()){
			//SEGNALA ERRORE
			errorLabel.setText("Wrong ip or port or DBname or user or password");
			ip.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			port.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			name.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			pwd.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
		}else if(!ServerListener.testMail()){
			//SEGNALA ERRORE
			errorLabel.setText("Wrong email or email password");
			//resetta i contorni di prima
			ip.setBorder(null);
			port.setBorder(null);
			name.setBorder(null);
			user.setBorder(null);
			pwd.setBorder(null);
			//nuovi errori
			email.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			emailPwd.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));

		}else if(ServerListener.AdminPresent()) {
			Platform.exit();
		} else {
			FirstAdminController.setDataBaseConnection(DB);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FirstAdmin.fxml"));
			ServerStarter.getStage().setScene(new Scene(loader.load()));
		}


	}
	/**
	 * Checks
	 * @param e Action on "Login" button.
	 * @throws IOException .
	 */
	public void login(ActionEvent e) throws IOException {
		login();
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			login();
		}
	}

}
