/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.domain.DatabaseUtils;
import kevytlaskutus.domain.Invoice;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 *
 * @author ilkka
 */
public class InvoiceDaoImplTest {

    Connection conn;
    Invoice mockInvoice;
    InvoiceDaoImpl dao;
    
    @Before
    public void setUp() {
        dao = new InvoiceDaoImpl();
        try {
            conn = this.getConnection();
            dao.setConnection(conn);
        } catch (SQLException e) {}
        
        mockInvoice = mock(Invoice.class);    
    }

    @Test
    public void newInvoiceCannotBeCreatedWithNullInvoiceObject() {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(1001);
        try {
            Integer id = dao.create(invoice);
            verify(mockInvoice).getAmount();
            boolean result = id > -1 ? true : false;
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCannotBeUpdatedWithNullInvoiceObject() {
        try {
            boolean result = dao.update(1, null);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCannotBeUpdatedWithoutValidId() {
        try {
            boolean result = dao.update(-1, mockInvoice);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanBeUpdatedWithValidId() {
        try {
            boolean result = dao.update(1, mockInvoice);
            assertTrue(result);    
        } catch (SQLException e) {}
    }
   
    @Test
    public void invoiceCannotDeletedWithoutValidId() {
        try {
            boolean result = dao.delete(-1);
            assertFalse(result);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceListAlwaysRetursnList() {
        try {
            List<Invoice> results = dao.getItems(1);
            assertEquals(results.size(), 0);    
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
        return DriverManager.getConnection("jdbc:h2:mem:testdb");
    }
}
