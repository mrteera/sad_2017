package dms;

import revenuerecognition.MfDate;
import revenuerecognition.Money;

/**
 * Created by mrteera on 3/28/2017 AD.
 */
public interface RecognitionService {
    long insertProductWP(String name);
    Contract insertContract(long productID, Money revenue, MfDate whenSigned);
    void calculateRevenueRecognitions(long contractNumber);
    boolean checkRevenue();
}
