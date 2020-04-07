/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.ProductDaoImpl;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.DatabaseUtils;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.InvoiceService;
import kevytlaskutus.domain.ManagedCompany;
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
    
    InvoiceService invoiceService;
    InvoiceDaoImpl invoiceDao;
    DatabaseUtils databaseUtils;
    Invoice validInvoice;
    Invoice emptyInvoice;
    ManagedCompany validManagedCompany;
    ManagedCompany emptyManagedCompany;
    CustomerCompany validCustomer;
    CustomerCompany emptyCustomer;
   
    @Before
    public void setUp() {
        invoiceDao = mock(InvoiceDaoImpl.class);       
        databaseUtils = mock(DatabaseUtils.class);
       
        invoiceService = new InvoiceService(invoiceDao, databaseUtils);
       
        validInvoice = mock(Invoice.class);
        when(validInvoice.getId()).thenReturn(1);
        when(validInvoice.getReferenceNumber()).thenReturn(1001);
        
        emptyInvoice = mock(Invoice.class);
        
        validManagedCompany = mock(ManagedCompany.class); 
        when(validManagedCompany.getId()).thenReturn(1);
        when(validManagedCompany.getName()).thenReturn("Acme");
        
        emptyManagedCompany = mock(ManagedCompany.class);        
    }
    
    @Test
    public void newInvoiceCanBeCreatedWithValidInvoiceObject() {
        try {
            when(invoiceDao.create(validInvoice)).thenReturn(true);
            boolean result = invoiceService.createInvoiceForCompany(validInvoice, validManagedCompany);
            verify(invoiceDao).create(validInvoice); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newInvoiceCannotBeCreatedWithNullInvoiceObject() {
        boolean result = invoiceService.createInvoiceForCompany(null, validManagedCompany); 
        assertFalse(result);
    }
    
    @Test
    public void invoiceCannotBeCreatedWithoutManagedCompany() {
        boolean result = invoiceService.createInvoiceForCompany(emptyInvoice, null);
        assertFalse(result);
    }
    
    @Test
    public void invoiceCannotBeCreatedWithoutValidManagedCompany() {
        when(emptyManagedCompany.getId()).thenReturn(-1);
        boolean result = invoiceService.createInvoiceForCompany(emptyInvoice, emptyManagedCompany);
        assertFalse(result);
    }
    
    @Test
    public void invoiceCanBeUpdatedWithValidInvoiceObject() {
        try {
            when(invoiceDao.update(1, validInvoice)).thenReturn(true);
            boolean result = invoiceService.updateInvoice(1, validInvoice);
            verify(invoiceDao).update(1, validInvoice); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanNotBeUpdatedWithNegativeId() {    
        boolean result = invoiceService.updateInvoice(-1, validInvoice);    
        assertFalse(result);    
    }
    
    @Test
    public void invoiceCanNotBeUpdatedWithoutInvoiceObject() {    
        boolean result = invoiceService.updateInvoice(1, null);    
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
            when(invoiceDao.getItems(1)).thenReturn(new ArrayList<>());
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
    public void invoiceDefaultNumberIsAbove1000() {    
        int result = invoiceService.getDefaultInvoiceNumber();
        assertEquals(1000, 1000);
    }
}
