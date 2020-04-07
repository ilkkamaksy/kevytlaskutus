package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

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
        return this.managedCompanyService.createManagedCompany(company);
    }
    
    public Boolean updateManagedCompany(int id, ManagedCompany company) {
        return this.managedCompanyService.updateManagedCompany(id, company);
    }
    
    public Boolean deleteManagedCompany(int id) {
        return this.managedCompanyService.deleteManagedCompany(id);
    }
    
    public ManagedCompany getManagedCompany(int id) {
        return this.managedCompanyService.getManagedCompany(id);
    }
    
    public List<ManagedCompany> getManagedCompanies() {
        return this.managedCompanyService.getManagedCompanies();
    }
    
    public boolean createCustomerCompany(CustomerCompany company) {
        return this.customerCompanyService.createCustomerCompany(company);
    }
    
    public Boolean updateCustomerCompany(int id, CustomerCompany company) {
        return this.customerCompanyService.updateCustomerCompany(id, company);
    }
  
    public Boolean deleteCustomerCompany(int id) {
        return this.customerCompanyService.deleteCustomerCompany(id);
    }
    
    public List<CustomerCompany> getCustomerCompanies() {
        return this.customerCompanyService.getCustomerCompanies();
    }
    
    public CustomerCompany getCustomerCompanyByName(String name) {
        return this.customerCompanyService.getCustomerCompanyByName(name);
    }
    
    public boolean createProduct(Product product) {
        return this.productService.createProduct(product);
    }
    
    public Boolean updateProduct(int id, Product product) {
        return this.productService.updateProduct(id, product);
    }
    
    public Boolean deleteProduct(int id) {
        return this.productService.deleteProduct(id);
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
        return this.invoiceService.createInvoiceForCompany(invoice, currentManagedCompany);
    }
    
    public Boolean updateInvoice(int id, Invoice invoice) {
        return this.invoiceService.updateInvoice(id, invoice);
    }
  
    public Boolean deleteInvoice(int id) {
        return this.invoiceService.deleteInvoice(id);
    }
    
    public List<Invoice> getInvoices() {
        return this.invoiceService.getInvoicesForCompany(this.currentManagedCompany.getId());
    }
   
}
