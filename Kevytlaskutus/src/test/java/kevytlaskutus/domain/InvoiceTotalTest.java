/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import kevytlaskutus.domain.InvoiceTotal;
import static org.mockito.Mockito.mock;

/**
 *
 * @author ilkka
 */
public class InvoiceTotalTest {

    InvoiceTotal invoiceTotal = new InvoiceTotal();

    @Before
    public void setUp() {     
        
        
    }
    
    @Test
    public void invoiceTotalIsZeroWhenNoProducts() {
        BigDecimal result = InvoiceTotal.calculateInvoiceTotal(new Invoice());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.CEILING), result);
    }
    
    @Test
    public void invoiceTotalWithTaxesZeroWhenNoProducts() {
        BigDecimal result = InvoiceTotal.calculateInvoiceTotalIncludingTaxes(new Invoice());
        assertEquals(BigDecimal.valueOf(0).setScale(2, RoundingMode.CEILING), result);
    }
    
}
