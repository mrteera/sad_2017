package revenuerecognition;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.math.BigDecimal;

/**
 * Created by mrteera on 1/19/2017 AD.
 */
public class RevenueRecognition {
    public static void main(String[] args) throws ParseException {
        System.out.println("TvT\n");
        Gateway gw = new Gateway();
        try {
            ResultSet contact1 = gw.findContract(1);
            ResultSetMetaData rsmd = contact1.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (contact1.next()) {
                for(int i = 1 ; i <= columnsNumber; i++){
                    System.out.print(contact1.getString(i) + " ");
                }
                System.out.println();
            }
            System.out.println("findContract works!\n");

            System.out.println("His revenue recognition:\n");
            ResultSet recog = gw.findRecognitionsFor(1, MfDate.today());
            ResultSetMetaData rsmd2 = recog.getMetaData();
            int columnsNumber2 = rsmd2.getColumnCount();
            while (recog.next()) {
                for(int i = 1 ; i <= columnsNumber2; i++){
                    System.out.print(recog.getString(i) + " ");
                }
                System.out.println();
            }
            System.out.println("Revenue recognition for him works!\n");

            // It works!
//            System.out.println("Insert revenue...\n");
//            Money my_money;
//            my_money = Money.dollars(2.51);
//            gw.insertRecognition(1, my_money, MfDate.today());

//            RecognitionService mrs = new RecognitionService();
//            try {
//                Money mmm = mrs.recognizedRevenue(1, MfDate.today());
//                System.out.println(mmm.toString());
//                mrs.calculateRevenueRecognitions(1);
//            } catch (ApplicationException e) {
//                e.printStackTrace();
//            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
