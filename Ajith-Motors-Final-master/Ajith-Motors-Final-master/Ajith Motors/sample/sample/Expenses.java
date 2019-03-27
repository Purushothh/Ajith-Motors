package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Expenses extends CustomMenuBarAdmin{

    @FXML
    private ComboBox<String> expense_category;

    @FXML
    private DatePicker date;

    @FXML
    private TextArea description;

    @FXML
    private TextField amount;

    @FXML
    void initialize(){
        page="expenses";
        expense_category.setValue("Select Expense Category");
        try {
            expense_category.setItems(getCategoryName());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateExpensesTable() throws SQLException, ClassNotFoundException {
        if(expense_category.getValue().equals("Select Expense Category")||date.getValue()==null||amount.getText().equals("")){
            System.out.println("Please fill all the required fields");
        }else {
            try {
                jdbcConnect();
                String expense_id = "";
                expense_id = getMaxExpenseId();
                boolean condition=false;
                for(int i=0;i<getCategoryName().size();i++){
                    if(getCategoryName().get(i).toLowerCase().equals(expense_category.getValue().toLowerCase())){
                        expense_category.setValue(getCategoryName().get(i));
                        condition=true;
                        break;
                    }
                }
                if(condition==false){
                    String insert1 = "INSERT INTO expense_category VALUES('"+expense_category.getValue()+"');";
                    ps = con.prepareStatement(insert1);
                    ps.execute();
                }
                String insert = "INSERT INTO expenses VALUES('" + expense_id + "','" + date.getValue().toString() + "','" + expense_category.getValue() + "','" + description.getText() + "'," + Double.valueOf(amount.getText()) + ");";
                ps = con.prepareStatement(insert);
                ps.execute();
                con.close();
                clearAllFields();
            } catch (NumberFormatException e){
                System.out.println("Please enter a valid amount");
            }
        }
    }

    private static String getMaxExpenseId() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT MAX(expense_id) FROM expenses";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        String expense_id="";
        String expense_number="";
        while (rs.next()){
            expense_id=rs.getString(1);
        }
        if(expense_id==null){
            expense_id=getYear()+String.format("%05d",0);
        }
        for(int i=6;i<expense_id.length();i++){
            expense_number+=expense_id.charAt(i);
        }
        String trans_no= "EX"+getYear()+String.format("%05d",Integer.parseInt(expense_number)+1);
        return trans_no;
    }

    private ObservableList<String> getCategoryName() throws SQLException, ClassNotFoundException {
        ObservableList<String> observableList=FXCollections.observableArrayList();
        jdbcConnect();
        String select="SELECT * FROM expense_category;";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()){
            observableList.add(rs.getString(1));
        }
        return observableList;
    }

    private void clearAllFields(){
        expense_category.setValue("Select Expense Category");
        date.setValue(null);
        description.setText("");
        amount.setText("");
    }

    private static String getYear(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy");
        LocalDateTime now = LocalDateTime.now();
        String year=dtf.format(now);
        return year;
    }

}
