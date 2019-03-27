package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.reports.Purchases;

import java.sql.SQLException;

public class PurchasesReport extends Connector {

    @FXML
    private TableView<Purchases> purchasesTable;

    @FXML
    private TableColumn<Purchases, String> date;

    @FXML
    private TableColumn<Purchases, String> invoice_no;

    @FXML
    private TableColumn<Purchases, String> supplier_name;

    @FXML
    private TableColumn<Purchases, Double> amount;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private Label lblTotal;

    @FXML
    void initialize() {
        try {
            purchasesTable.setItems(getPurchasesReport());
            setTotal(purchasesTable.getItems());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        date.setCellValueFactory(new PropertyValueFactory<Purchases, String>("date"));
        invoice_no.setCellValueFactory(new PropertyValueFactory<Purchases, String>("invoice_no"));
        supplier_name.setCellValueFactory(new PropertyValueFactory<Purchases,String>("supplier_name"));
        amount.setCellValueFactory(new PropertyValueFactory<Purchases, Double>("amount"));

        from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Purchases> purchasesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT p.purchase_date,p.purchase_invoice_no,s.supplier_name,SUM(p.net_price) FROM purchases p,supplier s WHERE p.supplier_id=s.supplier_id AND p.purchase_date>='"+from.getValue()+"' GROUP BY p.purchase_date,p.purchase_invoice_no,s.supplier_name;";
                }else{
                    select="SELECT p.purchase_date,p.purchase_invoice_no,s.supplier_name,SUM(p.net_price) FROM purchases p,supplier s WHERE p.supplier_id=s.supplier_id AND p.purchase_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"' GROUP BY p.purchase_date,p.purchase_invoice_no,s.supplier_name;";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Purchases purchases = new Purchases(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
                        purchasesList.add(purchases);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                purchasesTable.setItems(purchasesList);
                setTotal(purchasesList);

            }
        });

        to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Purchases> purchasesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT p.purchase_date,p.purchase_invoice_no,s.supplier_name,SUM(p.net_price) FROM purchases p,supplier s WHERE p.supplier_id=s.supplier_id AND p.purchase_date<='"+to.getValue()+"' GROUP BY p.purchase_date,p.purchase_invoice_no,s.supplier_name;";
                }else{
                    select="SELECT p.purchase_date,p.purchase_invoice_no,s.supplier_name,SUM(p.net_price) FROM purchases p,supplier s WHERE p.supplier_id=s.supplier_id AND p.purchase_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"' GROUP BY p.purchase_date,p.purchase_invoice_no,s.supplier_name;";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Purchases purchases = new Purchases(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
                        purchasesList.add(purchases);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                purchasesTable.setItems(purchasesList);
                setTotal(purchasesList);
            }
        });
    }

    private ObservableList<Purchases> getPurchasesReport() throws SQLException, ClassNotFoundException {
        ObservableList<Purchases> purchasesList = FXCollections.observableArrayList();
        jdbcConnect();
        String select = "SELECT p.purchase_date,p.purchase_invoice_no,s.supplier_name,SUM(p.net_price) FROM purchases p,supplier s WHERE p.supplier_id=s.supplier_id GROUP BY p.purchase_date,p.purchase_invoice_no,s.supplier_name;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(select);
        while (rs.next()) {
            Purchases purchases = new Purchases(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
            purchasesList.add(purchases);
        }
        con.close();
        return purchasesList;
    }

    private void setTotal(ObservableList<Purchases> purchasesList){
        double total=0;
        for(int i=0;i<purchasesList.size();i++){
            total+=purchasesList.get(i).getAmount();
        }
        lblTotal.setText(String.format("Rs. %.2f",total));
    }

}
