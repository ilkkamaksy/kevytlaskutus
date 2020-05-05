/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class responsible for calculating total sums for Invoices.
 */
public class InvoiceTotal {
    /**
     * Updates current invoice total sum before taxes by calculating sum of products on the invoice. 
     * If discount percentage has been defined, the discount amount will be calculated and substracted from total sum.
     * @see calculateInvoiceDiscountAmount
     * @see Invoice
     * @param invoice the Invoice for which the sum is calculated.
     * @return totalAmount  BigDecimal sum of products with discount amount substracted
     */
    public static BigDecimal calculateInvoiceTotal(Invoice invoice) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Product product : invoice.getProducts()) {
            if (product.getPrice() != null && product.getInvoiceId() == invoice.getId()) {
                totalAmount = totalAmount.add(product.getPrice());
            }
        }
        return calculateInvoiceDiscountAmount(totalAmount, invoice.getDiscount());
    }
    
    /**
     * Calculates the amount of discount by discount percentage in current Invoice and substracts the amount from invoice total sum.
     * 
     * @param totalAmount the total sum of products before taxes as BigDecimal.
     * @param discountPercentage the discount percentage as BigDecimal
     * @return BigDecimal total sum of current invoice substracted by discount amount.
     */
    private static BigDecimal calculateInvoiceDiscountAmount(BigDecimal totalAmount, BigDecimal discountPercentage) {
        BigDecimal discountAmount = totalAmount.multiply(discountPercentage.divide(BigDecimal.valueOf(100)));
        return totalAmount.subtract(discountAmount).setScale(2, RoundingMode.CEILING);
    }
    
    /**
     * Returns the total sum of Invoice with taxes included.
     * @param invoice the Invoice for which the sum is calculated.
     * @see Invoice
     * @return BigDecimal the total sum of current invoice plus taxes
     */
    public static BigDecimal calculateInvoiceTotalIncludingTaxes(Invoice invoice) {
        return invoice.getAmount().add(calculateInvoiceTotalTaxes(invoice).setScale(2, RoundingMode.CEILING));
    }
    
    /**
     * Calculates the amount of taxes for Invoice total sum.
     * @param invoice the Invoice for which the taxes are calculated.
     * @see Invoice
     * @return BigDecimal the amount of taxes for invoice total sum
     */
    public static BigDecimal calculateInvoiceTotalTaxes(Invoice invoice) {
        return invoice.getAmount().multiply(invoice.getVatPercentage().divide(BigDecimal.valueOf(100))).setScale(2, RoundingMode.CEILING); 
    }
}
