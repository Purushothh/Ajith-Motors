package sample;

import javafx.beans.property.SimpleDoubleProperty;

public class Pay {
    private String payment_date,method,reference;

    private SimpleDoubleProperty amount;

    public Pay(String method, String reference, SimpleDoubleProperty amount) {
        this.method = method;
        this.reference = reference;
        this.amount = amount;
    }

    public Pay(String payment_date, String method, String reference, SimpleDoubleProperty amount) {
        this.payment_date = payment_date;
        this.method = method;
        this.reference = reference;
        this.amount = amount;
    }

    public String getPaymentDate() {
        return payment_date;
    }

    public void setPaymentDate(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}
