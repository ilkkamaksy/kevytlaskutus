/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kevytlaskutus.dao.InvoiceDaoImpl;

/**
 * Service class responsible for dispatching requests for InvoiceDao and returning Invoice entities from database.
 * 
 */
public class InvoiceService {
    
    private InvoiceDaoImpl dao;
    private ProductService productService;
    private DatabaseUtils databaseUtils;
    private int startInvoiceNumbering = 1001;
    
    public InvoiceService(InvoiceDaoImpl dao, DatabaseUtils databaseUtils) {
        this.dao = dao;
        this.databaseUtils = databaseUtils;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
   
    /**
     * Retrieves a default invoice number
     *
     * @return integer
     * @see Invoice
     */
    public int getDefaultInvoiceNumber() {
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            this.startInvoiceNumbering += dao.getInvoiceCount();
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return startInvoiceNumbering;
    }
    
    /**
     * Save the current Invoice object in database.
     * @param invoice the Invoice object to save
     * @param company the ManagedCompany object attached to the invoice.
     * @return boolean
     */    
    public boolean saveInvoice(Invoice invoice, ManagedCompany company) {
        if (invoice == null || company == null || company.getId() < 1) {
            return false;
        }
        
        boolean success = false;

        if (invoice.getId() == 0) {
            Integer invoiceId = this.createInvoiceForCompany(invoice, company);
            success = invoiceId > -1 ? true : false;
            this.saveProductsInBatches(success, invoiceId, invoice.getProducts());
        } else {
            success = this.updateInvoice(invoice, company);
            this.updateProductsInBatches(success, invoice);
        }

        return success;
    }
    
    private void updateProductsInBatches(boolean success, Invoice invoice) {
        if (success && invoice.getProducts().size() > 0) {
            this.productService.updateProductsInBatches(invoice.getId(), invoice.getProducts());
            List<Product> newProducts = this.getNewProductsFromInvoice(invoice);
            if (newProducts.size() > 0) {
                this.saveProductsInBatches(success, invoice.getId(), newProducts);
            }
        }
    }

    private void saveProductsInBatches(boolean success, Integer invoiceId, List<Product> products) {
        if (success && products.size() > 0) {
            this.productService.saveProductsInBatches(invoiceId, products);
        }
    }
    
    private List<Product> getNewProductsFromInvoice(Invoice invoice) {
        List<Product> products = new ArrayList<>();
        for (Product prod : invoice.getProducts()) {
            if (prod.getId() == 0) {
                products.add(prod);
            }
        }
        return products;
    }
    
    private Integer createInvoiceForCompany(Invoice invoice, ManagedCompany managedCompany) {
        Integer result = -1;
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            invoice.setCompany(managedCompany);
            result = dao.create(invoice);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    private boolean updateInvoice(Invoice invoice, ManagedCompany managedCompany) {
        boolean result = false;
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            invoice.setCompany(managedCompany);
            result = dao.update(invoice.getId(), invoice);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
  
    /**
     * Delete an Invoice entity in database. 
     *
     * @param id the id of the Invoice to be updated
     * @return boolean
     * @see Invoice
     */
    public boolean deleteInvoice(int id) {
        boolean result = false;
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.delete(id);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return result;
    }
    
    /**
     * Retrieves all Invoice entities attached to a ManagedCompany from the database.
     *
     * @param managedCompanyId the id of the ManagedCompany 
     * @return List of Invoice objects.
     * @see Invoice
     */
    public List<Invoice> getInvoicesForCompany(int managedCompanyId) {
        List<Invoice> results = new ArrayList<>();
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            results = dao.getItems(managedCompanyId);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return results;
    }
    
    /**
     * Retrieve an Invoice entity from the database by id. 
     *
     * @param id the id of the Invoice to be fetched.
     * @return Invoice
     * @see Invoice
     */
    public Invoice getInvoiceById(int id) {
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            return dao.getItemById(id);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
