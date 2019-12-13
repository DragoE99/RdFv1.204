/**
 * 
 */
package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;

/**
 * @author gruppo aelv
 *
 */
public class ResetPasswordController {

	@FXML private Node pane;
	
	
	//dovremmo forse creare delle spiegazioni, es. "ti manderemo una mail con una nuova password"
	
	
	/**
	 * 
	 * @param e
	 * @throws IOException
	 */
	public void enter(ActionEvent e) throws IOException {
		
		//TODO
		//controlla se esiste un account, manda la mail,...
		
		//rimanda al login quando hai finito con successo
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
	}
	
}
