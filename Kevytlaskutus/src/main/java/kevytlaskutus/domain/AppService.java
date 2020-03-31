package kevytlaskutus.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.ProductDaoImpl;

/**
 * Class responsible for application logic. 
 */
public class AppService {

    private ManagedCompanyDao managedCompanyDao;
    private CustomerCompanyDao customerCompanyDao;
    private ProductDaoImpl productDao;
    
    private Company currentManagedCompany;
    private Company currentCustomerCompany;
    private Product currentProduct;
    
    public AppService(ManagedCompanyDao managedCompanyDao, CustomerCompanyDao customerCompanyDao, ProductDaoImpl productDao) {
        this.managedCompanyDao = managedCompanyDao;
        this.customerCompanyDao = customerCompanyDao;
        this.productDao = productDao;
        this.initDb();
        this.currentManagedCompany = new ManagedCompany();
        this.currentCustomerCompany = new CustomerCompany();
        this.currentProduct = new Product();
    }
    
    public void setCurrentManagedCompany(Company company) {
        this.currentManagedCompany = company;
    }
    
    public Company getCurrentManagedCompany() {
        return this.currentManagedCompany;
    }

    public Company getCurrentCustomerCompany() {
        return currentCustomerCompany;
    }

    public void setCurrentCustomerCompany(Company currentCustomerCompany) {
        this.currentCustomerCompany = currentCustomerCompany;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentProduct(Product currentProduct) {
        this.currentProduct = currentProduct;
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
            results = managedCompanyDao.getItems();
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
            results = customerCompanyDao.getItems();
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return results;
    }
    
    public boolean createProduct(Product product) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            productDao.setConnection(conn);
            result = productDao.create(product);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean updateProduct(int id, Product product) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            productDao.setConnection(conn);
            result = productDao.update(id, product);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Boolean deleteProduct(int id) {
        
        Boolean result = false;
        
        try {    
            Connection conn = this.getConnection();
            productDao.setConnection(conn);
            result = productDao.delete(id);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public Product getProduct(int id) {
        
        Product result = null;
        
        try {
            Connection conn = this.getConnection();
            productDao.setConnection(conn);
            result = productDao.getItemById(id);
        } catch (SQLException | ClassNotFoundException e) {
            Logger.getLogger(AppService.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return result;
    }
    
    public List<Product> getProducts() {
        
        List<Product> results = new ArrayList<>();
        
        try {
            Connection conn = this.getConnection();
            productDao.setConnection(conn);
            results = productDao.getItems();
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
            productDao.setConnection(conn);
            
            managedCompanyDao.initDb();
            customerCompanyDao.initDb();
            productDao.initDb();
            
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
