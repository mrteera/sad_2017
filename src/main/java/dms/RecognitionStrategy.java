package dms;

import java.util.List;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
public abstract class RecognitionStrategy {
    abstract List<RevenueRecognition> calculateRevenueRecognitions(Contract contract);
}
