package kevytlaskutus.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import kevytlaskutus.domain.CustomerCompany;
        
/**
 * Data Access Object handling customer company object database operations.
 */
public class CustomerCompanyDao implements CompanyDao<CustomerCompany, Integer, String> {

    Connection conn;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {
        
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Customer (\n"
            + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
            + "    name VARCHAR(200),\n"
            + "    regId VARCHAR(20),\n"
            + "    phone VARCHAR(20),\n"
            + "    street VARCHAR(50),\n"
            + "    postcode VARCHAR(10),\n"
            + "    commune VARCHAR(20),\n"
            + "    ovtId VARCHAR(40),\n"
            + "    provider VARCHAR(40)\n"
            + ");").executeUpdate();
    }
    
    @Override
    public boolean create(CustomerCompany company) throws SQLException {
   
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Customer (name, regId, phone, street, postcode, commune, ovtId, provider) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
        );
        this.buildCreateStatementData(stmt, company);
        int rows = stmt.executeUpdate();  
        
        conn.close();

        return rows > 0;
    }
    
    @Override
    public boolean update(Integer id, CustomerCompany company) throws SQLException {
       
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Customer SET name=?, regId=?, phone=?, street=?, postcode=?, commune=?, ovtId=?, provider=? WHERE id=?");
        this.buildUpdateStatementData(stmt, company, id);
        int rows = stmt.executeUpdate();  

        conn.close();
 
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Customer WHERE id=?");
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();  

        conn.close();
        
        return rows > 0;
    }

    @Override
    public CustomerCompany getItemById(Integer id) throws SQLException {
        
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Customer WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        CustomerCompany company = null;
        while (rs.next()) {
            company = this.populateCustomer(rs);
        }
        conn.close();
        return company;
    }
    
    @Override
    public CustomerCompany getItemByName(String name) throws SQLException {
        
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Customer WHERE name LIKE ? LIMIT 1"
        );
        stmt.setString(1, "%" + name + "%");
        
        ResultSet rs = stmt.executeQuery();
        
        CustomerCompany company = null;
        while (rs.next()) {
            company = this.populateCustomer(rs);
        }

        conn.close();
        return company;
    }
    
    @Override
    public List<CustomerCompany> getItems() throws SQLException {
        
        List<CustomerCompany> results = new ArrayList<>(); 
       
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customer");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            results.add(this.populateCustomer(rs));
        }
        
        conn.close();
        return results;
    }

    private void buildCreateStatementData(PreparedStatement stmt, CustomerCompany company) throws SQLException {
        this.addCustomerDefaultDataToStatement(stmt, company);
    }
    
    private void buildUpdateStatementData(PreparedStatement stmt, CustomerCompany company, int id) throws SQLException {
        this.addCustomerDefaultDataToStatement(stmt, company);
        stmt.setInt(9, id);
    }
   
    private void addCustomerDefaultDataToStatement(PreparedStatement stmt, CustomerCompany company) throws SQLException {
        stmt.setString(1, company.getName());
        stmt.setString(2, company.getRegId());
        stmt.setString(3, company.getPhone());
        stmt.setString(4, company.getStreet());
        stmt.setString(5, company.getPostcode());
        stmt.setString(6, company.getCommune());
        stmt.setString(7, company.getOvtId());
        stmt.setString(8, company.getProvider());
    }
    
    private CustomerCompany populateCustomer(ResultSet rs) throws SQLException {
        CustomerCompany customer = new CustomerCompany(
            rs.getString("Customer.name"),
            rs.getString("Customer.regId"), 
            rs.getString("Customer.phone"), 
            rs.getString("Customer.street"), 
            rs.getString("Customer.postcode"),
            rs.getString("Customer.commune"),
            rs.getString("Customer.ovtId"),
            rs.getString("Customer.provider")
        );
        customer.setId(rs.getInt("Customer.id"));
        return customer;
    }
}
