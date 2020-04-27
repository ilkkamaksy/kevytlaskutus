package kevytlaskutus.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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
    private InvoiceService invoiceService;
    private ProductService productService;
   
    private ManagedCompany currentManagedCompany;
    private CustomerCompany currentCustomerCompany;
    private Invoice currentInvoice;
    
    private NoticeMessages noticeMessages = new NoticeMessages();
    private NoticeQueue noticeQueue;
    
    public AppService(
            ManagedCompanyDao managedCompanyDao, 
            CustomerCompanyDao customerCompanyDao, 
            InvoiceDaoImpl invoiceDao,
            ProductDaoImpl productDao,
            DatabaseUtils databaseUtils
    ) {
        
        this.managedCompanyService = new ManagedCompanyService(managedCompanyDao, databaseUtils);
        this.customerCompanyService = new CustomerCompanyService(customerCompanyDao, databaseUtils);
        this.invoiceService = new InvoiceService(invoiceDao, databaseUtils);
        this.productService = new ProductService(productDao, databaseUtils);

        this.currentManagedCompany = new ManagedCompany();
        this.currentManagedCompany.setName("");
        this.currentCustomerCompany = new CustomerCompany();
        this.currentInvoice = new Invoice();
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
   
    public boolean saveCurrentManagedCompany() {
        if (this.currentManagedCompany == null || !this.currentManagedCompanyHasName()) {
            return false;
        }
        boolean result = this.managedCompanyService.createManagedCompany(this.currentManagedCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("create" + this.currentManagedCompany.getClass().getSimpleName()));
        return result;
    }
 
    public Boolean updateCurrentManagedCompany() {
        if (this.currentManagedCompany == null || !this.currentManagedCompanyHasName()) {
            return false;
        }
        boolean result = this.managedCompanyService.updateManagedCompany(this.currentManagedCompany.getId(), this.currentManagedCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("update" + this.currentManagedCompany.getClass().getSimpleName()));
        return result;
    }
    
    public boolean currentManagedCompanyHasName() {
        if (this.currentManagedCompany.getName() == null || this.currentManagedCompany.getName().isEmpty()) {
            this.addNoticeToQueue(false, "Please add a name for the company before saving.");
            return false;
        }
        
        return true;
    }
    
    public Boolean deleteManagedCompany(int id) {
        boolean result = this.managedCompanyService.deleteManagedCompany(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
        return result;
    }
    
    public ManagedCompany getManagedCompany(int id) {
        return this.managedCompanyService.getManagedCompany(id);
    }
    
    public List<ManagedCompany> getManagedCompanies() {
        return this.managedCompanyService.getManagedCompanies();
    }
    
    public boolean saveCurrentCustomerCompany() {
        if (this.currentCustomerCompany == null || !this.currentCustomerCompanyHasName()) {
            return false;
        }
        boolean result = this.customerCompanyService.createCustomerCompany(this.currentCustomerCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("create" + this.currentCustomerCompany.getClass().getSimpleName()));
        return result;
    }
    
    public Boolean updateCurrentCustomerCompany() {
        if (this.currentCustomerCompany == null || !this.currentCustomerCompanyHasName()) {
            return false;
        }
        boolean result = this.customerCompanyService.updateCustomerCompany(this.currentCustomerCompany.getId(), this.currentCustomerCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("update" + this.currentCustomerCompany.getClass().getSimpleName()));
        return result;
    }
    
    public boolean currentCustomerCompanyHasName() {
        if (this.currentCustomerCompany.getName() == null || this.currentCustomerCompany.getName().isEmpty()) {
            this.addNoticeToQueue(false, "Please add a name for the customer before saving.");
            return false;
        }
        return true;
    }
 
    public Boolean deleteCustomerCompany(int id) {
        boolean result = this.customerCompanyService.deleteCustomerCompany(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
        return result;
    }
    
    public List<CustomerCompany> getCustomerCompanies() {
        return this.customerCompanyService.getCustomerCompanies();
    }
    
    public CustomerCompany getCustomerCompanyByName(String name) {
        return this.customerCompanyService.getCustomerCompanyByName(name);
    }
        
    public int getDefaultInvoiceNumber() {
        return this.invoiceService.getDefaultInvoiceNumber();
    }
    
    public boolean saveCurrentInvoice() {
        if (!this.invoiceHasCustomer() || !this.allProductsHaveNames()) {
            return false;
        }
        
        Integer invoiceId = this.invoiceService.createInvoiceForCompany(this.currentInvoice, currentManagedCompany);
        boolean success = invoiceId > -1 ? true : false;
        
        if (success && this.currentInvoice.getProducts().size() > 0 && !this.currentInvoice.getProducts().get(0).getName().isEmpty()) {
            this.productService.saveProductsInBatches(invoiceId, this.currentInvoice.getProducts());
        } 
        
        this.addNoticeToQueue(success, noticeMessages.getNoticeMessage("create" + this.currentInvoice.getClass().getSimpleName()));
        return success;
    }
   
    public Boolean updateCurrentInvoice() {
        if (!this.invoiceHasCustomer() || !this.allProductsHaveNames()) {
            return false;
        }
        
        boolean result = this.invoiceService.updateInvoice(this.currentInvoice.getId(), this.currentInvoice, currentManagedCompany);

        this.updateProductsInBatches(result);
        
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("update" + this.currentInvoice.getClass().getSimpleName()));
        return result;
    }
  
    private void updateProductsInBatches(boolean success) {
        if (success && this.currentInvoice.getProducts().size() > 0) {          
            this.productService.updateProductsInBatches(this.currentInvoice.getId(), this.currentInvoice.getProducts());
           
            List<Product> newProducts = this.getNewProductsFromCurrentInvoice();
            if (newProducts.size() > 0) {
                this.productService.saveProductsInBatches(this.currentInvoice.getId(), newProducts);
            }
        }
    }
    
    private boolean allProductsHaveNames() {
        for (Product product : this.currentInvoice.getProducts()) {
            if (product.getName() == null || product.getName().isBlank()) {
                this.addNoticeToQueue(false, "Please make sure all products have at least a name.");
                return false;
            }
        }
        
        return true;
    }
    
    private List<Product> getNewProductsFromCurrentInvoice() {
        List<Product> products = new ArrayList<>();
        for (Product prod : this.currentInvoice.getProducts()) {
            if (prod.getId() == 0) {
                products.add(prod);
            }
        }
        return products;
    }
   
    private boolean invoiceHasCustomer() {
        if (this.currentInvoice.getCustomer() == null) {
            this.addNoticeToQueue(false, "Please select a customer for the invoice first.");
            return false;
        }
        
        return true;
    }
    
    public Boolean deleteInvoice(int id) {
        boolean result = this.invoiceService.deleteInvoice(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
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
    
    private void addNoticeToQueue(boolean success, String message) {
        Notice notice = null;
        if (success) {
            notice = new NoticeSuccess(message);
        } else {
            notice = new NoticeError(message);
        }
        this.noticeQueue.addNotice(notice);
    }
}
