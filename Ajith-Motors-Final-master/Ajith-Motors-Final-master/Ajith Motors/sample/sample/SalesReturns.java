package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class SalesReturns extends Connector{

    /*@FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product,String> item_code;

    @FXML
    private TableColumn<Product,String> description;*/

    @FXML
    private TableView<Cart> returnTable;

    @FXML
    private TableColumn<Cart,String> returnItemCode;

    @FXML
    private TableColumn<Cart,String> returnDescription;

    @FXML
    private TableColumn<Cart,String> returnQuantity;

    @FXML
    private TableColumn<Cart,Double> returnPrice;

    @FXML
    private TableColumn<Cart,Double> returnNetPrice;

    @FXML
    private TableColumn<Cart,Integer> returnWarranty;

    @FXML
    private TableView<Cart> salesTable;

    @FXML
    private TableColumn<Cart,String> salesItemCode;

    @FXML
    private TableColumn<Cart,String> salesDescription;

    @FXML
    private TableColumn<Cart,String> salesQuantity;

    @FXML
    private TableColumn<Cart,Double> salesPrice;

    @FXML
    private TableColumn<Cart,Double> salesNetPrice;

    @FXML
    private TableColumn<Cart,Integer> salesWarranty;

    @FXML
    private TextField txtinvoiceno;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnAddReturn;

    @FXML
    private Button btnAddSales;

    @FXML
    private Button btnFinalizeReturn;

    @FXML
    private Label lblTotal;

    private ObservableList<Product> observableList=FXCollections.observableArrayList();

    private ObservableList<Cart> observableListCartReturn=FXCollections.observableArrayList();

    private ObservableList<Cart> observableListCartSales=FXCollections.observableArrayList();

    @FXML
    void initialize(){
        Image imageSearch = new Image(getClass().getResourceAsStream("search.png"));
        btnSearch.setGraphic(new ImageView(imageSearch));

        btnFinalizeReturn.setDisable(true);
        observableListCartReturn.addListener(new ListChangeListener<Cart>() {
            @Override
            public void onChanged(Change<? extends Cart> c) {
                while(c.next()){
                    if(c.wasAdded()) {
                        btnFinalizeReturn.setDisable(false);
                    }
                }
            }
        });
        /*item_code.setCellValueFactory(new PropertyValueFactory<Product,String>("item_code"));
        description.setCellValueFactory(new PropertyValueFactory<Product,String>("description"));*/

        returnItemCode.setCellValueFactory(new PropertyValueFactory<Cart,String>("item_code"));
        returnDescription.setCellValueFactory(new PropertyValueFactory<Cart,String>("description"));
        returnQuantity.setCellValueFactory(new PropertyValueFactory<Cart,String>("quantity"));
        returnPrice.setCellValueFactory(new PropertyValueFactory<Cart,Double>("price"));
        returnNetPrice.setCellValueFactory(new PropertyValueFactory<Cart,Double>("netprice"));
//        returnWarranty.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("warranty"));

        returnTable.setEditable(true);
        returnQuantity.setEditable(true);
        /*returnPrice.setEditable(true);
        returnNetPrice.setEditable(true);*/

        returnQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        /*returnPrice.setCellFactory(TextFieldTableCell.<Cart,Double>forTableColumn(new DoubleStringConverter()));
        returnNetPrice.setCellFactory(TextFieldTableCell.<Cart,Double>forTableColumn(new DoubleStringConverter()));*/

        salesItemCode.setCellValueFactory(new PropertyValueFactory<Cart,String>("item_code"));
        salesDescription.setCellValueFactory(new PropertyValueFactory<Cart,String>("description"));
        salesQuantity.setCellValueFactory(new PropertyValueFactory<Cart,String>("quantity"));
        salesPrice.setCellValueFactory(new PropertyValueFactory<Cart,Double>("price"));
        salesNetPrice.setCellValueFactory(new PropertyValueFactory<Cart,Double>("netprice"));
//        returnWarranty.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("warranty"));

        /*salesTable.setEditable(true);
        salesQuantity.setEditable(true);
        salesPrice.setEditable(true);
        salesNetPrice.setEditable(true);

        salesQuantity.setCellFactory(TextFieldTableCell.forTableColumn());
        salesPrice.setCellFactory(TextFieldTableCell.<Cart,Double>forTableColumn(new DoubleStringConverter()));
        salesNetPrice.setCellFactory(TextFieldTableCell.<Cart,Double>forTableColumn(new DoubleStringConverter()));*/

        lblTotal.setText(String.format("Rs. %.2f",0.00));

        btnSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(getSales(txtinvoiceno.getText()).size()!=0) {
                        salesTable.setItems(getSales(txtinvoiceno.getText()));
                    }else{
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid invoice number." , ButtonType.OK);
                        alert.setTitle("Invalid Invoice No.");
                        alert.setHeaderText("");
                        alert.showAndWait();
                        txtinvoiceno.requestFocus();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        salesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnAddReturn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String item_code=salesTable.getSelectionModel().getSelectedItem().getItem_code();
                    String description=salesTable.getSelectionModel().getSelectedItem().getDescription();
                    String quantity=salesTable.getSelectionModel().getSelectedItem().getQuantity();
                    double unit_price=salesTable.getSelectionModel().getSelectedItem().getPrice();
                    double net_price=salesTable.getSelectionModel().getSelectedItem().getNetprice();
                    Cart cart=new Cart(new SimpleStringProperty(item_code),new SimpleStringProperty(description),quantity,new SimpleDoubleProperty(unit_price),new SimpleDoubleProperty(net_price),0);
                    observableListCartReturn.add(cart);
                    returnTable.setItems(observableListCartReturn);
                    lblTotal.setText(String.format("Rs. %.2f",calculateTotal(observableListCartReturn)));
                }
            });

        });
        /*try {
            FilteredList<Product> productList = new FilteredList(displayProductTable(), p -> true);
            tableProducts.setItems(productList);
            txtSearchBar.setOnKeyReleased(keyEvent -> {
                productList.setPredicate(p -> p.getDescription().toLowerCase().contains(txtSearchBar.getText().toLowerCase().trim()));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/

        /*tableProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            btnAddReturn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Cart cart=new Cart(new SimpleStringProperty(tableProducts.getSelectionModel().getSelectedItem().getItem_code()),new SimpleStringProperty(tableProducts.getSelectionModel().getSelectedItem().getDescription()),"0",new SimpleDoubleProperty(0),new SimpleDoubleProperty(0),0);
                    observableListCartReturn.add(cart);
                    returnTable.setItems(observableListCartReturn);
                    lblTotal.setText(String.format("Rs. %.2f",calculateTotal(salesTable.getItems())-calculateTotal(returnTable.getItems())));
                }
            });

            btnAddSales.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Cart cart=new Cart(new SimpleStringProperty(tableProducts.getSelectionModel().getSelectedItem().getItem_code()),new SimpleStringProperty(tableProducts.getSelectionModel().getSelectedItem().getDescription()),"0",new SimpleDoubleProperty(0),new SimpleDoubleProperty(0),0);
                    observableListCartSales.add(cart);
                    salesTable.setItems(observableListCartSales);
                    lblTotal.setText(String.format("Rs. %.2f",calculateTotal(salesTable.getItems())-calculateTotal(returnTable.getItems())));
                }
            });

        });*/
    }

    /*private ObservableList<Product> displayProductTable() throws SQLException, ClassNotFoundException {
        ObservableList<Product> productList= FXCollections.observableArrayList();
        jdbcConnect();
        String select="SELECT item_code,description,quantity,selling_price,bargain_price FROM products ORDER BY item_code";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            Product product=new Product(rs.getString(1),rs.getString(2),String.valueOf(rs.getDouble(3)),rs.getDouble(4),rs.getDouble(5));
            productList.add(product);
        }
        con.close();
        return productList;
    }*/

    @FXML
    private void editReturnQuantity(TableColumn.CellEditEvent<Cart,String> cellEditEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the quantity from "+Double.valueOf(cellEditEvent.getOldValue())+" to "+Double.valueOf(cellEditEvent.getNewValue()), ButtonType.YES,ButtonType.NO);
            alert.setTitle("Change Quantity");
            alert.setHeaderText("");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.YES){
                Cart cartSelect = returnTable.getSelectionModel().getSelectedItem();
                cartSelect.setQuantity(cellEditEvent.getNewValue());
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(returnTable.getItems())));
            }else{
                Cart cartSelect = returnTable.getSelectionModel().getSelectedItem();
                cartSelect.setQuantity(cellEditEvent.getOldValue());
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(returnTable.getItems())));
            }
        }catch (Exception e){
            Cart cartSelect = returnTable.getSelectionModel().getSelectedItem();
            cartSelect.setQuantity("0");
            cartSelect.setNetprice(0);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity.", ButtonType.OK);
            alert.setTitle("Invalid Quantity");
            alert.setHeaderText("");
            alert.showAndWait();

        }
    }

    @FXML
    private void editReturnPrice(TableColumn.CellEditEvent<Cart,Double> cellEditEvent) {
        try {
            Cart cartSelect = returnTable.getSelectionModel().getSelectedItem();
            cartSelect.setPrice(Double.valueOf(cellEditEvent.getNewValue()));
            cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
            lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(returnTable.getItems())));
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("");
            alert.showAndWait();
            Cart cartSelect = returnTable.getSelectionModel().getSelectedItem();
            cartSelect.setPrice(0);
            cartSelect.setNetprice(0);
        }
    }

    private void updateReturnsTable(){
        try {
            jdbcConnect();
            String srcode=getMaxReturnNo();
            String invoice_no=txtinvoiceno.getText();
            for(int i=0;i<returnTable.getItems().size();i++){
                String item_code=returnTable.getItems().get(i).getItem_code();
                double quantity=Double.valueOf(returnTable.getItems().get(i).getQuantity());
                double unit_price=returnTable.getItems().get(i).getPrice();
                double net_price=returnTable.getItems().get(i).getNetprice();
                String insert = "INSERT INTO salesreturn VALUES('"+srcode+"','"+invoice_no+"',now(),'"+item_code+"',"+quantity+","+unit_price+","+net_price+");";
                ps = con.prepareStatement(insert);
                ps.execute();
                updateProductsQuantity(quantity,item_code);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getMaxReturnNo() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT MAX(srcode) FROM salesreturn";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String sr_no="";
        String sr_number="";
        String sr_year="";
        while (rs.next()){
            sr_no=rs.getString(1);
        }
        if(sr_no==null){
            sr_no=getYear()+String.format("%05d",0);
        }
        for(int i=2;i<6;i++){
            sr_year+=sr_no.charAt(i);
        }
        for(int i=6;i<sr_no.length();i++){
            sr_number+=sr_no.charAt(i);
        }
        if(!sr_year.equals(getYear())){
            sr_number=String.format("%05d",0);
        }
        String trans_no= "SR"+getYear()+String.format("%05d",Integer.parseInt(sr_number)+1);
        return trans_no;
    }

    private static void updateProductsQuantity(double quantity, String item_code) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String update="UPDATE products SET quantity=quantity+"+quantity+" WHERE item_code='"+item_code+"';";
        ps=con.prepareStatement(update);
        ps.execute();
    }

    private static String getYear(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String year=dtf.format(now);
        return year;
    }

    @FXML
    void cashierPage(ActionEvent actionEvent) throws IOException {
        updateReturnsTable();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to proceed to sales ? ", ButtonType.YES,ButtonType.NO);
        alert.setTitle("Confirm");
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.YES) {
            FXMLLoader cashierPageLoader = new FXMLLoader(getClass().getResource("Cashier.fxml"));
            Parent cashierPane = cashierPageLoader.load();
            Scene cashierScene = new Scene(cashierPane, 1000, 700);
            Stage primaryStage = new Stage();
            primaryStage.getIcons().add(new Image("sample/images/logo.png"));
            primaryStage.setTitle("Welcome to Ajith Motors");
            primaryStage.setScene(cashierScene);
            primaryStage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            //FIT ACCORDING TO SCREEN SIZE
            //primaryStage.setWidth(primScreenBounds.getWidth());
            //primaryStage.setHeight(primScreenBounds.getHeight());
            primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        }
        returnTable.getScene().getWindow().hide();
    }

    @FXML
    public ObservableList<Cart> getSales(String invoice_no) throws SQLException, ClassNotFoundException {
        ObservableList<Cart> observableListCartSales=FXCollections.observableArrayList();
        jdbcConnect();
        String select="SELECT t.item_code,p.description,t.quantity,(t.net_price/t.quantity),t.net_price " +
                "FROM transactions t, products p " +
                "WHERE t.transaction_id='"+invoice_no+"' AND p.item_code=t.item_code";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while(rs.next()){
            Cart cart=new Cart(new SimpleStringProperty(rs.getString(1)),new SimpleStringProperty(rs.getString(2)),rs.getString(3),new SimpleDoubleProperty(rs.getDouble(4)),new SimpleDoubleProperty(rs.getDouble(5)),0);
            observableListCartSales.add(cart);
        }
        con.close();
        return observableListCartSales;
    }

    private double calculateTotal(ObservableList<Cart> list){
        double total=0;
        for(int i=0;i<list.size();i++){
            total+=list.get(i).getNetprice();
        }
        return total;
    }

}
