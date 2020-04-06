package kevytlaskutus.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kevytlaskutus.domain.Invoice;

public class InvoiceDaoImpl implements InvoiceDao<Invoice, Integer, String>  {
    
    Connection conn;
    
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
    
    public void initDb() throws SQLException {
        
        conn.prepareStatement("CREATE TABLE IF NOT EXISTS Invoice (\n"
            + "    id INTEGER AUTO_INCREMENT PRIMARY KEY,\n"
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
            + "    FOREIGN KEY (customerId) REFERENCES Customer(id) \n"
            + ");").executeUpdate();
    }
    
    @Override
    public boolean create(Invoice invoice) throws SQLException {
   
        PreparedStatement stmt = conn.prepareStatement(
            "INSERT INTO Invoice (invoiceNumber, \n"
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
                    + "additionalInfo \n"
            + ") "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        stmt.setInt(1, invoice.getInvoiceNumber());
        stmt.setInt(2, invoice.getReferenceNumber());
        stmt.setDate(3, invoice.getCreatedDate());
        stmt.setInt(4, invoice.getPaymentTerm());
        stmt.setDate(5, invoice.getDueDate());
        stmt.setBigDecimal(6, invoice.getDiscount());
        stmt.setDate(7, invoice.getDiscountDate());
        stmt.setBigDecimal(8, invoice.getPenaltyInterest());
        stmt.setBigDecimal(9, invoice.getAmount());
        stmt.setInt(10, invoice.getCustomerId());
        stmt.setString(11, invoice.getCustomerContactName());
        stmt.setString(12, invoice.getCustomerReference());
        stmt.setString(13, invoice.getCompanyReference());
        stmt.setString(14, invoice.getDeliveryTerms());
        stmt.setDate(15, invoice.getDeliveryDate());
        stmt.setString(16, invoice.getDeliveryInfo());
        stmt.setString(17, invoice.getAdditionalInfo());
        int rows = stmt.executeUpdate();  
        
        conn.close();

        return rows > 0;
    }
    
    @Override
    public Invoice getItemById(Integer id) throws SQLException {
        
        Invoice invoice = null;
         
        PreparedStatement stmt = conn.prepareStatement(
            "SELECT * FROM Invoice WHERE id=" + id + " LIMIT 1"
        );
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            invoice = new Invoice(rs.getDate("createdDate"));
            invoice.setId(rs.getInt("id"));
            invoice.setInvoiceNumber(rs.getInt("invoiceNumber"));
            invoice.setReferenceNumber(rs.getInt("referenceNumber"));
            invoice.setCreatedDate(rs.getDate("createdDate"));
            invoice.setPaymentTerm(rs.getInt("paymentTerm"));
            invoice.setDueDate(rs.getDate("dueDate"));
            invoice.setDiscount(rs.getBigDecimal("discount"));
            invoice.setDiscountDate(rs.getDate("discountDate"));
            invoice.setPenaltyInterest(rs.getBigDecimal("penaltyInterest"));
            invoice.setAmount(rs.getBigDecimal("amount"));
            invoice.setCustomerId(rs.getInt("customerId"));
            invoice.setCustomerContactName(rs.getString("customerContactName"));
            invoice.setCustomerReference(rs.getString("customerReference"));
            invoice.setCompanyReference(rs.getString("companyReference"));
            invoice.setDeliveryTerms(rs.getString("deliveryTerms"));
            invoice.setDeliveryDate(rs.getDate("deliveryDate"));
            invoice.setDeliveryInfo(rs.getString("deliveryInfo"));
            invoice.setAdditionalInfo(rs.getString("additionalInfo"));
        }

        conn.close();

        return invoice;

    }

    @Override
    public boolean update(int id, Invoice invoice) throws SQLException {
       
        PreparedStatement stmt = conn.prepareStatement(
            "UPDATE Invoice SET invoiceNumber=?, "
                + "referenceNumber=?, "
                + "createdDate=?, "
                + "paymentTerm=?, "
                + "dueDate=?, "
                + "discount=?, "
                + "discountDate=?, "
                + "penaltyInterest=?, "
                + "amount=?, "
                + "customerId=? "
                + "customerContactName=? "
                + "customerReference=? "
                + "companyReference=? "
                + "deliveryTerms=? "
                + "deliveryDate=? "
                + "deliveryInfo=? "
                + "additionalInfo=? "
                + "WHERE id=?");
        stmt.setInt(1, invoice.getInvoiceNumber());
        stmt.setInt(2, invoice.getReferenceNumber());
        stmt.setDate(3, invoice.getCreatedDate());
        stmt.setInt(4, invoice.getPaymentTerm());
        stmt.setDate(5, invoice.getDueDate());
        stmt.setBigDecimal(6, invoice.getDiscount());
        stmt.setDate(7, invoice.getDiscountDate());
        stmt.setBigDecimal(8, invoice.getPenaltyInterest());
        stmt.setBigDecimal(9, invoice.getAmount());
        stmt.setInt(10, invoice.getCustomerId());
        stmt.setString(11, invoice.getCustomerContactName());
        stmt.setString(12, invoice.getCustomerReference());
        stmt.setString(13, invoice.getCompanyReference());
        stmt.setString(14, invoice.getDeliveryTerms());
        stmt.setDate(15, invoice.getDeliveryDate());
        stmt.setString(16, invoice.getDeliveryInfo());
        stmt.setString(17, invoice.getAdditionalInfo());
        stmt.setInt(18, id);
        int rows = stmt.executeUpdate();  

        conn.close();
 
        return rows > 0;
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
    public List<Invoice> getItems() throws SQLException {
        
        List<Invoice> results = new ArrayList<>(); 
       
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Invoice");
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Invoice invoice = new Invoice(rs.getDate("createdDate"));
            invoice.setId(rs.getInt("id"));
            invoice.setInvoiceNumber(rs.getInt("invoiceNumber"));
            invoice.setReferenceNumber(rs.getInt("referenceNumber"));
            invoice.setCreatedDate(rs.getDate("createdDate"));
            invoice.setPaymentTerm(rs.getInt("paymentTerm"));
            invoice.setDueDate(rs.getDate("dueDate"));
            invoice.setDiscount(rs.getBigDecimal("discount"));
            invoice.setDiscountDate(rs.getDate("discountDate"));
            invoice.setPenaltyInterest(rs.getBigDecimal("penaltyInterest"));
            invoice.setAmount(rs.getBigDecimal("amount"));
            invoice.setCustomerId(rs.getInt("customerId"));
            invoice.setCustomerContactName(rs.getString("customerContactName"));
            invoice.setCustomerReference(rs.getString("customerReference"));
            invoice.setCompanyReference(rs.getString("companyReference"));
            invoice.setDeliveryTerms(rs.getString("deliveryTerms"));
            invoice.setDeliveryDate(rs.getDate("deliveryDate"));
            invoice.setDeliveryInfo(rs.getString("deliveryInfo"));
            invoice.setAdditionalInfo(rs.getString("additionalInfo"));
            invoice.setId(rs.getInt("id"));
            results.add(invoice);
        }

        conn.close();

        return results;
        
    }

}