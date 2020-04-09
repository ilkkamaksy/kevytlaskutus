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
import kevytlaskutus.domain.CustomerCompany;
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
public class CustomerCompanyDaoTest {
    
    Connection conn;
    CustomerCompany mockCustomer;
    CustomerCompanyDao dao;
    
    @Before
    public void setUp() {
        dao = new CustomerCompanyDao();
        try {
            conn = this.getConnection();
            dao.setConnection(conn);
        } catch (SQLException e) {}
        
        mockCustomer = mock(CustomerCompany.class);
        when(mockCustomer.getId()).thenReturn(1);
        when(mockCustomer.getName()).thenReturn("Acme");
    }

    @Test
    public void newCustomerCanBeCreated() {
        try {
            boolean result = dao.create(mockCustomer);
            assertTrue(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void customerCanBeUpdated() {
        try {
            boolean result = dao.update(1, mockCustomer);
            assertTrue(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void customerCannotBeUpdatedWithoutValidId() {
        try {
            boolean result = dao.update(-1, mockCustomer);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
  
    @Test
    public void customerCannotDeletedWithoutValidId() {
        try {
            boolean result = dao.delete(-1);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void customerCanBeRetrievedById() {
        try {
            this.dao.create(mockCustomer);
            CustomerCompany result = this.dao.getItemById(1);
            assertEquals(result.getName(), mockCustomer.getName());    
        } catch (SQLException e) {}
    }
    
    @After
    public void tearDown() {
        try {
            conn.close();
        } catch (SQLException e) {}
    }
    
    // Helpers
    
    public Connection getConnection() throws SQLException {     
        return DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }    
    
}
