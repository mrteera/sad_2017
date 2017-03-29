package dms;

import revenuerecognition.Money;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
public class ThreeWayRecognitionStrategy extends RecognitionStrategy {
    private int firstRecognitionOffset;
    private int secondRecognitionOffset;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset,int secondRecognitionOffset) {
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }

    RevenueRecognition calculateRevenueRecognitions(Contract contract) {
        RevenueRecognition revenueRecognition;
        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
        Money[] allocation = contract.getRevenue().allocate(3);
        contract.addRevenueRecognition(new RevenueRecognition(allocation[0], contract.getWhenSigned()));
        contract.addRevenueRecognition(new RevenueRecognition(allocation[1], contract.getWhenSigned().addDays(firstRecognitionOffset)));
        contract.addRevenueRecognition(new RevenueRecognition(allocation[2], contract.getWhenSigned().addDays(secondRecognitionOffset)));
        return revenueRecognition;
    }
}
