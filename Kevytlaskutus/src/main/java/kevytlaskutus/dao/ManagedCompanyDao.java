package kevytlaskutus.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kevytlaskutus.domain.ManagedCompany;

/**
 * Data Access Object handling managed company object database operations.
 */
public class ManagedCompanyDao implements CompanyDao<ManagedCompany, Integer, String> {

    Connection conn;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {

        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Company (\n"
            + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
            + "    name VARCHAR(200),\n"
            + "    regId VARCHAR(20),\n"
            + "    phone VARCHAR(20),\n"
            + "    street VARCHAR(50),\n"
            + "    postcode VARCHAR(10),\n"
            + "    commune VARCHAR(20),\n"
            + "    ovtId VARCHAR(40),\n"
            + "    provider VARCHAR(40),\n"
            + "    iban VARCHAR(40),\n"
            + "    bic VARCHAR(40)\n"
            + ");").executeUpdate();
        
    }
    
    @Override
    public boolean create(ManagedCompany company) throws SQLException {
                
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Company (name, regId, phone, street, postcode, commune, ovtId, provider, iban, bic) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        stmt.setString(1, company.getName());
        stmt.setString(2, company.getRegId());
        stmt.setString(3, company.getPhone());
        stmt.setString(4, company.getStreet());
        stmt.setString(5, company.getPostcode());
        stmt.setString(6, company.getCommune());
        stmt.setString(7, company.getOvtId());
        stmt.setString(8, company.getProvider());
        stmt.setString(9, company.getIban());
        stmt.setString(10, company.getBic());
        int rows = stmt.executeUpdate();  

        conn.close();

        return rows > 0;
    }
    
    @Override
    public ManagedCompany getItemById(Integer id) throws SQLException {
        
        ManagedCompany company = null;
       
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Company WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            company = new ManagedCompany(
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
            company.setIban(rs.getString("iban"));
            company.setBic(rs.getString("bic"));
        }

        conn.close();
 
        return company;

    }

    @Override
    public boolean update(Integer id, ManagedCompany company) throws SQLException {
       
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Company SET name=?, regId=?, phone=?, street=?, postcode=?, commune=?, ovtId=?, provider=?, iban=?, bic=? WHERE id=?");
        stmt.setString(1, company.getName());
        stmt.setString(2, company.getRegId());
        stmt.setString(3, company.getPhone());
        stmt.setString(4, company.getStreet());
        stmt.setString(5, company.getPostcode());
        stmt.setString(6, company.getCommune());
        stmt.setString(7, company.getOvtId());
        stmt.setString(8, company.getProvider());
        stmt.setString(9, company.getIban());
        stmt.setString(10, company.getBic());
        stmt.setInt(11, id);
        int rows = stmt.executeUpdate();  

        conn.close();
        
        return rows > 0;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
       
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Company WHERE id=?");
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();  

        conn.close();
 
        return rows > 0;
    }

    @Override
    public List<ManagedCompany> getItems() throws SQLException {
        
        List<ManagedCompany> results = new ArrayList<>(); 
       
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Company");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            ManagedCompany company = new ManagedCompany(
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
            company.setIban(rs.getString("iban"));
            company.setBic(rs.getString("bic"));
            results.add(company);
        }

        conn.close();
       
        return results;
        
    }

}
