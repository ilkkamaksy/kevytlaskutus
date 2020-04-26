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
 *
 * @author ilkka
 */
public class ProductService {
    
    private ProductDaoImpl dao;
    
    private DatabaseUtils databaseUtils;
    
     public ProductService(ProductDaoImpl dao, DatabaseUtils databaseUtils) {
        this.databaseUtils = databaseUtils;
        this.dao = dao;
    }    
     
    public void saveProductsInBatches(Integer invoiceId, List<Product> products) {
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            dao.saveProductsInBatches(invoiceId, products);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void updateProductsInBatches(Integer invoiceId, List<Product> products) {
        try {    
            Connection conn = this.databaseUtils.getConnection();
            dao.setConnection(conn);
            dao.updateProductsInBatches(invoiceId, products);
        } catch (SQLException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    
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
