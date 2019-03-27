package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;

public class GeneralSettings extends SaveChanges{

    @FXML
    private TextField txtName;

    @FXML
    private TextArea txtAddress;

    @FXML
    private TextArea txtPhone;

    public static String name,address,phone;

    /*private String address;

    private String phone;*/

    @FXML
    void initialize(){
        settingsTab="General";
        txtName.setEditable(false);
        try {
            txtName.setText(getDetails().get(0));
            txtAddress.setText(getDetails().get(1));
            txtPhone.setText(getDetails().get(2));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        name=txtName.getText();
        address=txtAddress.getText();
        phone=txtPhone.getText();
        txtAddress.setOnKeyReleased(keyEvent->{
            address=txtAddress.getText();
        });
        txtPhone.setOnKeyReleased(keyEvent->{
            phone=txtPhone.getText();
        });
        /*btnApply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                applyChanges();
            }
        });*/
    }



    private ArrayList<String> getDetails() throws SQLException, ClassNotFoundException {
        ArrayList<String> arrayList=new ArrayList<>();
        jdbcConnect();
        String select="SELECT * FROM company";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()){
            arrayList.add(0,rs.getString(1));
            name=rs.getString(1);
            arrayList.add(1,rs.getString(2));
            address=rs.getString(2);
            arrayList.add(2,rs.getString(3));
            phone=rs.getString(3);
        }
        return arrayList;
    }

    @Override
    public void applyChanges() {
        try {
            jdbcConnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String update="UPDATE company SET address='"+address+"' ,phone_no='"+phone+"' WHERE name='"+name+"';";
        System.out.println(name+" "+address+" "+phone);
        try {
            ps=con.prepareStatement(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
