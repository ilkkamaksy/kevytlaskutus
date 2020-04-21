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
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ManagedCompanyDao;

/**
 *
 * @author ilkka
 */
public class DatabaseUtils {
    
    private ManagedCompanyDao managedCompanyDao;
    private CustomerCompanyDao customerCompanyDao;
    private InvoiceDaoImpl invoiceDao;
    
    private String dbOption;
    private String dbUser;
    private String dbPassword;
    
    public DatabaseUtils(
        ManagedCompanyDao managedCompanyDao, 
        CustomerCompanyDao customerCompanyDao, 
        InvoiceDaoImpl invoiceDao,
        String dbOption,
        String dbUser,
        String dbPassword
    ) {
        this.managedCompanyDao = managedCompanyDao;
        this.customerCompanyDao = customerCompanyDao;
        this.invoiceDao = invoiceDao;
        this.dbOption = dbOption;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public void initDb() {
        
        try {
            Connection conn = this.getConnection();
            this.prepareCustomerCompanyDaoConnection(conn);
            this.prepareInvoiceDaoConnection(conn);
            this.prepareManagedCompanyDaoConnection(conn);
            
            managedCompanyDao.initDb();
            customerCompanyDao.initDb();
            invoiceDao.initDb();
            
            conn.close();
            
        } catch (SQLException e) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    

    public void prepareInvoiceDaoConnection(Connection conn) {
        this.invoiceDao.setConnection(conn);
    }
    
    public void prepareManagedCompanyDaoConnection(Connection conn) {
        this.managedCompanyDao.setConnection(conn);
    }
    
    public void prepareCustomerCompanyDaoConnection(Connection conn) {
        this.customerCompanyDao.setConnection(conn);
    }
   
    public Connection getConnection() throws SQLException {     
        return DriverManager.getConnection(this.dbOption, this.dbUser, this.dbPassword);
    }
}
