package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class AdvanceRecord extends Connector{

    @FXML
    private TextField total;

    @FXML
    private TextField paid;

    @FXML
    private TextField ref;

    @FXML
    private ComboBox payment_type;

    @FXML
    private TableView<Pay> payment_table;

    @FXML
    private TableColumn<Pay,String> payment_date;

    @FXML
    private TableColumn<Pay,String> payment_method;

    @FXML
    private TableColumn<Pay,String> payment_reference;

    @FXML
    private TableColumn<Pay,Double> amount_paid;

    private ObservableList<Pay> observableListPayment=FXCollections.observableArrayList();

    final ObservableList<String> payment_types = FXCollections.observableArrayList("Cash","Credit Card","Cheque");

    public static String advance_no;


    @FXML
    void initialize(){
        payment_type.setValue("Select Payment Method");
        payment_type.setItems(payment_types);
        ObservableList<Cart> carts=Cashier.tableCart.getItems();
        double sum=0;
        for(int i=0;i<carts.size();i++){
            sum+=carts.get(i).getNetprice();
        }
        total.setText(String.format("%.2f",sum));
        total.setEditable(false);
        payment_date.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getPaymentDate()));
        payment_method.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMethod()));
        payment_reference.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getReference()));
        amount_paid.setCellValueFactory(c-> c.getValue().amountProperty().asObject());
        try {
            payment_table.setItems(displayAdvancePaymentsTable(advance_no));
            advance_no="";
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addPayment(){
        double sum=0;
        if (!payment_type.getSelectionModel().getSelectedItem().equals("Select Payment Method")) {
            if(!paid.getText().equals("")) {
                Pay payment=new Pay(getDate(),payment_type.getSelectionModel().getSelectedItem().toString(),ref.getText(),new SimpleDoubleProperty(Double.valueOf(paid.getText())));
                double amount=0;
                for(int i=0;i<observableListPayment.size();i++){
                    if(observableListPayment.get(i).getMethod().equals(payment.getMethod())){
                        payment.setAmount(observableListPayment.get(i).getAmount()+payment.getAmount());
                        observableListPayment.remove(i);
                        break;
                    }
                }
                observableListPayment.add(observableListPayment.size(),payment);
                payment_table.setItems(observableListPayment);
                for (int i = 0; i < observableListPayment.size(); i++) {
                    sum += observableListPayment.get(i).getAmount();
                }
                if(Double.valueOf(total.getText())<sum&&payment_type.getSelectionModel().getSelectedItem().equals("Cash")) {
                    paid.setText("");
                }else if(payment_type.getSelectionModel().getSelectedItem().equals("Credit Card")||payment_type.getSelectionModel().getSelectedItem().equals("Cheque")||payment_type.getSelectionModel().getSelectedItem().equals("Credit")){
                    paid.setText("");
                }else {
                    paid.setText(String.valueOf(Double.valueOf(total.getText())-sum));
                }
                ref.setText("");
                payment_type.setValue("Select Payment Method");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid amount." , ButtonType.OK);
                alert.setTitle("Invalid Amount");
                alert.setHeaderText("");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a payment method." , ButtonType.OK);
            alert.setTitle("Invalid Payment Method");
            alert.setHeaderText("");
            alert.showAndWait();
        }
    }

    private String updateAdvancePaymentTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String advance_no="";
        if(!AdvancePayments.advanceno.equals("")){
            advance_no=AdvancePayments.advanceno;
        }else {
            advance_no = Cashier.getMaxAdvanceNo();
        }
        for(int i=0;i<observableListPayment.size();i++) {
            String insert ="INSERT INTO advance_payments VALUES(now(),'"+advance_no+"','"+observableListPayment.get(i).getMethod()+"','"+observableListPayment.get(i).getReference()+"',"+observableListPayment.get(i).getAmount()+");";
            ps = con.prepareStatement(insert);
            ps.execute();
        }
        con.close();
        return advance_no;
    }

    private ObservableList<Pay> displayAdvancePaymentsTable(String advance_no) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT payment_date,payment_method,reference,amount " +
                "FROM advance_payments " +
                "WHERE advance_no='"+advance_no+"'";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            String date=formatDate(rs.getString(1));
            Pay pay=new Pay(date,rs.getString(2),rs.getString(3),new SimpleDoubleProperty(rs.getDouble(4)));
            observableListPayment.add(pay);
        }
        con.close();
        return observableListPayment;
    }

    @FXML
    private void finishPayment(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to print a receipt ? " , ButtonType.YES,ButtonType.NO);
        alert.setTitle("Print Receipt?");
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        Cashier cashier=new Cashier();
        String ad_no="";
        if(result.get()==ButtonType.YES) {
            try {
                cashier.updateAdvanceTable();
                ad_no=updateAdvancePaymentTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Cashier.transaction_no=ad_no;
            ArrayList<String> payment_methods=new ArrayList<>();
            for(int i=0;i<observableListPayment.size();i++){
                payment_methods.add(i,observableListPayment.get(i).getMethod());
            }
            Payment.payment_methods=payment_methods;
            Print.printReceipt();
        }else{
            try {
                cashier.updateAdvanceTable();
                updateAdvancePaymentTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        /*try {
            updateAdvancePaymentTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        closeCurrentStage();
    }

    private void closeCurrentStage() {
        Stage stage=(Stage)ref.getScene().getWindow();
        stage.close();
    }

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);
        return date;
    }

    private String formatDate(String date){
        String year=date.split("-")[0];
        String month=(date.split("-")[1]);
        String day=(date.split("-")[2]);
        String formattedDate=day+"/"+month+"/"+year;
        return formattedDate;
    }

}
