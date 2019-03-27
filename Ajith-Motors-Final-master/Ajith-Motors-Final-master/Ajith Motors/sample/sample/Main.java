package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Sign In.fxml"));
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(new Scene(root, 380, 210));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
