package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUsers extends SaveChanges {

    public TextField fName;
    public TextField lName;
    public TextField uName;
    public PasswordField pWord;
    public PasswordField pWordCon;
    public TextField email;
    public ComboBox privileges;

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User,String> first_name;

    @FXML
    private TableColumn<User,String> last_name;

    @FXML
    private TableColumn<User,String> username;

    @FXML
    private ButtonBar menuBar;

    private ObservableList<User> observableList;

    final ObservableList<String> privilege = FXCollections.observableArrayList("Select User Privileges","Administrator","Cashier");

    @FXML
    void initialize(){
        settingsTab="AddUsers";
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

    }

    public void addUser(ActionEvent event) throws ClassNotFoundException, SQLException {


        if(fName.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter First Name !" , ButtonType.OK);
            alert.setTitle("Enter First Name");
            alert.setHeaderText("");
            alert.showAndWait();
        }else{
            if(uName.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a username !" , ButtonType.OK);
                alert.setTitle("Enter Username");
                alert.setHeaderText("");
                alert.showAndWait();
            }else{
                if(pWord.getText().equals("")){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter a password !" , ButtonType.OK);
                    alert.setTitle("Enter Password");
                    alert.setHeaderText("");
                    alert.showAndWait();
                }else{
                    if (!pWord.getText().equals(pWordCon.getText())) {
                        //To make sure that user has entered the same password in both password and confirmation field.
                        Alert alert = new Alert(Alert.AlertType.ERROR, "The two password fields don't match. Please try again !", ButtonType.OK);
                        alert.setTitle("Password Error");
                        alert.setHeaderText("");
                        alert.showAndWait();
                    }else{
                        if(privileges.getSelectionModel().getSelectedItem().equals(privilege.get(0))){
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select user privileges !", ButtonType.OK);
                            alert.setTitle("Select Privileges");
                            alert.setHeaderText("");
                            alert.showAndWait();
                        }else {
                            String prvilege="false";
                            if(privileges.getSelectionModel().getSelectedItem().equals(privilege.get(1))){
                                prvilege="true";
                            }
                            jdbcConnect();
                            String insert = "INSERT INTO login VALUES('" + fName.getText() + "','" + lName.getText() + "','" + uName.getText() + "','" + pWord.getText() + "','" + email.getText() + "',"+prvilege+");";
                            ps = con.prepareStatement(insert);
                            ps.execute();
                            con.close();
                            clearFields();
                            table.refresh();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION, "The staff member has been added successfully.\nNow s/he can log into the system.", ButtonType.OK);
                            alert.setTitle("User Added");
                            alert.setHeaderText("");
                            alert.showAndWait();
                        }
                    }
                }
            }
        }
    }

    private void clearFields(){
        fName.setText("");
        lName.setText("");
        uName.setText("");
        pWord.setText("");
        pWordCon.setText("");
        email.setText("");
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
}
