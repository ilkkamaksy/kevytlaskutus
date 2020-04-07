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
public class Notice {
    
    private boolean isactive;
    private String actionType;
    private HashMap<String, String> messages = new HashMap<>();
   
    public Notice() {
        this.isactive = false;
        this.actionType = "";
        messages.put("createManagedCompanytrue", "A new company has been added");
        messages.put("updateManagedCompanytrue", "Company has been updated");
        messages.put("deleteManagedCompanytrue", "Company has been deleted");
        messages.put("createCustomerCompanytrue", "A new customer has been added");
        messages.put("updateCustomerCompanytrue", "Customer has been updated");
        messages.put("deleteCustomerCompanytrue", "Customer has been deleted");
        messages.put("createProducttrue", "A new product has been added");
        messages.put("updateProducttrue", "Product has been updated");
        messages.put("deleteProducttrue", "Product has been deleted");
        messages.put("createInvoicetrue", "A new invoice has been added");
        messages.put("updateInvoicetrue", "Invoice has been updated");
        messages.put("deleteInvoicetrue", "Invoice has been deleted");
        messages.put("fail", "Action failed, please try again");
    }

    public String getNoticeMessage() {
        return messages.get(this.actionType);
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setActive() {
        this.isactive = true;
    }

    public void disable() {
        this.isactive = false;
    }
    
    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
        System.out.println(this.actionType);
    }
    
}
