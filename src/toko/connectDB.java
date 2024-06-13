package toko;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connectDB {
    private String url, usr, pwd, db;
    
    public connectDB() {
        db = "toko";  
        url = "jdbc:mysql://localhost/" + db;
        usr = "root";  
        pwd = "";      
    }

    public Connection getConnect() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection(url, usr, pwd);
            System.out.println("Koneksi Berhasil");
        } catch (ClassNotFoundException e) {
            System.out.println("Error #1: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error #2: " + e.getMessage());
        }

        return cn;
    }

    public static void main(String[] args) {
        new connectDB().getConnect();
    }
}
