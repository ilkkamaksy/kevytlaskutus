/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kevytlaskutus.dao.ProductDao;
import kevytlaskutus.dao.ProductDaoImpl;

/**
 * Service class responsible for dispatching requests for ProductDao and returning Product entities from database.
 */
public class ProductService {
    
    private ProductDaoImpl dao;
    
    private DatabaseUtils databaseUtils;
    
    public ProductService(ProductDaoImpl dao, DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
        this.dao = dao;
    }    
     
    /**
     * Saves Product objects in batches to database.
     * @param invoiceId the id of the Invoice that the products are associated with.
     * @param products the List of Product objects to be saved
     * @return boolean
     * @see Product
     */
    public boolean saveProductsInBatches(Integer invoiceId, List<Product> products) {
        boolean success = false;
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            success = dao.saveProductsInBatches(invoiceId, products);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return success;
    }
    
    /**
     * Update Product objects in batches in the database.
     * @param invoiceId the id of the Invoice associated with the products.
     * @param products the List of Product objects to be saved
     * @return boolean
     * @see Product
     */
    public boolean updateProductsInBatches(Integer invoiceId, List<Product> products) {
        boolean success = false;
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            success = dao.updateProductsInBatches(invoiceId, products);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        return success;
    }
    
    /**
     * Retrieves a List of Product objects associated with an Invoice from the database.
     * @param id the id of the Invoice 
     * @return List of Product objects 
     * @see Product
     */
    public List<Product> getProductsByInvoiceId(Integer id) {
        List<Product> results = new ArrayList<>();
        
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            results = dao.getProductsByInvoiceId(id);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }
}
