package dms;

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
        @Autowired RecognitionService rs;

//    private RecognitionService rs;

//        RecognitionServiceImpl rs = new RecognitionServiceImpl();

        MfDate dateToday;

//    @BeforeEach
//    void setUp() {
//        dateToday = new MfDate();
//    }

    @Test
    public void calculateRevenueRecognitionsServiceThreeWay() {
        dateToday = new MfDate();
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

        assertTrue(1500 == result.amount().doubleValue());
//        System.out.println("33333333333333333333");
//        System.out.println(rs.countRecognitions());
//        System.out.println("33333333333333333333");
//        assertTrue(3 == rs.countRecognitions());
//        assertEquals(3, rs.countRecognitions());
    }

//        @Test
//        public void testCalculaterevenuerecognitionsservicecomplete() throws InterruptedException {
//            dateToday = new MfDate();
//            long newContractID = rs.insertContract(rs.insertProductWP(
//                    "emacs"),
//                    Money.dollars(1500),
//                    dateToday.addDays(-1)
//            ).getId();
//            rs.calculateRevenueRecognitions(newContractID);
//
//            Money result = rs.recognizedRevenue(
//                    1,
//                    dateToday.addDays(-1)
//            );
//            assertTrue(1500 == result.amount().doubleValue());
//        }
}
