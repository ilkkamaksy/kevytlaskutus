/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ilkka
 */
public class ReferenceNumberTest {
   
    Invoice invoice;

    @Before
    public void setUp() {
        this.invoice = new Invoice();
    }
    
    @Test
    public void validInvoiceReferenceNumberCanBeGenerated() {
        Integer expResult = 12304561;
        this.invoice.setInvoiceNumber(1230456);
        Integer result = ReferenceNumber.generateInvoiceReferenceNumber(invoice);
        assertEquals(expResult, result);
    }
    
    @Test
    public void validInvoiceReferenceNumberCanBeGeneratedWhenSumOfDigitsIs10() {
        Integer expResult = 1010;
        this.invoice.setInvoiceNumber(101);
        Integer result = ReferenceNumber.generateInvoiceReferenceNumber(this.invoice);
        assertEquals(expResult, result);
    }
    
}
