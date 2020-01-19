package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import playerRdF.Client;
import util.User;

/**
 * The controller for settings window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class SettingsController implements Initializable{

	@FXML private Button okName;
	@FXML private Button okSurname;
	@FXML private Button okNickname;
	@FXML private Button okEmail;

	@FXML private TextField name;
	@FXML private TextField surname;
	@FXML private TextField nickname;
	@FXML private TextField email;

	@FXML private ImageView back;
	@FXML private Label title;

	private static User temp = Client.getUser();

	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	//display textfield and okButton
	/**
	 * Displays textfield and okButton for change name.
	 * @param e Action on "Change name" button.
	 * @throws IOException .
	 */
	public void changeName(ActionEvent e) throws IOException {
		okName.setVisible(true);
		name.setVisible(true);			
	}

	/**
	 * Displays textfield and okButton for change surname.
	 * @param e Action on "Change surname" button.
	 * @throws IOException .
	 */
	public void changeSurname(ActionEvent e) throws IOException {
		okSurname.setVisible(true);
		surname.setVisible(true);	
	}

	/**
	 * Displays textfield and okButton for change nickname.
	 * @param e Action on "Change nickname" button.
	 * @throws IOException .
	 */
	public void changeNickname(ActionEvent e) throws IOException {
		okNickname.setVisible(true);
		nickname.setVisible(true);
	}

	/**
	 * Displays textfield and okButton for change email.
	 * @param e Action on "Change email" button.
	 * @throws IOException .
	 */
	public void changeEmail(ActionEvent e) throws IOException {
		okEmail.setVisible(true);
		email.setVisible(true);
	}

	//set invisible textField and OkButton after Ok pressed
	//TODO rendere invisibili i textfield se sono vuoti, se sono pieni lasciarli visualizzati cosi' si vedono modifiche

	/**
	 * Sets invisible ok button near name.
	 * @param e Action on "ok" near name button.
	 * @throws IOException .
	 */
	public void okName(ActionEvent e) throws IOException {
		okName.setVisible(false);
		//name.setVisible(false);
		temp.setName(name.getText());
	}

	/**
	 * Sets invisible ok button near surname.
	 * @param e Action on "ok" near surname button.
	 * @throws IOException .
	 */
	public void okSurname(ActionEvent e) throws IOException {
		okSurname.setVisible(false);
		//surname.setVisible(false);
		temp.setSurname(surname.getText());
	}

	/**
	 * Sets invisible ok button near nickname.
	 * @param e Action on "ok" near nickname button.
	 * @throws IOException .
	 */
	public void okNickname(ActionEvent e) throws IOException {
		okNickname.setVisible(false);
		//nickname.setVisible(false);
		temp.setNickname(nickname.getText());
	}

	/**
	 * Sets invisible ok button near email.
	 * @param e Action on "ok" near email button.
	 * @throws IOException .
	 */
	public void okEmail(ActionEvent e) throws IOException {
		okEmail.setVisible(false);
		//email.setVisible(false);
		temp.setEmail(email.getText());
	}

	/**
	 * Sends to change password.
	 * @param e Action on "Change password" button.
	 * @throws IOException .
	 */
	public void changePassword(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ChangePassword.fxml"))));
	}

	/**
	 * Modifies only user's data that had corresponding text field filled out.
	 * @param e Action on "Apply" button.
	 * @throws IOException .
	 */
	public void apply(ActionEvent e) throws IOException {
		//TODO creare un user con le nuove caratteristiche, ma solo quelle che ha modificato
		System.out.println(Client.getUser().getId());
		System.out.println(temp.getEmail() + " " + temp.getName() + temp.getId());

		//invio il nuovo utente al proxy
		Client.getProxy().updateUser(temp);

		//TODO eventuale conferma con il solito sistema Commands.OK

		//modifico l'utente della sessione attuale
		Client.setUser(temp);	

		//System.out.println(Client.getUser().getEmail() + " " + Client.getUser().getName());

		//torna al menu principale
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));	
	}

	/**
	 * Goes to the previous window.
	 * @param e Action on back icon.
	 * @throws IOException .
	 */
	public void back(MouseEvent e) throws IOException {

		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
		}
	}

}
