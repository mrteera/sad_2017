package revenuerecognition;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by mrteera on 1/19/2017 AD.
 */
public class DBHelper {
    public static DBHelper instance;
    public static final String PG_URL = "jdbc:postgresql://localhost:5432/";

    public static final String DB_NAME = "revenue_recognition";
    public static final String DB_URL = PG_URL + DB_NAME;
    public static final String DB_USR = "mrteera";
//    public static final String DB_PWD = "password";

    public static Connection conn;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (instance == null)
            instance = new DBHelper();
        return instance;
    }

    public void establishConnection() {
        if (conn != null)
            return;
        Properties props = new Properties();
        props.put("user", DB_USR);
//        props.put("password", DB_PWD);
        try {
            conn = DriverManager.getConnection(DB_URL, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException {
        return conn.prepareStatement(sql);
    }


    public PreparedStatement prepareStatement(String sql, int resultSetType) throws SQLException {
        return conn.prepareStatement(sql, resultSetType);
    }

    public static void createDatabase() {
        String sql = "CREATE DATABASE " + DB_NAME;

        Properties props = new Properties();
        props.put("user", DB_USR);
//        props.put("password", DB_PWD);

        try {
            Connection conn = DriverManager.getConnection(PG_URL, props);

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.execute();
            conn.close();

            System.out.println("Database created successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    public static void migrate() {
//        String sql;
//        Properties props = new Properties();
//        props.put("user", DB_USR);
//        props.put("password", DB_PWD);
//
//        try {
//            sql = "CREATE TABLE " + Article.TABLE_NAME + "(:SCHEMA)".replace(":SCHEMA", Article.SCHEMA);
//
//            Connection conn = DriverManager.getConnection(DB_URL, props);
//
//            PreparedStatement stmt = conn.prepareStatement(sql);
//
//            stmt.execute();
//            conn.close();
//
//            System.out.println("Database migrated.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static void dropDatabase() {
        String sql = "DROP DATABASE " + DB_NAME;

        Properties props = new Properties();
//        props.put("user", DB_USR);
//        props.put("password", DB_PWD);

        try {
            Connection conn = DriverManager.getConnection(PG_URL, props);

            PreparedStatement stmt;
            stmt = conn.prepareStatement(sql);
            stmt.execute();
            System.out.println("Database dropped successfully.");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
