package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

public class InvoiceDaoImpl implements InvoiceDao<Invoice, Integer, String>  {
    
    Connection conn;
    
    static Populate populate;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Invoice (\n"
            + "    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,\n"
            + "    invoiceNumber INTEGER,\n"
            + "    referenceNumber INTEGER,\n"
            + "    createdDate DATE,\n"
            + "    paymentTerm INTEGER,\n"
            + "    dueDate DATE,\n"
            + "    discount DECIMAL,\n"
            + "    discountDate DATE,\n"
            + "    penaltyInterest DECIMAL,\n"
            + "    amount DECIMAL,\n"
            + "    customerId INTEGER, \n"
            + "    customerContactName VARCHAR(100), \n"
            + "    customerReference VARCHAR(100), \n"
            + "    companyReference VARCHAR(100), \n"
            + "    deliveryTerms VARCHAR(100), \n"
            + "    deliveryDate DATE, \n"
            + "    deliveryInfo VARCHAR(100), \n"
            + "    additionalInfo VARCHAR(255), \n"
            + "    companyId INTEGER, \n"
            + "    FOREIGN KEY (customerId) "
            + "         REFERENCES Customer(id) "
            + "         ON DELETE CASCADE"
            + "         ON UPDATE CASCADE, "
            + "    FOREIGN KEY (companyId) "
            + "         REFERENCES Company(id) "
            + "         ON DELETE CASCADE "
            + "         ON UPDATE CASCADE "
            + ");").executeUpdate();
        
        
    }
    
    @Override
    public Integer create(Invoice invoice) throws SQLException {
   
        PreparedStatement pstmt = conn.prepareStatement(
            "INSERT INTO Invoice ("
            + "invoiceNumber, \n"
            + "referenceNumber, \n"
            + "createdDate, \n"
            + "paymentTerm, \n" 
            + "dueDate, \n"
            + "discount, \n"
            + "discountDate, \n"
            + "penaltyInterest, \n"
            + "amount, \n"
            + "customerId, \n"
            + "customerContactName, \n"
            + "customerReference, \n"
            + "companyReference, \n"
            + "deliveryTerms, \n"
            + "deliveryDate, \n"
            + "deliveryInfo, \n"
            + "additionalInfo, \n"
            + "companyId \n"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS
        );
        populate.populateCreateStatementData(pstmt, invoice);
        pstmt.executeUpdate();
        
        int invoiceId = this.getGeneratedItemKey(pstmt);
        
        pstmt.close();
        conn.close();
        
        return invoiceId;
    }

    private int getGeneratedItemKey(PreparedStatement stmt) throws SQLException {
        int id = -1;
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }
        
        generatedKeys.close();
        return id;
    }
    
    @Override
    public boolean update(int id, Invoice invoice) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Invoice SET "
                + "invoiceNumber=?, "
                + "referenceNumber=?, "
                + "createdDate=?, "
                + "paymentTerm=?, " 
                + "dueDate=?, "
                + "discount=?, "
                + "discountDate=?, "
                + "penaltyInterest=?, "
                + "amount=?, "
                + "customerId=?, "
                + "customerContactName=?, "
                + "customerReference=?, "
                + "companyReference=?, "
                + "deliveryTerms=?, "
                + "deliveryDate=?, "
                + "deliveryInfo=?, "
                + "additionalInfo=?, "
                + "companyId=? "
                + "WHERE id=?");
        populate.populateUpdateStatementData(stmt, invoice);
        int rows = stmt.executeUpdate();
        
        stmt.close();
        conn.close();
        return rows > 0;
    }
  
    @Override
    public Invoice getItemById(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Invoice AS Invoice \n"
            + "LEFT JOIN Customer AS Customer ON Invoice.customerId = Customer.id \n" 
            + "LEFT JOIN Company AS Company ON Invoice.companyId = Company.id \n"
            + "LEFT JOIN Product AS Product ON Invoice.id = Product.invoiceId \n" 
            + "WHERE Invoice.id=? \n"
            + "GROUP BY Invoice.id, Product.id"
        );
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        Invoice invoice = null;
        
        int i = 0;
        while (rs.next()) {
            if (i == 0) {
                invoice = populate.populateInvoice(rs);
                invoice.setCustomer(populate.populateCustomer(rs));
                invoice.setCompany(populate.populateManagedCompany(rs));    
            }
            
            Product prod = populate.populateProduct(rs);
            prod.setInvoiceId(invoice.getId());
            invoice.getProducts().add(prod);
            
            i++;
        }
       
        stmt.close();
        conn.close();
        return invoice;
    }
    
    @Override
    public boolean delete(Integer id) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Invoice WHERE id=?");
        stmt.setInt(1, id);
        int rows = stmt.executeUpdate();  
        
        conn.close();
        return rows > 0;
    }

    @Override
    public List<Invoice> getItems(Integer managedCompanyId) throws SQLException {
        
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Invoice WHERE companyId = ?");
        stmt.setInt(1, managedCompanyId);
        ResultSet rs = stmt.executeQuery();

        List<Invoice> results = new ArrayList<>(); 
        while (rs.next()) {
            Invoice invoice = populate.populateInvoice(rs);
            results.add(invoice);
        }

        conn.close();
        return results;
    }
    
    @Override
    public Integer getInvoiceCount() throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) AS rowcount FROM Invoice");
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        conn.close();
        return count;
    }
}