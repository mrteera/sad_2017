package dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional(readOnly = true)
public class CompleteRecognitionStrategy extends RecognitionStrategy {
    @PersistenceContext
    private EntityManager entityManager;


    @Transactional
    void calculateRevenueRecognitions(Contract contract) {
        RevenueRecognition revenueRecognition;
        revenueRecognition = new RevenueRecognition(contract.getRevenue(),contract.getWhenSigned());
        System.out.println("------------REVENUERECOGNITION---------------");
        System.out.println(contract.getRevenue().amount());
        System.out.println(contract.getWhenSigned());
        System.out.println(revenueRecognition.getAmount().amount());
        System.out.println("------------REVENUERECOGNITION---------------");
        entityManager.persist(revenueRecognition);
    }
}
