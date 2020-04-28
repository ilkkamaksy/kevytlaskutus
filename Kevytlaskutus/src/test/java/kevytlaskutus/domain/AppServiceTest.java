/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.math.BigDecimal;
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
public class AppServiceTest {
    
    AppService appService;
    
    InvoiceService mockInvoiceService;
    ManagedCompanyDao mockManagedCompanyDao;
    CustomerCompanyDao mockCustomerCompanyDao;
    InvoiceDaoImpl mockInvoiceDao;
    ProductDaoImpl mockProductDao;
    
    ManagedCompany mockManagedCompany;
    CustomerCompany mockCustomer;
    Invoice mockInvoice;
    
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
        
        appService = new AppService(mockManagedCompanyDao, mockCustomerCompanyDao, mockInvoiceDao, mockProductDao, databaseUtils);
        
        mockInvoice = mock(Invoice.class);
        
        mockManagedCompany = mock(ManagedCompany.class); 
        when(mockManagedCompany.getId()).thenReturn(1);
        when(mockManagedCompany.getName()).thenReturn("Acme");

        mockCustomer = mock(CustomerCompany.class); 
        when(mockCustomer.getId()).thenReturn(1);
        when(mockCustomer.getName()).thenReturn("Acme");
        
        mockInvoiceService = mock(InvoiceService.class);
    }
    
    @Test
    public void getCurrentManagedCompanyReturnsCompanyObject() {
        ManagedCompany company = this.appService.getCurrentManagedCompany();
        assertNotNull(company);
    }
    
    @Test
    public void newManagedCompanyCanBeCreatedWithValidCompanyObject() {
        ManagedCompany company = new ManagedCompany(
                "Acme",
                "regid",
                "09123123",
                "Katuosoite",
                "postikoodi",
                "toimipaikka",
                "ovt",
                "provider"
        );
       
        try {
            when(mockManagedCompanyDao.create(company)).thenReturn(true);
            this.appService.setCurrentManagedCompany(company);
            assertEquals("Acme", this.appService.getCurrentManagedCompany().getName());
            assertEquals("regid", this.appService.getCurrentManagedCompany().getRegId());
            assertEquals("09123123", this.appService.getCurrentManagedCompany().getPhone());
            assertEquals("Katuosoite", this.appService.getCurrentManagedCompany().getStreet());
            assertEquals("postikoodi", this.appService.getCurrentManagedCompany().getPostcode());
            assertEquals("toimipaikka", this.appService.getCurrentManagedCompany().getCommune());
            assertEquals("ovt", this.appService.getCurrentManagedCompany().getOvtId());
            assertEquals("provider", this.appService.getCurrentManagedCompany().getProvider());
            assertTrue(this.appService.getCurrentManagedCompany().equals(company));
            assertFalse(company.equals(this.mockManagedCompany));    
            
            boolean result = appService.saveCurrentManagedCompany();  
            assertTrue(result);
        } catch (SQLException ex) {}
    }
    
    @Test
    public void newManagedCompanyCanNotBeCreatedWithEmptyCompanyObject() {
        this.appService.setCurrentManagedCompany(null);
        boolean result = appService.saveCurrentManagedCompany();
        assertFalse(result);
    }
    
    @Test
    public void newManagedCompanyCanNotBeCreatedWithoutName() {
        
        ManagedCompany company = new ManagedCompany();
        company.setName("");
        company.setRegId("regid");
        company.setPhone("phone");
        company.setStreet("street");
        company.setPostcode("postcode");
        company.setCommune("commune");
        company.setBic("bic");
        company.setIban("iban");
        company.setProvider("provider");
        company.setOvtId("ovt");
       
        this.appService.setCurrentManagedCompany(company);
        assertEquals("iban", this.appService.getCurrentManagedCompany().getIban());
        assertEquals("bic", this.appService.getCurrentManagedCompany().getBic());
        assertTrue(this.appService.getCurrentManagedCompany().getName().isEmpty());
        
        boolean result = appService.saveCurrentManagedCompany();
        assertFalse(result);
    }
    
    @Test
    public void existingManagedCompanyCanBeUpdated() {
        try {
            when(mockManagedCompanyDao.update(1, mockManagedCompany)).thenReturn(true);
            this.appService.setCurrentManagedCompany(mockManagedCompany);
            boolean result = appService.updateCurrentManagedCompany();  
            assertTrue(result);
        } catch (SQLException ex) {}
       
    }
    
    @Test
    public void updatingNonExistingManagedCompanyReturnsFalse() {
        this.appService.setCurrentManagedCompany(null);
        boolean result = appService.updateCurrentManagedCompany(); 
        assertFalse(result);
    }
    
    @Test
    public void deletingExistingManagedCompanyReturnsTrue() {
        try {
            when(mockManagedCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(mockManagedCompanyDao).delete(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingManagedCompanyReturnsFalse() {
        try {
            when(mockManagedCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteManagedCompany(1); 
            verify(mockManagedCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            companies.add(mockManagedCompany);
            when(mockManagedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(mockManagedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfManagedCompaniesCanBeRetrieved() {
        try {
            List<ManagedCompany> companies = new ArrayList<>();
            when(mockManagedCompanyDao.getItems()).thenReturn(companies);    
            List<ManagedCompany> results = appService.getManagedCompanies(); 
            verify(mockManagedCompanyDao).getItems(); 
            assertEquals(results.size(), companies.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void newCustomerCanBeCreatedWithValidCompanyObject() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);
            this.appService.setCurrentCustomerCompany(mockCustomer);
            boolean result = appService.saveCurrentCustomerCompany();  
            assertTrue(result);
        } catch (SQLException ex) {}
    }
    
    @Test
    public void newCustomerCanNotBeCreatedWithEmptyCustomer() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(false);    
            boolean result = appService.saveCurrentCustomerCompany();
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void existingCustomerCanBeUpdated() {
        try {
            when(this.mockCustomerCompanyDao.update(1, mockCustomer)).thenReturn(true);    
            this.appService.setCurrentCustomerCompany(mockCustomer);
            boolean result = appService.updateCurrentCustomerCompany(); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void updatingNonExistingCustomerReturnsFalse() {    
        this.appService.setCurrentCustomerCompany(null);
        boolean result = appService.updateCurrentCustomerCompany(); 
        assertFalse(result);
    }
    
    @Test
    public void deletingExistingCustomerReturnsTrue() {
        try {
            when(mockCustomerCompanyDao.delete(1)).thenReturn(true);    
            boolean result = appService.deleteCustomerCompany(1); 
            assertTrue(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void deletingNonExistingCustomerReturnsFalse() {
        try {
            when(mockCustomerCompanyDao.delete(1)).thenReturn(false);    
            boolean result = appService.deleteCustomerCompany(1); 
            verify(mockCustomerCompanyDao).delete(1); 
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
    @Test
    public void listOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            customers.add(mockCustomer);
            when(mockCustomerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(mockCustomerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }
    
    @Test
    public void emptyListOfCustomersCanBeRetrieved() {
        try {
            List<CustomerCompany> customers = new ArrayList<>();
            when(mockCustomerCompanyDao.getItems()).thenReturn(customers);    
            List<CustomerCompany> results = appService.getCustomerCompanies();
            verify(mockCustomerCompanyDao).getItems(); 
            assertEquals(results.size(), customers.size());
        } catch (SQLException e) {}
    }

    @Test
    public void afterAddingNewItemThereAreNotificationsPending() {
        try {
            when(mockCustomerCompanyDao.create(mockCustomer)).thenReturn(true);      
            boolean result = appService.saveCurrentCustomerCompany();
            assertTrue(appService.hasNoticePending());
        } catch (SQLException e) {}
    }
    
    @Test
    public void pendingNoticeCanBeRetrieved() {
        CustomerCompany customer = new CustomerCompany(
                "Acme",
                "regid",
                "09123123",
                "Katuosoite",
                "postikoodi",
                "toimipaikka",
                "ovt",
                "provider"
        );
       
        try {
            when(this.mockCustomerCompanyDao.create(customer)).thenReturn(true);
            this.appService.setCurrentCustomerCompany(customer);
            
            assertEquals("Acme", this.appService.getCurrentCustomerCompany().getName());
            assertEquals("regid", this.appService.getCurrentCustomerCompany().getRegId());
            assertEquals("09123123", this.appService.getCurrentCustomerCompany().getPhone());
            assertEquals("Katuosoite", this.appService.getCurrentCustomerCompany().getStreet());
            assertEquals("postikoodi", this.appService.getCurrentCustomerCompany().getPostcode());
            assertEquals("toimipaikka", this.appService.getCurrentCustomerCompany().getCommune());
            assertEquals("ovt", this.appService.getCurrentCustomerCompany().getOvtId());
            assertEquals("provider", this.appService.getCurrentCustomerCompany().getProvider());
            assertTrue(this.appService.getCurrentCustomerCompany().equals(customer));
            assertFalse(this.appService.getCurrentCustomerCompany().equals(this.mockCustomer));
            
            boolean result = appService.saveCurrentCustomerCompany();
            assertTrue(result);
            assertEquals("A new customer has been added", appService.getPendingNotice().getNoticeMessage());
        } catch (SQLException ex) {}
    }
    
    @Test
    public void emptyInvoiceCreatedForCurrentInvoice() {
        assertNotNull(this.appService.getCurrentInvoice());
    }
    
    
    @Test
    public void newInvoiceCanBeCreatedWithValidInvoiceObject() {

        CustomerCompany customer = new CustomerCompany();
        customer.setName("Acme");
        customer.setRegId("regid");
        customer.setOvtId("ovt");
        customer.setProvider("provider");
        customer.setStreet("katu");
        customer.setPostcode("postcode");
        customer.setCommune("commune");
        customer.setPhone("phone");

        Product product = new Product();
        product.setName("Acme");

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.getProducts().add(product);

        when(mockInvoiceService.createInvoiceForCompany(invoice, mockManagedCompany)).thenReturn(1);

        this.appService.setCurrentManagedCompany(mockManagedCompany);
        this.appService.setCurrentCustomerCompany(customer);
        this.appService.setCurrentInvoice(invoice);
        boolean result = this.appService.saveCurrentInvoice();

        assertTrue(result);   
    }
    

    @Test
    public void newInvoiceCannotBeCreatedWithoutACustomer() {
        try {
            when(mockInvoiceDao.create(mockInvoice)).thenReturn(0);
            this.appService.setCurrentInvoice(mockInvoice);
            boolean result = this.appService.saveCurrentInvoice();
            assertFalse(result);
        } catch (SQLException e) {}
    }
    
   
    @Test
    public void invoiceCanNotBeDeletedWithInvalidId() {
        CustomerCompany customer = new CustomerCompany();
        customer.setName("Acme");
        try {
            when(mockInvoiceDao.delete(Integer.valueOf(1))).thenReturn(false);
            boolean result = this.appService.deleteInvoice(1);
            assertFalse(result);
        } catch (SQLException e) {}
    }

    public void invoiceCanBeUpdatedWithValidInvoiceObject() {

        CustomerCompany customer = new CustomerCompany();
        customer.setName("Acme");

        Product product = new Product();
        product.setName("Acme");
        product.setPrice(BigDecimal.ONE);
        product.setPriceUnit("kpl");
        product.setDescription("kuvaus");

        assertEquals("Acme", product.getName());
        assertEquals(BigDecimal.ONE, product.getPrice());
        assertEquals("kpl", product.getPriceUnit());
        assertEquals("kuvaus", product.getDescription());
        assertTrue(product.equals(product));
        assertFalse(product.equals(this.mockCustomer));
        
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setReferenceNumber(Integer.valueOf(0));
        invoice.getProducts().add(product);

        when(mockInvoiceService.updateInvoice(1, invoice, mockManagedCompany)).thenReturn(true);

        this.appService.setCurrentManagedCompany(mockManagedCompany);
        this.appService.setCurrentCustomerCompany(customer);
        this.appService.setCurrentInvoice(invoice);
        this.appService.getCurrentInvoice().setReferenceNumber(Integer.valueOf(1));
        
        assertEquals(Integer.valueOf(1), this.appService.getCurrentInvoice().getReferenceNumber());
        
        boolean result = this.appService.updateCurrentInvoice();

        assertTrue(result);
        
    }   
    
   
    @After
    public void tearDown() {
        
    }

}
