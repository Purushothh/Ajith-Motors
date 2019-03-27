package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.controlsfx.control.textfield.TextFields;

import java.sql.SQLException;
import java.util.Optional;

import static sample.Connector.jdbcConnect;
import static sample.Connector.stmt;

public class AddInventory extends sample.CustomMenuBarAdmin {

    @FXML
    private ComboBox supplier_name;

    @FXML
    private Label lblAddSupplier;

    @FXML
    private TextField invoice_no;

    @FXML
    private DatePicker date;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private ComboBox category;

    @FXML
    private ComboBox units;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtNetPrice;

    @FXML
    private TextField txtUnitFloorPrice;

    @FXML
    private Label lblTotal;

    @FXML
    private TableView<sample.Product> tablePurchases;

    @FXML
    private TableColumn<sample.Product,String> item_code;

    @FXML
    private TableColumn<sample.Product,String> description;

    @FXML
    private TableColumn<sample.Product,String> quantity;

    @FXML
    private TableColumn<sample.Product,Double> unit_cost;

    @FXML
    private TableColumn<sample.Product,Double> unit_price;

    @FXML
    private TableColumn<sample.Product,Double> floor_price;

    @FXML
    private TableColumn<sample.Product,Double> net_price;

    private ObservableList<sample.Product> updateProduct= FXCollections.observableArrayList();

    private ObservableList<sample.Product> addProduct= FXCollections.observableArrayList();

    private ObservableList<String> category_names=FXCollections.observableArrayList("Select Item Category","Item Based","Length Based");

    private ObservableList<String> unit_names=FXCollections.observableArrayList("Select Item Units","Units","cm","m","ft","kg");

    @FXML
    void initialize(){
        page="inventory";
        category.setItems(category_names);
        category.setValue(category_names.get(0));
        units.setItems(unit_names);
        units.setValue(unit_names.get(0));
        item_code.setCellValueFactory(new PropertyValueFactory<sample.Product,String>("item_code"));
        description.setCellValueFactory(new PropertyValueFactory<sample.Product,String>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<sample.Product,String>("quantity_available"));
        unit_cost.setCellValueFactory(new PropertyValueFactory<sample.Product,Double>("buying_price"));
        unit_price.setCellValueFactory(new PropertyValueFactory<sample.Product,Double>("selling_price"));
        floor_price.setCellValueFactory(new PropertyValueFactory<sample.Product,Double>("bargain_price"));
        net_price.setCellValueFactory(new PropertyValueFactory<sample.Product,Double>("net_price"));
        supplier_name.setEditable(true);
        supplier_name.getEditor().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode kc = ke.getCode();
                if ((kc.isLetterKey())||kc.isArrowKey()||kc.equals(KeyCode.BACK_SPACE)) {
                    try {
                        TextFields.bindAutoCompletion(supplier_name.getEditor(), displaySupplierName());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tablePurchases.setEditable(true);
        quantity.setEditable(true);
        quantity.setCellFactory(TextFieldTableCell.forTableColumn());
        quantity.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<sample.Product, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<sample.Product, String> event) {
                try {
                    Double.valueOf(event.getNewValue());
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the quantity from " + Double.valueOf(event.getOldValue()) + " to " + Double.valueOf(event.getNewValue()), ButtonType.YES, ButtonType.NO);
                    alert.setTitle("Change Quantity");
                    alert.setHeaderText("");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.YES) {
                        sample.Product productSelect = tablePurchases.getSelectionModel().getSelectedItem();
                        productSelect.setQuantity_available(event.getNewValue());
                        productSelect.setNet_price(productSelect.getBuying_price() * Double.valueOf(productSelect.getQuantity_available()));
                    } else {
                        sample.Product productSelect = tablePurchases.getSelectionModel().getSelectedItem();
                        productSelect.setQuantity_available(event.getOldValue());
                        productSelect.setNet_price(productSelect.getBuying_price() * Double.valueOf(productSelect.getQuantity_available()));
                    }
                    lblTotal.setText(String.valueOf(calculateTotal(addProduct)));
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity.", ButtonType.OK);
                    alert.setTitle("Invalid Quantity");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    sample.Product productSelect = tablePurchases.getSelectionModel().getSelectedItem();
                    productSelect.setQuantity_available(event.getOldValue());
                }
            }
        });

            txtItemCode.setOnKeyReleased(keyEvent -> {
                try {
                    FilteredList<sample.Product> productList = new FilteredList(displayProductsTable(txtItemCode.getText()), p -> true);
                    productList.setPredicate(p -> p.getItem_code().toLowerCase().contains(txtItemCode.getText().toLowerCase().trim()));
                    txtDescription.setText(productList.get(0).getDescription());
                    category.setValue(productList.get(0).getCategory_name());
                    txtUnitPrice.setText(String.valueOf(productList.get(0).getBuying_price()));
                    txtNetPrice.setText(String.valueOf(productList.get(0).getSelling_price()));
                    txtUnitFloorPrice.setText(String.valueOf(productList.get(0).getBargain_price()));
                }catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IndexOutOfBoundsException e){
                    clearAllFields(false,false);
                    System.out.println("No such product found"+"in line 'txtItemCode.setOnKeyReleased'");
                }
            });

    }

    private ObservableList<sample.Product> displayProductsTable(String item_code) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        ObservableList<sample.Product> productsList=FXCollections.observableArrayList();
        String select="SELECT p.item_code,p.description,p.quantity,p.buying_price,p.bargain_price,p.selling_price,c.category_name " +
                "FROM products p,category c " +
                "WHERE p.category_id=c.category_id AND p.item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            sample.Product product=new sample.Product(rs.getString(1),rs.getString(2),String.valueOf(rs.getDouble(3)),rs.getDouble(4),rs.getDouble(5),rs.getDouble(6),rs.getString(7));
            productsList.add(product);
        }
        con.close();
        return productsList;
    }

    private ObservableList<String> displaySupplierName() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        ObservableList<String> suppliers=FXCollections.observableArrayList();
        String select="SELECT * FROM supplier";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            String supplier=new String(rs.getString(2));
            suppliers.add(supplier);
        }
        con.close();
        return suppliers;
    }

    private String getSupplierId(String supplier_name) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT supplier_id FROM supplier WHERE supplier_name='"+supplier_name+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String supplier_id="";
        while (rs.next()) {
            supplier_id=new String(rs.getString(1));
        }
        con.close();
        return supplier_id;
    }

    @FXML
    private void addItemsToTable(){
        try{
            sample.Product purchasedItem = new sample.Product(txtItemCode.getText(), txtDescription.getText(), txtQuantity.getText(), Double.valueOf(txtUnitPrice.getText()), Double.valueOf(txtUnitFloorPrice.getText()), Double.valueOf(txtNetPrice.getText()), 0.0,category.getValue().toString());
            addProduct.add(purchasedItem);
            tablePurchases.setItems(addProduct);
            lblTotal.setText(String.valueOf(calculateTotal(addProduct)));
            clearAllFields(true,false);
        } catch (NumberFormatException e){
            System.out.println("Fill all required fields");
        }catch (NullPointerException e){
            System.out.println("Fill all required fields");
        }
    }

    @FXML
    private void updatePurchasesTable(){
        try {
            updateProductsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            //Here a multiple inventory can be added at the same time without entering the supplier name.
            //If required, entering the supplier name can be made compulsory.
            String supplier_id=getSupplierId(supplier_name.getValue().toString());
            jdbcConnect();
            for(int i=0;i<addProduct.size();i++) {
                String item_code = addProduct.get(i).getItem_code();
                double buying_price = addProduct.get(i).getBuying_price();
                double quantity = Double.parseDouble(addProduct.get(i).getQuantity_available());

                String insert = "INSERT INTO purchases VALUES('" + invoice_no.getText() + "','" + date.getValue().toString() + "','" + supplier_id + "','" + item_code + "'," + buying_price + "," + quantity + "," + buying_price*quantity + ");";
                ps = con.prepareStatement(insert);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        clearAllFields(true,true);
    }

    private void updateProductsTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        for(int i=0;i<addProduct.size();i++) {
            String item_code = addProduct.get(i).getItem_code();
            String description = addProduct.get(i).getDescription();
            double buying_price = addProduct.get(i).getBuying_price();
            double bargain_price = addProduct.get(i).getBargain_price();
            double selling_price = addProduct.get(i).getSelling_price();
            int category_id = checkCategory(addProduct.get(i).getCategory_name());
            if(!getCurrentQuantity(item_code).equals("")) {
                double quantity = Double.parseDouble(addProduct.get(i).getQuantity_available()) + Double.valueOf(getCurrentQuantity(item_code));
                String update = "UPDATE products SET description='" + description + "',quantity=" + quantity + ",buying_price=" + buying_price + ",bargain_price=" + bargain_price + ",selling_price=" + selling_price + ", category_id='" + category_id + "' WHERE item_code='" + item_code + "';";
                ps = con.prepareStatement(update);
                ps.execute();
            }else{
                double quantity = Double.parseDouble(addProduct.get(i).getQuantity_available());
                String insert = "INSERT INTO products VALUES('"+item_code+"','" + description + "'," + quantity + "," + buying_price + "," + bargain_price + "," + selling_price + "," + category_id + ");";
                ps = con.prepareStatement(insert);
                ps.execute();
            }
        }
    }

    private String getCurrentQuantity(String item_code) throws SQLException, ClassNotFoundException {
        String qty="";
        jdbcConnect();
        String select="SELECT quantity FROM products WHERE item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            qty=rs.getString(1);
        }
        return qty;
    }

    /*private void addProducts() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String item_code=addProduct.get(0).getItem_code();
        String description=addProduct.get(0).getDescription();
        double quantity= Double.parseDouble(addProduct.get(0).getQuantity_available());
        double buying_price= addProduct.get(0).getBuying_price();
        double bargain_price= addProduct.get(0).getBargain_price();
        double selling_price= addProduct.get(0).getSelling_price();
        int category_id=checkCategory(addProduct.get(0).getCategory_name());
        String insert = "INSERT INTO products VALUES('"+item_code+"','"+description+"',"+quantity+","+buying_price+","+bargain_price+","+selling_price+","+category_id+");";
        ps = con.prepareStatement(insert);
        ps.execute();
        con.close();
    }*/

    private void clearAllFields(boolean clearItemCode,boolean transactionFinished){
        if(clearItemCode==true){
            txtItemCode.setText(null);
        }
        if(transactionFinished==true){
            supplier_name.setValue("");
            invoice_no.setText("");
            date.setValue(null);
            tablePurchases.setItems(null);
        }
        txtDescription.setText(null);
        category.setValue(category_names.get(0));
        units.setValue(unit_names.get(0));
        txtUnitPrice.setText(null);
        txtNetPrice.setText(null);
        txtUnitFloorPrice.setText(null);
        txtQuantity.setText(null);
    }

    private double calculateTotal(ObservableList<sample.Product> list){
        double total=0;
        for(int i=0;i<list.size();i++){
            total+=list.get(i).getNet_price();
        }
        return total;
    }

    private int checkCategory(String category_name){
        int category=-1;
        if(category_name.equals("Item Based")){
            category=1;
        }else if (category_name.equals("Length Based")){
            category=2;
        }
        return category;
    }
}
