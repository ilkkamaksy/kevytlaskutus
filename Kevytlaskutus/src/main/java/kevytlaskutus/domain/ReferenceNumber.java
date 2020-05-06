/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

/**
 * Class responsible for generating reference numbers for Invoices.
 */
public class ReferenceNumber {
   
    /**
     * Generates a reference number for the Invoice based on the invoice number.
     * @param invoice the Invoice object for which the reference number is generated.
     * @return Integer the reference number
     * @see Invoice
     */
    public static Integer generateInvoiceReferenceNumber(Invoice invoice) {
        String invoiceNumberAsString = invoice.getInvoiceNumber().toString();
        Integer checkSum = calculateCheckSumByDigits(invoiceNumberAsString);
        
        return Integer.valueOf(invoiceNumberAsString + Math.abs(checkSum));
    }
    
    private static Integer calculateCheckSumByDigits(String invoiceNumberAsString) {
        Integer checkSum = 0;
        int[] multipliers = {7, 1, 3};
        int multiplierIndex = 0;
        for (int i = 0; i < invoiceNumberAsString.length(); i++) {
            int digit = Integer.valueOf(String.valueOf(invoiceNumberAsString.charAt(i)));
            checkSum += digit * multipliers[multiplierIndex];
            multiplierIndex = multiplierIndex < multipliers.length - 1 ? ++multiplierIndex : 0;
        }
        
        return substractNearestTen(checkSum);
    }
    
    private static Integer substractNearestTen(Integer checkSum) {
        int nearestTen = (int) Math.ceil(checkSum / 10.0) * 10;
        checkSum = nearestTen - checkSum;
        if (checkSum == 10) {
            checkSum = 0;
        }
        return checkSum;
    }
}
