package revenuerecognition;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import revenuerecognition.DBHelper;

/**
 * Created by mrteera on 1/19/2017 AD.
 */
public class Gateway {
    private static final String findRecognitionsStatement =
            "SELECT amount " + "  FROM revenueRecognitions " +
                    "  WHERE contract = ? AND recognizedOn  <= ?";
    private static final String findContractStatement =
            "SELECT * " + "  FROM contracts c, products p " +
                    "  WHERE c.id = ? AND c.product = p.ID";
    private static final String findProductStatement =
            "SELECT * " + "  FROM products WHERE ID = ?";
    private static final String insertRecognitionStatement =
            "INSERT INTO revenueRecognitions VALUES (?, ?, ?)";
    private static final String insertProductStatement =
            "INSERT INTO products (name, type) VALUES (?, ?)";
    private static final String insertContractStatement =
            "INSERT INTO contracts (product, revenue, dateSigned) VALUES (?, ?, ?)";
//    private Connection db;

    private DBHelper db;
    public Gateway() {
        db = DBHelper.getInstance();
        db.establishConnection();
    }

    public int insertProduct(String name, String type) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(insertProductStatement);
        stmt.setString(1, name);
        stmt.setString(2, type);
        return stmt.executeUpdate();
    }

    public int insertContract(int product_id, double revenue, MfDate dateSigned) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(insertContractStatement);
        stmt.setInt(1, product_id);
        stmt.setDouble(2, revenue);
        stmt.setDate(3, dateSigned.toSqlDate());
        return stmt.executeUpdate();
    }

    public ResultSet findRecognitionsFor(long contractID, MfDate asof) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(findRecognitionsStatement);
        stmt.setLong(1, contractID);
        stmt.setDate(2, asof.toSqlDate());
        return stmt.executeQuery();
    }

    public ResultSet findProduct (long productID) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(findProductStatement);
        stmt.setLong(1, productID);
        return stmt.executeQuery();
    }

    public ResultSet findContract (long contractID) throws SQLException{
        PreparedStatement stmt = db.prepareStatement(findContractStatement);
        stmt.setLong(1, contractID);
        return stmt.executeQuery();
    }

    public void insertRecognition (long contractID, Money amount, MfDate asof) throws SQLException {
        PreparedStatement stmt = db.prepareStatement(insertRecognitionStatement);
        stmt.setLong(1, contractID);
        stmt.setBigDecimal(2, amount.amount());
//        stmt.setDouble(2, amount.amount());
        stmt.setDate(3, asof.toSqlDate());
        System.out.println(stmt);
        stmt.executeUpdate();
    }
}
