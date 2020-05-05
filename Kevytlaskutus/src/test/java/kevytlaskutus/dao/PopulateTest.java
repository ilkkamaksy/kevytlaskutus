/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
/**
 *
 * @author ilkka
 */
public class PopulateTest {
    
    Connection conn;
    ResultSet mockRs;
    PreparedStatement mockStmt;
    
    Populate populate;
    
    
    @Before
    public void setUp() {     
        
        this.mockRs = mock(ResultSet.class);
        this.mockStmt = mock(PreparedStatement.class);
        
        try {
            conn = this.getConnection();
        } catch (SQLException e) {}
    }
   
    @Test
    public void productCanBePopulated() {
        
        try {
            when(this.mockRs.getString("Product.name")).thenReturn("Acme");
            when(this.mockRs.getBigDecimal("Product.price")).thenReturn(BigDecimal.ONE);
            when(this.mockRs.getString("Product.priceUnit")).thenReturn("kpl");
            when(this.mockRs.getString("Product.description")).thenReturn("desc");
            when(this.mockRs.getInt("Product.id")).thenReturn(1);
       
            Product prod = this.populate.populateProduct(this.mockRs);
            
            assertEquals("Acme", prod.getName());
            assertEquals(BigDecimal.ONE, prod.getPrice());
            assertEquals("kpl", prod.getPriceUnit());
            assertEquals("desc", prod.getDescription());
            
        } catch (SQLException ex) {}

    }

    @Test
    public void productDefaultsCanBePopulated() {
      
        Date today = new Date(new java.util.Date().getTime());
        
        try {
            when(this.mockRs.getInt("Invoice.id")).thenReturn(1);
            when(this.mockRs.getInt("invoiceNumber")).thenReturn(2);
            when(this.mockRs.getInt("referenceNumber")).thenReturn(1000);
            when(this.mockRs.getDate("createdDate")).thenReturn(today);
            when(this.mockRs.getInt("paymentTerm")).thenReturn(10);
            when(this.mockRs.getDate("dueDate")).thenReturn(today);
            when(this.mockRs.getBigDecimal("discount")).thenReturn(BigDecimal.ONE);
            when(this.mockRs.getDate("discountDate")).thenReturn(today);
            when(this.mockRs.getBigDecimal("penaltyInterest")).thenReturn(BigDecimal.ONE);
            when(this.mockRs.getBigDecimal("amount")).thenReturn(BigDecimal.ONE);
            when(this.mockRs.getString("customerContactName")).thenReturn("name");
            when(this.mockRs.getString("customerReference")).thenReturn("ref");
            when(this.mockRs.getString("companyReference")).thenReturn("ref");
            when(this.mockRs.getString("deliveryTerms")).thenReturn("terms");
            when(this.mockRs.getDate("deliveryDate")).thenReturn(today);
            when(this.mockRs.getString("deliveryInfo")).thenReturn("info");
            when(this.mockRs.getString("additionalInfo")).thenReturn("info");
       
            Invoice result = this.populate.populateInvoice(this.mockRs);
            
            assertEquals(1, result.getId());
            assertEquals(Integer.valueOf(2), result.getInvoiceNumber());
            
            
        } catch (SQLException ex) {}
    }
    
    @Test
    public void customerCompanyCanBePopulated() {
                   
        try {
            when(this.mockRs.getString("Customer.name")).thenReturn("acme");
            when(this.mockRs.getString("Customer.regId")).thenReturn("123");
            when(this.mockRs.getString("Customer.phone")).thenReturn("phone");
            when(this.mockRs.getString("Customer.street")).thenReturn("katu");
            when(this.mockRs.getString("Customer.postcode")).thenReturn("postcode");
            when(this.mockRs.getString("Customer.commune")).thenReturn("commune");
            when(this.mockRs.getString("Customer.ovtId")).thenReturn("ovt");
            when(this.mockRs.getString("Customer.provider")).thenReturn("provider");
            when(this.mockRs.getInt("Customer.id")).thenReturn(1);
       
            CustomerCompany result = this.populate.populateCustomer(this.mockRs);
            
            assertEquals("acme", result.getName());
            assertEquals("123", result.getRegId());
            assertEquals("phone", result.getPhone());
            assertEquals("katu", result.getStreet());

        } catch (SQLException ex) {}
    }
    
    @Test
    public void managedCompanyCanBePopulated() {
        
        try {

            when(this.mockRs.getString("Company.name")).thenReturn("acme");
            when(this.mockRs.getString("Company.regId")).thenReturn("123");
            when(this.mockRs.getString("Company.phone")).thenReturn("phone");
            when(this.mockRs.getString("Company.street")).thenReturn("katu");
            when(this.mockRs.getString("Company.postcode")).thenReturn("postcode");
            when(this.mockRs.getString("Company.commune")).thenReturn("commune");
            when(this.mockRs.getString("Company.ovtId")).thenReturn("ovt");
            when(this.mockRs.getString("Company.provider")).thenReturn("provider");
            when(this.mockRs.getInt("Company.id")).thenReturn(1);
            when(this.mockRs.getString("Company.iban")).thenReturn("iban");
            when(this.mockRs.getString("Company.bic")).thenReturn("bic");
            
            ManagedCompany result = this.populate.populateManagedCompany(this.mockRs);
            
            assertEquals("acme", result.getName());
            assertEquals("123", result.getRegId());
            assertEquals("phone", result.getPhone());
            assertEquals("katu", result.getStreet());

        } catch (SQLException ex) {}
    }
    
    @Test
    public void customerDefaultDataCanBePopulated() {
        
        try {

            ManagedCompany company = new ManagedCompany(
                "Acme",
                "regid",
                "09123123",
                "Katuosoite",
                "postikoodi",
                "toimipaikka",
                "ovt",
                "provider"
            );
            
            populate.populateCustomerDefaultDataToStatement(this.mockStmt, company);
    
        } catch (SQLException ex) {}
    }
    
    @Test
    public void managedCompanyCreateStatementDataCanBePopulated() {
        
        try {

            ManagedCompany company = new ManagedCompany(
                "Acme",
                "regid",
                "09123123",
                "Katuosoite",
                "postikoodi",
                "toimipaikka",
                "ovt",
                "provider"
            );
            
            populate.populateCreateStatementData(this.mockStmt, company);
    
        } catch (SQLException ex) {}
    }
    
    public void managedCompanyUpdateStatementDataCanBePopulated() {
        
        try {

            ManagedCompany company = new ManagedCompany(
                "Acme",
                "regid",
                "09123123",
                "Katuosoite",
                "postikoodi",
                "toimipaikka",
                "ovt",
                "provider"
            );
            
            populate.populateUpdateStatementData(this.mockStmt, company, 1);
    
        } catch (SQLException ex) {}
    }
    
    public void invoiceDefaultDataCanBePopulated() {
        
        try {

            CustomerCompany customer = new CustomerCompany();
            customer.setName("Acme");
            customer.setRegId("regid");
            customer.setOvtId("ovt");
            customer.setProvider("provider");
            customer.setStreet("katu");
            customer.setPostcode("postcode");
            customer.setCommune("commune");
            customer.setPhone("phone");

            Product product = new Product();
            product.setName("Acme");

            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(1);
            invoice.setCustomer(customer);
            invoice.getProducts().add(product);
            
            populate.addInvoiceDefaultDataToStatement(this.mockStmt, invoice);
            populate.populateCreateStatementData(this.mockStmt, invoice);
    
        } catch (SQLException ex) {}
    }
    
    @After
    public void tearDown() {
        try {
            conn.close();
        } catch (SQLException e) {}
    }
    
    public Connection getConnection() throws SQLException {     
        return DriverManager.getConnection("jdbc:h2:mem:testdb");
    }
    
}
