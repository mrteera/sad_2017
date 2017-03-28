package revenuerecognition;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by mrteera on 1/19/2017 AD.
 */
public class RecognitionService {
    private Gateway db = new Gateway();

    public Gateway getGateway(){
        return db;
    }

    public Money recognizedRevenue(long contractNumber, MfDate asof) throws ApplicationException
    {
        Money result = Money.dollars(0);
        try
        {
            ResultSet rs = db.findRecognitionsFor(contractNumber, asof);
            while (rs.next())
            {
                result = result.add(Money.dollars(rs.getDouble("amount")));
//                result = result.add(Money.dollars(rs.getBigDecimal("amount")));
            }
            return result;
        }
        catch (SQLException e)
        {
            throw new ApplicationException(e);
        }
    }

    public void calculateRevenueRecognitions(long contractNumber) throws ApplicationException
    {
        try
        {
            ResultSet contracts = db.findContract(contractNumber);
            contracts.next();
//            Money totalRevenue = Money.dollars(contracts.getBigDecimal("revenue"));
            Money totalRevenue = Money.dollars(contracts.getDouble("revenue"));
            MfDate recognitionDate = new MfDate(contracts.getDate("dateSigned"));
            String type = contracts.getString("type");
            if (type.equals("S"))
            {
                Money[] allocation = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(60));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(90));
            }
            else if (type.equals("W"))
            {
                db.insertRecognition(contractNumber, totalRevenue, recognitionDate);
            }
            else if (type.equals("D"))
            {
                Money[] allocation = totalRevenue.allocate(3);
                db.insertRecognition(contractNumber, allocation[0], recognitionDate);
                db.insertRecognition(contractNumber, allocation[1], recognitionDate.addDays(30));
                db.insertRecognition(contractNumber, allocation[2], recognitionDate.addDays(60));
            }
        }
        catch (SQLException e)
        {
            throw new ApplicationException(e);
        }
    }

    public int createProduct(String name, String type) throws SQLException {
        return db.insertProduct(name, type);
    }

    public int createContract(int product_id, double revenue, MfDate dateSigned) throws SQLException {
        return db.insertContract(product_id, revenue, dateSigned);
    }
}
