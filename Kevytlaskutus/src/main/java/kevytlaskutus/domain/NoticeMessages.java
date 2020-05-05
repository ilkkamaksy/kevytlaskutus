/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.HashMap;

/**
 * Class containing default notice messages for UI.
 */
public class NoticeMessages {
   
    public static HashMap<String, String> messages = new HashMap<>();
   
    public NoticeMessages() {     
        messages.put("saveManagedCompany", "Company has been saved successfully.");
        messages.put("saveCustomerCompany", "Customer has been saved successfully.");
        messages.put("saveInvoice", "Invoice has been saved successfully.");
        messages.put("delete", "Item has been deleted");
    }

    public String getNoticeMessage(String actionType) {
        return messages.get(actionType);
    }
}
