package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AdvancePayments extends CustomMenuBarAdmin{

    @FXML
    private TableView<Advance> tableAdvance;

    @FXML
    private TableColumn<Advance,String> date;

    @FXML
    private TableColumn<Advance,String> advance_no;

    @FXML
    private TableColumn<Advance,String> customer_name;

    @FXML
    private TableColumn<Advance,String> vehicle_no;

    @FXML
    private TableColumn<Advance,Double> total;

    public static String advanceno="";

    public static String previousScene="";

    private ObservableList<Advance> advanceList= FXCollections.observableArrayList();

    @FXML
    void initialize(){
        date.setCellValueFactory(new PropertyValueFactory<Advance,String>("advance_date"));
        advance_no.setCellValueFactory(new PropertyValueFactory<Advance,String>("advance_no"));
        customer_name.setCellValueFactory(new PropertyValueFactory<Advance,String>("customer_name"));
        vehicle_no.setCellValueFactory(new PropertyValueFactory<Advance,String>("vehicle_no"));
        total.setCellValueFactory(new PropertyValueFactory<Advance, Double>("bill_amount"));
        try {
            tableAdvance.setItems(displayAdvanceTable());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        tableAdvance.setRowFactory( tv -> {
            TableRow<Advance> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    previousScene="Advance";
                    Advance rowData = row.getItem();
                    advanceno=rowData.getAdvance_no();
                    AdvanceRecord.advance_no=advanceno;
                    tableAdvance.getScene().getWindow().hide();
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
                }
            });
            return row ;
        });

    }

    private ObservableList<Advance>displayAdvanceTable() throws SQLException, ClassNotFoundException {
        jdbcConnect();
        String select="SELECT advance_date,advance_no,customer_name,vehicle_no,SUM(net_price) " +
                "FROM advance_transactions " +
                "GROUP BY advance_date,advance_no,customer_name,vehicle_no;";
        stmt=con.createStatement();
        rs=stmt.executeQuery(select);
        while (rs.next()) {
            Advance advance=new Advance(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),new SimpleDoubleProperty(rs.getDouble(5)));
            advanceList.add(advance);
        }
        con.close();
        return advanceList;
    }

}
