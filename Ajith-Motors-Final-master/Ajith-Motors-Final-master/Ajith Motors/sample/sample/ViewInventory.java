package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.converter.DoubleStringConverter;

import java.sql.SQLException;
import java.util.Optional;

public class ViewInventory extends CustomMenuBarAdmin{

    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product,String> icode;

    @FXML
    private TableColumn<Product,String> desc;

    @FXML
    private TableColumn<Product,String> qty;

    @FXML
    private TableColumn<Product,Double> buyprice;

    @FXML
    private TableColumn<Product,Double> barprice;

    @FXML
    private TableColumn<Product,Double> sellprice;

    @FXML
    private ComboBox categoryType;

    @FXML
    private TextField searchbar;

    @FXML
    private Label totalInventory;

    final ObservableList<String> category = FXCollections.observableArrayList("All Items","Item Based","Length Based");

    private ObservableList<Product> productsList= FXCollections.observableArrayList();

    @FXML
    void initialize(){
        page="inventory";
        productTable.setEditable(true);
        qty.setEditable(true);
        buyprice.setEditable(true);
        barprice.setEditable(true);
        sellprice.setEditable(true);
        try {
            FilteredList<Product> productList = new FilteredList(displayProductsTable(), p -> true);
            productTable.setItems(productList);
            totalInventory.setText(String.format("%.2f",calculateTotal(productList)));
            searchbar.setOnKeyReleased(keyEvent -> {
                productList.setPredicate(p -> p.getDescription().toLowerCase().contains(searchbar.getText().toLowerCase().trim()));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        icode.setCellValueFactory(new PropertyValueFactory<Product,String>("item_code"));
        desc.setCellValueFactory(new PropertyValueFactory<Product,String>("description"));
        qty.setCellValueFactory(new PropertyValueFactory<Product,String>("quantity_available"));
        qty.setCellFactory(TextFieldTableCell.forTableColumn());
        qty.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, String> event) {
                try {
                    Double.valueOf(event.getNewValue());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the quantity from "+Double.valueOf(event.getOldValue())+" to "+Double.valueOf(event.getNewValue()), ButtonType.YES,ButtonType.NO);
                    alert.setTitle("Change Quantity");
                    alert.setHeaderText("");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.YES) {
                        Product productSelect = productTable.getSelectionModel().getSelectedItem();
                        productSelect.setQuantity_available(event.getNewValue());
                        try {
                            updateProductTable(productSelect.getItem_code(), "quantity", Double.valueOf(event.getNewValue()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Product productSelect = productTable.getSelectionModel().getSelectedItem();
                        productSelect.setQuantity_available(event.getOldValue());
                        try {
                            updateProductTable(productSelect.getItem_code(), "quantity", Double.valueOf(event.getOldValue()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    productTable.refresh();
                    totalInventory.setText(String.format("%.2f", calculateTotal(productTable.getItems())));
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity.", ButtonType.OK);
                    alert.setTitle("Invalid Quantity");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    productSelect.setQuantity_available(event.getOldValue());
                }
            }
        });
        buyprice.setCellValueFactory(new PropertyValueFactory<Product,Double>("buying_price"));
        buyprice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
        buyprice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Double> event) {
                try {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the cost from "+Double.valueOf(event.getOldValue())+" to "+Double.valueOf(event.getNewValue()), ButtonType.YES,ButtonType.NO);
                    alert.setTitle("Change Cost");
                    alert.setHeaderText("");
                    Optional<ButtonType> result = alert.showAndWait();
                    if(result.get()==ButtonType.YES) {
                        Product productSelect = productTable.getSelectionModel().getSelectedItem();
                        productSelect.setBuying_price(event.getNewValue());
                        try {
                            updateProductTable(productSelect.getItem_code(), "buying_price", Double.valueOf(event.getNewValue()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Product productSelect = productTable.getSelectionModel().getSelectedItem();
                        productSelect.setBuying_price(event.getOldValue());
                        try {
                            updateProductTable(productSelect.getItem_code(), "buying_price", Double.valueOf(event.getOldValue()));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    totalInventory.setText(String.format("%.2f", calculateTotal(productTable.getItems())));
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid cost.", ButtonType.OK);
                    alert.setTitle("Invalid Cost");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    productSelect.setBuying_price(event.getOldValue());
                }
            }
        });
        barprice.setCellValueFactory(new PropertyValueFactory<Product,Double>("bargain_price"));
        barprice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
        barprice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Double> event) {
                try {
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    if(event.getNewValue()>productSelect.getSelling_price()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry ! Price cannot be increased further. ", ButtonType.OK);
                        alert.setTitle("Invalid Price");
                        alert.setHeaderText("");
                        alert.showAndWait();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the floor price from "+Double.valueOf(event.getOldValue())+" to "+Double.valueOf(event.getNewValue()), ButtonType.YES,ButtonType.NO);
                        alert.setTitle("Change Floor Price");
                        alert.setHeaderText("");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get()==ButtonType.YES) {
                            productSelect.setBargain_price(event.getNewValue());
                            try {
                                updateProductTable(productSelect.getItem_code(), "bargain_price", Double.valueOf(event.getNewValue()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }else{
                            productSelect.setBargain_price(event.getOldValue());
                            try {
                                updateProductTable(productSelect.getItem_code(), "bargain_price", Double.valueOf(event.getOldValue()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid floor price.", ButtonType.OK);
                    alert.setTitle("Invalid Floor Price");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    productSelect.setBargain_price(event.getOldValue());
                }
            }
        });
        /*barprice.setCellFactory(col->{
            return new TableCell<Product, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(String.valueOf(item));
                        setTextFill(Color.BLACK);
                        setStyle("-fx-background-color: yellow;-fx-border-color:#f1f1f1;-fx-border-width:0.5px;");
                    }
                }
            };
        });*/
        sellprice.setCellValueFactory(new PropertyValueFactory<Product,Double>("selling_price"));
        sellprice.setCellFactory(TextFieldTableCell.<Product,Double>forTableColumn(new DoubleStringConverter()));
        sellprice.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Double>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Double> event) {
                try {
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    if(event.getNewValue()<productSelect.getBargain_price()){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry ! Price cannot be reduced further. ", ButtonType.OK);
                        alert.setTitle("Invalid Price");
                        alert.setHeaderText("");
                        alert.showAndWait();
                    }else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the price from "+Double.valueOf(event.getOldValue())+" to "+Double.valueOf(event.getNewValue()), ButtonType.YES,ButtonType.NO);
                        alert.setTitle("Change Price");
                        alert.setHeaderText("");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get()==ButtonType.YES) {
                            productSelect.setSelling_price(event.getNewValue());
                            try {
                                updateProductTable(productSelect.getItem_code(), "selling_price", Double.valueOf(event.getNewValue()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }else{
                            productSelect.setSelling_price(event.getOldValue());
                            try {
                                updateProductTable(productSelect.getItem_code(), "selling_price", Double.valueOf(event.getOldValue()));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
                    alert.setTitle("Invalid Price");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    Product productSelect = productTable.getSelectionModel().getSelectedItem();
                    productSelect.setSelling_price(event.getOldValue());
                }
            }
        });
        categoryType.setValue(category.get(0));
        categoryType.setItems(category);

        //TO BE IMPLEMENTED IF NEEDED (SORT BY CATEGORY)
        categoryType.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) ->{
            if(categoryType.getValue().equals("Item Based")){
                //clearAllFields();
                //unitType.setVisible(false);
                ObservableList<Product> itemProducts= FXCollections.observableArrayList();
                int itemcount=0;
                for(int i=0;i<productsList.size();i++){
                    if(productsList.get(i).getCategory_name().equals("Item Based")){
                        itemProducts.add(itemcount,productsList.get(i));
                        ++itemcount;
                    }
                }
                productTable.setItems(itemProducts);
            }else if(categoryType.getValue().equals("Length Based")){
                //clearAllFields();
                //unitType.setVisible(true);
                ObservableList<Product> lengthProducts= FXCollections.observableArrayList();
                int lengthcount=0;
                for(int i=0;i<productsList.size();i++){
                    if(productsList.get(i).getCategory_name().equals("Length Based")){
                        lengthProducts.add(lengthcount,productsList.get(i));
                        ++lengthcount;
                    }
                }
                productTable.setItems(lengthProducts);
            }else if(categoryType.getValue().equals("All Items")){
                productsList=FXCollections.observableArrayList();
                try {
                    productsList=displayProductsTable();
                    productTable.setItems(productsList);
                }catch (SQLException e) {
                    e.printStackTrace();
                }catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ObservableList<Product> displayProductsTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT p.item_code,p.description,p.quantity,p.buying_price,p.bargain_price,p.selling_price,c.category_name " +
                "FROM products p,category c " +
                "WHERE p.category_id=c.category_id;";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            Product product=new Product(rs.getString(1),rs.getString(2),String.valueOf(rs.getDouble(3)),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7));
            productsList.add(product);
        }
        con.close();
        return productsList;
    }

    private void updateProductTable(String item_code,String column_name,double value) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String update="UPDATE products SET "+column_name+"="+value+" WHERE item_code='"+item_code+"';";
        ps=con.prepareStatement(update);
        ps.execute();
    }

    private double calculateTotal(ObservableList<Product> productList){
        double total=0;
        for(int i=0;i<productList.size();i++){
            total+=Double.valueOf(productList.get(i).getQuantity_available())*productList.get(i).getBuying_price();
        }
        return total;
    }

}
