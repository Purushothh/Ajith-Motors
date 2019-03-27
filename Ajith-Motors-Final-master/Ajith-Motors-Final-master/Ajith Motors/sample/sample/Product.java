package sample;

public class Product {

    private String item_code,description,quantity_available,category_name;

    private int category_id;

    private double bargain_price,selling_price,buying_price,net_price;

    public Product(String item_code, String description, String quantity_available, double selling_price, double bargain_price) {
        this.item_code = item_code;
        this.description = description;
        this.quantity_available = quantity_available;
        this.bargain_price = bargain_price;
        this.selling_price = selling_price;
    }

    public Product(String item_code, String description, String quantity_available,double buying_price, double bargain_price, double selling_price, int category_id) {
        this.item_code = item_code;
        this.description = description;
        this.quantity_available = quantity_available;
        this.category_id = category_id;
        this.bargain_price = bargain_price;
        this.selling_price = selling_price;
        this.buying_price = buying_price;
    }

    public Product(String item_code, String description, String quantity_available, double buying_price,double bargain_price, double selling_price, String category_name) {
        this.item_code = item_code;
        this.description = description;
        this.quantity_available = quantity_available;
        this.category_name = category_name;
        this.bargain_price = bargain_price;
        this.selling_price = selling_price;
        this.buying_price = buying_price;
    }

    public Product(String item_code, String description, String quantity_available, double buying_price,double bargain_price, double selling_price, double net_price,String category_name) {
        this.item_code = item_code;
        this.description = description;
        this.quantity_available = quantity_available;
        this.bargain_price = bargain_price;
        this.selling_price = selling_price;
        this.buying_price = buying_price;
        this.net_price = buying_price*Double.valueOf(quantity_available);
        this.category_name=category_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity_available() {
        return quantity_available;
    }

    public void setQuantity_available(String quantity_available) {
        this.quantity_available = quantity_available;
    }

    public double getBargain_price() {
        return bargain_price;
    }

    public void setBargain_price(double bargain_price) {
        this.bargain_price = bargain_price;
    }

    public double getSelling_price() {
        return selling_price;
    }

    public void setSelling_price(double selling_price) {
        this.selling_price = selling_price;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public double getBuying_price() {
        return buying_price;
    }

    public void setBuying_price(double buying_price) {
        this.buying_price = buying_price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public double getNet_price() {
        return net_price;
    }

    public void setNet_price(double net_price) {
        this.net_price = net_price;
    }
}
