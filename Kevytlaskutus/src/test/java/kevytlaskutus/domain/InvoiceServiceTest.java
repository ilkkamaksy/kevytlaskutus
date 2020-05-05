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
import kevytlaskutus.dao.ProductDaoImpl;
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
    ProductDaoImpl mockProductDao;
    
    InvoiceService invoiceService;
    ProductService mockProductService;
    
    Invoice mockInvoice;
    ManagedCompany mockCompany;
    CustomerCompany mockCustomer;
   
    @Before
    public void setUp() {
        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);
        
        mockProductService = mock(ProductService.class);
        
        databaseUtils = new DatabaseUtils(
                mockManagedCompanyDao, 
                mockCustomerCompanyDao, 
                mockInvoiceDao,
                mockProductDao,
                "jdbc:h2:mem:testdb", 
                "sa", 
                ""
        );
        databaseUtils.initDb();
        invoiceService = new InvoiceService(mockInvoiceDao, databaseUtils);
        invoiceService.setProductService(mockProductService);
        
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
            Invoice invoice = new Invoice();
            ManagedCompany company = new ManagedCompany();
            company.setName("Acme");
            company.setId(1);
            Product prod = new Product();
            prod.setName("tuote");
            invoice.setCompany(company);
            invoice.getProducts().add(prod);
            invoice.setReferenceNumber(123);
            assertEquals(Integer.valueOf(123), invoice.getReferenceNumber());
            
            when(mockInvoiceDao.create(invoice)).thenReturn(1);
            boolean success = invoiceService.saveInvoice(invoice, company);

            assertTrue(success);
        } catch (SQLException e) {}
    }
   
    @Test
    public void invoiceCannotBeCreatedWithoutManagedCompany() {
        boolean success = invoiceService.saveInvoice(mockInvoice, null);
        assertFalse(success);
    }
    
    @Test
    public void invoiceCannotBeCreatedWithoutValidManagedCompany() {
        when(mockCompany.getId()).thenReturn(-1);
        boolean success = invoiceService.saveInvoice(mockInvoice, mockCompany);
        assertFalse(success);
    }
    
    @Test
    public void invoiceCanBeUpdatedWithValidInvoiceObject() {
        try {
            when(mockInvoiceDao.update(1, mockInvoice)).thenReturn(true);
            boolean result = invoiceService.saveInvoice(mockInvoice, mockCompany);
            verify(mockInvoiceDao).update(1, mockInvoice); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanNotBeUpdatedWithNegativeId() {    
        boolean result = invoiceService.saveInvoice(mockInvoice, mockCompany);    
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
    
    @Test
    public void productsAreUpdatedInBatchesIfInvoiceHasAny() {
        try {
            Invoice invoice = new Invoice();
            invoice.setId(1);
            invoice.setReferenceNumber(123);

            ManagedCompany company = new ManagedCompany();
            company.setName("Acme");
            company.setId(1);
            invoice.setCompany(company);
            
            Product prod1 = new Product();
            prod1.setId(1);
            prod1.setName("vanha");
            Product prod2 = new Product();
            prod2.setName("uusi");
            invoice.getProducts().add(prod1);
            invoice.getProducts().add(prod2);
            
            when(mockInvoiceDao.update(1, invoice)).thenReturn(true);
            when(this.mockProductService.saveProductsInBatches(1, invoice.getProducts())).thenReturn(true);
            
            boolean success = invoiceService.saveInvoice(invoice, company);
            
            assertTrue(success);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanNotBeCreatedWithoutExistingManagedCompany() {
        Invoice invoice = new Invoice();
        invoice.setReferenceNumber(123);
        boolean success = invoiceService.saveInvoice(invoice, new ManagedCompany());
        assertFalse(success);    
    }
    
    @Test
    public void getInvoiceByIdReturnsNullIfNotFound() {
        try {
            when(mockInvoiceDao.getItemById(1)).thenThrow(new SQLException("virhe"));
            Invoice invoice = invoiceService.getInvoiceById(1);
            assertNull(invoice);    
        } catch (SQLException e) {}
    }
}
