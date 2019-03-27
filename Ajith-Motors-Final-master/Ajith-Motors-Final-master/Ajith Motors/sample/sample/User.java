package sample;

import javafx.beans.property.SimpleStringProperty;

public class User {

    private String fname,lname,uname;

    public User(String fname, String lname, String uname) {
        this.fname = fname;
        this.lname = lname;
        this.uname = uname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
