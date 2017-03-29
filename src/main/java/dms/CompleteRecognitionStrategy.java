package dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

//@Repository
//@Transactional(readOnly = true)
public class CompleteRecognitionStrategy extends RecognitionStrategy {
//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private RecognitionService rs;


//    @Transactional
    List<RevenueRecognition> calculateRevenueRecognitions(Contract contract) {
        List<RevenueRecognition> revenueRecognitions = new ArrayList<RevenueRecognition>();
        RevenueRecognition revenueRecognition;
        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
        revenueRecognitions.add(revenueRecognition);
        return revenueRecognitions;
//        rs.recognizedRevenueComplete(revenueRecognition);
    }
}
