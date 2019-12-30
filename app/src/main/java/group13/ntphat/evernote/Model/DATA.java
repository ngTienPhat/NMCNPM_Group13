package group13.ntphat.evernote.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DATA {
    static private final String url = "jdbc:postgresql://localhost/dvdrental";
    static private final String user = "postgres";
    static private final String password = "<add your password>";

    static public String login(String userName, String password) {
        //check login
        return "";
    }
    static public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}