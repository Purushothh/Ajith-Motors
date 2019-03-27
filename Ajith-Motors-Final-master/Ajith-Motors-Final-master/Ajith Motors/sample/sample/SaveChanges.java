package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class SaveChanges extends Connector {

    public static String settingsTab;

    @FXML
    public Button btnApply;

    @FXML
    public Button btnCancel;

    @FXML
    public Button btnOK;

    @FXML
    void initialize(){
        btnOK.setDisable(true);
        btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applyChanges();
            }
        });

        btnOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    overviewPage(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    overviewPage(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void applyChanges(){
        if(settingsTab.equals("General")){
            GeneralSettings generalSettings=new GeneralSettings();
            generalSettings.applyChanges();
            System.out.println("Updated");
            btnOK.setDisable(false);
            btnCancel.setDisable(true);
        }else{
            btnApply.setVisible(false);
        }
    }

    void overviewPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader overviewPageLoader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
        Parent overviewPane = overviewPageLoader.load();
        Scene overviewScene = new Scene(overviewPane, 1000, 600);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(overviewScene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

}
