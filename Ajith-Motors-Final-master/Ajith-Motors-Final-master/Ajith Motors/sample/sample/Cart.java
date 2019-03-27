package sample;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class Cart {

    private SimpleStringProperty item_code,description;

    private SimpleDoubleProperty price,netprice;

    private String quantity;

    private int warranty;

    public Cart(SimpleStringProperty item_code, SimpleStringProperty description, String quantity, SimpleDoubleProperty price, SimpleDoubleProperty netprice,int warranty) {
        this.item_code = item_code;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.netprice = netprice;
        this.warranty = warranty;
    }

    public String getItem_code() {
        return item_code.get();
    }

    public SimpleStringProperty item_codeProperty() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code.set(item_code);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity=quantity;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public double getNetprice() {
        return netprice.get();
    }

    public SimpleDoubleProperty netpriceProperty() {
        return netprice;
    }

    public void setNetprice(double netprice) {
        this.netprice.set(netprice);
    }

    public int getWarranty() {
        return warranty;
    }

    public void setWarranty(int warranty) {
        this.warranty = warranty;
    }
}
