/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.sql.SQLException;
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
public class ManagedCompanyServiceTest {
    
    ManagedCompanyService service;
    
    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    InvoiceDaoImpl mockInvoiceDao;
    ProductDaoImpl mockProductDao;
    
    ManagedCompany mockCompany;
    
    @Before
    public void setUp() {
        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);
        
        DatabaseUtils databaseUtils = new DatabaseUtils(
                mockManagedCompanyDao, 
                mockCustomerCompanyDao, 
                mockInvoiceDao,
                mockProductDao,
                "jdbc:h2:mem:testdb", 
                "sa", 
                ""
        );
        databaseUtils.initDb();
        
        service = new ManagedCompanyService(mockManagedCompanyDao, databaseUtils);
       
        mockCompany = mock(ManagedCompany.class); 
        when(mockCompany.getId()).thenReturn(1);
        when(mockCompany.getName()).thenReturn("Acme");
    }
   
    @Test
    public void testGetCustomerCompanyByName() {
        try {
            when(mockManagedCompanyDao.getItemById(1)).thenReturn(mockCompany);
            ManagedCompany result = this.service.getManagedCompany(1);
            verify(this.mockManagedCompanyDao).getItemById(1); 
            
            assertEquals(result.getName(), mockCompany.getName());
        } catch (SQLException e) {}
    }
    
    @Test
    public void getCompanyByIdReturnsNullIfNotFound() {
        try {
            when(mockManagedCompanyDao.getItemById(1)).thenThrow(new SQLException("virhe"));
            ManagedCompany result = this.service.getManagedCompany(1);
            assertNull(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void getCompaniesReturnsEmptyListIfNotFound() {
        try {
            when(mockManagedCompanyDao.getItems()).thenThrow(new SQLException("virhe"));
            List<ManagedCompany> result = this.service.getManagedCompanies();
            assertEquals(0, result.size());    
        } catch (SQLException e) {}
    }
    
    @Test
    public void deleteCompanyReturnsFalseIfNotFound() {
        try {
            when(mockManagedCompanyDao.delete(1)).thenThrow(new SQLException("virhe"));
            boolean result = this.service.deleteManagedCompany(1);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    
}
