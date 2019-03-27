package sample;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class ForgotPassword extends Connector{

    @FXML
    private TextField username;

    public void sendReset() throws SQLException, ClassNotFoundException {
        if(getEmail()!=null) {
            String password = Mail.resetPassword(getEmail());
            updatePassword(password);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "A temporary password has been sent to your email address." , ButtonType.OK);
            alert.setTitle("Reset Password - Ajith Motors");
            alert.setHeaderText("");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get()==ButtonType.OK){
                alert.close();
                closeCurrentStage();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "User doesn't exist !" , ButtonType.OK);
            alert.setTitle("Invalid User");
            alert.setHeaderText("");
            Optional<ButtonType> result =alert.showAndWait();
            if(result.get()==ButtonType.OK){
                alert.close();
            }
        }
    }

    private void closeCurrentStage() {
        Stage stage=(Stage)username.getScene().getWindow();
        stage.close();
    }

    private String getEmail() throws SQLException, ClassNotFoundException {
        String email=null;
        jdbcConnect();
        String select="SELECT email_address FROM login WHERE username='"+username.getText()+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while(rs.next()){
            email=rs.getString(1);
        }
        return email;
    }

    private void updatePassword(String password) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String update="UPDATE login SET password="+password+" WHERE username='"+username.getText()+"';";
        ps=con.prepareStatement(update);
        ps.execute();
    }

}
