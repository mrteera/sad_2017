package revenuerecognition;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by mrteera on 1/24/2017 AD.
 */
class RecognitionServiceImplTest {
    private int contract_id;
    private MfDate test_date;
    private Money money;
    private RecognitionService recognition_service;

    void resetDB() {
        String command = "/Users/mrteera/workspace/sad/dump_db.sh";
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(command);
            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    void setUp() throws SQLException {
        resetDB();
        test_date = new MfDate(2017, 1, 24);
        money = money.dollars(10000);
        recognition_service = new RecognitionService();
        int product_id = recognition_service
                .createProduct("AIS", "S");
        contract_id = recognition_service
                .createContract(product_id, 10000, test_date);
    }

    @Test
    void recognizedRevenueTest() throws SQLException {
        Money expected = Money.dollars(10000);
        Money actual = null;
        try {
            recognition_service.getGateway().insertRecognition(contract_id, money, test_date);
            actual = recognition_service
                    .recognizedRevenue(contract_id, test_date);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        assertEquals(expected.amount(), actual.amount());
    }

    @Test
    void calculateRevenueRecognitionsTest() throws SQLException, ApplicationException {
        double actual = 0;
        recognition_service
            .calculateRevenueRecognitions(
                contract_id
            );
        ResultSet rs = recognition_service.getGateway()
                .findRecognitionsFor(contract_id, test_date.addDays(90));
        ResultSetMetaData meta = rs.getMetaData();
        rs.next();
        for (int i = 1 ; i <= rs.getRow(); i++){
            double my_value = rs.getDouble(1);
            actual += rs.getDouble(1);
            rs.next();
        }
        assertEquals(10000, actual);
    }
}