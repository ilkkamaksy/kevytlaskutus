/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;

/**
 *
 * @author ilkka
 */
public class Populate {
    
    public static void populateCreateStatementData(PreparedStatement stmt, Product product) throws SQLException {
        addProductDefaultDataToStatement(stmt, product);
    }
    
    public static void populateUpdateStatementData(PreparedStatement stmt, Product product, int id) throws SQLException {
        addProductDefaultDataToStatement(stmt, product);
        stmt.setInt(5, id);
    }
    
    public static void addProductDefaultDataToStatement(PreparedStatement stmt, Product product) throws SQLException {
        stmt.setString(1, product.getName());
        stmt.setString(2, product.getPrice());
        stmt.setString(3, product.getPriceUnit());
        stmt.setString(4, product.getDescription());
    }
    
    public static Product populateProduct(ResultSet rs) throws SQLException {
        Product product = new Product(
            rs.getString("Product.name"), 
            rs.getString("Product.price"), 
            rs.getString("Product.priceUnit"), 
            rs.getString("Product.description")
        );
        product.setId(rs.getInt("Product.id"));
        return product;
    }
    
    public static void populateCreateStatementData(PreparedStatement stmt, Invoice invoice) throws SQLException {
        addInvoiceDefaultDataToStatement(stmt, invoice);
    }
    
    public static void populateUpdateStatementData(PreparedStatement stmt, Invoice invoice) throws SQLException {
        addInvoiceDefaultDataToStatement(stmt, invoice);
        stmt.setInt(19, invoice.getId());
    }
   
    public static void addInvoiceDefaultDataToStatement(PreparedStatement stmt, Invoice invoice) throws SQLException {
        stmt.setInt(1, invoice.getInvoiceNumber());
        stmt.setInt(2, invoice.getReferenceNumber());
        stmt.setDate(3, invoice.getCreatedDate());
        stmt.setInt(4, invoice.getPaymentTerm());
        stmt.setDate(5, invoice.getDueDate());
        stmt.setBigDecimal(6, invoice.getDiscount());
        stmt.setDate(7, invoice.getDiscountDate());
        stmt.setBigDecimal(8, invoice.getPenaltyInterest());
        stmt.setBigDecimal(9, invoice.getAmount());
        stmt.setInt(10, invoice.getCustomer().getId());
        stmt.setString(11, invoice.getCustomerContactName());
        stmt.setString(12, invoice.getCustomerReference());
        stmt.setString(13, invoice.getCompanyReference());
        stmt.setString(14, invoice.getDeliveryTerms());
        stmt.setDate(15, invoice.getDeliveryDate());
        stmt.setString(16, invoice.getDeliveryInfo());
        stmt.setString(17, invoice.getAdditionalInfo());
        stmt.setInt(18, invoice.getCompany().getId());
    }
    
    public static Invoice populateInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice(rs.getDate("createdDate"));        
        invoice.setId(rs.getInt("Invoice.id"));
        invoice.setInvoiceNumber(rs.getInt("invoiceNumber"));
        invoice.setReferenceNumber(rs.getInt("referenceNumber"));
        invoice.setCreatedDate(rs.getDate("createdDate"));
        invoice.setPaymentTerm(rs.getInt("paymentTerm"));
        invoice.setDueDate(rs.getDate("dueDate"));
        invoice.setDiscount(rs.getBigDecimal("discount"));
        invoice.setDiscountDate(rs.getDate("discountDate"));
        invoice.setPenaltyInterest(rs.getBigDecimal("penaltyInterest"));
        invoice.setAmount(rs.getBigDecimal("amount"));
        invoice.setCustomerContactName(rs.getString("customerContactName"));
        invoice.setCustomerReference(rs.getString("customerReference"));
        invoice.setCompanyReference(rs.getString("companyReference"));
        invoice.setDeliveryTerms(rs.getString("deliveryTerms"));
        invoice.setDeliveryDate(rs.getDate("deliveryDate"));
        invoice.setDeliveryInfo(rs.getString("deliveryInfo"));
        invoice.setAdditionalInfo(rs.getString("additionalInfo"));
        return invoice;
    }
    
    public static CustomerCompany populateCustomer(ResultSet rs) throws SQLException {
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
    
    public static ManagedCompany populateManagedCompany(ResultSet rs) throws SQLException {
        ManagedCompany company = new ManagedCompany(
            rs.getString("Company.name"), 
            rs.getString("Company.regId"), 
            rs.getString("Company.phone"), 
            rs.getString("Company.street"), 
            rs.getString("Company.postcode"),
            rs.getString("Company.commune"),
            rs.getString("Company.ovtId"),
            rs.getString("Company.provider")
        );
        company.setId(rs.getInt("Company.id"));
        company.setIban(rs.getString("Company.iban"));
        company.setBic(rs.getString("Company.bic"));
        return company;
    }
    
    public static void populateCreateStatementData(PreparedStatement stmt, ManagedCompany company) throws SQLException {
        populateCustomerDefaultDataToStatement(stmt, company);
    }
    
    public static void populateUpdateStatementData(PreparedStatement stmt, ManagedCompany company, int id) throws SQLException {
        populateCustomerDefaultDataToStatement(stmt, company);
        stmt.setInt(11, id);
    }
    
    public static void populateCustomerDefaultDataToStatement(PreparedStatement stmt, ManagedCompany company) throws SQLException {
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
    }
  
}
