package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class SettingsController {
	
	@FXML private Button okName;
	@FXML private Button okSurname;
	@FXML private Button okNickname;
	@FXML private Button okEmail;
	
	@FXML private TextField name;
	@FXML private TextField surname;
	@FXML private TextField nickname;
	@FXML private TextField email;
	
	@FXML private ImageView back;
	
	
//display textfiel and okButton
	public void changeName(ActionEvent e) throws IOException {
		okName.setVisible(true);
		name.setVisible(true);			
	}
	
	public void changeSurname(ActionEvent e) throws IOException {
		okSurname.setVisible(true);
		surname.setVisible(true);	
	}
	
	public void changeNickname(ActionEvent e) throws IOException {
		okNickname.setVisible(true);
		nickname.setVisible(true);
	}
	
	public void changeEmail(ActionEvent e) throws IOException {
		okEmail.setVisible(true);
		email.setVisible(true);
	}
	
//set invisible textField and OkButton after Ok pressed
	
	public void okName(ActionEvent e) throws IOException {
		okName.setVisible(false);
		name.setVisible(false);
	}
	public void okSurname(ActionEvent e) throws IOException {
		okSurname.setVisible(false);
		surname.setVisible(false);
	}
	public void okNickname(ActionEvent e) throws IOException {
		okNickname.setVisible(false);
		nickname.setVisible(false);
	}
	
	public void okEmail(ActionEvent e) throws IOException {
		okEmail.setVisible(false);
		email.setVisible(false);
	}
	
	public void changePassword(ActionEvent e) throws IOException {
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ChangePassword.fxml"))));
	}
	
public void back(MouseEvent e) throws IOException {
		
		if (12 < e.getX() && e.getX()< 76 && 15 < e.getY() && e.getY()< 64) {
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
		}
	}

	
}