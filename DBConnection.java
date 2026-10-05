package employee;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    static String url = "jdbc:mysql://localhost:3306/leave_management";
    static String username = "root";
    static String password = "Meenakshi@2003";

    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection( url, username, password);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
    public  static void showConnectionMessage() {
    	 System.out.println("Database Connected Successfully!");

    }
}