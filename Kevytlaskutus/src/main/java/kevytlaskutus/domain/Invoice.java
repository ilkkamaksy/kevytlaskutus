package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Invoice data entity class. 
 * 
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Invoice {

    private int id; 
    
    private CustomerCompany customer;
    private ManagedCompany company;
    
    private List<Product> products;
    
    private Integer invoiceNumber;
    private Integer referenceNumber;
    private Date createdDate;
    private Integer paymentTerm;
    private Date dueDate;
    private BigDecimal discount;
    private Date discountDate;
    private BigDecimal penaltyInterest;
    private BigDecimal amount;
    private String customerContactName = "";
    private String customerReference = "";
    private String companyReference = "";
    private String deliveryTerms = "";
    private Date deliveryDate;
    private String deliveryInfo = "";
    private String additionalInfo = "";

    public Invoice() {
        this(new Date(new java.util.Date().getTime()));
    }
    
    public Invoice(Date createdDate) {
        this.createdDate = createdDate;
        this.referenceNumber = 0;
        this.paymentTerm = 14;
        this.dueDate = this.calculateDueDate(createdDate);
        this.penaltyInterest = new BigDecimal(10.00);
        this.amount = new BigDecimal(0);
        this.discount = new BigDecimal(0);
        this.deliveryDate = createdDate;
        this.products = new ArrayList<>();
    }

    private Date calculateDueDate(Date createdDate) {
        return new Date(createdDate.getTime() + this.paymentTerm * 24 * 60 * 60 * 1000);
    }
    
    public void setReferenceNumber(Integer value) {
        this.referenceNumber = value;
    }
}
