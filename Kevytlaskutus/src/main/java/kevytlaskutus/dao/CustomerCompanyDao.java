/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import kevytlaskutus.domain.CustomerCompany;
        
/**
 *
 * @author ilkka
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
        stmt.setString(1, company.getName());
        stmt.setString(2, company.getRegId());
        stmt.setString(3, company.getPhone());
        stmt.setString(4, company.getStreet());
        stmt.setString(5, company.getPostcode());
        stmt.setString(6, company.getCommune());
        stmt.setString(7, company.getOvtId());
        stmt.setString(8, company.getProvider());
        int rows = stmt.executeUpdate();  
        
        conn.close();

        return rows > 0;
    }
    
    @Override
    public CustomerCompany getItemById(Integer id) throws SQLException {
        
        CustomerCompany company = null;
         
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Customer WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            company = new CustomerCompany(
                rs.getString("name"), 
                rs.getString("regId"), 
                rs.getString("phone"), 
                rs.getString("street"), 
                rs.getString("postcode"),
                rs.getString("commune"),
                rs.getString("ovtId"),
                rs.getString("provider")
            );
            company.setId(rs.getInt("id"));
        }

        conn.close();

        return company;

    }

    @Override
    public boolean update(int id, CustomerCompany company) throws SQLException {
       
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Customer SET name=?, regId=?, phone=?, street=?, postcode=?, commune=?, ovtId=?, provider=? WHERE id=?");
        stmt.setString(1, company.getName());
        stmt.setString(2, company.getRegId());
        stmt.setString(3, company.getPhone());
        stmt.setString(4, company.getStreet());
        stmt.setString(5, company.getPostcode());
        stmt.setString(6, company.getCommune());
        stmt.setString(7, company.getOvtId());
        stmt.setString(8, company.getProvider());
        stmt.setInt(9, id);
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
    public List<CustomerCompany> getItems() throws SQLException {
        
        List<CustomerCompany> results = new ArrayList<>(); 
       
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Customer");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            CustomerCompany company = new CustomerCompany(
                rs.getString("name"), 
                rs.getString("regId"), 
                rs.getString("phone"), 
                rs.getString("street"), 
                rs.getString("postcode"),
                rs.getString("commune"),
                rs.getString("ovtId"),
                rs.getString("provider")
            );
            company.setId(rs.getInt("id"));
            results.add(company);
        }

        conn.close();

        return results;
        
    }

}
