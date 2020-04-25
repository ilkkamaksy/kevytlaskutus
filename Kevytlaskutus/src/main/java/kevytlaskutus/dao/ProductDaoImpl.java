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
    
    static Populate populate;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Product (\n"
            + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
            + "    invoiceId INTEGER,\n"
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
    public void updateProductsInBatches(Integer invoiceId, List<Product> products) throws SQLException {
        String sql = "UPDATE Product "
                +   "SET name=?, price=?, priceUnit=?, description=?, modifiedTime=? \n" 
                +   "WHERE id=?";
                
        PreparedStatement stmt = null;    
        this.conn.setAutoCommit(false);
        stmt = conn.prepareStatement(sql);
        
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);

        for (Product product : products) {
            System.out.println("looppu " + product.getId() + " " + product.getName());
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setString(3, product.getPriceUnit());
            stmt.setString(4, product.getDescription());
            stmt.setTimestamp(5, ts);
            stmt.setInt(6, product.getId());
            stmt.addBatch();
        }

        int[] affectedRecords = stmt.executeBatch();
        
        conn.commit();
        conn.setAutoCommit(true);
       
        this.deleteObsolete(ts, invoiceId);
    }
    
    public void deleteObsolete(Timestamp ts, Integer id) throws SQLException {
        System.out.println("timestamp " + ts.toString());
        String sql = "DELETE FROM Product WHERE invoiceId=? AND modifiedTime < ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.setTimestamp(2, ts);
        int rows = stmt.executeUpdate();
        System.out.println("rows " + rows);
    }
    
    @Override
    public void saveProductsInBatches(Integer invoiceId, List<Product> products) throws SQLException {
        String sql = "INSERT INTO Product (name, price, priceUnit, description, invoiceId, modifiedTime) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = null;    
        this.conn.setAutoCommit(false);
        stmt = conn.prepareStatement(sql);
        
        long timeNow = Calendar.getInstance().getTimeInMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(timeNow);
       
        for (Product product : products) {
            stmt.setString(1, product.getName());
            stmt.setBigDecimal(2, product.getPrice());
            stmt.setString(3, product.getPriceUnit());
            stmt.setString(4, product.getDescription());
            stmt.setInt(5, invoiceId);
            stmt.setTimestamp(6, ts);
            stmt.addBatch();
        }

        int[] affectedRecords = stmt.executeBatch();
        
        conn.commit();
        conn.setAutoCommit(true);
    }
  
  
    @Override
    public List<Product> getProductsByInvoiceId(Integer id) throws SQLException {
        String sql = "SELECT * FROM Product WHERE invoiceId = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        List<Product> results = new ArrayList<>(); 
        while (rs.next()) {
            results.add(populate.populateProduct(rs));
        }

        conn.close();
        return results;
    }
    
    public void getAll() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Product AS Product"
        );
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            Product prod = populate.populateProduct(rs);
            System.out.println("getall " + prod.getName());
            System.out.println("time " + rs.getTimestamp("modifiedTime"));
        }
        
        stmt.close();
        conn.close();
    }
    
}
