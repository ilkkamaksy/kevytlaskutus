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
 *
 * @author ilkka
 */
public class InvoiceService {
    
    private InvoiceDaoImpl dao;
    
    private DatabaseUtils databaseUtils;
    
    private int startInvoiceNumbering = 1001;
    
    public InvoiceService(InvoiceDaoImpl dao, DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
        this.dao = dao;
    }
    
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
    
    public boolean createInvoiceForCompany(Invoice invoice, ManagedCompany managedCompany) {
        if (invoice == null || managedCompany == null || managedCompany.getId() < 1) {
            return false;
        }
        
        Boolean result = false;
        
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
    
    public Boolean updateInvoice(int id, Invoice invoice, ManagedCompany managedCompany) {
        Boolean result = false;
        
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            invoice.setCompany(managedCompany);
            result = dao.update(id, invoice);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
  
    public Boolean deleteInvoice(int id) {
        Boolean result = false;
        
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.delete(id);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
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
