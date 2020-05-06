package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
        this.invoiceService.setProductService(this.productService);
        
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

    public void setCurrentCustomerCompany(CustomerCompany customer) {
        this.currentCustomerCompany = customer;
    }

    public Invoice getCurrentInvoice() {
        return currentInvoice;
    }

    public void setCurrentInvoice(Invoice invoice) {
        this.currentInvoice = invoice;
    }
   
    /**
     * Save the current ManagedCompany object into database.
     * @return boolean result of operation 
     */
    public boolean saveCurrentManagedCompany() {
        if (!Validate.managedCompanyHasName(currentManagedCompany)) {
            this.addNoticeToQueue(false, "Please add a name for the company before saving.");
            return false;
        }
        
        boolean result = this.managedCompanyService.saveCurrentManagedCompany(currentManagedCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("save" + this.currentManagedCompany.getClass().getSimpleName()));
        return result;
    }
   
    /**
    * Delete a ManagedCompany data entity from the database. The id argument must specify a ManagedCompany id.  
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
     * Get a ManagedCompany entity from the database by id.
     * @param id the id of the ManagedCompany
     * @return ManagedCompany
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
     * @return boolean result of operation
     */
    public boolean saveCurrentCustomerCompany() {
        if (!Validate.customerCompanyHasName(this.currentCustomerCompany)) {
            this.addNoticeToQueue(false, "Please add a name for the customer before saving.");
            return false;
        }
        boolean result = this.customerCompanyService.saveCustomerCompany(currentCustomerCompany);
        this.addNoticeToQueue(result, noticeMessages.getNoticeMessage("save" + this.currentCustomerCompany.getClass().getSimpleName()));
        return result;
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
    * Retrieves a list of all CustomerCompany entities from the database.
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
     * Update the reference number for current Invoice by invoice number.
     * @see Invoice
     * @see ReferenceNumber
     * @return Integer referenceNumber
     */
    public Integer updateCurrentInvoiceReferenceNumber() {
        Integer referenceNumber = ReferenceNumber.generateInvoiceReferenceNumber(this.currentInvoice);
        this.currentInvoice.setReferenceNumber(referenceNumber);
        return referenceNumber;
    }
    
    /**
     * Updates current invoice total sum before taxes, by calculating sum of products on the invoice. 
     * If discount percentage has been defined, the discount amount will be substracted from total sum.
     * @see InvoiceTotal
     * @see Invoice
     * @return totalAmount  BigDecimal sum of products with discount amount substracted
     */
    public BigDecimal updateCurrentInvoiceTotal() {
        BigDecimal totalAmount = InvoiceTotal.calculateInvoiceTotal(this.currentInvoice);
        this.currentInvoice.setAmount(totalAmount.setScale(2, RoundingMode.CEILING));
        return totalAmount;
    }
   
    /**
     * Returns the total sum of current invoice with taxes included.
     * @return BigDecimal the total sum of current invoice plus taxes
     */
    public BigDecimal getCurrentInvoiceTotalIncludingTaxes() {
        return InvoiceTotal.calculateInvoiceTotalIncludingTaxes(currentInvoice); 
    }
    
    /**
     * Returns the amount of taxes for current invoice total sum.
     * @return BigDecimal the amount of taxes for current invoice total sum
     */
    public BigDecimal getCurrentInvoiceTotalTaxes() {
        return InvoiceTotal.calculateInvoiceTotalTaxes(currentInvoice); 
    }
    
    /**
     * Save the current Invoice object in database.
     * @return boolean
     */    
    public boolean saveCurrentInvoice() {
        if (!this.validateInvoiceProperties()) {
            return false;
        }
        boolean success = this.invoiceService.saveInvoice(this.currentInvoice, this.currentManagedCompany);
        this.addNoticeToQueue(success, noticeMessages.getNoticeMessage("save" + this.currentInvoice.getClass().getSimpleName()));
        return success;
    }
    
    private boolean validateInvoiceProperties() {
        if (!Validate.invoiceHasCustomer(this.currentInvoice)) {
            this.addNoticeToQueue(false, "Please select a customer for the invoice first.");
            return false;
        }
        if (!Validate.invoiceProductsHaveNames(this.currentInvoice)) {
            this.addNoticeToQueue(false, "Please make sure all products on invoice have names.");
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
