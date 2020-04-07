/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.dao.*;
import kevytlaskutus.domain.Company;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.InvoiceService;
import kevytlaskutus.domain.ManagedCompany;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

/**
 *
 * @author ilkka
 */
public class AppServiceTest {
    
    AppService appService;
    InvoiceService invoiceService;
    ManagedCompanyDao managedCompanyDao;
    CustomerCompanyDao customerCompanyDao;
    ProductDaoImpl productDao;
    InvoiceDaoImpl invoiceDao;
    
    ManagedCompany validManagedCompany;
    ManagedCompany emptyManagedCompany;
    CustomerCompany validCustomer;
    CustomerCompany emptyCustomer;
    Invoice mockInvoice;
    
    @Before
    public void setUp() {
        managedCompanyDao = mock(ManagedCompanyDao.class);       
        customerCompanyDao = mock(CustomerCompanyDao.class);
        invoiceService = mock(InvoiceService.class);
        invoiceDao = mock(InvoiceDaoImpl.class);
        productDao = mock(ProductDaoImpl.class);
        mockInvoice = mock(Invoice.class);
        appService = new AppService(managedCompanyDao, customerCompanyDao, productDao, invoiceDao);
        
        validManagedCompany = mock(ManagedCompany.class); 
        when(validManagedCompany.getId()).thenReturn(1);
        when(validManagedCompany.getName()).thenReturn("Acme");
        
        emptyManagedCompany = mock(ManagedCompany.class); 
        
        validCustomer = mock(CustomerCompany.class); 
        when(validCustomer.getId()).thenReturn(1);
        when(validCustomer.getName()).thenReturn("Acme");
        
        emptyCustomer = mock(CustomerCompany.class); 
    }
    
    @Test
    public void getCurrentManagedCompanyReturnsCompanyObject() {
        Company company = this.appService.getCurrentManagedCompany();
        assertNotNull(company);
    }
    
    @Test
    public void newManagedCompanyCanBeCreatedWithValidCompanyObject() {
        try {
            when(managedCompanyDao.create(validManagedCompany)).thenReturn(true);      
            boolean result = appService.createManagedCompany(validManagedCompany);
            verify(managedCompanyDao).create(validManagedCompany); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newManagedCompanyCanNotBeCreatedWithEmptyCompanyObject() {
        try {
            when(managedCompanyDao.create(emptyManagedCompany)).thenReturn(false);    
            boolean result = appService.createManagedCompany(emptyManagedCompany);
            verify(managedCompanyDao).create(emptyManagedCompany); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void existingManagedCompanyCanBeUpdated() {
        try {
            when(managedCompanyDao.update(1, validManagedCompany)).thenReturn(true);    
            boolean result = appService.updateManagedCompany(1, validManagedCompany); 
            verify(managedCompanyDao).update(1, validManagedCompany); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void updatingNonExistingManagedCompanyReturnsFalse() {
        try {
            when(managedCompanyDao.update(1, validManagedCompany)).thenReturn(false);    
            boolean result = appService.updateManagedCompany(1, validManagedCompany); 
            verify(managedCompanyDao).update(1, validManagedCompany); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingExistingManagedCompanyReturnsTrue() {
        try {
            when(managedCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(managedCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingManagedCompanyReturnsFalse() {
        try {
            when(managedCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(managedCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            companies.add(validManagedCompany);
            when(managedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(managedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            when(managedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(managedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void newCustomerCanBeCreatedWithValidCompanyObject() {
        try {
            when(customerCompanyDao.create(validCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(validCustomer);
            verify(customerCompanyDao).create(validCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newCustomerCanNotBeCreatedWithEmptyCustomer() {
        try {
            when(customerCompanyDao.create(emptyCustomer)).thenReturn(false);    
            boolean result = appService.createCustomerCompany(emptyCustomer);
            verify(customerCompanyDao).create(emptyCustomer); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void existingCustomerCanBeUpdated() {
        try {
            when(customerCompanyDao.update(1, validCustomer)).thenReturn(true);    
            boolean result = appService.updateCustomerCompany(1, validCustomer); 
            verify(customerCompanyDao).update(1, validCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void updatingNonExistingCustomerReturnsFalse() {
        try {
            when(customerCompanyDao.update(1, emptyCustomer)).thenReturn(false);    
            boolean result = appService.updateCustomerCompany(1, emptyCustomer); 
            verify(customerCompanyDao).update(1, emptyCustomer); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingExistingCustomerReturnsTrue() {
        try {
            when(customerCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteCustomerCompany(1); 
            verify(customerCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingCustomerReturnsFalse() {
        try {
            when(customerCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteCustomerCompany(1); 
            verify(customerCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            customers.add(validCustomer);
            when(customerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(customerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            when(customerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(customerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }

    @Test
    public void afterAddingNewItemThereAreNotificationsPending() {
        try {
            when(customerCompanyDao.create(validCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(validCustomer);
            assertTrue(appService.isNoticePending());
        } catch (SQLException e) {}
    }
    
    @Test
    public void pendingNoticeCanBeRetrieved() {
        try {
            when(customerCompanyDao.create(validCustomer)).thenReturn(true);      
            boolean result = appService.createCustomerCompany(validCustomer);
            assertEquals(appService.getPendingNotice(), "A new customer has been added");
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyInvoiceCreatedForCurrentInvoice() {
        assertNotNull(this.appService.getCurrentInvoice());
    }
}
