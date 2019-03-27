package sample;

public class Drafts {

    private String draft_id,draft_date;

    private double amount;

    public Drafts(String draft_date, String draft_id, double amount) {
        this.draft_id = draft_id;
        this.draft_date = draft_date;
        this.amount = amount;
    }

    public String getDraft_id() {
        return draft_id;
    }

    public void setDraft_id(String draft_id) {
        this.draft_id = draft_id;
    }

    public String getDraft_date() {
        return draft_date;
    }

    public void setDraft_date(String draft_date) {
        this.draft_date = draft_date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
