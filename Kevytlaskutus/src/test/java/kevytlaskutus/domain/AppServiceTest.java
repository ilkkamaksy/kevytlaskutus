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
public class AppServiceTest {
    
    AppService appService;
    InvoiceService invoiceService;
    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    ProductDaoImpl mockProductDao;
    InvoiceDaoImpl mockInvoiceDao;
    
    ManagedCompany mockManagedCompany;
    CustomerCompany mockCustomer;
    Invoice mockInvoice;
    
    @Before
    public void setUp() {
        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);
        
        DatabaseUtils databaseUtils = new DatabaseUtils(
                mockManagedCompanyDao, 
                mockCustomerCompanyDao, 
                mockProductDao, 
                mockInvoiceDao,
                "jdbc:h2:mem:testdb", 
                "sa", 
                ""
        );
        databaseUtils.initDb();
        
        appService = new AppService(mockManagedCompanyDao, mockCustomerCompanyDao, mockProductDao, mockInvoiceDao, databaseUtils);
        
        mockInvoice = mock(Invoice.class);
        
        mockManagedCompany = mock(ManagedCompany.class); 
        when(mockManagedCompany.getId()).thenReturn(1);
        when(mockManagedCompany.getName()).thenReturn("Acme");

        mockCustomer = mock(CustomerCompany.class); 
        when(mockCustomer.getId()).thenReturn(1);
        when(mockCustomer.getName()).thenReturn("Acme");
    }
    
    @Test
    public void getCurrentManagedCompanyReturnsCompanyObject() {
        Company company = this.appService.getCurrentManagedCompany();
        assertNotNull(company);
    }
    
    @Test
    public void newManagedCompanyCanBeCreatedWithValidCompanyObject() {
        try {
            when(mockManagedCompanyDao.create(mockManagedCompany)).thenReturn(true);      
            boolean result = appService.createManagedCompany(mockManagedCompany);
            verify(mockManagedCompanyDao).create(mockManagedCompany); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newManagedCompanyCanNotBeCreatedWithEmptyCompanyObject() {
        try {
            when(mockManagedCompanyDao.create(mockManagedCompany)).thenReturn(false);    
            boolean result = appService.createManagedCompany(mockManagedCompany);
            verify(mockManagedCompanyDao).create(mockManagedCompany); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void existingManagedCompanyCanBeUpdated() {
        try {
            when(mockManagedCompanyDao.update(1, mockManagedCompany)).thenReturn(true);    
            boolean result = appService.updateManagedCompany(1, mockManagedCompany); 
            verify(mockManagedCompanyDao).update(1, mockManagedCompany); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void updatingNonExistingManagedCompanyReturnsFalse() {
        try {
            when(mockManagedCompanyDao.update(1, mockManagedCompany)).thenReturn(false);    
            boolean result = appService.updateManagedCompany(1, mockManagedCompany); 
            verify(mockManagedCompanyDao).update(1, mockManagedCompany); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingExistingManagedCompanyReturnsTrue() {
        try {
            when(mockManagedCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(mockManagedCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingManagedCompanyReturnsFalse() {
        try {
            when(mockManagedCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(mockManagedCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            companies.add(mockManagedCompany);
            when(mockManagedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(mockManagedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            when(mockManagedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(mockManagedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void newCustomerCanBeCreatedWithValidCompanyObject() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(mockCustomer);
            verify(mockCustomerCompanyDao).create(mockCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newCustomerCanNotBeCreatedWithEmptyCustomer() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(false);    
            boolean result = appService.createCustomerCompany(mockCustomer);
            verify(mockCustomerCompanyDao).create(mockCustomer); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void existingCustomerCanBeUpdated() {
        try {
            when(mockCustomerCompanyDao.update(1, mockCustomer)).thenReturn(true);    
            boolean result = appService.updateCustomerCompany(1, mockCustomer); 
            verify(mockCustomerCompanyDao).update(1, mockCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void updatingNonExistingCustomerReturnsFalse() {
        try {
            when(mockCustomerCompanyDao.update(1, mockCustomer)).thenReturn(false);    
            boolean result = appService.updateCustomerCompany(1, mockCustomer); 
            verify(mockCustomerCompanyDao).update(1, mockCustomer); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingExistingCustomerReturnsTrue() {
        try {
            when(mockCustomerCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteCustomerCompany(1); 
            verify(mockCustomerCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingCustomerReturnsFalse() {
        try {
            when(mockCustomerCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteCustomerCompany(1); 
            verify(mockCustomerCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            customers.add(mockCustomer);
            when(mockCustomerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(mockCustomerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            when(mockCustomerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(mockCustomerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }

    @Test
    public void afterAddingNewItemThereAreNotificationsPending() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(mockCustomer);
            assertTrue(appService.isNoticePending());
        } catch (SQLException e) {}
    }
    
    @Test
    public void pendingNoticeCanBeRetrieved() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(mockCustomer);
            assertEquals(appService.getPendingNotice(), "A new customer has been added");
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyInvoiceCreatedForCurrentInvoice() {
        assertNotNull(this.appService.getCurrentInvoice());
    }
    
    @After
    public void tearDown() {
        
    }

}
