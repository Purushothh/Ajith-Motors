package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;
import java.util.ArrayList;

public class EditUsers extends SaveChanges{

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User,String> first_name;

    @FXML
    private TableColumn<User,String> last_name;

    @FXML
    private TableColumn<User,String> username;

    @FXML
    private TextField txtFName;

    @FXML
    private TextField txtLName;

    @FXML
    private TextField txtUName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtCNewPassword;

    @FXML
    private TextField txtEmail;

    @FXML
    private ComboBox privileges;

    private String uname;

    private ObservableList<User> observableList;

    final ObservableList<String> privilege = FXCollections.observableArrayList("Select User Privileges","Administrator","Cashier");

    @FXML
    void initialize(){
        settingsTab="EditUsers";
        privileges.setItems(privilege);
        privileges.setValue(privilege.get(0));
        first_name.setCellValueFactory(new PropertyValueFactory<User,String>("fname"));
        last_name.setCellValueFactory(new PropertyValueFactory<User,String>("lname"));
        username.setCellValueFactory(new PropertyValueFactory<User,String>("uname"));
        try {
            table.setItems(getUser());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        table.refresh();
        //txtFName.setEditable(false);
        //txtLName.setEditable(false);
        txtUName.setEditable(false);
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            uname=table.getSelectionModel().getSelectedItem().getUname();
            try {
                txtFName.setText(getUserDetails(uname).get(0));
                txtLName.setText(getUserDetails(uname).get(1));
                txtUName.setText(getUserDetails(uname).get(2));
                txtEmail.setText(getUserDetails(uname).get(4));
                privileges.setValue(getUserDetails(uname).get(5));
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });

    }

    public void updateUser(ActionEvent event) throws ClassNotFoundException, SQLException {
        if(txtFName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the first name !", ButtonType.OK);
            alert.setTitle("Required Fields");
            alert.setHeaderText("");
            alert.showAndWait();
            txtFName.requestFocus();
        }else{
            if(txtEmail.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the email address !", ButtonType.OK);
                alert.setTitle("Required Fields");
                alert.setHeaderText("");
                alert.showAndWait();
                txtEmail.requestFocus();
            }else{
                if(txtPassword.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the current password !", ButtonType.OK);
                    alert.setTitle("Required Fields");
                    alert.setHeaderText("");
                    alert.showAndWait();
                    txtPassword.requestFocus();
                }else{
                    if(!txtPassword.getText().equals(getUserDetails(uname).get(3))){
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a valid password !", ButtonType.OK);
                        alert.setTitle("Incorrect Password");
                        alert.setHeaderText("");
                        alert.showAndWait();
                        txtPassword.requestFocus();
                    }else{
                        if(txtNewPassword.getText().equals("")&&!txtCNewPassword.getText().equals("")){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter the new password !", ButtonType.OK);
                            alert.setTitle("Required Fields");
                            alert.setHeaderText("");
                            alert.showAndWait();
                            txtNewPassword.requestFocus();
                        }else{
                            if(!txtNewPassword.getText().equals("")&&txtCNewPassword.getText().equals("")){
                                Alert alert = new Alert(Alert.AlertType.ERROR, "Please re-type the new password !", ButtonType.OK);
                                alert.setTitle("Required Fields");
                                alert.setHeaderText("");
                                alert.showAndWait();
                                txtCNewPassword.requestFocus();
                            }else {
                                if(!txtNewPassword.getText().equals(txtCNewPassword.getText())){
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Passwords do not match !", ButtonType.OK);
                                    alert.setTitle("Incorrect Password");
                                    alert.setHeaderText("");
                                    alert.showAndWait();
                                    txtNewPassword.requestFocus();
                                }else{
                                    if(privileges.getValue().equals(privilege.get(0))){
                                        Alert alert = new Alert(Alert.AlertType.ERROR, "Please select user privileges !", ButtonType.OK);
                                        alert.setTitle("Required Fields");
                                        alert.setHeaderText("");
                                        alert.showAndWait();
                                        privileges.requestFocus();
                                    }else{
                                        updateDetails();
                                        clearFields();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void clearFields(){
        txtFName.setText("");
        txtLName.setText("");
        txtUName.setText("");
        txtPassword.setText("");
        txtNewPassword.setText("");
        txtCNewPassword.setText("");
        txtEmail.setText("");
        privileges.setValue(privilege.get(0));
    }

    public ObservableList<User> getUser() throws SQLException, ClassNotFoundException {
        observableList= FXCollections.observableArrayList();
        jdbcConnect();
        String select="SELECT first_name,last_name,username FROM login;";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);

        while (rs.next()){
            User user=new User(rs.getString(1),rs.getString(2),rs.getString(3));
            observableList.add(user);
        }
        con.close();
        return observableList;
    }

    private ArrayList<String> getUserDetails(String username) throws SQLException, ClassNotFoundException {
        ArrayList<String> userList= new ArrayList();
        jdbcConnect();
        String select="SELECT * FROM login WHERE username='"+username+"'";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);

        while (rs.next()){
            for(int i=1;i<=6;i++){
                userList.add(i-1,rs.getString(i));
            }
        }
        if(userList.get(5).equals("1")){
            userList.set(5,privilege.get(1));
        }else if(userList.get(5).equals("0")){
            userList.set(5,privilege.get(2));
        }
        con.close();
        return userList;
    }

    public void updateDetails() {
        try {
            jdbcConnect();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int admin_privilege=0;
        String update="";
        if(privileges.getValue().equals(privilege.get(1))){
            admin_privilege=1;
        }
        if(txtNewPassword.getText().equals("") &&txtCNewPassword.getText().equals("")) {
            update = "UPDATE login SET first_name='"+txtFName.getText()+"',last_name='"+txtLName.getText()+"',password='"+txtPassword.getText()+"',email_address='"+txtEmail.getText()+"', admin_privileges="+admin_privilege+" WHERE username='"+txtUName.getText()+"';";
        }else{
            update = "UPDATE login SET first_name='"+txtFName.getText()+"',last_name='"+txtLName.getText()+"',password='"+txtNewPassword.getText()+"',email_address='"+txtEmail.getText()+"', admin_privileges="+admin_privilege+" WHERE username='"+txtUName.getText()+"';";
        }
        try {
            ps=con.prepareStatement(update);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
