package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
            + "    name VARCHAR(200),\n"
            + "    price VARCHAR(20),\n"
            + "    priceUnit VARCHAR(20),\n"
            + "    description VARCHAR(50)\n"
            + ");").executeUpdate();
    }
    
    @Override
    public boolean create(Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Product (name, price, priceUnit, description) "
            + "VALUES (?, ?, ?, ?)"
        );
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getPrice());
        stmt.setString(3, product.getPriceUnit());
        stmt.setString(4, product.getDescription());
        int rows = stmt.executeUpdate();  

        conn.close();

        return rows > 0;
    }

    @Override
    public Product getItemById(Integer id) throws SQLException {
        Product product = null;
       
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Product WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            product = new Product(
                rs.getString("name"), 
                rs.getString("price"), 
                rs.getString("priceUnit"), 
                rs.getString("description")
            );
            product.setId(rs.getInt("id"));
        }

        conn.close();
 
        return product;
    }

    @Override
    public boolean update(int id, Product product) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Product SET name=?, price=?, priceUnit=?, description=? WHERE id=?");
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getPrice());
        stmt.setString(3, product.getPriceUnit());
        stmt.setString(4, product.getDescription());
        stmt.setInt(5, id);
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
    public List<Product> getItems() throws SQLException {
        List<Product> results = new ArrayList<>(); 
       
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Product");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Product product = new Product(
                rs.getString("name"), 
                rs.getString("price"), 
                rs.getString("priceUnit"), 
                rs.getString("description")
            );
            product.setId(rs.getInt("id"));
            results.add(product);
        }

        conn.close();
       
        return results;
    }
    
}
