package dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

//@Repository
//@Transactional(readOnly = true)
public class CompleteRecognitionStrategy extends RecognitionStrategy {
//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    private RecognitionService rs;


//    @Transactional
    RevenueRecognition calculateRevenueRecognitions(Contract contract) {
        RevenueRecognition revenueRecognition;
        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
        return revenueRecognition;
//        rs.recognizedRevenueComplete(revenueRecognition);
    }
}
