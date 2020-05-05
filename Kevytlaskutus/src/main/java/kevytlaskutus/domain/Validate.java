/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

/**
 * Class responsible for validating objects before saving into database.
 */
public class Validate {
    
    /**
     * Checks if the current ManagedCompany object has a name.
     * @param company the ManagedCompany object to check.
     * @return boolean 
     */
    public static boolean managedCompanyHasName(ManagedCompany company) {
        if (company.getName() == null || company.getName().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Checks if the current CustomerCompany object has a name. 
     * @param customer the CustomerCompany object to check.
     * @return boolean
     */
    public static boolean customerCompanyHasName(CustomerCompany customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            return false;
        }
        return true;
    }
    
    /**
     * Check if all products on invoice have a name.
     * @param invoice the Invoice object to check
     * @return boolean
     */
    public static boolean invoiceProductsHaveNames(Invoice invoice) {
        for (Product product : invoice.getProducts()) {
            if (product.getName() == null || product.getName().isBlank()) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if the Invoice has a CustomerCompany attached to it
     * @param invoice the Invoice object to check
     * @return boolean
     */
    public static boolean invoiceHasCustomer(Invoice invoice) {
        if (invoice.getCustomer() == null) {
            return false;
        }
        return true;
    }
}
