package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.reports.Expenses;

import java.sql.SQLException;

public class ExpensesReport extends Connector{

    @FXML
    private ComboBox searchby;

    @FXML
    private TextField searchbar;

    @FXML
    private ComboBox categoryList;

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private TableView table_expenses;

    @FXML
    private TableColumn date;

    @FXML
    private TableColumn description;

    @FXML
    private TableColumn amount;

    @FXML
    private Label lblTotal;

    final ObservableList searchbylist= FXCollections.observableArrayList("Description","Category");

    @FXML
    void initialize(){
        try {
            FilteredList<Expenses> expenseList=new FilteredList<>(getExpensesReport(),e->true);
            table_expenses.setItems(expenseList);
            setTotal(expenseList);
            searchbar.setOnKeyReleased(keyEvent -> {
                    if (searchby.getValue().equals(searchbylist.get(0))){
                        expenseList.setPredicate(e -> e.getDescription().toLowerCase().contains(searchbar.getText().toLowerCase().trim()));
                    }
            });
            ObservableList<String> expenseCategory=getCategoryList();
            categoryList.setItems(expenseCategory);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        date.setCellValueFactory(new PropertyValueFactory<Expenses, String>("date"));
        description.setCellValueFactory(new PropertyValueFactory<Expenses, String>("description"));
        amount.setCellValueFactory(new PropertyValueFactory<Expenses, Double>("amount"));

        searchby.setValue(searchbylist.get(0));
        searchby.setItems(searchbylist);
        searchbar.setPromptText("Search by "+searchbylist.get(0));
        categoryList.setVisible(false);
        lblTotal.setText("0.00");

        from.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Expenses> expensesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT expense_date,description,expense_category,amount FROM expenses WHERE expense_date>='"+from.getValue()+"';";
                }else{
                    select="SELECT expense_date,description,expense_category,amount FROM expenses WHERE expense_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"';";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Expenses expenses = new Expenses(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
                        expensesList.add(expenses);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                table_expenses.setItems(expensesList);
                setTotal(expensesList);

            }
        });

        to.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Expenses> expensesList = FXCollections.observableArrayList();
                try {
                    jdbcConnect();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                String select="";
                if(to.getValue()==null){
                    select="SELECT expense_date,description,expense_category,amount FROM expenses WHERE expense_date<='"+to.getValue()+"';";
                }else{
                    select="SELECT expense_date,description,expense_category,amount FROM expenses WHERE expense_date BETWEEN '"+from.getValue()+"' AND '"+to.getValue()+"';";
                }
                try {
                    stmt = con.createStatement();
                    rs = stmt.executeQuery(select);
                    while (rs.next()) {
                        Expenses expenses = new Expenses(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
                        expensesList.add(expenses);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                table_expenses.setItems(expensesList);
                setTotal(expensesList);
            }
        });
    }

    @FXML
    private void change(){
        if(searchby.getValue().equals(searchbylist.get(0))){
            categoryList.setVisible(false);
            searchbar.setVisible(true);
        }else if(searchby.getValue().equals(searchbylist.get(1))){
            searchbar.setVisible(false);
            categoryList.setVisible(true);
        }
    }

    private ObservableList<Expenses> getExpensesReport() throws SQLException, ClassNotFoundException {
        ObservableList<Expenses> expensesList = FXCollections.observableArrayList();
        jdbcConnect();
        String select = "SELECT expense_date,description,expense_category,amount FROM expenses;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(select);
        while (rs.next()) {
            Expenses expenses = new Expenses(rs.getString(1), rs.getString(2),rs.getString(3), rs.getDouble(4));
            expensesList.add(expenses);
        }
        con.close();
        return expensesList;
    }

    private void setTotal(ObservableList<Expenses> expensesList){
        double total=0;
        for(int i=0;i<expensesList.size();i++){
            total+=expensesList.get(i).getAmount();
        }
        lblTotal.setText(String.format("Rs. %.2f",total));
    }

    private ObservableList<String> getCategoryList() throws SQLException, ClassNotFoundException {
        ObservableList<String> categoryList=FXCollections.observableArrayList();
        jdbcConnect();
        String select = "SELECT category_name FROM expense_category;";
        stmt = con.createStatement();
        rs = stmt.executeQuery(select);
        while (rs.next()) {
            String category = rs.getString(1);
            categoryList.add(category);
        }
        con.close();
        return categoryList;
    }
}
