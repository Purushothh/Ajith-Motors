package sample;

import javafx.beans.property.SimpleDoubleProperty;

public class Advance {

    private String advance_date,advance_no,customer_name,vehicle_no;

    private SimpleDoubleProperty bill_amount;

    public Advance(String advance_date, String advance_no, String customer_name, String vehicle_no, SimpleDoubleProperty bill_amount) {
        this.advance_date = advance_date;
        this.advance_no = advance_no;
        this.customer_name = customer_name;
        this.vehicle_no = vehicle_no;
        this.bill_amount = bill_amount;
    }

    public String getAdvance_date() {
        return advance_date;
    }

    public void setAdvance_date(String advance_date) {
        this.advance_date = advance_date;
    }

    public String getAdvance_no() {
        return advance_no;
    }

    public void setAdvance_no(String advance_no) {
        this.advance_no = advance_no;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public double getBill_amount() {
        return bill_amount.get();
    }

    public SimpleDoubleProperty bill_amountProperty() {
        return bill_amount;
    }

    public void setBill_amount(double bill_amount) {
        this.bill_amount.set(bill_amount);
    }
}
