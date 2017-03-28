package dms;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
public class CompleteRecognitionStrategy extends RecognitionStrategy {
    void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned()));
    }
}
