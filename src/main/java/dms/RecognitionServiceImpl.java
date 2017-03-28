package dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import revenuerecognition.MfDate;
import revenuerecognition.Money;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by mrteera on 1/26/2017 AD.
 */
@Repository
@Transactional(readOnly = true)
public class RecognitionServiceImpl implements RecognitionService {
//    @PersistenceContext(name="NewPersistenceUnit")
//    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

//    @Autowired
//    private Product product;

    public void calculateRevenueRecognitions(long contractNumber) {
        System.out.println("contractID:" + contractNumber);
        Contract contract = Contract.read(contractNumber);
        contract.calculateRecognitions();
    }

    public Money recognizedRevenue(long contractNumber, MfDate asOf) {
        return Contract.read(contractNumber).recognizedRevenue(asOf);
    }

    @Transactional
    public long insertProductWP(String name)
    {
//        return Product.newWordProcessor(name);
        Product product = new Product(name, "WP", new CompleteRecognitionStrategy());
        entityManager.persist(product);
        return product.getId();
    }

    public long insertProductDatabase(String name)
    {
        return Product.newDatabase(name);
    }

    public long insertProductSpreadsheet(String name)
    {
        return Product.newSpreadsheet(name);
    }

    public Contract insertContract(long productID, Money revenue, MfDate whenSigned)
    {
        return  Contract.addNewContract(Product.read(productID), revenue, whenSigned);
    }

    public int countRecognitions()
    {	return Contract.countRecognitions();
    }
}
