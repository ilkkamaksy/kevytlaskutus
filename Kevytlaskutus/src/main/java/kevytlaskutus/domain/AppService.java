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
    
    /**
    * Sets a ManagedCompany object to currently selected in program state.
    * The company argument must specify a ManagedCompany object.  
    *
    * @param  company  a ManagedCompany object.
    * @see         ManagedCompany
    */
    public void setCurrentManagedCompany(ManagedCompany company) {
        this.currentManagedCompany = company;
    }
    
    /**
    * Retrieves the currently selected ManagedCompany object in program state.
    *
    * @return ManagedCompany
    * @see         ManagedCompany
    */
    public ManagedCompany getCurrentManagedCompany() {
        return this.currentManagedCompany;
    }

    /**
    * Retrieves the currently selected CustomerCompany object in program state.
    *
    * @return CustomerCompany
    * @see    CustomerCompany
    */
    public CustomerCompany getCurrentCustomerCompany() {
        return currentCustomerCompany;
    }

    /**
    * Sets a CustomerCompany object to currently selected in program state.
    * The company argument must specify a CustomerCompany object.  
    *
    * @param  customer  a CustomerCompany object.
    * @see CustomerCompany
    */
    public void setCurrentCustomerCompany(CustomerCompany customer) {
        this.currentCustomerCompany = customer;
    }

    /**
    * Retrieves the currently selected Invoice object in program state.
    *
    * @return Invoice
    * @see    Invoice
    */
    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    /**
    * Sets an Invoice object to currently selected in program state.
    * The invoice argument must specify an Invoice object.  
    *
    * @param  invoice  an Invoice object.
    * @see Invoice
    */
    public void setCurrentInvoice(Invoice invoice) {
        this.currentInvoice = invoice;
    }
   
    /**
     * Save the current ManagedCompany object into database.
     * @return boolean
     */
    public boolean saveCurrentManagedCompany() {
        if (this.currentManagedCompany == null || !this.currentManagedCompanyHasName()) {
            return false;
        }
        boolean result = this.managedCompanyService.createManagedCompany(this.currentManagedCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("create" + this.currentManagedCompany.getClass().getSimpleName()));
        return result;
    }
 
    /**
     * Update the current ManagedCompany object in the database.
     * @return boolean
     */
    public boolean updateCurrentManagedCompany() {
        if (this.currentManagedCompany == null || !this.currentManagedCompanyHasName()) {
            return false;
        }
        boolean result = this.managedCompanyService.updateManagedCompany(this.currentManagedCompany.getId(), this.currentManagedCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("update" + this.currentManagedCompany.getClass().getSimpleName()));
        return result;
    }
    
    /**
     * Checks if the current ManagedCompany object has a name.
     * @return boolean
     */
    public boolean currentManagedCompanyHasName() {
        if (this.currentManagedCompany.getName() == null || this.currentManagedCompany.getName().isEmpty()) {
            this.addNoticeToQueue(false, "Please add a name for the company before saving.");
            return false;
        }
        
        return true;
    }
    
    /**
    * Delete a ManagedCompany data entity from the database.
    * The id argument must specify a ManagedCompany id.  
    *
    * @param  id  the id of the ManagedCompany
    * @return boolean
    * @see ManagedCompany
    */
    public boolean deleteManagedCompany(int id) {
        boolean result = this.managedCompanyService.deleteManagedCompany(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
        return result;
    }
    
    /**
    * Retrieves a ManagedCompany from the database by id.
    * The id argument must specify a ManagedCompany id.  
    *
    * @param  id  the id of the ManagedCompany
    * @return ManagedCompany
    * @see ManagedCompany
    */
    public ManagedCompany getManagedCompany(int id) {
        return this.managedCompanyService.getManagedCompany(id);
    }
    
    /**
    * Retrieves all ManagedCompany objects from the database.
    *
    * @return List containing ManagedCompany objects
    * @see ManagedCompany
    */
    public List<ManagedCompany> getManagedCompanies() {
        return this.managedCompanyService.getManagedCompanies();
    }
    
    /**
     * Save the current CustomerCompany object in database.
     * @return boolean
     */
    public boolean saveCurrentCustomerCompany() {
        if (this.currentCustomerCompany == null || !this.currentCustomerCompanyHasName()) {
            return false;
        }
        boolean result = this.customerCompanyService.createCustomerCompany(this.currentCustomerCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("create" + this.currentCustomerCompany.getClass().getSimpleName()));
        return result;
    }
    
    /**
     * Update the current CustomerCompany in database.
     * @return boolean
     */
    public boolean updateCurrentCustomerCompany() {
        if (this.currentCustomerCompany == null || !this.currentCustomerCompanyHasName()) {
            return false;
        }
        boolean result = this.customerCompanyService.updateCustomerCompany(this.currentCustomerCompany.getId(), this.currentCustomerCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("update" + this.currentCustomerCompany.getClass().getSimpleName()));
        return result;
    }
    
    /**
     * Checks if the current CustomerCompany object has a name. 
     * @return boolean
     */
    public boolean currentCustomerCompanyHasName() {
        if (this.currentCustomerCompany.getName() == null || this.currentCustomerCompany.getName().isEmpty()) {
            this.addNoticeToQueue(false, "Please add a name for the customer before saving.");
            return false;
        }
        return true;
    }
 
    /**
    * Delete a CustomerCompany in the database by id.
    *
    * @param  id the id of the CustomerCompany
    * @return boolean
    * @see CustomerCompany
    */
    public boolean deleteCustomerCompany(int id) {
        boolean result = this.customerCompanyService.deleteCustomerCompany(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
        return result;
    }

    /**
    * Retrieves all CustomerCompany entities from the database.
    *
    * @return List containing CustomerCompany objects
    * @see CustomerCompany
    */
    public List<CustomerCompany> getCustomerCompanies() {
        return this.customerCompanyService.getCustomerCompanies();
    }
    
    /**
    * Retrieves a CustomerCompany entity from the database by name.
    *
    * @param  name the name of the CustomerCompany
    * @return a CustomerCompany matching the name
    * @see CustomerCompany
    */
    public CustomerCompany getCustomerCompanyByName(String name) {
        return this.customerCompanyService.getCustomerCompanyByName(name);
    }
    
    /**
    * Returns a default invoice number.
    *
    * @return integer 
    */
    public int getDefaultInvoiceNumber() {
        return this.invoiceService.getDefaultInvoiceNumber();
    }

    /**
     * Save the current Invoice object in database.
     * @return boolean
     */    
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
   
    /**
     * Update the current Invoice object data in database.
     * @return boolean
     */
    public boolean updateCurrentInvoice() {
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
    
    /**
     * Delete an invoice entity in database by id.
     *
     * @param  id the id for the invoice
     * @return boolean
     */
    public boolean deleteInvoice(int id) {
        boolean result = this.invoiceService.deleteInvoice(id);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("delete"));
        return result;
    }
    
    /**
    * Retrieves all Invoice entities from the database.
    *
    * @return List containing Invoice objects
    * @see Invoice
    */
    public List<Invoice> getInvoices() {
        return this.invoiceService.getInvoicesForCompany(this.currentManagedCompany.getId());
    }
   
    /**
    * Retrieves an Invoice entity from the database by id.
    * The id argument must specify an Invoice id.  
    *
    * @param  id  the id of the Invoice
    * @return Invoice
    * @see Invoice
    */
    public Invoice getInvoiceById(int id) {
        return this.invoiceService.getInvoiceById(id);
    }
   
    /**
     * Checks if there are notices in NoticeQueue.
     * @return boolean
     */
    public boolean hasNoticePending() {
        return this.noticeQueue.hasPendingNotice();
    }
   
    /**
     * Get the first pending notice from NoticeQueue.
     * @return Notice
     */
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
