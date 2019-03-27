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
import sample.reports.Sales;

import java.sql.SQLException;


public class SalesReport extends Connector {

    @FXML
    private TableView<Sales> salesTable;

    @FXML
    private TableColumn<Sales, String> date;

    @FXML
    private TableColumn<Sales, String> invoice_no;

    @FXML
    private TableColumn<Sales, String> customer_name;

    @FXML
    private TableColumn<Sales, Double> amount;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private Label lblTotal;

    @FXML
    void initialize() {
        try {
            salesTable.setItems(getSalesReport());
            setTotal(salesTable.getItems());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        date.setCellValueFactory(new PropertyValueFactory<Sales, String>("date"));
        invoice_no.setCellValueFactory(new PropertyValueFactory<Sales, String>("invoice_no"));
        amount.setCellValueFactory(new PropertyValueFactory<Sales, Double>("amount"));

        from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Sales> salesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT transaction_date,transaction_id,SUM(net_price) FROM transactions WHERE transaction_date>='"+from.getValue()+"' GROUP BY transaction_date,transaction_id; ";
                }else{
                    select="SELECT transaction_date,transaction_id,SUM(net_price) FROM transactions WHERE transaction_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"' GROUP BY transaction_date,transaction_id ";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Sales sales = new Sales(rs.getString(1), rs.getString(2), rs.getDouble(3));
                        salesList.add(sales);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                salesTable.setItems(salesList);
                setTotal(salesList);

            }
        });

        to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Sales> salesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT transaction_date,transaction_id,SUM(net_price) FROM transactions WHERE transaction_date<='"+to.getValue()+"' GROUP BY transaction_date,transaction_id; ";
                }else{
                    select="SELECT transaction_date,transaction_id,SUM(net_price) FROM transactions WHERE transaction_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"' GROUP BY transaction_date,transaction_id ";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Sales sales = new Sales(rs.getString(1), rs.getString(2), rs.getDouble(3));
                        salesList.add(sales);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                salesTable.setItems(salesList);
                setTotal(salesList);
            }
        });
    }

    private ObservableList<Sales> getSalesReport() throws SQLException, ClassNotFoundException {
        ObservableList<Sales> salesList = FXCollections.observableArrayList();
        jdbcConnect();
        String select = "SELECT transaction_date,transaction_id,SUM(net_price) FROM transactions GROUP BY transaction_date,transaction_id;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(select);
        while (rs.next()) {
            Sales sales = new Sales(rs.getString(1), rs.getString(2), rs.getDouble(3));
            salesList.add(sales);
        }
        con.close();
        return salesList;
    }

    private void setTotal(ObservableList<Sales> salesList){
        double total=0;
        for(int i=0;i<salesList.size();i++){
            total+=salesList.get(i).getAmount();
        }
        lblTotal.setText(String.format("Rs. %.2f",total));
    }

}


