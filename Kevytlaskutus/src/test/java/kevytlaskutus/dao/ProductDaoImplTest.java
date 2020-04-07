/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 * @author ilkka
 */
public class ProductDaoImplTest {
    
    Connection conn;
    Product mockProduct;
    ProductDaoImpl dao;
    
    @Before
    public void setUp() {
        dao = new ProductDaoImpl();
        try {
            conn = this.getConnection();
            dao.setConnection(conn);
        } catch (SQLException e) {}
        
        mockProduct = mock(Product.class);
        
    }

    @Test
    public void newProductCannotBeCreatedWithNullInvoiceObject() {
        try {
            boolean result = dao.create(mockProduct);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void productCannotBeUpdatedWithNullProductObject() {
        try {
            boolean result = dao.update(1, null);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void productCannotBeUpdatedWithoutValidId() {
        try {
            boolean result = dao.update(-1, mockProduct);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void productCanBeUpdatedWithValidId() {
        try {
            boolean result = dao.update(1, mockProduct);
            assertTrue(result);    
        } catch (SQLException e) {}
    }
   
    @Test
    public void productCannotDeletedWithoutValidId() {
        try {
            boolean result = dao.delete(-1);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    public Connection getConnection() throws SQLException {     
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }    
}
