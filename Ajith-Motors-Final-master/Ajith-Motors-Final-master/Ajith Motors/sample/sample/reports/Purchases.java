package sample.reports;

public class Purchases {

    private String date,invoice_no,supplier_name;

    private double amount;

    public Purchases(String date, String invoice_no, String supplier_name, double amount) {
        this.date = date;
        this.invoice_no = invoice_no;
        this.supplier_name = supplier_name;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
