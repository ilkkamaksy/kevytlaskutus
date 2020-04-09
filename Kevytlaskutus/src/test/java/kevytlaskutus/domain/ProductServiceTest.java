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
public class ProductServiceTest {
    
    DatabaseUtils databaseUtils;

    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    ProductDaoImpl mockProductDao;
    InvoiceDaoImpl mockInvoiceDao;

    ProductService service;
    
    Invoice mockInvoice;
    ManagedCompany mockCompany;
    CustomerCompany mockCustomer;
    Product mockProduct;
   
    @Before
    public void setUp() {
        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);  
        
        databaseUtils = new DatabaseUtils(
                mockManagedCompanyDao, 
                mockCustomerCompanyDao, 
                mockProductDao, 
                mockInvoiceDao,
                "jdbc:h2:mem:testdb", 
                "sa", 
                ""
        );
        databaseUtils.initDb();  
        service = new ProductService(mockProductDao, databaseUtils);
       
        mockProduct = mock(Product.class);
        when(mockProduct.getId()).thenReturn(1);
        when(mockProduct.getName()).thenReturn("Fake Product");
    }

    @Test
    public void newProductCanBeCreatedWithValidProductObject() {
        try {
            when(mockProductDao.create(mockProduct)).thenReturn(true);
            boolean result = service.createProduct(mockProduct);
            verify(mockProductDao).create(mockProduct); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void newProductCannotBeCreatedWithNullObject() {
        boolean result = service.createProduct(null); 
        assertFalse(result);
    }
    
    @Test
    public void productCanBeUpdatedWithValidProductObject() {
        try {
            when(mockProductDao.update(1, mockProduct)).thenReturn(true);
            boolean result = service.updateProduct(1, mockProduct);
            verify(mockProductDao).update(1, mockProduct); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void productCanNotBeUpdatedWithNegativeId() {    
        boolean result = service.updateProduct(-1, mockProduct);    
        assertFalse(result);    
    }
    
    @Test
    public void productCanNotBeUpdatedWithoutProductObject() {    
        boolean result = service.updateProduct(1, null);    
        assertFalse(result);    
    }
    
    @Test
    public void productCanNotBeDeletedWithInvalidId() {    
        boolean result = service.deleteProduct(-1);    
        assertFalse(result);    
    }
    
    @Test
    public void productListAlwaysReturnsAlist() {    
        try {
            when(mockProductDao.getItems()).thenReturn(new ArrayList<>());
            List<Product> results = service.getProducts();
            assertEquals(results.size(), 0);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void productcanBeRetrievedById() {    
        try {
            when(mockProductDao.getItemById(1)).thenReturn(mockProduct);
            Product result = this.service.getProduct(1);
            verify(mockProductDao).getItemById(1); 
            assertEquals(result.getName(), mockProduct.getName());    
        } catch (SQLException e) {}
    }
    
    @Test
    public void getProductReturnsNullWithInvalidId() {    
        Product result = this.service.getProduct(-1);
        assertNull(result);
    }
}
