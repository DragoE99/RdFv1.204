package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 
 * @author gruppo aelv
 *
 */
public class SignUpController {
	

	@FXML private Node pane;
	
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField surname;
	
	@FXML
	private TextField nickname;
	
	@FXML
	private TextField email;
	
	@FXML
	private PasswordField password;

	@FXML
	private PasswordField passwordCheck;
	
	//altri label per messaggi per errori, operazione non riuscita...
	
	/**
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void register(ActionEvent e) throws IOException {
			
			if (!password.getText().equals(passwordCheck.getText())) {
				//messaggio di errore, bisogna creare una label o simile VERONIKA
			} else {
				//TODO
				//errori se mail, nickame esistono gia',...
			}
//TODO far inviare codice verifica	
			//rimanda al codice conferma quando hai finito con successo
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ActivationCode.fxml"))));
		}
}
