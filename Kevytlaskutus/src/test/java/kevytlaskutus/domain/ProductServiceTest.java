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
import static org.mockito.Mockito.when;

/**
 *
 * @author ilkka
 */
public class ProductServiceTest {
    
    DatabaseUtils databaseUtils;
    ProductDaoImpl mockProductDao;
    ProductService productService;
    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    InvoiceDaoImpl mockInvoiceDao;
    
    Invoice mockInvoice;

    @Before
    public void setUp() {

        mockManagedCompanyDao = mock(ManagedCompanyDao.class);       
        mockCustomerCompanyDao = mock(CustomerCompanyDao.class);
        mockInvoiceDao = mock(InvoiceDaoImpl.class);
        mockProductDao = mock(ProductDaoImpl.class);        
        
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
        productService = new ProductService(mockProductDao, databaseUtils);
 
        mockInvoice = mock(Invoice.class);
        when(mockInvoice.getId()).thenReturn(1);
        when(mockInvoice.getReferenceNumber()).thenReturn(1001);
               
    }
    
    @Test
    public void invoiceProductsCanBeRetrieved() {
        try {

            List<Product> prods = new ArrayList<>();
            Product prod = new Product();
            prod.setName("tuote");
            prods.add(prod);
            
            when(mockProductDao.getProductsByInvoiceId(1)).thenReturn(prods);
            List<Product> res = productService.getProductsByInvoiceId(1);

            assertEquals(prods, res);
        } catch (SQLException e) {}
    }
    
    @Test
    public void productsCanBeSavedInBatches() {
        try {
            List<Product> prods = new ArrayList<>();
            Product prod = new Product();
            prod.setName("tuote");
            prods.add(prod);
            
            when(mockProductDao.saveProductsInBatches(1, prods)).thenReturn(true);
            boolean success = productService.saveProductsInBatches(1, prods);
            assertTrue(success);
        } catch (SQLException e) {}
    }
    
    @Test
    public void batchSaveReturnsFalseWhenNoProductsToSave() {
        try {
            List<Product> prods = new ArrayList<>();
            when(mockProductDao.saveProductsInBatches(1, prods)).thenReturn(false);
            boolean success = productService.saveProductsInBatches(1, prods);
            assertFalse(success);
        } catch (SQLException e) {}
    }
    
    @Test
    public void productsCanBeUpdatedInBatches() {
        try {
            List<Product> prods = new ArrayList<>();
            Product prod = new Product();
            prod.setName("tuote");
            prods.add(prod);
            
            when(mockProductDao.updateProductsInBatches(1, prods)).thenReturn(true);
            boolean success = productService.updateProductsInBatches(1, prods);
            assertTrue(success);
        } catch (SQLException e) {}
    }
    
    @Test
    public void batchUpdateReturnsFalseWhenNoProductsToUpdate() {
        try {
            List<Product> prods = new ArrayList<>();
            when(mockProductDao.updateProductsInBatches(1, prods)).thenReturn(false);
            boolean success = productService.updateProductsInBatches(1, prods);
            assertFalse(success);
        } catch (SQLException e) {}
    }
}
