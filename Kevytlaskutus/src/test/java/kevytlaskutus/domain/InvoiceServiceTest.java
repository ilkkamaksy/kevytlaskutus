/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.domain.InvoiceService;
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
public class InvoiceServiceTest {
    
        
    DatabaseUtils databaseUtils;

    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    InvoiceDaoImpl mockInvoiceDao;

    InvoiceService invoiceService;
    
    Invoice mockInvoice;
    ManagedCompany mockCompany;
    CustomerCompany mockCustomer;
   
    @Before
    public void setUp() {
        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        
        databaseUtils = new DatabaseUtils(
                mockManagedCompanyDao, 
                mockCustomerCompanyDao, 
                mockInvoiceDao,
                "jdbc:h2:mem:testdb", 
                "sa", 
                ""
        );
        databaseUtils.initDb();
        invoiceService = new InvoiceService(mockInvoiceDao, databaseUtils);
       
        mockInvoice = mock(Invoice.class);
        when(mockInvoice.getId()).thenReturn(1);
        when(mockInvoice.getReferenceNumber()).thenReturn(1001);
       
        mockCompany = mock(ManagedCompany.class); 
        when(mockCompany.getId()).thenReturn(1);
        when(mockCompany.getName()).thenReturn("Acme");
    }
    
    @Test
    public void newInvoiceCanBeCreatedWithValidInvoiceObject() {
        try {
            when(mockInvoiceDao.create(mockInvoice)).thenReturn(true);
            boolean result = invoiceService.createInvoiceForCompany(mockInvoice, mockCompany);
            verify(mockInvoiceDao).create(mockInvoice); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newInvoiceCannotBeCreatedWithNullInvoiceObject() {
        boolean result = invoiceService.createInvoiceForCompany(null, mockCompany); 
        assertFalse(result);
    }
    
    @Test
    public void invoiceCannotBeCreatedWithoutManagedCompany() {
        boolean result = invoiceService.createInvoiceForCompany(mockInvoice, null);
        assertFalse(result);
    }
    
    @Test
    public void invoiceCannotBeCreatedWithoutValidManagedCompany() {
        when(mockCompany.getId()).thenReturn(-1);
        boolean result = invoiceService.createInvoiceForCompany(mockInvoice, mockCompany);
        assertFalse(result);
    }
    
    @Test
    public void invoiceCanBeUpdatedWithValidInvoiceObject() {
        try {
            when(mockInvoiceDao.update(1, mockInvoice)).thenReturn(true);
            boolean result = invoiceService.updateInvoice(1, mockInvoice, mockCompany);
            verify(mockInvoiceDao).update(1, mockInvoice); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanNotBeUpdatedWithNegativeId() {    
        boolean result = invoiceService.updateInvoice(-1, mockInvoice, mockCompany);    
        assertFalse(result);    
    }
   
    @Test
    public void invoiceCanNotBeDeletedWithInvalidId() {    
        boolean result = invoiceService.deleteInvoice(-1);    
        assertFalse(result);    
    }
    
    @Test
    public void invoiceListAlwaysReturnsAlist() {    
        try {
            when(mockInvoiceDao.getItems(1)).thenReturn(new ArrayList<>());
            List<Invoice> results = invoiceService.getInvoicesForCompany(1);
            assertEquals(results.size(), 0);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceListIsEmptyWithoutValidCompanyId() {    
        List<Invoice> results = invoiceService.getInvoicesForCompany(-1);
        assertEquals(results.size(), 0);
    }
    
    @Test
    public void invoiceDefaultNumberIsAbove1001() {    
        int result = invoiceService.getDefaultInvoiceNumber();
        assertEquals(result, 1001);
    }
    
    @Test
    public void invoiceCanBeRetrievedById() {    
        try {
            when(mockInvoiceDao.getItemById(1)).thenReturn(mockInvoice);
            Invoice invoice = invoiceService.getInvoiceById(1);
            assertEquals(invoice.getId(), mockInvoice.getId());    
        } catch (SQLException e) {}
    }
    
}
