package dms;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;
import revenuerecognition.MfDate;
import revenuerecognition.Money;

import static org.junit.Assert.*;

/**
 * Created by mrteera on 3/28/2017 AD.
 */
//@Commit
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class RecognitionServiceImplTest extends AbstractRecognitionTest {
    MfDate dateToday;
    @Autowired RecognitionService rs;

    @Before
    public void setUp() {
        dateToday = new MfDate();
    }

    @Test
    public void calculateRevenueRecognitionsServiceThreeWay() {
        long newContractID = rs.insertContract(rs.insertProductSpreadsheet(
            "ais"),
            Money.dollars(1500),
            dateToday
        ).getId();
        rs.calculateRevenueRecognitions(newContractID);
        Money result = rs.recognizedRevenue(
            newContractID,
            dateToday.addDays(90)
        );

        assertEquals(1500.0, result.amount().doubleValue(), 0);
        assertEquals(3, rs.countRecognitions());
    }

        @Test
        public void testCalculaterevenuerecognitionsservicecomplete() throws InterruptedException {
            long newContractID = rs.insertContract(rs.insertProductWP(
                    "emacs"),
                    Money.dollars(1500),
                    dateToday.addDays(-1)
            ).getId();
            rs.calculateRevenueRecognitions(newContractID);

            Money result = rs.recognizedRevenue(
                    1,
                    dateToday.addDays(-1)
            );
            assertEquals(1500, result.amount().doubleValue(), 0);
        }
}
