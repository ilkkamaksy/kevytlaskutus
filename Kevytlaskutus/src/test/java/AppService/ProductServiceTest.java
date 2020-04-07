package AppService;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ProductDaoImpl;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.DatabaseUtils;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.InvoiceService;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;
import kevytlaskutus.domain.ProductService;
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
    
    ProductService service;
    ProductDaoImpl dao;
    DatabaseUtils databaseUtils;
    
    Product validProduct;
    Product emptyProduct;
   
    @Before
    public void setUp() {
        dao = mock(ProductDaoImpl.class);       
        databaseUtils = mock(DatabaseUtils.class);
       
        service = new ProductService(dao, databaseUtils);
       
        validProduct = mock(Product.class);
        emptyProduct = mock(Product.class);
        
        when(validProduct.getId()).thenReturn(1);
        when(validProduct.getName()).thenReturn("Acme");
        
    }

    @Test
    public void newProductCanBeCreatedWithValidProductObject() {
        try {
            when(dao.create(validProduct)).thenReturn(true);
            boolean result = service.createProduct(validProduct);
            verify(dao).create(validProduct); 
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
            when(dao.update(1, validProduct)).thenReturn(true);
            boolean result = service.updateProduct(1, validProduct);
            verify(dao).update(1, validProduct); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void productCanNotBeUpdatedWithNegativeId() {    
        boolean result = service.updateProduct(-1, validProduct);    
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
            when(dao.getItems()).thenReturn(new ArrayList<>());
            List<Product> results = service.getProducts();
            assertEquals(results.size(), 0);    
        } catch (SQLException e) {}
    }
    
}
