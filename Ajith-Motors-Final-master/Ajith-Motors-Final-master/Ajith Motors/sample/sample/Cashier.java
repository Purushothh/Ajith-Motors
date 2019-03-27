package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
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
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class Cashier extends CustomMenuBarAdmin{

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField item_code;

    @FXML
    public TableView<Cart> table;

    public static TableView<Cart> tableCart=new TableView<Cart>();

    @FXML
    public Button btnBill;

    @FXML
    public Button btnAddItem;

    @FXML
    private TableColumn<Cart,String> code;
    @FXML
    private TableColumn<Cart,String> desc;
    @FXML
    private TableColumn<Cart,String> quantity;

    @FXML
    private TableColumn<Cart,Double>price;

    @FXML
    private TableColumn<Cart,Double> netprice;

    @FXML
    private TableColumn<Cart,Integer> warranty;

    private ObservableList<Cart> observableList=FXCollections.observableArrayList();

    private ObservableList<Product> productList=FXCollections.observableArrayList();

    private static ObservableList<Cart> advanceItemsList=FXCollections.observableArrayList();

    @FXML
    private TableView<Product> tableProducts;

    @FXML
    private TableColumn<Product,String> itemcode;

    @FXML
    private TableColumn<Product,String> description;

    @FXML
    private TableColumn<Product,String> quantity_available;

    @FXML
    private TableColumn<Product,Double> bargain_price;

    @FXML
    private TableColumn<Product,Double> selling_price;

    @FXML
    private Label lblTotal;

    @FXML
    private Label salesperson;

    @FXML
    private TextField searchbar;

    @FXML
    private ComboBox customer_title;

    @FXML
    private TextField customer_name;

    @FXML
    private TextField mobile_number;

    @FXML
    private TextField vehicle_number;

    @FXML
    private TextField service_description;

    @FXML
    private TextField service_amount;

    public static String cus_name,mobile,vehicle,transaction_no;

    private int unit_qty;

    private double length_qty;

    final ObservableList<String> title=FXCollections.observableArrayList("Mr.","Mrs.","Ms.");

    private ObservableList<Cart> draftTransactions=FXCollections.observableArrayList();

    private ObservableList<Cart> advanceTransactions=FXCollections.observableArrayList();

    private static ArrayList<String> draftsList=new ArrayList<>();

    private static ArrayList<String> advanceList=new ArrayList<>();

    public static String advance_id;

    private static String ad;

    public static String getAd() {
        return ad;
    }

    @FXML
    void initialize() {
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem cut = new MenuItem("Cut");
        MenuItem copy = new MenuItem("Copy");
        MenuItem paste = new MenuItem("Paste");
        //previous="Cashier";

        /*try {
            tableProducts.setItems(displayProductTable());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        try {
            FilteredList<Product> productList = new FilteredList(displayProductTable(), p -> true);
            tableProducts.setItems(productList);
            searchbar.setOnKeyReleased(keyEvent -> {
                productList.setPredicate(p -> p.getDescription().toLowerCase().contains(searchbar.getText().toLowerCase().trim()));
            });
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        customer_title.setValue(title.get(0));
        customer_title.setItems(title);
        salesperson.setText(Login.uname);
        //item_code,description,quantity,price,netprice are instance variables in Cart class
        code.setCellValueFactory(new PropertyValueFactory<Cart,String>("item_code"));
        desc.setCellValueFactory(new PropertyValueFactory<Cart,String>("description"));
        quantity.setCellValueFactory(new PropertyValueFactory<Cart,String>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<Cart,Double>("price"));
        netprice.setCellValueFactory(new PropertyValueFactory<Cart,Double>("netprice"));
        warranty.setCellValueFactory(new PropertyValueFactory<Cart,Integer>("warranty"));
        //delete.setCellValueFactory(new PropertyValueFactory<Cart,Button>("delete"));
        itemcode.setCellValueFactory(new PropertyValueFactory<Product,String>("item_code"));
        description.setCellValueFactory(new PropertyValueFactory<Product,String>("description"));
        quantity_available.setCellValueFactory(new PropertyValueFactory<Product,String>("quantity_available"));
        bargain_price.setCellValueFactory(new PropertyValueFactory<Product,Double>("bargain_price"));
        selling_price.setCellValueFactory(new PropertyValueFactory<Product,Double>("selling_price"));
        page="cashier";
        table.setEditable(true);
        quantity.setEditable(true);
        price.setEditable(true);
        warranty.setEditable(true);
        quantity.setCellFactory(TextFieldTableCell.forTableColumn());
        price.setCellFactory(TextFieldTableCell.<Cart,Double>forTableColumn(new DoubleStringConverter()));
        warranty.setCellFactory(TextFieldTableCell.<Cart,Integer>forTableColumn(new IntegerStringConverter()));

        tableProducts.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            try {
                item_code.setText(tableProducts.getSelectionModel().getSelectedItem().getItem_code());
            }catch (Exception e){
                System.out.println(e+" cashier.initialize()");
            }

        });
        /*table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

            @Override
            public void handle(ContextMenuEvent event) {

                contextMenu.show(table, event.getScreenX(), event.getScreenY());
            }
        });*/
        /*table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            table.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {

                    contextMenu.show(table, event.getScreenX(), event.getScreenY());
                }
            });
            /*table.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.isSecondaryButtonDown()) {
                        contextMenu.show(pane, event.getScreenX(), event.getScreenY());
                    }
                }
            });*/
        //});

        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                if(t.getButton() == MouseButton.SECONDARY) {
                    contextMenu.show(table, t.getScreenX(), t.getScreenY());
                }
            }
        });
        tableProducts.refresh();
        if(DraftsController.previousScene.equals("Drafts")){
            try {
                observableList=sendToCart(DraftsController.draftno);
                table.setItems(observableList);
                tableCart=table;
                lblTotal.setText(String.valueOf(calculateTotal(observableList)));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            DraftsController.previousScene="";
        }
        if(AdvancePayments.previousScene.equals("Advance")){
            ad=AdvancePayments.advanceno;
            try {
                ArrayList<String> details=getCustomerDetails(ad);
                customer_title.setValue(details.get(0));
                customer_name.setText(details.get(1).trim());
                mobile_number.setText(details.get(2));
                vehicle_number.setText(details.get(3));
                observableList=sendAdvanceToCart(ad);
                table.setItems(observableList);
                tableCart=table;
                lblTotal.setText(String.valueOf(calculateTotal(observableList)));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            AdvancePayments.previousScene="";
            AdvancePayments.advanceno="";
        }
    }

    public ObservableList<Cart> displayCart(String item_code) throws SQLException, ClassNotFoundException {
        length_qty=0;
        unit_qty=0;
        String cate=getCategory(item_code);
        if(cate.equals("Item Based")){
            unit_qty=1;
            for(int i=0;i<observableList.size();i++) {
                tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.valueOf(tableProducts.getItems().get(i).getQuantity_available()).intValue()-1));
                tableProducts.refresh();
                if (!item_code.equals(observableList.get(i).getItem_code())){
                    unit_qty=1;
                    /*tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.valueOf(tableProducts.getItems().get(i).getQuantity_available()).intValue()-unit_qty));
                    tableProducts.refresh();*/
                }else{
                    unit_qty=Double.valueOf(observableList.get(i).getQuantity()).intValue();
                    ++unit_qty;
                    observableList.get(i).setQuantity(String.valueOf(unit_qty));
                    observableList.get(i).setNetprice(observableList.get(i).getPrice()*unit_qty);
                    /*tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.valueOf(tableProducts.getItems().get(i).getQuantity_available()).intValue()-1));
                    tableProducts.refresh();*/
                    break;
                }
            }
        }else{
            length_qty=1;
            for(int i=0;i<observableList.size();i++) {
                tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.parseDouble(tableProducts.getItems().get(i).getQuantity_available())-1));
                tableProducts.refresh();
                if (!item_code.equals(observableList.get(i).getItem_code())){
                    length_qty=1;
                    /*tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.parseDouble(tableProducts.getItems().get(i).getQuantity_available())-length_qty));
                    tableProducts.refresh();*/
                }else{
                    length_qty=Double.valueOf(observableList.get(i).getQuantity());
                    ++length_qty;
                    observableList.get(i).setQuantity(String.valueOf(length_qty));
                    observableList.get(i).setNetprice(observableList.get(i).getPrice()*length_qty);
                    /*tableProducts.getItems().get(i).setQuantity_available(String.valueOf(Double.parseDouble(tableProducts.getItems().get(i).getQuantity_available())-1));
                    tableProducts.refresh();*/
                    break;
                }
            }
        }
        jdbcConnect();
        String selectCate="SELECT * FROM products WHERE item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(selectCate);

        while(rs.next()){
            SimpleStringProperty code=new SimpleStringProperty(rs.getString(1));
            SimpleStringProperty desc=new SimpleStringProperty(rs.getString(2));
            double bargain_price=rs.getDouble(5);
            String category=rs.getString(7);
            SimpleDoubleProperty price=new SimpleDoubleProperty(Double.valueOf(String.format("%.2f",rs.getDouble(6))));
            SimpleDoubleProperty netprice=new SimpleDoubleProperty();
            if(cate.equals("Item Based")){
                netprice=new SimpleDoubleProperty(Double.valueOf(String.format("%.2f",rs.getDouble(6)*unit_qty)));
            }else {
                netprice = new SimpleDoubleProperty(Double.valueOf(String.format("%.2f",rs.getDouble(6)*length_qty)));
            }
            //If the selling price,after setting the price to a lower value, is lesser than the bargaining price, give an error
            if(rs.getDouble(6)<bargain_price){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Sorry ! Product cannot be bargained further. " , ButtonType.OK);
                alert.setTitle("Cannot be bargained further");
                alert.setHeaderText("");
                alert.showAndWait();
            }else{
                /*if((rs.getDouble(3)-length_qty)<=0||(rs.getDouble(3)-unit_qty)<=0){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Product not in stock. Please re - order stock and proceed with the transaction." , ButtonType.OK);
                    alert.setTitle("Out of Stock");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }*/
                if(length_qty==1){
                    Cart cart= new Cart(code,desc,String.valueOf(length_qty),price,netprice,0);
                    observableList.add(cart);
                }else if(unit_qty==1){
                    Cart cart= new Cart(code,desc,String.valueOf(unit_qty),price,netprice,0);
                    observableList.add(cart);
                }
            }
        }

        con.close();
        return observableList;
    }

    @FXML
    public void updateTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT item_code FROM products WHERE item_code='"+item_code.getText()+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        if(rs.next()){
            observableList=displayCart(item_code.getText());
            table.setItems(observableList);
            tableCart=table;
            table.refresh();
            //tableProducts.getSelectionModel().clearSelection();
            lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(observableList)));
            System.out.println("Successfully added");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid product code !\nPlease enter a valid product code. " , ButtonType.OK);
            alert.setTitle("Invalid Product Code");
            alert.setHeaderText("");
            alert.showAndWait();
        }
        con.close();
        item_code.setText("");
        tableProducts.getSelectionModel().clearSelection();
    }

    public void editQuantity(TableColumn.CellEditEvent<Cart,String> cellEditEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the quantity from "+Double.valueOf(cellEditEvent.getOldValue())+" to "+Double.valueOf(cellEditEvent.getNewValue()), ButtonType.YES,ButtonType.NO);
            alert.setTitle("Change Quantity");
            alert.setHeaderText("");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.YES){
                Cart cartSelect = table.getSelectionModel().getSelectedItem();
                cartSelect.setQuantity(cellEditEvent.getNewValue());
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(observableList)));
            }else{
                Cart cartSelect = table.getSelectionModel().getSelectedItem();
                cartSelect.setQuantity(cellEditEvent.getOldValue());
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(observableList)));
            }
        }catch (Exception e){
            Cart cartSelect = table.getSelectionModel().getSelectedItem();
            cartSelect.setQuantity("0");
            cartSelect.setNetprice(0);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid quantity.", ButtonType.OK);
            alert.setTitle("Invalid Quantity");
            alert.setHeaderText("");
            alert.showAndWait();

        }
    }

    public void editWarranty(TableColumn.CellEditEvent<Cart,Integer> cellEditEvent) {
        try {
            Cart cartSelect = table.getSelectionModel().getSelectedItem();
            cartSelect.setWarranty(cellEditEvent.getNewValue());
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid warranty period.", ButtonType.OK);
            alert.setTitle("Invalid Warranty");
            alert.setHeaderText("");
            alert.showAndWait();
            Cart cartSelect = table.getSelectionModel().getSelectedItem();
            cartSelect.setWarranty(0);
        }
    }

    @FXML
    public void editPrice(TableColumn.CellEditEvent<Cart,Double> cellEditEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the price from "+Double.valueOf(cellEditEvent.getOldValue())+" to "+Double.valueOf(cellEditEvent.getNewValue()), ButtonType.YES,ButtonType.NO);
            alert.setTitle("Change Price");
            alert.setHeaderText("");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.YES){
                Cart cartSelect = table.getSelectionModel().getSelectedItem();
                cartSelect.setPrice(Double.valueOf(cellEditEvent.getNewValue()));
                double bargain_price=getBargainPrice(cartSelect.getItem_code());
                if(cartSelect.getPrice()<bargain_price){
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "Sorry ! Product cannot be bargained further. " , ButtonType.OK);
                    alert1.setTitle("Cannot be bargained further");
                    alert1.setHeaderText("");
                    alert1.showAndWait();
                    cartSelect.setPrice(bargain_price);
                }
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(observableList)));
            }else{
                Cart cartSelect = table.getSelectionModel().getSelectedItem();
                cartSelect.setPrice(Double.valueOf(cellEditEvent.getOldValue()));
                cartSelect.setNetprice(cartSelect.getPrice() * Double.valueOf(cartSelect.getQuantity()));
                lblTotal.setText("Rs."+String.format("%.2f",calculateTotal(observableList)));
            }

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("");
            alert.showAndWait();
            Cart cartSelect = table.getSelectionModel().getSelectedItem();
            cartSelect.setPrice(0);
            cartSelect.setNetprice(0);
        }
    }

    private double getBargainPrice(String item_code) throws SQLException, ClassNotFoundException {
        double bargain_price=0;
        jdbcConnect();
        String select="SELECT bargain_price FROM products WHERE item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()){
            bargain_price=rs.getDouble(1);
        }
        con.close();
        return bargain_price;
    }

    private double calculateTotal(ObservableList<Cart> list){
        double total=0;
        for(int i=0;i<list.size();i++){
            total+=list.get(i).getNetprice();
        }
        return total;
    }

    private String getCategory(String item_code) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT c.category_name FROM  products p, category c WHERE c.category_id=p.category_id AND p.item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String cate=new String();
        while (rs.next()){
            cate=rs.getString(1);
        }
        con.close();
        return cate;
    }

    private ObservableList<Product> displayProductTable() throws SQLException, ClassNotFoundException {
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
    }

    @FXML
    public void confirmOrder(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Proceed with the transaction?", ButtonType.OK,ButtonType.CANCEL);
        alert.setTitle("Confirm Order");
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get()==ButtonType.OK){
            try {
                ActionEvent actionEvent = null;
                paymentPage(actionEvent);
                cus_name = customer_title.getSelectionModel().getSelectedItem() + " " + customer_name.getText();
                mobile = mobile_number.getText();
                vehicle = vehicle_number.getText();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void getTotal(){
        String total="Rs."+String.format("%.2f",calculateTotal(table.getItems()));
        lblTotal.setText(total);
    }

    @FXML
    void paymentPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader returnsPageLoader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Parent returnsPane = returnsPageLoader.load();
        Scene returnsScene = new Scene(returnsPane, 720, 480);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(returnsScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public static void updateTransactionTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        int[] transno=getMaxTransNo();
        int trans_id=transno[1]+1;
        int transyear=transno[0];
        for(int i=0;i<tableCart.getItems().size();i++) {
            String item_code = tableCart.getItems().get(i).getItem_code();
            System.out.println(item_code);
            double quantity = Double.valueOf(tableCart.getItems().get(i).getQuantity());
            double net_price = tableCart.getItems().get(i).getNetprice();
            if(transyear!=Integer.valueOf(getYear())){
                trans_id=1;
            }
            String trans_no=String.format(getYear()+"%05d",trans_id);
            transaction_no=trans_no;
            String insert = "INSERT INTO transactions VALUES('"+trans_no+"',now(),'"+item_code+"',"+quantity+","+net_price+");";
            ps = con.prepareStatement(insert);
            ps.execute();
            updateProductsQuantity(getQuantity(item_code)-quantity,item_code);
        }
        con.close();
    }

    private static int[] getMaxTransNo() throws SQLException, ClassNotFoundException {
        int[] transno=new int[2];
        jdbcConnect();
        String select="SELECT MAX(transaction_id) FROM transactions";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String transaction_no="";
        String transaction_number="";
        String transaction_year="";
        while (rs.next()){
            transaction_no=rs.getString(1);
        }
        if(transaction_no==null){
            transaction_no=getYear()+String.format("%05d",0);
        }
        for(int i=0;i<4;i++){
            transaction_year+=transaction_no.charAt(i);
        }
        for(int i=4;i<transaction_no.length();i++){
            transaction_number+=transaction_no.charAt(i);
        }
        int trans_no= Integer.parseInt(transaction_number);
        transno[0]=Integer.valueOf(transaction_year);
        transno[1]=trans_no;
        return transno;
    }

    private static double getQuantity(String item_code) throws SQLException, ClassNotFoundException {
        double quantity=0;
        jdbcConnect();
        String select="SELECT quantity FROM products WHERE item_code='"+item_code+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while(rs.next()){
            quantity=rs.getDouble(1);
        }
        return quantity;
    }

    private static void updateProductsQuantity(double quantity, String item_code) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String update="UPDATE products SET quantity="+quantity+" WHERE item_code='"+item_code+"';";
        ps=con.prepareStatement(update);
        ps.execute();
    }

    @FXML
    private void updateDraftsTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String draft_id="";
        String insert="";
        if(DraftsController.draftno!=""){
            draft_id=DraftsController.draftno;
        }else {
            draft_id = getMaxDraftNo();
        }
        for(int i=0;i<observableList.size();i++) {
            String item_code = observableList.get(i).getItem_code();
            double quantity = Double.valueOf(observableList.get(i).getQuantity());
            double net_price = observableList.get(i).getNetprice();
            if(DraftsController.draftno!=""){
                draft_id=DraftsController.draftno;
                if(isInPreviousList(item_code,draftsList)) {
                    insert = "UPDATE drafts SET draft_id='" + draft_id + "',draft_date=now(),item_code='" + item_code + "',quantity=" + quantity + ",net_price=" + net_price + " WHERE draft_id='" + draft_id + "' AND item_code='" + item_code + "';";
                }else{
                    insert = "INSERT INTO drafts VALUES('"+draft_id+"',now(),'"+item_code+"',"+quantity+","+net_price+");";
                }
            }else {
                System.out.println(draft_id);
                insert = "INSERT INTO drafts VALUES('"+draft_id+"',now(),'"+item_code+"',"+quantity+","+net_price+");";

            }
            System.out.println(insert);
            ps = con.prepareStatement(insert);
            ps.execute();
            //Can add if required
            // updateProductsQuantity(getQuantity(item_code)-quantity,item_code);
        }
        DraftsController.previousScene="";
        DraftsController.draftno="";
        table.setItems(null);
        observableList=FXCollections.observableArrayList();
        lblTotal.setText("Rs. 0.00");
        System.out.println(DraftsController.previousScene);
        con.close();
    }

    public void updateAdvanceTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        advance_id="";
        String insert="";
        System.out.println(ad);
        try {
            if (!ad.equals("")) {
                advance_id = ad;
            }
        }catch (NullPointerException e){
            advance_id=getMaxAdvanceNo();
        }/*else {
            advance_id = getMaxAdvanceNo();
        }*/
        for(int i=0;i<advanceItemsList.size();i++) {
            String item_code = advanceItemsList.get(i).getItem_code();
            double quantity = Double.valueOf(advanceItemsList.get(i).getQuantity());
            double net_price = advanceItemsList.get(i).getNetprice();
            try {
                if (!ad.equals("")) {
                    advance_id = ad;
                    if (isInPreviousList(item_code, advanceList)) {
                        insert = "UPDATE advance_transactions SET advance_no='" + advance_id + "',advance_date=now(),item_code='" + item_code + "',quantity=" + quantity + ",net_price=" + net_price + " WHERE advance_no='" + advance_id + "' AND item_code='" + item_code + "';";
                    } else {
                        insert = "INSERT INTO advance_transactions VALUES('" + advance_id + "',now(),'" + cus_name + "','" + mobile + "','" + vehicle + "','" + item_code + "'," + quantity + "," + net_price + ");";
                    }
                }
            }catch (NullPointerException e){
                insert = "INSERT INTO advance_transactions VALUES('"+advance_id+"',now(),'"+cus_name+"','"+mobile+"','"+vehicle+"','"+item_code+"',"+quantity+","+net_price+");";
            }/*else {
                //System.out.println(advance_id);
                insert = "INSERT INTO advance_transactions VALUES('"+advance_id+"',now(),'"+cus_name+"','"+mobile+"','"+vehicle+"','"+item_code+"',"+quantity+","+net_price+");";

            }*/
            System.out.println(insert);
            ps = con.prepareStatement(insert);
            ps.execute();
            //Can add if required
            // updateProductsQuantity(getQuantity(item_code)-quantity,item_code);
        }
//        table.setItems(null);
        advanceItemsList=FXCollections.observableArrayList();
//        lblTotal.setText("Rs. 0.00");
        con.close();
    }

    private boolean isInPreviousList(String item_code, ArrayList<String> list){
        boolean condition=false;
        for(int i = 0; i< list.size(); i++){
            if(list.get(i).equals(item_code)){
                condition=true;
                break;
            }
        }
        return condition;
    }

    private static String getMaxDraftNo() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT MAX(draft_id) FROM drafts";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String draft_no="";
        String draft_number="";
        while (rs.next()){
            draft_no=rs.getString(1);
        }
        if(draft_no==null){
            draft_no=getYear()+String.format("%05d",0);
        }
        for(int i=6;i<draft_no.length();i++){
            draft_number+=draft_no.charAt(i);
        }
        String trans_no= "DR"+getYear()+String.format("%05d",Integer.parseInt(draft_number)+1);
        return trans_no;
    }

    public static String getMaxAdvanceNo() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT MAX(advance_no) FROM advance_transactions";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String advance_no="";
        String advance_number="";
        String advance_year="";
        while (rs.next()){
            advance_no=rs.getString(1);
        }
        if(advance_no==null){
            advance_no=getYear()+String.format("%05d",0);
        }
        for(int i=2;i<6;i++){
            advance_year+=advance_no.charAt(i);
        }
        for(int i=6;i<advance_no.length();i++){
            advance_number+=advance_no.charAt(i);
        }
        if(!advance_year.equals(getYear())){
            advance_number=String.format("%05d",0);
        }
        String trans_no= "AD"+getYear()+String.format("%05d",Integer.parseInt(advance_number)+1);
        return trans_no;
    }

    @FXML
    private void addServices(){
        if(service_description.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the description." , ButtonType.OK);
            alert.setTitle("Fill The Required Fields");
            alert.setHeaderText("");
            alert.showAndWait();
        }else{
            if (service_amount.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill the amount." , ButtonType.OK);
                alert.setTitle("Fill The Required Fields");
                alert.setHeaderText("");
                alert.showAndWait();
            }else {
                try {
                    if (Integer.parseInt(service_amount.getText()) <= 0) {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid amount.", ButtonType.OK);
                        alert.setTitle("Invalid Amount");
                        alert.setHeaderText("");
                        alert.showAndWait();
                    } else {
                        if (service_description.getText() != null && service_amount.getText() != null) {
                            Cart cart = new Cart(new SimpleStringProperty("SC"), new SimpleStringProperty(service_description.getText()), "1", new SimpleDoubleProperty(Double.valueOf(service_amount.getText())), new SimpleDoubleProperty(Double.valueOf(service_amount.getText())),0);
                            observableList.add(observableList.size(), cart);
                            table.setItems(observableList);
                            lblTotal.setText("Rs." + String.format("%.2f",calculateTotal(observableList)));
                            service_description.setText("");
                            service_amount.setText("");
                        }
                    }
                }catch (NumberFormatException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid amount.", ButtonType.OK);
                    alert.setTitle("Invalid Amount");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }
            }
        }
    }

    private ObservableList<Cart> sendToCart(String draft_id) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT d.item_code,p.description,d.quantity,p.selling_price,d.net_price FROM products p,drafts d WHERE d.item_code=p.item_code AND draft_id='"+draft_id+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        int count=-1;
        while (rs.next()) {
            Cart cart=new Cart(new SimpleStringProperty(rs.getString(1)),new SimpleStringProperty(rs.getString(2)),rs.getString(3),new SimpleDoubleProperty(rs.getDouble(4)),new SimpleDoubleProperty(rs.getDouble(5)),0);
            draftsList.add(++count,rs.getString(1));
            draftTransactions.add(cart);
        }
        con.close();
        return draftTransactions;
    }

    private ObservableList<Cart> sendAdvanceToCart(String advance_no) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT a.item_code,p.description,a.quantity,p.selling_price,a.net_price FROM products p,advance_transactions a WHERE a.item_code=p.item_code AND advance_no='"+advance_no+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        int count=-1;
        while (rs.next()) {
            Cart cart=new Cart(new SimpleStringProperty(rs.getString(1)),new SimpleStringProperty(rs.getString(2)),rs.getString(3),new SimpleDoubleProperty(rs.getDouble(4)),new SimpleDoubleProperty(rs.getDouble(5)),0);
            advanceList.add(++count,rs.getString(1));
            advanceTransactions.add(cart);
        }
        con.close();
        return advanceTransactions;
    }

    private ArrayList<String> getCustomerDetails(String advance_id) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        ArrayList<String> details=new ArrayList<>();
        String select="SELECT DISTINCT customer_name,contact_no,vehicle_no FROM advance_transactions WHERE advance_no='"+advance_id+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        int count=0;
        while (rs.next()) {
            details.add(0,rs.getString(1).split(" ")[0]);
            details.add(1,rs.getString(1).replace(rs.getString(1).split(" ")[0],""));
            details.add(2,rs.getString(2));
            details.add(3,rs.getString(3));
        }
        con.close();
        return details;
    }
    private static String getYear(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String year=dtf.format(now);
        return year;
    }

    /* ADVANCE PAYMENTS */

    @FXML
    void advancePaymentPage(ActionEvent actionEvent) throws IOException {
        advanceItemsList=observableList;
        cus_name=customer_title.getSelectionModel().getSelectedItem()+" "+customer_name.getText();
        mobile=mobile_number.getText();
        vehicle=vehicle_number.getText();
        FXMLLoader returnsPageLoader = new FXMLLoader(getClass().getResource("AdvanceRecord.fxml"));
        Parent returnsPane = returnsPageLoader.load();
        Scene returnsScene = new Scene(returnsPane, 720, 480);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(returnsScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }


}
