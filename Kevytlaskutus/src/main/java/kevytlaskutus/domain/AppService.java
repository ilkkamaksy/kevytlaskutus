/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import kevytlaskutus.Main;

import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.CustomerCompanyDao;

/**
 *
 * @author ilkka
 */
public class AppService {

    private ManagedCompanyDao managedCompanyDao;
    private CustomerCompanyDao customerCompanyDao;
    
    private Company currentCompany;
    
    public AppService(ManagedCompanyDao managedCompanyDao, CustomerCompanyDao customerCompanyDao) {
        this.managedCompanyDao = managedCompanyDao;
        this.customerCompanyDao = customerCompanyDao;
        this.initDb();
        this.currentCompany = new Company();
    }
    
    public void setCurrentCompany(Company company) {
        this.currentCompany = company;
    }
    
    public Company getCurrentCompany() {
        return this.currentCompany;
    }
    
    public boolean createManagedCompany(ManagedCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            result = managedCompanyDao.create(company);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean updateManagedCompany(int id, ManagedCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            result = managedCompanyDao.update(id, company);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean deleteManagedCompany(int id) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            result = managedCompanyDao.delete(id);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public ManagedCompany getManagedCompany(int id) {
        
        ManagedCompany result = null;
        
        try {
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            result = managedCompanyDao.getItemById(id);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public List<ManagedCompany> getManagedCompanies() {
        
        List<ManagedCompany> results = new ArrayList<>();
        
        try {
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            results = managedCompanyDao.list();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }
    
    public boolean createCustomerCompany(CustomerCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            customerCompanyDao.setConnection(conn);
            result = customerCompanyDao.create(company);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean updateCustomerCompany(int id, CustomerCompany company) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            customerCompanyDao.setConnection(conn);
            result = customerCompanyDao.update(id, company);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
  
    public Boolean deleteCustomerCompany(int id) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            customerCompanyDao.setConnection(conn);
            result = customerCompanyDao.delete(id);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public List<CustomerCompany> getCustomerCompanies() {
        
        List<CustomerCompany> results = new ArrayList<>();
        
        try {
            Connection conn = this.getConnection();
            customerCompanyDao.setConnection(conn);
            results = customerCompanyDao.list();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }
    
    private void initDb() {
        
        try {
            Connection conn = this.getConnection();
            managedCompanyDao.setConnection(conn);
            customerCompanyDao.setConnection(conn);
            
            managedCompanyDao.initDb();
            customerCompanyDao.initDb();
            
            conn.close();
            
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private Connection getConnection() throws ClassNotFoundException, SQLException {     
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:file:./database/kevytlaskutusdb", "sa", "");
    }
}
