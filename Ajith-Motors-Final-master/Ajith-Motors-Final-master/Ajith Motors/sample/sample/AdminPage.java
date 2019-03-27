package sample;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminPage extends CustomMenuBarAdmin {

    @FXML
    private ButtonBar menuBar;

    @FXML
    private Circle colour;

    @FXML
    private Label status;

    @FXML
    private Label date;

    @FXML
    private Label copyright;

    @FXML
    private Label lblCashInHand;

    @FXML
    private Label lblSales;

    @FXML
    private Label lblPurchases;

    @FXML
    private Label lblExpenses;

    @FXML
    private Label lblProfit;

    @FXML
    void initialize(){
        page="overview";
        checkInternet();
        date.setText(getDate());
        copyright.setText("Copyright © "+getYear()+". All Rights Reserved.");
        try {
            lblCashInHand.setText(String.format("Rs. %.2f",getCashInHand()));
            lblSales.setText(String.format("Rs. %.2f",getSales()));
            lblPurchases.setText(String.format("Rs. %.2f",getPurchases()));
            lblExpenses.setText(String.format("Rs. %.2f",getExpenses()));
            lblProfit.setText(String.format("Rs. %.2f",getSales()));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        /*Task<Void> task = new Task<Void>(){

            @Override
            protected Void call() throws Exception {
                while(! isCancelled()){
                    try {
                       checkInternet();
                    } catch (Exception exc) {
                        if (isCancelled()) {
                            break ;
                        }
                    }
                }
                return null;
            }
        };
        Thread th = new Thread(task);
        th.start();*/
    }

    public void checkInternet() {
        try {
            URL url = new URL("https://www.google.com/");
            URLConnection connection = url.openConnection();
            connection.connect();
            status.setText("Online");
            colour.setFill(Color.LIGHTGREEN);
        }
        catch (Exception e) {
            status.setText("Offline");
            colour.setFill(Color.RED);
        }
    }

    public String getDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        String date=dtf.format(now);
        return date;
    }

    public String getYear(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String year=dtf.format(now);
        return year;
    }

    @FXML
    void purchasesReport(MouseEvent mouseEvent) throws IOException {
        FXMLLoader purchasesReportLoader = new FXMLLoader(getClass().getResource("PurchasesReport.fxml"));
        Parent purchasesReportPane = purchasesReportLoader.load();
        Scene purchasesReportScene = new Scene(purchasesReportPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(purchasesReportScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void salesReport(MouseEvent mouseEvent) throws IOException {
        FXMLLoader salesReportLoader = new FXMLLoader(getClass().getResource("SalesReport.fxml"));
        Parent salesReportPane = salesReportLoader.load();
        Scene salesReportScene = new Scene(salesReportPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(salesReportScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void expensesReport(MouseEvent mouseEvent) throws IOException {
        FXMLLoader expensesReportLoader = new FXMLLoader(getClass().getResource("ExpensesReport.fxml"));
        Parent expensesReportPane = expensesReportLoader.load();
        Scene expensesReportScene = new Scene(expensesReportPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(expensesReportScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    private double getCashInHand() throws SQLException, ClassNotFoundException {
        double cash=0;
        jdbcConnect();
        String select="SELECT \n" +
                "(SELECT COALESCE(SUM(amount),0) FROM advance_payments WHERE payment_method='Cash' AND payment_date=DATE(now()))+\n" +
                "(SELECT COALESCE(SUM(amount),0) FROM payments WHERE payment_method='Cash' AND payment_date=DATE(now()))-\n" +
                "(SELECT COALESCE(SUM(amount),0) FROM expenses WHERE expense_date=DATE(now()));";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            cash=rs.getDouble(1);
        }
        return cash;
    }

    private double getSales() throws SQLException, ClassNotFoundException {
        double sales=0;
        jdbcConnect();
        String select="SELECT COALESCE(SUM(net_price),0) FROM transactions WHERE transaction_date=DATE(now());";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            sales=rs.getDouble(1);
        }
        return sales;
    }

    private double getPurchases() throws SQLException, ClassNotFoundException {
        double purchases=0;
        jdbcConnect();
        String select="SELECT COALESCE(SUM(net_price),0) FROM purchases WHERE purchase_date=DATE(now());";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            purchases=rs.getDouble(1);
        }
        return purchases;
    }

    private double getExpenses() throws SQLException, ClassNotFoundException {
        double expenses=0;
        jdbcConnect();
        String select="SELECT COALESCE(SUM(amount),0) FROM expenses WHERE expense_date=DATE(now());";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            expenses=rs.getDouble(1);
        }
        return expenses;
    }
}
