/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 *
 * @author ilkka
 */
public class InvoiceTest {
    
    Invoice invoice;
    Invoice mockInvoice;
    CustomerCompany mockCustomer;
    ManagedCompany mockCompany;
      
    @Before
    public void setUp() {
        invoice = new Invoice();
        
        mockCompany = mock(ManagedCompany.class); 
        when(mockCompany.getId()).thenReturn(1);
        when(mockCompany.getName()).thenReturn("yritys");
        
        mockCustomer = mock(CustomerCompany.class);
        when(mockCompany.getId()).thenReturn(2);
        when(mockCompany.getName()).thenReturn("asiakas");
        
        mockInvoice = mock(Invoice.class);
    }
    
   
    @Test
    public void customerCanBeSet() {
        invoice.setCustomer(mockCustomer);
        assertEquals(invoice.getCustomer().getName(), mockCustomer.getName());
    }
    
    @Test
    public void customerCanBeRetrieved() {
        invoice.setCustomer(mockCustomer);
        CustomerCompany result = invoice.getCustomer();
        assertEquals(result.getName(), mockCustomer.getName());
    }

    @Test
    public void managedCompanyCanBeSet() {
        invoice.setCompany(mockCompany);
        assertEquals(invoice.getCompany().getName(), mockCompany.getName());
    }

    @Test
    public void managedCompanyCanBeRetrieved() {
        invoice.setCompany(mockCompany);
        ManagedCompany result = invoice.getCompany();
        assertEquals(result.getName(), mockCompany.getName());
    }

   
    @Test
    public void testGetInvoiceNumber() {
        invoice.setInvoiceNumber(1);
        int result = invoice.getInvoiceNumber();
        assertEquals(result, 1);
    }


    @Test
    public void invoiceIsInitializedWithTodaysDate() {
        Date today = new Date(new java.util.Date().getTime());
        assertEquals(invoice.getCreatedDate().toString(), today.toString());
    }

    @Test
    public void paymentTermInitializedAs14Days() {
        assertEquals(invoice.getPaymentTerm(), Integer.valueOf(14));
    }

    @Test
    public void testSetPaymentTerm() {
        invoice.setPaymentTerm(10);
        assertEquals(invoice.getPaymentTerm(), Integer.valueOf(10));
    }

    @Test
    public void dueDateIsInitially14DaysFromToday() {
        Date today = new Date(new java.util.Date().getTime());
        Date dueDate = new Date(today.getTime() + 14 * 24 * 60 * 60 * 1000);
        assertEquals(invoice.getDueDate().toString(), dueDate.toString());
    }

    @Test
    public void dueDateCanBeSet() {
        Date today = new Date(new java.util.Date().getTime());
        invoice.setDueDate(today);
        assertEquals(invoice.getDueDate().toString(), today.toString());
    }

    @Test
    public void discountInitiallZero() {
        assertEquals(invoice.getDiscount(), new BigDecimal(0.0));
    }

    @Test
    public void testSetDiscount() {
        BigDecimal discount = new BigDecimal(10.0);        
        invoice.setDiscount(discount);
        assertEquals(invoice.getDiscount(), discount);
    }

    @Test
    public void penaltyInterestInitiallyTenPercent() {
        BigDecimal penaltyInterest = new BigDecimal(10.0);
        assertEquals(invoice.getPenaltyInterest(), penaltyInterest);
    }

    @Test
    public void penaltyInterestCanBeSet() {
        BigDecimal penaltyInterest = new BigDecimal(15.0);
        invoice.setPenaltyInterest(penaltyInterest);
        assertEquals(invoice.getPenaltyInterest(), penaltyInterest);
    }

    @Test
    public void sumInitiallyZero() {
        BigDecimal sum = BigDecimal.ZERO;
        assertEquals(invoice.getAmount(), sum.setScale(2, RoundingMode.CEILING));
    }

    @Test
    public void testSetAmount() {
        BigDecimal sum = new BigDecimal(10);
        invoice.setAmount(sum);
        assertEquals(invoice.getAmount(), sum.setScale(2, RoundingMode.CEILING));
    }

    public void customerContactNameInitiallyEmpty() {
        assertTrue(invoice.getCustomerContactName().isEmpty());
    }

    @Test
    public void testSetCustomerContactName() {
        String customerContactName = "test";
        invoice.setCustomerContactName(customerContactName);
        assertEquals(invoice.getCustomerContactName(), customerContactName);
    }

    @Test
    public void customerReferenceInitiallyEmpty() {
        assertTrue(invoice.getCustomerReference().isEmpty());
    }

    @Test
    public void testSetCustomerReference() {
        String customerReference = "test";
        invoice.setCustomerReference(customerReference);
        assertEquals(invoice.getCustomerReference(), customerReference);
    }

    @Test
    public void companyReferenceInitiallyEmpty() {
        assertTrue(invoice.getCompanyReference().isEmpty());
    }

    @Test
    public void testSetCompanyReference() {
        String companyReference = "test";
        invoice.setCompanyReference(companyReference);
        assertEquals(invoice.getCompanyReference(), companyReference);
    }

    @Test
    public void deliveryTermsInitiallyEmpty() {
        assertTrue(invoice.getDeliveryTerms().isEmpty());
    }

    @Test
    public void testSetDeliveryTerms() {
        String terms = "test";
        invoice.setDeliveryTerms(terms);
        assertEquals(invoice.getDeliveryTerms(), terms);
    }

    @Test
    public void deliveryDateInitiallyToday() {
        Date today = new Date(new java.util.Date().getTime());
        assertEquals(invoice.getDeliveryDate().toString(), today.toString());
    }

    @Test
    public void testSetDeliveryDate() {
        Date today = new Date(new java.util.Date().getTime());
        invoice.setDeliveryDate(today);
        assertEquals(invoice.getDeliveryDate().toString(), today.toString());
    }

    @Test
    public void deliveryInfoInitiallyEmpty() {
        assertTrue(invoice.getDeliveryInfo().isEmpty());
    }

    @Test
    public void testSetDeliveryInfo() {
        String info = "test";
        invoice.setDeliveryInfo(info);
        assertEquals(invoice.getDeliveryInfo(), info);
    }

    @Test
    public void additionalInfoInitiallyEmpty() {
        assertTrue(invoice.getAdditionalInfo().isEmpty());
    }

    @Test
    public void testSetAdditionalInfo() {
        String info = "test";
        invoice.setAdditionalInfo(info);
        assertEquals(invoice.getAdditionalInfo(), info);
    }
    
    @Test
    public void invoiceEqualsSameInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(1001);
        assertTrue(invoice.equals(invoice));
    }
    
    @Test
    public void invoiceDoesNotEqualDifferentInvoice() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(1001);
        assertFalse(invoice.equals(mockInvoice));
    }
   
}
