package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class DraftsController extends CustomMenuBarAdmin{

    @FXML
    private TableView tableDraft;

    @FXML
    private TableColumn draftid;

    @FXML
    private TableColumn draftdate;

    @FXML
    private TableColumn amount;

    private ObservableList<Drafts> draftList= FXCollections.observableArrayList();

    private ObservableList<Cart> draftTransactions= FXCollections.observableArrayList();

    public static String draftno="";

    public static String previousScene="";

    private ObservableList<Drafts> displayDraftsTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT draft_id,draft_date,sum(net_price)\n" +
                "FROM drafts\n" +
                "GROUP BY draft_id,draft_date";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            Drafts drafts=new Drafts(rs.getString(2),rs.getString(1),rs.getDouble(3));
            draftList.add(drafts);
        }
        con.close();
        return draftList;
    }

    private ObservableList<Cart> sendToCart(int draft_id) throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT d.item_code,p.description,d.quantity,p.selling_price,d.net_price FROM products p,drafts d WHERE d.item_code=p.item_code AND draft_id='"+draft_id+"';";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            Cart cart=new Cart(new SimpleStringProperty(rs.getString(1)),new SimpleStringProperty(rs.getString(2)),rs.getString(3),new SimpleDoubleProperty(rs.getDouble(4)),new SimpleDoubleProperty(rs.getDouble(5)),0);
            draftTransactions.add(cart);
        }
        con.close();
        return draftTransactions;
    }

    @FXML
    void initialize(){
        draftid.setCellValueFactory(new PropertyValueFactory<Drafts,String>("draft_id"));
        draftdate.setCellValueFactory(new PropertyValueFactory<Drafts,String>("draft_date"));
        amount.setCellValueFactory(new PropertyValueFactory<Drafts, Double>("amount"));
        try {
            tableDraft.setItems(displayDraftsTable());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tableDraft.setRowFactory( tv -> {
            previousScene="Drafts";
            TableRow<Drafts> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Drafts rowData = row.getItem();
                    draftno=rowData.getDraft_id();
                    tableDraft.getScene().getWindow().hide();
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setLocation(getClass().getResource("Cashier.fxml"));
                    /*
                     * if "fx:controller" is not set in fxml
                     * fxmlLoader.setController(NewWindowController);
                     */
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 1000, 700);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage stage = new Stage();
                    stage.getIcons().add(new Image("sample/images/logo.png"));
                    stage.setTitle("Welcome to Ajith Motors");
                    stage.setScene(scene);
                    stage.show();
                    Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
                    stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
                    stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
                    /*try {
                        Cashier cashier=fxmlLoader.getController();
                        draftTransactions=FXCollections.observableArrayList();
                        draftTransactions=sendToCart(Integer.parseInt(rowData.getDraft_id()));
                        cashier.table.setItems(draftTransactions);
                        cashier.getTotal();
                        cashier.btnBill.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                cashier.confirmOrder();
                            }
                        });
                        cashier.btnAddItem.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    cashier.updateTable();
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }*/
                }
            });
            return row ;
        });
    }

}
