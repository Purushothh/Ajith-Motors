package sample;

import java.sql.*;

public class Connector{
    public static Connection con;
    public static Statement stmt;
    public static PreparedStatement ps;
    public static ResultSet rs;

    //Connect to the database
    public static Connection jdbcConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        //con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12259789", "sql12259789", "WMsSmRIfmF");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ajith_motors", "root", "");
        return con;
    }
}