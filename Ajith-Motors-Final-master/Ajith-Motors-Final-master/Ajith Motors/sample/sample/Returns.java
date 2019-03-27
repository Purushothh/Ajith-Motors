package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Returns extends CustomMenuBarAdmin{

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ComboBox returntype;

    final ObservableList<String> returnTypes = FXCollections.observableArrayList("Sales Returns","Purchases Returns");


    @FXML
    void initialize(){
        page="cashier";
        returntype.setValue(returnTypes.get(0));
        try {
            anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("SalesReturns.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        returntype.setItems(returnTypes);
        returntype.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if(returntype.getValue().equals(returnTypes.get(0))){
                try {
                    try {
                        anchorPane.getChildren().remove(0);
                    } catch (IndexOutOfBoundsException e){

                    }
                    anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("SalesReturns.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else if(returntype.getValue().equals(returnTypes.get(1))){
                try {
                    try {
                        anchorPane.getChildren().remove(0);
                    }catch (IndexOutOfBoundsException e){

                    }
                    anchorPane.getChildren().add(FXMLLoader.load(getClass().getResource("PurchaseReturns.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
