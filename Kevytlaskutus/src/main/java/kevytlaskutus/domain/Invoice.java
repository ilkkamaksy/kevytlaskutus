package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private BigDecimal vatPercentage;
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
        this.penaltyInterest = BigDecimal.valueOf(10);
        this.vatPercentage = BigDecimal.ZERO;
        this.amount = BigDecimal.ZERO;
        this.discount = BigDecimal.ZERO;
        this.deliveryDate = createdDate;
        this.setDueDateByCreatedDate();
        
        this.products = new ArrayList<>();
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        this.setDueDateByCreatedDate();
    }
    
    public void setPaymentTerm(Integer term) {
        this.paymentTerm = term;
        this.setDueDateByCreatedDate();
    }
    
    private void setDueDateByCreatedDate() {
        this.dueDate = new Date(this.createdDate.getTime() + this.paymentTerm * 24 * 60 * 60 * 1000);
    }
   
    public void setReferenceNumber(Integer value) {
        this.referenceNumber = value;
    }
    
    public BigDecimal getAmount() {
        return this.amount.setScale(2, RoundingMode.CEILING);
    }
}
