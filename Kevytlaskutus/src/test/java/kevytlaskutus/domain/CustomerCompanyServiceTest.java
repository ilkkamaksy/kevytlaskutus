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
public class CustomerCompanyServiceTest {
 
    CustomerCompanyService service;
    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    ProductDaoImpl mockProductDao;
    InvoiceDaoImpl mockInvoiceDao;
    
    CustomerCompany mockCustomer;
    
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
        
        service = new CustomerCompanyService(mockCustomerCompanyDao, databaseUtils);
       
        mockCustomer = mock(CustomerCompany.class); 
        when(mockCustomer.getId()).thenReturn(1);
        when(mockCustomer.getName()).thenReturn("Acme");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateCustomerCompany() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);
            boolean result = this.service.createCustomerCompany(mockCustomer);
            verify(this.mockCustomerCompanyDao).create(mockCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }

    @Test
    public void testUpdateCustomerCompany() {
        try {
            when(mockCustomerCompanyDao.update(1, mockCustomer)).thenReturn(true);
            boolean result = this.service.updateCustomerCompany(1, mockCustomer);
            verify(this.mockCustomerCompanyDao).update(1, mockCustomer); 
            assertTrue(result);
        } catch (SQLException e) {}
    }

    @Test
    public void testDeleteCustomerCompany() {
        try {
            when(mockCustomerCompanyDao.delete(1)).thenReturn(true);
            boolean result = this.service.deleteCustomerCompany(1);
            verify(this.mockCustomerCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }

    @Test
    public void testGetCustomerCompanies() {
        try {
            when(mockCustomerCompanyDao.getItems()).thenReturn(new ArrayList<>());
            List<CustomerCompany> results = this.service.getCustomerCompanies();
            verify(this.mockCustomerCompanyDao).getItems(); 
            assertEquals(results.size(), 0);
        } catch (SQLException e) {}
    }

    @Test
    public void testGetCustomerCompanyByName() {
        try {
            when(mockCustomerCompanyDao.getItemByName("Acme")).thenReturn(mockCustomer);
            CustomerCompany result = this.service.getCustomerCompanyByName("Acme");
            verify(this.mockCustomerCompanyDao).getItemByName("Acme"); 
            assertEquals(result.getName(), mockCustomer.getName());
        } catch (SQLException e) {}
    }
    
}
