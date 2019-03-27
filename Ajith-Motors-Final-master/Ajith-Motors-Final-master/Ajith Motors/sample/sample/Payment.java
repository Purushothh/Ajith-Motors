package sample;

import com.sun.javafx.stage.StageHelper;
import com.sun.org.apache.bcel.internal.generic.DCMPG;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Payment extends Connector{

    @FXML
    private TextField total;

    @FXML
    private TextField paid;

    @FXML
    private TextField change;

    @FXML
    private TextField ref;

    @FXML
    private ComboBox payment_type;

    @FXML
    private TableView<Pay> payment_table;

    @FXML
    private TableColumn<Pay,String> payment_method;

    @FXML
    private TableColumn<Pay,String> payment_reference;

    @FXML
    private TableColumn<Pay,Double> payment_amount;

    @FXML
    private Button btnOk;

    private ObservableList<Pay> observableListPayment=FXCollections.observableArrayList();

    final ObservableList<String> payment_types = FXCollections.observableArrayList("Cash","Credit Card","Cheque","Credit");

    private double change_amount;

    public static ObservableList<Pay> payObservableList=FXCollections.observableArrayList();

    public static ArrayList<String> payment_methods=new ArrayList<>();


    @FXML
    void initialize(){
        /*Cashier cashier=new Cashier();
        total.setText(cashier.getTotal());*/
        payment_table.setEditable(true);
        payment_type.setValue("Select Payment Method");
        payment_type.setItems(payment_types);
        ObservableList<Cart> carts=Cashier.tableCart.getItems();
        double sum=0;
        for(int i=0;i<carts.size();i++){
            sum+=carts.get(i).getNetprice();
        }
        total.setText(String.format("%.2f",sum));
        total.setEditable(false);
        /*payment_method.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getMethod()));
        payment_reference.setCellValueFactory(c-> new SimpleStringProperty(c.getValue().getReference()));*/
        payment_table.setEditable(true);
        payment_amount.setEditable(true);
        payment_amount.setCellFactory(TextFieldTableCell.<Pay,Double>forTableColumn(new DoubleStringConverter()));
        payment_amount.setCellValueFactory(new PropertyValueFactory<Pay,Double>("amount"));
        payment_method.setCellValueFactory(new PropertyValueFactory<Pay,String>("method"));
        payment_reference.setCellValueFactory(new PropertyValueFactory<Pay,String>("reference"));
        //payment_amount.setCellValueFactory(cellData-> cellData.getValue().amountProperty().asObject());
        change.setEditable(false);
        btnOk.setDisable(true);
        try {
            System.out.println(Cashier.getAd());
            payment_table.setItems(displayAdvancePaymentsTable(Cashier.getAd()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void finalizePayment(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to print a receipt ? " , ButtonType.YES,ButtonType.NO);
        alert.setTitle("Print Receipt?");
        alert.setHeaderText("");
        Optional<ButtonType> result = alert.showAndWait();
        payObservableList=observableListPayment;
        if(result.get()==ButtonType.YES){
            //Print Receipt
            /*ObservableList<Cart> carts=Cashier.tableCart.getItems();
            for(int i=0;i<carts.size();i++){
                System.out.println(carts.get(i).getDescription());
            }*/
            try {
                Cashier.updateTransactionTable();
                deleteDraft();
                updatePaymentTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Print.printReceipt();
        }else if(result.get()==ButtonType.NO){
            try {
                Cashier.updateTransactionTable();
                deleteDraft();
                updatePaymentTable();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        closeCurrentStage();
        alert.close();

        //updateDatabase(Transaction Table)
    }

    @FXML
    private void addPayment(){
        double sum=0;
        if (!payment_type.getSelectionModel().getSelectedItem().equals("Select Payment Method")) {
            if(!paid.getText().equals("")) {
                Pay payment=new Pay(payment_type.getSelectionModel().getSelectedItem().toString(),ref.getText(),new SimpleDoubleProperty(Double.valueOf(paid.getText())));
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
                    change_amount=sum - Double.valueOf(total.getText());
                    change.setText(String.format("%.2f", change_amount));
                    paid.setText("");
                }/*else if(payment_type.getSelectionModel().getSelectedItem().equals("Credit Card")||payment_type.getSelectionModel().getSelectedItem().equals("Cheque")||payment_type.getSelectionModel().getSelectedItem().equals("Credit")){
                    change.setText(String.format("%.2f", change_amount));
                    paid.setText("");
                }*/else {
                    change.setText(String.format("%.2f", change_amount));
                    paid.setText(String.valueOf(Double.valueOf(total.getText())-sum));
                }
                if(sum>=Double.valueOf(total.getText())){
                    total.setText("0.00");
                    btnOk.setDisable(false);
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

    private void closeCurrentStage() {
        Stage stage=(Stage)payment_type.getScene().getWindow();
        stage.close();
    }

    private void deleteDraft(){
        try {
            jdbcConnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String delete="";
        if(!DraftsController.draftno.equals("")){
            String draft_id=DraftsController.draftno;
            System.out.println(draft_id);
            delete = "DELETE FROM drafts WHERE draft_id='"+draft_id+"';";
            try {
                ps = con.prepareStatement(delete);
                ps.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void editPaymentAmount(TableColumn.CellEditEvent<Pay,Double> cellEditEvent) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to change the quantity from "+Double.valueOf(cellEditEvent.getOldValue())+" to "+Double.valueOf(cellEditEvent.getNewValue()), ButtonType.YES,ButtonType.NO);
            alert.setTitle("Change Quantity");
            alert.setHeaderText("");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.YES){
                Pay paySelect = payment_table.getSelectionModel().getSelectedItem();
                System.out.println(paySelect.getMethod());
                paySelect.setAmount(cellEditEvent.getNewValue());
            }else{
                Pay paySelect = payment_table.getSelectionModel().getSelectedItem();
                System.out.println(paySelect.getMethod());
                paySelect.setAmount(cellEditEvent.getOldValue());
            }
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid price.", ButtonType.OK);
            alert.setTitle("Invalid Price");
            alert.setHeaderText("");
            alert.showAndWait();
            Pay paySelect = payment_table.getSelectionModel().getSelectedItem();
            paySelect.setAmount(0);
        }
    }

    private void updatePaymentTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        for(int i=0;i<observableListPayment.size();i++) {
            payment_methods.add(i,observableListPayment.get(i).getMethod());
            String insert = "INSERT INTO payments VALUES(now(),'"+Cashier.transaction_no+"','"+observableListPayment.get(i).getMethod()+"','"+observableListPayment.get(i).getReference()+"',"+observableListPayment.get(i).getAmount()+");";
            ps = con.prepareStatement(insert);
            ps.execute();
        }
        con.close();
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

    private String formatDate(String date){
        String year=date.split("-")[0];
        String month=(date.split("-")[1]);
        String day=(date.split("-")[2]);
        String formattedDate=day+"/"+month+"/"+year;
        return formattedDate;
    }
}


