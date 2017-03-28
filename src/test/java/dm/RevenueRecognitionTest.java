package dm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import revenuerecognition.*;

import javax.persistence.*;
import java.sql.SQLException;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
class RevenueRecognitionTest {
    static private EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUp() throws Exception {
        entityManagerFactory = Persistence.createEntityManagerFactory( "NewPersistenceUnit" );
    }

    @AfterAll
    static void tearDown() throws Exception {
        entityManagerFactory.close();
    }

    @Test
    public void testCalculateRevenueRecognitionThreeWayProduct() throws SQLException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Product threeWayProduct = new Product("Oracle", "DB", new ThreeWayRecognitionStrategy(30, 60));
        Contract contract = new Contract(threeWayProduct, Money.dollars(30000), new MfDate());
        entityManager.persist(threeWayProduct);
        entityManager.persist(contract);
        entityManager.getTransaction().commit();
        entityManager.close();
        int beforeInsert = contract._getRevenueRecognitionSize();
        contract.calculateRecognitions();
        int afterInsert =  contract._getRevenueRecognitionSize();

        Money resultAmount = contract
                .recognizedRevenue(contract.getWhenSigned().addDays(90));

        assertEquals(0, beforeInsert);
        assertEquals(3, afterInsert);
        assertEquals(30000, resultAmount.amount().doubleValue());
    }

    @Test
    public void testCalculateRevenueRecognitionCompleteProduct() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Product completeProduct = new Product("emacs", "WORD", new CompleteRecognitionStrategy());
        Contract contract = new Contract(completeProduct, Money.dollars(10000), new MfDate());
        entityManager.persist(completeProduct);
        entityManager.persist(contract);
        entityManager.getTransaction().commit();
        entityManager.close();
        contract.calculateRecognitions();
        Money resultAmount = contract
                .recognizedRevenue(contract.getWhenSigned());
        assertEquals(10000, resultAmount.amount().doubleValue());
    }
}