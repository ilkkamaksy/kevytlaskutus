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
import kevytlaskutus.domain.DatabaseUtils;
import kevytlaskutus.domain.ManagedCompany;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilkka
 */
public class ManagedCompanyDaoTest {
    
    Connection conn;
    CustomerCompany mockCustomer;
    CustomerCompanyDao customerCompanyDao;
    ManagedCompanyDao managedCompanyDao;
    InvoiceDaoImpl invoiceDao;
    ProductDaoImpl productDao;
    DatabaseUtils databaseUtils;
    
    public ManagedCompanyDaoTest() {
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
        databaseUtils.initDb();
    }
    
    @Before
    public void setUp() {
        try {
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
        } catch (SQLException e) {
            
        }
    }
    
    @Test
    public void newManagedCompanyCanBeCreated() {
        try {
            ManagedCompany company = new ManagedCompany();
            company.setName("Acme");
            boolean success = this.managedCompanyDao.create(company);
            assertTrue(success);    
        } catch (SQLException e) {
            
        }
    }
    
    @Test
    public void companyCanBeUpdated() {
        try {
            ManagedCompany company = new ManagedCompany();
            company.setName("Original");
            boolean success = this.managedCompanyDao.create(company);
            assertTrue(success);    
            
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            ManagedCompany originalCompany = this.managedCompanyDao.getItemByName("Original");
            originalCompany.setName("Changed");
            
            conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            success = this.managedCompanyDao.update(originalCompany.getId(), originalCompany);
            assertTrue(success);
            
            conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            ManagedCompany changedCompany = this.managedCompanyDao.getItemByName("Changed");
            assertEquals(originalCompany.getName(), changedCompany.getName());
        } catch (SQLException e) {}
    }
    
    @Test
    public void companyCannotBeUpdatedWithoutValidId() {
        try {
            boolean success = this.managedCompanyDao.update(-1, new ManagedCompany());
            assertFalse(success);    
        } catch (SQLException e) {}
    }

    @Test
    public void companyCannotDeletedWithoutValidId() {
        try {
            boolean success = this.managedCompanyDao.delete(-1);
            assertFalse(success);    
        } catch (SQLException e) {}
    }
    
    @Test
    public void companyCanBeRetrievedById() {
        try {
            List<ManagedCompany> companies = this.managedCompanyDao.getItems();
            Integer id = companies.get(0).getId();
            
            Connection conn = this.databaseUtils.getConnection();
            this.managedCompanyDao.setConnection(conn);
            ManagedCompany result = this.managedCompanyDao.getItemById(id);
            assertEquals(companies.get(0).getId(), result.getId());
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
