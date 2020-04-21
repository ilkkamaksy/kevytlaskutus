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
   
    private ManagedCompany currentManagedCompany;
    private CustomerCompany currentCustomerCompany;
    private Product currentProduct;
    private Invoice currentInvoice;
    
    private NoticeMessages noticeMessages = new NoticeMessages();
    private NoticeQueue noticeQueue;
    
    public AppService(
            ManagedCompanyDao managedCompanyDao, 
            CustomerCompanyDao customerCompanyDao, 
            ProductDaoImpl productDao,
            InvoiceDaoImpl invoiceDao,
            DatabaseUtils databaseUtils
    ) {
        
        this.managedCompanyService = new ManagedCompanyService(managedCompanyDao, databaseUtils);
        this.customerCompanyService = new CustomerCompanyService(customerCompanyDao, databaseUtils);
        this.productService = new ProductService(productDao, databaseUtils);
        this.invoiceService = new InvoiceService(invoiceDao, databaseUtils);

        this.currentManagedCompany = new ManagedCompany();
        this.currentManagedCompany.setName("");
        this.currentCustomerCompany = new CustomerCompany();
        this.currentProduct = new Product();
        this.currentInvoice = new Invoice();
        this.currentInvoice.setInvoiceNumber(this.getDefaultInvoiceNumber());
        this.noticeQueue = new NoticeQueue();
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
        this.addNoticeToQueue(result, "create" + company.getClass().getSimpleName());
        return result;
    }
  
    public Boolean updateManagedCompany(int id, ManagedCompany company) {
        boolean result = this.managedCompanyService.updateManagedCompany(id, company);
        this.addNoticeToQueue(result, "update" + company.getClass().getSimpleName());
        return result;
    }
    
    public Boolean deleteManagedCompany(int id) {
        boolean result = this.managedCompanyService.deleteManagedCompany(id);
        this.addNoticeToQueue(result, "delete");
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
        this.addNoticeToQueue(result, "create" + company.getClass().getSimpleName());
        return result;
    }
    
    public Boolean updateCustomerCompany(int id, CustomerCompany company) {
        boolean result = this.customerCompanyService.updateCustomerCompany(id, company);
        this.addNoticeToQueue(result, "update" + company.getClass().getSimpleName());
        return result;
    }
  
    public Boolean deleteCustomerCompany(int id) {
        boolean result = this.customerCompanyService.deleteCustomerCompany(id);
        this.addNoticeToQueue(result, "delete");
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
        this.addNoticeToQueue(result, "create" + product.getClass().getSimpleName());
        return result;
    }
    
    public Boolean updateProduct(int id, Product product) {
        boolean result = this.productService.updateProduct(id, product);
        this.addNoticeToQueue(result, "update" + product.getClass().getSimpleName());
        return result;
    }
    
    public Boolean deleteProduct(int id) {
        boolean result = this.productService.deleteProduct(id);
        this.addNoticeToQueue(result, "delete");
        return result;
    }
    
    public Product getProduct(int id) {
        return this.productService.getProduct(id);
    }
    
    public Product getProduct(String name) {
        return this.productService.getProduct(name);
    }
    
    public List<Product> getProducts() {
        return this.productService.getProducts();
    }
    
    public int getDefaultInvoiceNumber() {
        return this.invoiceService.getDefaultInvoiceNumber();
    }
    
    public boolean createInvoice(Invoice invoice) {
        boolean result = this.invoiceService.createInvoiceForCompany(invoice, currentManagedCompany);
        this.addNoticeToQueue(result, "create" + invoice.getClass().getSimpleName());
        return result;
    }
    
    public Boolean updateInvoice(int id, Invoice invoice) {
        boolean result = this.invoiceService.updateInvoice(id, invoice, currentManagedCompany);
        this.addNoticeToQueue(result, "update" + invoice.getClass().getSimpleName());
        return result;
    }
  
    public Boolean deleteInvoice(int id) {
        boolean result = this.invoiceService.deleteInvoice(id);
        this.addNoticeToQueue(result, "delete");
        return result;
    }
    
    public List<Invoice> getInvoices() {
        return this.invoiceService.getInvoicesForCompany(this.currentManagedCompany.getId());
    }
   
    public Invoice getInvoiceById(int id) {
        return this.invoiceService.getInvoiceById(id);
    }
   
    public boolean hasNoticePending() {
        return this.noticeQueue.hasPendingNotice();
    }
   
    public Notice getPendingNotice() {
        return this.noticeQueue.getPendingNotice();
    }
    
    private void addNoticeToQueue(boolean success, String eventType) {
        Notice notice = null;
        if (success) {
            notice = new NoticeSuccess(noticeMessages.getNoticeMessage(eventType));
        } else {
            notice = new NoticeError(noticeMessages.getNoticeMessage(eventType));
        }
        this.noticeQueue.addNotice(notice);
    }
}
