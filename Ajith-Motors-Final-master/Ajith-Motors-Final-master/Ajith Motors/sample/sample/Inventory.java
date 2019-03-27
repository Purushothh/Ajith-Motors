package sample;

import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import javax.swing.event.TreeModelEvent;
import java.io.IOException;
import java.sql.SQLException;

public class Inventory extends CustomMenuBarAdmin {

    @FXML
    private AnchorPane viewInventory;

    @FXML
    private AnchorPane addInventory;

    @FXML
    void initialize(){
        try {
            viewInventory.getChildren().add(FXMLLoader.load(getClass().getResource("ViewInventory.fxml")));
            addInventory.getChildren().add(FXMLLoader.load(getClass().getResource("AddInventory.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
