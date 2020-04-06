/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;

/**
 *
 * @author ilkka
 */
public class FormActionSaveNewInvoice extends FormAction {
    private Invoice invoice; 
    
    public FormActionSaveNewInvoice(AppService appService) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.makeInvoiceFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createInvoice(this.invoice);
    }
    
    protected void makeInvoiceFromFieldValues(HashMap<String, Node> formFields, int id) {
        
        // Date
        DatePicker createdDate = (DatePicker) formFields.get("Date");
        
        // Customer id
        ComboBox customerDropDown = (ComboBox) formFields.get("Customer");
        String customerName = customerDropDown.getValue().toString();
        CustomerCompany customer = this.appService.getCustomerCompanyByName(customerName);
        
        // Invoice number
        TextField invoiceNumberField = (TextField) formFields.get("Invoice Number");
        int invoiceNumber = Integer.valueOf(invoiceNumberField.getText());
        
        // Payment terms
        TextField paymentTermsField = (TextField) formFields.get("Payment due in number of days");
        int paymentTerm = Integer.valueOf(paymentTermsField.getText());
        
        // Due Date
        DatePicker dueDateField = (DatePicker) formFields.get("Due Date");

        // Overdue interest 
        TextField overDueInterestField = (TextField) formFields.get("Overdue Penalty Interest rate");
        BigDecimal overDueInterest = new BigDecimal(overDueInterestField.getText());
        
        // Discount
        TextField discountField = (TextField) formFields.get("Discount");
        BigDecimal discount = new BigDecimal(discountField.getText());
        
        // Customer Contact Name
        TextField customerContactField = (TextField) formFields.get("Customer Contact Name");
        String customerContactName = customerContactField.getText();
        
        // Customer Reference
        TextField customerReferenceField = (TextField) formFields.get("Customer Reference");
        String customerReference = customerReferenceField.getText();

        // Our Reference
        TextField companyReferenceField = (TextField) formFields.get("Our Reference");
        String companyReference = companyReferenceField.getText();
        
        // Delivery Terms
        TextField deliveryTermsField = (TextField) formFields.get("Delivery Terms");
        String deliveryTerms = deliveryTermsField.getText();
        
        // Delivery Date
        DatePicker deliveryDateField = (DatePicker) formFields.get("Delivery Date");
        
        // Delivery Information
        TextField deliveryInfoField = (TextField) formFields.get("Delivery Information");
        String deliveryInfo = deliveryInfoField.getText();
        
        // Additional Information
        TextField additionalInfoField = (TextField) formFields.get("Additional Information");
        String additionalInfo = additionalInfoField.getText();

        // Amount
        TextField amountField = (TextField) formFields.get("Amount");
        BigDecimal amount = new BigDecimal(amountField.getText());
            
        this.invoice = new Invoice(Date.valueOf(createdDate.getValue()));
        this.invoice.setId(id);
        this.invoice.setInvoiceNumber(invoiceNumber);
        this.invoice.setCustomerId(customer.getId());
        this.invoice.setInvoiceNumber(invoiceNumber);
        this.invoice.setPaymentTerm(paymentTerm);
        this.invoice.setDueDate(Date.valueOf(dueDateField.getValue()));
        this.invoice.setPenaltyInterest(overDueInterest);
        this.invoice.setDiscount(discount);
        this.invoice.setCustomerContactName(customerContactName);
        this.invoice.setCustomerReference(customerReference);
        this.invoice.setCompanyReference(companyReference);
        this.invoice.setDeliveryTerms(deliveryTerms);
        this.invoice.setDueDate(Date.valueOf(deliveryDateField.getValue()));
        this.invoice.setDeliveryInfo(deliveryInfo);
        this.invoice.setAdditionalInfo(additionalInfo);
        this.invoice.setAmount(amount);
    }
}
