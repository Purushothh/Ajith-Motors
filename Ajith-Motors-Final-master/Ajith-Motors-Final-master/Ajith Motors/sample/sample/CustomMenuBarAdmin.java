package sample;

import com.sun.javafx.stage.StageHelper;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomMenuBarAdmin extends sample.Connector {

    @FXML
    public Button btnLogOut;

    @FXML
    public Button btnSettings;

    @FXML
    public Button btnInventory;

    @FXML
    public Button btnOverview;

    @FXML
    public Button btnExpenses;

    @FXML
    public MenuButton menuButton;

    @FXML
    public MenuItem sales;

    @FXML
    public MenuItem returns;

    @FXML
    public MenuItem drafts;

    @FXML
    public MenuItem advance;

    public static String page;

    @FXML
    void backToNormal(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("logout.png"));
        Image imageInventory = new Image(getClass().getResourceAsStream("inventory.png"));
        Image imageOverview = new Image(getClass().getResourceAsStream("overview.png"));
        Image imageSettings = new Image(getClass().getResourceAsStream("settings.png"));
        Image imageCashier = new Image(getClass().getResourceAsStream("cashier.png"));
        Image imageSales = new Image(getClass().getResourceAsStream("cashier.png"));
        Image imageReturns = new Image(getClass().getResourceAsStream("sales-return.png"));
        Image imageDrafts = new Image(getClass().getResourceAsStream("draft.png"));
        Image imageAdvance = new Image(getClass().getResourceAsStream("advance.png"));
        Image imageExpenses = new Image(getClass().getResourceAsStream("expenses.png"));
        Image imageEmployees = new Image(getClass().getResourceAsStream("employees.png"));
        Image imageSuppliers = new Image(getClass().getResourceAsStream("suppliers.png"));
        if(page.equals("overview")){
            imageOverview = new Image(getClass().getResourceAsStream("overview-hover.png"));
        }else if(page.equals("expenses")){
            imageExpenses= new Image(getClass().getResourceAsStream("expenses-hover.png"));
        }else if(page.equals("inventory")){
            imageInventory = new Image(getClass().getResourceAsStream("inventory-hover.png"));
        }else if(page.equals("settings")){
            imageSettings = new Image(getClass().getResourceAsStream("settings-hover.png"));
        }else if(page.equals("cashier")){
            imageCashier = new Image(getClass().getResourceAsStream("cashier-hover.png"));
        }
        btnLogOut.setGraphic(new ImageView(imageLogOut));
        btnLogOut.setPrefSize(150,30);
        btnInventory.setGraphic(new ImageView(imageInventory));
        btnInventory.setPrefSize(150,30);
        btnOverview.setGraphic(new ImageView(imageOverview));
        btnOverview.setPrefSize(150,30);
        btnSettings.setGraphic(new ImageView(imageSettings));
        btnSettings.setPrefSize(150,30);
        btnExpenses.setGraphic(new ImageView(imageExpenses));
        btnExpenses.setPrefSize(150,30);
        menuButton.setGraphic(new ImageView(imageCashier));
        menuButton.setPrefSize(150,30);
        sales.setGraphic(new ImageView(imageSales));
        returns.setGraphic(new ImageView(imageReturns));
        drafts.setGraphic(new ImageView(imageDrafts));
        advance.setGraphic(new ImageView(imageAdvance));
    }

    @FXML
    void initialize(){
        if(sample.Login.privileges.equals("Staff")){
            btnOverview.setVisible(false);
            btnSettings.setVisible(false);
        }
        Image imageLogOut = new Image(getClass().getResourceAsStream("logout.png"));
        Image imageInventory = new Image(getClass().getResourceAsStream("inventory.png"));
        Image imageOverview = new Image(getClass().getResourceAsStream("overview.png"));
        Image imageSettings = new Image(getClass().getResourceAsStream("settings.png"));
        Image imageCashier = new Image(getClass().getResourceAsStream("cashier.png"));
        Image imageSales = new Image(getClass().getResourceAsStream("cashier.png"));
        Image imageExpenses = new Image(getClass().getResourceAsStream("expenses.png"));
        Image imageReturns = new Image(getClass().getResourceAsStream("sales-return.png"));
        Image imageDrafts = new Image(getClass().getResourceAsStream("draft.png"));
        Image imageAdvance = new Image(getClass().getResourceAsStream("advance.png"));
        Image imageEmployees = new Image(getClass().getResourceAsStream("employees.png"));
        Image imageSuppliers = new Image(getClass().getResourceAsStream("suppliers.png"));
        /*if(page.equals("overview")){
            imageOverview = new Image(getClass().getResourceAsStream("overview-hover.png"));
        }else if(page.equals("products")){
            imageCart = new Image(getClass().getResourceAsStream("cart-hover.png"));
        }else if(page.equals("inventory")){
            imageInventory = new Image(getClass().getResourceAsStream("inventory-hover.png"));
        }else if(page.equals("users")){
            imageUsers = new Image(getClass().getResourceAsStream("users-hover.png"));
        }else if(page.equals("settings")){
            imageSettings = new Image(getClass().getResourceAsStream("settings-hover.png"));
        }else if(page.equals("cashier")){
            imageCashier = new Image(getClass().getResourceAsStream("settings-hover.png"));
        }*/
        btnLogOut.setGraphic(new ImageView(imageLogOut));
        btnLogOut.setPrefSize(150,30);
        btnInventory.setGraphic(new ImageView(imageInventory));
        btnInventory.setPrefSize(150,30);
        btnOverview.setGraphic(new ImageView(imageOverview));
        btnOverview.setPrefSize(150,30);
        btnSettings.setGraphic(new ImageView(imageSettings));
        btnSettings.setPrefSize(150,30);
        menuButton.setGraphic(new ImageView(imageCashier));
        menuButton.setPrefSize(150,30);
        btnExpenses.setGraphic(new ImageView(imageExpenses));
        btnExpenses.setPrefSize(150,30);
        /*sales.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    cashierPage(event);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
        Platform.runLater(() -> {
            btnInventory.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.F5), () -> {
                btnInventory.fire();
            });
        });
        sales.setAccelerator(new KeyCodeCombination(KeyCode.F12));
        sales.setGraphic(new ImageView(imageSales));
        returns.setGraphic(new ImageView(imageReturns));
        drafts.setGraphic(new ImageView(imageDrafts));
        advance.setGraphic(new ImageView(imageAdvance));
    }

    @FXML
    void changeLogout(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("logout-hover.png"));
        btnLogOut.setGraphic(new ImageView(imageLogOut));
        btnLogOut.setPrefSize(150,30);
    }

    @FXML
    void changeInventory(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("inventory-hover.png"));
        btnInventory.setGraphic(new ImageView(imageLogOut));
        btnInventory.setPrefSize(150,30);
    }

    @FXML
    void changeOverview(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("overview-hover.png"));
        btnOverview.setGraphic(new ImageView(imageLogOut));
        btnOverview.setPrefSize(150,30);
    }

    @FXML
    void changeSettings(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("settings-hover.png"));
        btnSettings.setGraphic(new ImageView(imageLogOut));
        btnSettings.setPrefSize(150,30);
    }

    @FXML
    void changeCashier(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("cashier-hover.png"));
        menuButton.setGraphic(new ImageView(imageLogOut));
        menuButton.setPrefSize(150,30);
    }

    @FXML
    void changeExpenses(MouseEvent mouseEvent){
        Image imageLogOut = new Image(getClass().getResourceAsStream("expenses-hover.png"));
        btnExpenses.setGraphic(new ImageView(imageLogOut));
        btnExpenses.setPrefSize(150,30);
    }

    @FXML
    void logOut(ActionEvent actionEvent) throws IOException {
        /*ObservableList<Stage> observableList=StageHelper.getStages();
        for(int i=0;i<observableList.size();i++){
            if(observableList.size()>1) {
                System.out.println(observableList.get(i));
                observableList.get(i).close();
            }
        }*/
        Platform.exit();
        FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("Sign In.fxml"));
        Parent loginPane = loginPageLoader.load();
        Scene loginScene = new Scene(loginPane, 380, 210);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(loginScene);
        primaryStage.show();
        /*
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        */
    }

    @FXML
    void overviewPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader overviewPageLoader = new FXMLLoader(getClass().getResource("AdminPage.fxml"));
        Parent overviewPane = overviewPageLoader.load();
        Scene overviewScene = new Scene(overviewPane, 1920, 1080);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(overviewScene);
        /*
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        */
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @FXML
    void cashierPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader cashierPageLoader = new FXMLLoader(getClass().getResource("Cashier.fxml"));
        Parent cashierPane = cashierPageLoader.load();
        Scene cashierScene = new Scene(cashierPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(cashierScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        //FIT ACCORDING TO SCREEN SIZE
        //primaryStage.setWidth(primScreenBounds.getWidth());
        //primaryStage.setHeight(primScreenBounds.getHeight());
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void returnsPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader returnsPageLoader = new FXMLLoader(getClass().getResource("Returns.fxml"));
        Parent returnsPane = returnsPageLoader.load();
        Scene returnsScene = new Scene(returnsPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(returnsScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        /*
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
        */
    }

    @FXML
    void draftsPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader draftsPageLoader = new FXMLLoader(getClass().getResource("Drafts.fxml"));
        Parent draftsPane = draftsPageLoader.load();
        Scene draftsScene = new Scene(draftsPane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(draftsScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void advancePage(ActionEvent actionEvent) throws IOException {
        FXMLLoader advancePageLoader = new FXMLLoader(getClass().getResource("AdvancePayments.fxml"));
        Parent advancePane = advancePageLoader.load();
        Scene advanceScene = new Scene(advancePane, 1920, 1080);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setTitle("Welcome to Ajith Motors");
        primaryStage.setScene(advanceScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void settingsPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader settingsPageLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent settingsPane = settingsPageLoader.load();
        Scene settingsScene = new Scene(settingsPane, 1920, 1080);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(settingsScene);
        primaryStage.setMaximized(true);
        primaryStage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    @FXML
    void inventoryPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader settingsPageLoader = new FXMLLoader(getClass().getResource("Inventory.fxml"));
        Parent settingsPane = settingsPageLoader.load();
        Scene settingsScene = new Scene(settingsPane, 1920, 1080);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(settingsScene);
        primaryStage.setMaximized(true);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        //primaryStage.setWidth(primScreenBounds.getWidth());
        //primaryStage.setHeight(primScreenBounds.getHeight());
        primaryStage.setMaximized(true);

    }

    @FXML
    void expensesPage(ActionEvent actionEvent) throws IOException {
        FXMLLoader settingsPageLoader = new FXMLLoader(getClass().getResource("Expenses.fxml"));
        Parent settingsPane = settingsPageLoader.load();
        Scene settingsScene = new Scene(settingsPane, 1920, 1080);
        Stage primaryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        primaryStage.getIcons().add(new Image("sample/images/logo.png"));
        primaryStage.setScene(settingsScene);
        primaryStage.setMaximized(true);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
