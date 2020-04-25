/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

/**
 *
 * @author ilkka
 */
public abstract class FormInvoiceAction extends FormAction {
    
    protected Invoice invoice; 
    private static FormFieldDataExtractor dataExtractor;
 
    public FormInvoiceAction(AppService appService) {
        super(appService);
    }

    public abstract boolean execute();
    
    protected void makeInvoiceFromFieldValues(HashMap<String, Node> formFields, int id) {
        
        this.invoice = new Invoice(dataExtractor.createDateFromDatePicker("Date", formFields));
        this.invoice.setId(id);
        
        String customerName = dataExtractor.getSelectedValueFromComboBox("Customer", formFields);
        CustomerCompany customer = this.appService.getCustomerCompanyByName(customerName);
        this.invoice.setCustomer(customer);    
        
        this.invoice.setInvoiceNumber(dataExtractor.getIntFromTextField("Invoice Number", formFields));
        this.invoice.setReferenceNumber(dataExtractor.getIntFromTextField("Reference Number", formFields));
        
        this.invoice.setPaymentTerm(dataExtractor.getIntFromTextField("Payment due in number of days", formFields));
        this.invoice.setDueDate(dataExtractor.createDateFromDatePicker("Due Date", formFields));
        
        this.invoice.setCustomerContactName(dataExtractor.getValueFromTextField("Customer Contact Name", formFields));
        this.invoice.setCustomerReference(dataExtractor.getValueFromTextField("Customer Reference", formFields));
        this.invoice.setCompanyReference(dataExtractor.getValueFromTextField("Our Reference", formFields));
        
        this.invoice.setDeliveryTerms(dataExtractor.getValueFromTextField("Delivery Terms", formFields));
        this.invoice.setDeliveryDate(dataExtractor.createDateFromDatePicker("Delivery Date", formFields));
        this.invoice.setDeliveryInfo(dataExtractor.getValueFromTextField("Delivery Information", formFields));
        
        this.invoice.setAdditionalInfo(dataExtractor.getValueFromTextField("Additional Information", formFields));
        
        BigDecimal overDueInterest = dataExtractor.getBigDecimalFromTextField("Overdue Penalty Interest rate", formFields);
        if (overDueInterest != null && isBetween(overDueInterest, new BigDecimal(0), new BigDecimal(100))) {
            this.invoice.setPenaltyInterest(overDueInterest);    
        }
               
        BigDecimal discount = dataExtractor.getBigDecimalFromTextField("Discount", formFields);
        if (discount != null && isBetween(discount, new BigDecimal(0), new BigDecimal(100))) {
            this.invoice.setDiscount(discount);
        }
        
        BigDecimal amount = dataExtractor.getBigDecimalFromTextField("Amount", formFields);
        if (amount != null && isBetween(amount, new BigDecimal(0), new BigDecimal(1000000))) {
            this.invoice.setAmount(amount);
        }    
        
        int i = 1;
        for (String fieldLabel : formFields.keySet()) {
            if (fieldLabel.contains("Product Name #" + i)) {

                Product product = new Product();
                
                Integer productId = dataExtractor.getIntFromTextField("Product ID #" + i, formFields);
                if (productId != 0) {
                    product.setId(productId); 
                }
                
                product.setName(dataExtractor.getValueFromTextField("Product Name #" + i, formFields));
                
                BigDecimal price = dataExtractor.getBigDecimalFromTextField("Product Price #" + i, formFields);
                product.setPrice(price);
                
                product.setPriceUnit(dataExtractor.getValueFromTextField("Product Price Unit #" + i, formFields));
                product.setDescription(dataExtractor.getValueFromTextField("Product Description #" + i, formFields));
                if (!product.getName().isEmpty()) {
                    this.invoice.getProducts().add(product);
                }
                i++;
            }
        }
        
        if (invoice.getProducts().size() == 0) {
            invoice.getProducts().add(new Product());
        }
    }
    
    private static boolean isBetween(BigDecimal value, BigDecimal start, BigDecimal end) {
        return value.compareTo(start) > 0 && value.compareTo(end) < 0;
    }
}
