package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
            + "    name VARCHAR(200),\n"
            + "    price VARCHAR(20),\n"
            + "    priceUnit VARCHAR(20),\n"
            + "    description VARCHAR(50),\n"
            + "    FOREIGN KEY (invoiceId) REFERENCES Invoice(id) \n"
            + ");").executeUpdate();
    }
    
    public void saveInBatches(int invoiceId, List<Product> products) throws SQLException {
        Statement stmt = null;    
        conn.setAutoCommit(false);
        stmt = conn.createStatement();

        for (Product product : products) {
            stmt.addBatch(
                "INSERT INTO Product (invoiceId, name, price, priceUnit, description) VALUES (" + invoiceId + ", '" + product.getName() + "'," + product.getPrice() + ",'" + product.getPriceUnit() + "','" + product.getDescription() + "')"
            );
        }

        int[] updateCounts = stmt.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        
        stmt.close();
        conn.close();
    }
    
    @Override
    public boolean create(Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Product (name, price, priceUnit, description) "
            + "VALUES (?, ?, ?, ?)"
        );
        populate.populateCreateStatementData(stmt, product);
        int rows = stmt.executeUpdate();  

        conn.close();

        return rows > 0;
    }

    @Override
    public boolean update(Integer id, Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Product SET name=?, price=?, priceUnit=?, description=? WHERE id=?");
        populate.populateUpdateStatementData(stmt, product, id);
        int rows = stmt.executeUpdate();  

        conn.close();
        
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Product WHERE id=?");
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();  

        conn.close();
 
        return rows > 0;
    }

    @Override
    public Product getItemById(Integer id) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Product WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        Product product = null;
        while (rs.next()) {
            product = populate.populateProduct(rs);
        }

        conn.close();
        return product;
    }

    @Override
    public Product getItemByName(String name) throws SQLException {

        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Product WHERE name LIKE ? LIMIT 1"
        );
        stmt.setString(1, "%" + name + "%");
        ResultSet rs = stmt.executeQuery();

        Product product = null;
        while (rs.next()) {
            product = populate.populateProduct(rs);
        }

        conn.close();
        return product;
    }
    
    @Override
    public List<Product> getItems() throws SQLException {
      
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Product");
        ResultSet rs = stmt.executeQuery();

        List<Product> results = new ArrayList<>(); 
        while (rs.next()) {
            results.add(populate.populateProduct(rs));
        }

        conn.close();
        return results;
    }
    
    
}
