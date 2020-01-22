/**
 * 
 */
package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * @author gruppo aelv
 *
 */
public class ResetPasswordController {

	
	@FXML private TextField email;
	
	
	//dovremmo forse creare delle spiegazioni, es. "ti manderemo una mail con una nuova password"
	
	
	/**
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void enter(ActionEvent e) throws IOException, ClassNotFoundException {
		
	//	Commands reply = Client.getProxy().resetPwd(email.getText());
		
	/*	if(reply == Commands.OK) {

			//rimanda al login quando hai finito con successo
			Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
		} else if (reply == Commands.NO){
			//TODO
			System.err.println("Utente non esiste");
		}*/
		
	}
	
}
