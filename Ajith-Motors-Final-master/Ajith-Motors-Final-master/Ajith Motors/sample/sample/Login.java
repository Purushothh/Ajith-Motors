package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login extends Connector {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    public static String uname,privileges;

    public void login(ActionEvent actionEvent) throws SQLException, ClassNotFoundException, IOException {
        String[] loginarr=loginDetails(username.getText());
        if(!username.getText().equals(loginarr[1])){
            Alert alert = new Alert(Alert.AlertType.ERROR, "User doesn't exist !" , ButtonType.OK);
            alert.setTitle("Invalid User");
            alert.setHeaderText("");
            alert.showAndWait();
        }else{
            if(!password.getText().equals(loginarr[2])){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password !" , ButtonType.OK);
                alert.setTitle("Invalid Credentials");
                alert.setHeaderText("");
                alert.showAndWait();
            }else{
                /*
                Alert alert = new Alert(Alert.AlertType.NONE, "Login Successful !" , ButtonType.OK);
                alert.setTitle("Login Successful");
                uname=username.getText();
                alert.setHeaderText("");
                alert.showAndWait();*/
                if(loginarr[0].equals("1")){
                    privileges="Administrator";
                    openAdmin(actionEvent);
                }else if(loginarr[0].equals("0")){
                    privileges="Staff";
                    openCashier(actionEvent);
                }

            }
        }
    }

    public void openAdmin(javafx.event.ActionEvent actionEvent) throws IOException {
        FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
        Parent loginPane = loginPageLoader.load();
        Scene loginScene = new Scene(loginPane, 1920, 1070);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(loginScene);
        /*
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);*/
        primaryStage.setMaximized(true);
    }

    public void openCashier(ActionEvent actionEvent) throws IOException {
        FXMLLoader cashierPageLoader = new FXMLLoader(getClass().getResource("Cashier.fxml"));
        Parent cashierPane = cashierPageLoader.load();
        Scene cashierScene = new Scene(cashierPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(cashierScene);

        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        //FIT ACCORDING TO SCREEN SIZE
        //primaryStage.setWidth(primScreenBounds.getWidth());
        //primaryStage.setHeight(primScreenBounds.getHeight());
        /*
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        */
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void resetPassword() throws IOException {
        FXMLLoader resetPageLoader = new FXMLLoader(getClass().getResource("ForgotPassword.fxml"));
        Parent resetPane = resetPageLoader.load();
        Scene resetScene = new Scene(resetPane, 365, 210);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Reset Password - Ajith Motors ");
        primaryStage.setScene(resetScene);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public static String[] loginDetails(String username) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String[] loginarr=new String[3];
        String select="SELECT admin_privileges,username,password FROM login WHERE username='"+username+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while(rs.next()){
            String privileges=rs.getString(1);
            String uname=rs.getString(2);
            String password=rs.getString(3);
            loginarr[0]=privileges;
            loginarr[1]=uname;
            loginarr[2]=password;
        }
        con.close();
        return  loginarr;
    }

    public void passwordReset(MouseEvent mouseEvent) throws IOException {
        resetPassword();
    }

}
