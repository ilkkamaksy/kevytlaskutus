/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.HashMap;

/**
 *
 * @author ilkka
 */
public class NoticeMessages {
   
    public static HashMap<String, String> messages = new HashMap<>();
   
    public NoticeMessages() {     
        messages.put("createManagedCompany", "A new company has been added");
        messages.put("updateManagedCompany", "Company has been updated");
        messages.put("createCustomerCompany", "A new customer has been added");
        messages.put("updateCustomerCompany", "Customer has been updated");
        messages.put("createProduct", "A new product has been added");
        messages.put("updateProduct", "Product has been updated");
        messages.put("createInvoice", "A new invoice has been added");
        messages.put("updateInvoice", "Invoice has been updated");
        messages.put("delete", "Item has been deleted");
    }

    public String getNoticeMessage(String actionType) {
        return messages.get(actionType);
    }
}
