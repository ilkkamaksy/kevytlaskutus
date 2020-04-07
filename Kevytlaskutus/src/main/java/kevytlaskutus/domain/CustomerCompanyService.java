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
import kevytlaskutus.dao.CustomerCompanyDao;

/**
 *
 * @author ilkka
 */
public class CustomerCompanyService {
    
    private CustomerCompanyDao dao;

    private DatabaseUtils databaseUtils;
    
    public CustomerCompanyService(CustomerCompanyDao dao, DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
        this.dao = dao;
    }    
    
    public boolean createCustomerCompany(CustomerCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.create(company);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean updateCustomerCompany(int id, CustomerCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.update(id, company);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
  
    public Boolean deleteCustomerCompany(int id) {
        
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
    
    public List<CustomerCompany> getCustomerCompanies() {
        
        List<CustomerCompany> results = new ArrayList<>();
        
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            results = dao.getItems();
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }
    
    public CustomerCompany getCustomerCompanyByName(String name) {
        
        CustomerCompany result = null;
        
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.getItemByName(name);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }

}
