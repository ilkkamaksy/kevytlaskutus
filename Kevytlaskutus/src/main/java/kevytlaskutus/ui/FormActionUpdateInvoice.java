/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;

/**
 *
 * @author ilkka
 */
public class FormActionUpdateInvoice extends FormAction {
    
    private Invoice invoice; 
    private static FormFieldDataExtractor dataExtractor;
    
    public FormActionUpdateInvoice(AppService appService) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.makeInvoiceFromFieldValues(formFields, id);
    }
    
    @Override
    public boolean execute() {
        return super.appService.updateInvoice(this.invoice.getId(), this.invoice);
    }
  
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
        
        System.out.println(invoice.getCreatedDate() + " " + invoice.getDiscount());
    }
    
    public static boolean isBetween(BigDecimal value, BigDecimal start, BigDecimal end) {
        return value.compareTo(start) > 0 && value.compareTo(end) < 0;
    }
}
