package gui;

import adminRdF.AdminClient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import playerRdF.Client;
import serverRdF.DataBaseConnection;
import util.Admin;
import util.Commands;
import util.Player;
import util.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FirstAdminController {

    @FXML
    private ImageView back;
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

    private static DataBaseConnection dataBaseConnection;

    public static void setDataBaseConnection(DataBaseConnection dataBaseConnection) {
        FirstAdminController.dataBaseConnection = dataBaseConnection;
    }

    /**
     * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
     */


    public void initialize() {
        title.setFocusTraversable(true);
    }

    //altri label per messaggi per errori, operazione non riuscita...

    /**
     * Utility method that registers user.
     * @throws IOException .
     * @throws ClassNotFoundException .
     */
    private void register() throws IOException, ClassNotFoundException {
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

            User u = (User)new Admin(name.getText(), surname.getText(), email.getText(), nickname.getText(), password.getText());
            if(dataBaseConnection.checkMailExistence(u.getEmail())){
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

            } else if(dataBaseConnection.checkNicknameExistence(u.getNickname())) {
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
            }else {
                dataBaseConnection.insertUser(u);
                Platform.exit();
                return;

                //rimanda al login quando hai finito con successo? NO
                //manda alla schermata di attivazione account(?)
                //Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
                //Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ActivationCode.fxml"))));

            }
        }
    }
    /**
     * Registers the new user. If successful sends to activation code sceen. Controls if new password and check password are equal, if nick or email already exists.
     * @param e Action on "Register" button.
     * @throws IOException .
     * @throws ClassNotFoundException .
     */
    public void register(ActionEvent e) throws IOException, ClassNotFoundException {
        register();
    }

    /**
     * Allows to press confirm button by pressing ENTER.
     * @param e the pressed key.
     * @throws ClassNotFoundException .
     * @throws IOException .
     */
    public void buttonPressed(KeyEvent e) throws IOException, ClassNotFoundException {
        if(e.getCode().toString().equals("ENTER")) {
            register();
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
