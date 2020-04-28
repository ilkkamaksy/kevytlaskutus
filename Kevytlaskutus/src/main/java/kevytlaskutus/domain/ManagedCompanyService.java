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
import kevytlaskutus.dao.ManagedCompanyDao;

/**
 * Service class responsible for dispatching requests for ManagedCompanyDao and returning ManagedCompany entities from database.
 * 
 */
public class ManagedCompanyService {
    
    private ManagedCompanyDao dao;

    private DatabaseUtils databaseUtils;
    
    public ManagedCompanyService(ManagedCompanyDao dao, DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
        this.dao = dao;
    }    
    
    /**
     * Save a ManagedCompany object in database. 
     * 
     * @param company the ManagedCompany to be saved
     * @return boolean
     * @see ManagedCompany
     */
    public boolean createManagedCompany(ManagedCompany company) {
        
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
    
    /**
     * Update a ManagedCompany object in database. 
     *
     * @param id the id of the ManagedCompany to be updated
     * @param company the ManagedCompany to be updated
     * @return boolean
     * @see ManagedCompany
     */
    public Boolean updateManagedCompany(int id, ManagedCompany company) {
        
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
    
    /**
     * Delete a ManagedCompany entity in database. 
     *
     * @param id the id of the ManagedCompany to be updated
     * @return boolean
     * @see ManagedCompany
     */
    public Boolean deleteManagedCompany(int id) {
        
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
    
    /**
     * Retrieve a ManagedCompany object from the database by id. 
     *
     * @param id the id of the ManagedCompany to be fetched.
     * @return ManagedCompany
     * @see ManagedCompany
     */
    public ManagedCompany getManagedCompany(int id) {
        
        ManagedCompany result = null;
        
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            result = dao.getItemById(id);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    /**
     * Retrieves all ManagedCompany entities from the database.
     *
     * @return List of ManagedCompany objects.
     * @see ManagedCompany
     */
    public List<ManagedCompany> getManagedCompanies() {
        
        List<ManagedCompany> results = new ArrayList<>();
        
        try {
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            results = dao.getItems();
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }

}
