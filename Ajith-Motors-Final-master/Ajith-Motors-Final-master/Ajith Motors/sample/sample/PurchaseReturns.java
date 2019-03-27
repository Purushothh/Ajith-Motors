package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PurchaseReturns extends Connector{

    @FXML
    private TextField txtinvoiceno;

    @FXML
    private Button btnSearch;

    @FXML
    void initialize(){
        Image imageSearch = new Image(getClass().getResourceAsStream("search.png"));
        btnSearch.setGraphic(new ImageView(imageSearch));
    }



}
