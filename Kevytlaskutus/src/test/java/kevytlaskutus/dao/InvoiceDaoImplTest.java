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
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.DatabaseUtils;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.ManagedCompany;
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
    CustomerCompany mockCustomer;
    CustomerCompanyDao customerCompanyDao;
    ManagedCompanyDao managedCompanyDao;
    InvoiceDaoImpl invoiceDao;
    ProductDaoImpl productDao;
    DatabaseUtils databaseUtils;
    Invoice mockInvoice;
    
    public InvoiceDaoImplTest() {
        
        this.managedCompanyDao = new ManagedCompanyDao();
        this.customerCompanyDao = new CustomerCompanyDao();
        this.invoiceDao = new InvoiceDaoImpl();
        this.productDao = new ProductDaoImpl();
       
        this.databaseUtils = new DatabaseUtils(
                managedCompanyDao, 
                customerCompanyDao, 
                invoiceDao,
                productDao,
                "jdbc:h2:mem:testdb",
                "sa", 
                ""
        ); 
        this.databaseUtils.initDb();
    }
    
    @Before
    public void setUp() {
        try {
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            ManagedCompany company = new ManagedCompany();
            company.setName("Company");
            managedCompanyDao.create(company);

            conn = this.databaseUtils.getConnection();
            this.customerCompanyDao.setConnection(conn);
            CustomerCompany customer = new CustomerCompany();
            customer.setName("Acme");
            customerCompanyDao.create(customer);
            
            conn = this.databaseUtils.getConnection();
            this.productDao.setConnection(conn);
            this.invoiceDao.setConnection(conn);
            
        } catch (SQLException e) {
            System.out.println("stup " + e);
        }
        
        mockInvoice = mock(Invoice.class);    
    }

    @Test
    public void newInvoiceCanBeCreatedWithCustomerAndManagedCompany() {

        try {
            
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            List<ManagedCompany> companies = this.managedCompanyDao.getItems();
            
            conn = this.databaseUtils.getConnection();
            this.customerCompanyDao.setConnection(conn);
            List<CustomerCompany> customers = this.customerCompanyDao.getItems();

            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(1001);
            invoice.setCompany(companies.get(0));
            invoice.setCustomer(customers.get(0));
            
            Integer id = this.invoiceDao.create(invoice);
            boolean result = id > -1 ? true : false;
            assertTrue(result);
        } catch (SQLException e) {
            System.out.println("error " + e);
        }
    }
    
    @Test
    public void invoiceCanBeUpdated() {
        try {
            
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            List<ManagedCompany> companies = this.managedCompanyDao.getItems();

            conn = this.databaseUtils.getConnection();
            this.customerCompanyDao.setConnection(conn);
            List<CustomerCompany> customers = this.customerCompanyDao.getItems();

            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(1001);
            invoice.setCompany(companies.get(0));
            invoice.setCustomer(customers.get(0));
            
            Integer id = this.invoiceDao.create(invoice);
            
            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);
            Invoice invoiceToBeChanged = this.invoiceDao.getItemById(id);
            invoiceToBeChanged.setDeliveryInfo("delivery info");
            boolean result = this.invoiceDao.update(invoiceToBeChanged.getId(), invoiceToBeChanged);
            assertTrue(result);    
            
            Invoice changedInvoice = this.invoiceDao.getItemById(invoiceToBeChanged.getId());
            assertEquals(invoiceToBeChanged.getAdditionalInfo(), changedInvoice.getAdditionalInfo());
            
        } catch (SQLException e) {}
    }

   
    @Test
    public void invoiceCanBeDeleted() {
        try {
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            List<ManagedCompany> companies = this.managedCompanyDao.getItems();

            conn = this.databaseUtils.getConnection();
            this.customerCompanyDao.setConnection(conn);
            List<CustomerCompany> customers = this.customerCompanyDao.getItems();

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(1001);
            invoice.setCompany(companies.get(0));
            invoice.setCustomer(customers.get(0));
            
            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);
            Integer id = this.invoiceDao.create(invoice);
            
            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);
            boolean result = this.invoiceDao.delete(id);
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceListAlwaysRetursnList() {
        try {
            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);
            List<Invoice> results = this.invoiceDao.getItems(0);
            assertEquals(0, results.size());    
        } catch (SQLException e) {}
    }
    
    @Test
    public void invoiceCanBeRetrievedById() {
        try {
            
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            List<ManagedCompany> companies = this.managedCompanyDao.getItems();

            conn = this.databaseUtils.getConnection();
            this.customerCompanyDao.setConnection(conn);
            List<CustomerCompany> customers = this.customerCompanyDao.getItems();

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(1001);
            invoice.setCompany(companies.get(0));
            invoice.setCustomer(customers.get(0));

            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);            
            Integer id = this.invoiceDao.create(invoice);
            
            conn = this.databaseUtils.getConnection();
            this.invoiceDao.setConnection(conn);
            Invoice invoiceInDb = this.invoiceDao.getItemById(id);
            assertEquals(invoice.getInvoiceNumber(), invoiceInDb.getInvoiceNumber());
            
        } catch (SQLException e) {}
    }
   
    @AfterClass
    public static void tearDownClass() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb");
            conn.prepareStatement("DROP DATABASE testdb").execute();
        } catch (SQLException e) {}
    }

}
