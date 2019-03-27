package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class Settings extends CustomMenuBarAdmin{

    @FXML
    private TreeView<String> treeView;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private SplitPane splitPane;

    @FXML
    void initialize(){
        try {
            AnchorPane anchorPane=(AnchorPane)FXMLLoader.load(getClass().getResource("GeneralSettings.fxml"));
            splitPane.getItems().set(1,anchorPane);
            splitPane.setDividerPosition(0,0.2265);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObservableList<String> observableList= FXCollections.observableArrayList("General","Users","Advanced");
        TreeItem<String> rootItem=new TreeItem<>("Settings");
        rootItem.setExpanded(true);
        TreeItem<String> general=new TreeItem<>("General");
        TreeItem<String> users=new TreeItem<>("Users");
        TreeItem<String> addUsers=new TreeItem<>("Add Users");
        TreeItem<String> updateUsers=new TreeItem<>("Edit Users");
        TreeItem<String> advanced=new TreeItem<>("Advanced");
        rootItem.getChildren().add(0,general);
        rootItem.getChildren().add(1,users);
        users.getChildren().add(0,addUsers);
        users.getChildren().add(1,updateUsers);
        //rootItem.getChildren().add(2,advanced);
        treeView.setRoot(rootItem);
        treeView.setShowRoot(false);
        treeView.setOnMouseClicked((event -> {
            try {
                if (treeView.getSelectionModel().getSelectedItem().getValue().equals("General")) {
                    try {
                        AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("GeneralSettings.fxml"));
                        splitPane.getItems().set(1, anchorPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Add Users")) {
                    try {
                        AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("Add Users.fxml"));
                        splitPane.getItems().set(1, anchorPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Edit Users")) {
                    try {
                        AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("EditUsers.fxml"));
                        splitPane.getItems().set(1, anchorPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (treeView.getSelectionModel().getSelectedItem().getValue().equals("Advanced")) {
                    try {
                        AnchorPane anchorPane = (AnchorPane) FXMLLoader.load(getClass().getResource("AdvancedSettings.fxml"));
                        splitPane.getItems().set(1, anchorPane);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (NullPointerException e){

            }
        }));
    }

}