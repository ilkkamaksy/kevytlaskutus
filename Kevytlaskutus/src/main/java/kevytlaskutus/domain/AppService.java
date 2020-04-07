package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ProductDaoImpl;

/**
 * Class responsible for application logic. 
 */
public class AppService {
    
    private ManagedCompanyService managedCompanyService; 
    private CustomerCompanyService customerCompanyService;
    private ProductService productService;
    private InvoiceService invoiceService;
    
    private DatabaseUtils databaseUtils;
    
    private ManagedCompany currentManagedCompany;
    private CustomerCompany currentCustomerCompany;
    private Product currentProduct;
    private Invoice currentInvoice;
    
    private Notice notice;
    
    public AppService(
            ManagedCompanyDao managedCompanyDao, 
            CustomerCompanyDao customerCompanyDao, 
            ProductDaoImpl productDao,
            InvoiceDaoImpl invoiceDao
    ) {
        this.databaseUtils = new DatabaseUtils(managedCompanyDao, customerCompanyDao, productDao, invoiceDao);
        this.databaseUtils.initDb();
        
        this.managedCompanyService = new ManagedCompanyService(managedCompanyDao, databaseUtils);
        this.customerCompanyService = new CustomerCompanyService(customerCompanyDao, databaseUtils);
        this.productService = new ProductService(productDao, databaseUtils);
        this.invoiceService = new InvoiceService(invoiceDao, databaseUtils);

        this.currentManagedCompany = new ManagedCompany();
        this.currentCustomerCompany = new CustomerCompany();
        this.currentProduct = new Product();
        this.currentInvoice = new Invoice();
        this.notice = new Notice();
    }
    
    public void setCurrentManagedCompany(ManagedCompany company) {
        this.currentManagedCompany = company;
    }
    
    public ManagedCompany getCurrentManagedCompany() {
        return this.currentManagedCompany;
    }

    public CustomerCompany getCurrentCustomerCompany() {
        return currentCustomerCompany;
    }

    public void setCurrentCustomerCompany(CustomerCompany currentCustomerCompany) {
        this.currentCustomerCompany = currentCustomerCompany;
    }

    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    public void setCurrentInvoice(Invoice currentInvoice) {
        this.currentInvoice = currentInvoice;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
    }

    public boolean createManagedCompany(ManagedCompany company) {
        boolean result = this.managedCompanyService.createManagedCompany(company);
        this.setupNoticeForPreviousAction("createManagedCompany" + result);
        return result;
    }
   
    public Boolean updateManagedCompany(int id, ManagedCompany company) {
        boolean result = this.managedCompanyService.updateManagedCompany(id, company);
        this.setupNoticeForPreviousAction("updateManagedCompany" + result);
        return result;
    }
    
    public Boolean deleteManagedCompany(int id) {
        boolean result = this.managedCompanyService.deleteManagedCompany(id);
        this.setupNoticeForPreviousAction("deleteManagedCompany" + result);
        return result;
    }
    
    public ManagedCompany getManagedCompany(int id) {
        return this.managedCompanyService.getManagedCompany(id);
    }
    
    public List<ManagedCompany> getManagedCompanies() {
        return this.managedCompanyService.getManagedCompanies();
    }
    
    public boolean createCustomerCompany(CustomerCompany company) {
        boolean result = this.customerCompanyService.createCustomerCompany(company);
        this.setupNoticeForPreviousAction("createCustomerCompany" + result);
        return result;
    }
    
    public Boolean updateCustomerCompany(int id, CustomerCompany company) {
        boolean result = this.customerCompanyService.updateCustomerCompany(id, company);
        this.setupNoticeForPreviousAction("updateCustomerCompany" + result);
        return result;
    }
  
    public Boolean deleteCustomerCompany(int id) {
        boolean result = this.customerCompanyService.deleteCustomerCompany(id);
        this.setupNoticeForPreviousAction("deleteCustomerCompany" + result);
        return result;
    }
    
    public List<CustomerCompany> getCustomerCompanies() {
        return this.customerCompanyService.getCustomerCompanies();
    }
    
    public CustomerCompany getCustomerCompanyByName(String name) {
        return this.customerCompanyService.getCustomerCompanyByName(name);
    }
    
    public boolean createProduct(Product product) {
        boolean result = this.productService.createProduct(product);
        this.setupNoticeForPreviousAction("createProduct" + result);
        return result;
    }
    
    public Boolean updateProduct(int id, Product product) {
        boolean result = this.productService.updateProduct(id, product);
        this.setupNoticeForPreviousAction("updateProduct" + result);
        return result;
    }
    
    public Boolean deleteProduct(int id) {
        boolean result = this.productService.deleteProduct(id);
        this.setupNoticeForPreviousAction("deleteProduct" + result);
        return result;
    }
    
    public Product getProduct(int id) {
        return this.productService.getProduct(id);
    }
    
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }
    
    public int getDefaultInvoiceNumber() {
        return this.invoiceService.getDefaultInvoiceNumber();
    }
    
    public boolean createInvoice(Invoice invoice) {
        boolean result = this.invoiceService.createInvoiceForCompany(invoice, currentManagedCompany);
        this.setupNoticeForPreviousAction("createInvoice" + result);
        return result;
    }
    
    public Boolean updateInvoice(int id, Invoice invoice) {
        boolean result = this.invoiceService.updateInvoice(id, invoice);
        this.setupNoticeForPreviousAction("updateInvoice" + result);
        return result;
    }
  
    public Boolean deleteInvoice(int id) {
        boolean result = this.invoiceService.deleteInvoice(id);
        this.setupNoticeForPreviousAction("deleteInvoice" + result);
        return result;
    }
    
    public List<Invoice> getInvoices() {
        return this.invoiceService.getInvoicesForCompany(this.currentManagedCompany.getId());
    }
   
    private void setupNoticeForPreviousAction(String action) {
        this.notice.setActive();
        this.notice.setActionType(action);
    }
    
    public boolean isNoticePending() {
        return this.notice.isIsactive();
    }
   
    public String getPendingNotice() {
        String message = this.notice.getNoticeMessage();
        this.notice.disable();
        return message;
    }
}
