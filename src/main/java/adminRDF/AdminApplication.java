package adminRDF;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("GameGui.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
        root.getStylesheets().add("/resources/PrimaryTheme.css");

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }
}
