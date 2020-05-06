package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import static java.time.LocalDate.now;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import kevytlaskutus.dao.Populate;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

/**
 * Data Access Object handling product object database operations.
 */
public class ProductDaoImpl implements ProductDao<Product, Integer, String> {

    Connection conn;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Product (\n"
            + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
            + "    invoiceId INTEGER NULL,\n"
            + "    modifiedTime TIMESTAMP,\n"
            + "    name VARCHAR(200),\n"
            + "    price DECIMAL,\n"
            + "    priceUnit VARCHAR(20),\n"
            + "    description VARCHAR(50),\n"
            + "    FOREIGN KEY (invoiceId) "
            + "         REFERENCES Invoice(id) "
            + "         ON DELETE CASCADE "
            + "         ON UPDATE CASCADE "
            + ");").executeUpdate();
    }
    
    @Override
    public boolean updateProductsInBatches(Integer invoiceId, List<Product> products) throws SQLException {
        String sql = "UPDATE Product "
                +   "SET name=?, price=?, priceUnit=?, description=?, invoiceId=? \n" 
                +   "WHERE id=?";
                
        PreparedStatement stmt = null;    
        this.conn.setAutoCommit(false);
        stmt = conn.prepareStatement(sql);

        for (Product product : products) {
            Populate.populateUpdateStatementData(stmt, product);
            stmt.addBatch();
        }

        int[] affectedRecords = stmt.executeBatch();
        
        conn.commit();
        conn.setAutoCommit(true);
            
        this.deleteObsolete(0);
        return affectedRecords.length > 0;
    }
    
    public void deleteObsolete(Integer id) throws SQLException {
        String sql = "DELETE FROM Product WHERE invoiceId=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
    }
    
    @Override
    public boolean saveProductsInBatches(Integer invoiceId, List<Product> products) throws SQLException {
        String sql = "INSERT INTO Product (name, price, priceUnit, description, invoiceId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;    
        this.conn.setAutoCommit(false);
        stmt = conn.prepareStatement(sql);
       
        for (Product product : products) {
            Populate.populateCreateStatementData(stmt, product, invoiceId);
            stmt.addBatch();
        }
        int[] affectedRecords = stmt.executeBatch();
        
        conn.commit();
        conn.setAutoCommit(true);
        return affectedRecords.length > 0;
    }
  
  
    @Override
    public List<Product> getProductsByInvoiceId(Integer id) throws SQLException {
        String sql = "SELECT * FROM Product WHERE invoiceId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        List<Product> results = new ArrayList<>(); 
        while (rs.next()) {
            results.add(Populate.populateProduct(rs));
        }

        conn.close();
        return results;
    }
   
}
