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
        Populate.populateCreateStatementData(stmt, company);
        int rows = stmt.executeUpdate();  

        conn.close();
        return rows > 0;
    }
    
    @Override
    public boolean update(Integer id, ManagedCompany company) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Company SET name=?, regId=?, phone=?, street=?, postcode=?, commune=?, ovtId=?, provider=?, iban=?, bic=? WHERE id=?");
        Populate.populateUpdateStatementData(stmt, company, id);
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
    public ManagedCompany getItemById(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Company WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        ManagedCompany company = null;
        while (rs.next()) {
            company = Populate.populateManagedCompany(rs);
        }

        conn.close();
        return company;
    }
    
    @Override
    public ManagedCompany getItemByName(String name) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Company WHERE name LIKE %" + name + "% LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        ManagedCompany company = null;
        while (rs.next()) {
            company = Populate.populateManagedCompany(rs);
        }

        conn.close();
        return company;
    }
    
    @Override
    public List<ManagedCompany> getItems() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Company");
        ResultSet rs = stmt.executeQuery();

        List<ManagedCompany> results = new ArrayList<>(); 
        while (rs.next()) {
            results.add(Populate.populateManagedCompany(rs));
        }

        conn.close();
        return results;
    }
   
}
