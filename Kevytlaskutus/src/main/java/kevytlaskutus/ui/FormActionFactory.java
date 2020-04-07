package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;

import kevytlaskutus.domain.*;


/**
 * Form action factory.
 */
public class FormActionFactory {
    
    AppService appService;
    HashMap<String, FormAction> commands;
    
    public FormActionFactory(AppService appService) {
        this.appService = appService;
        this.commands = new HashMap<>();
        this.commands.put("NewManagedCompany", new FormActionSaveNewManagedCompany(appService));
        this.commands.put("UpdateManagedCompany", new FormActionUpdateManagedCompany(appService));
        this.commands.put("NewCustomerCompany", new FormActionSaveNewCustomerCompany(appService));
        this.commands.put("UpdateCustomerCompany", new FormActionUpdateCustomerCompany(appService));
        this.commands.put("NewProduct", new FormActionSaveNewProduct(appService));
        this.commands.put("UpdateProduct", new FormActionUpdateProduct(appService));
        this.commands.put("NewInvoice", new FormActionSaveNewInvoice(appService));
    }
    
    public void execute(String actionType, HashMap<String, Node> formFields, int id) {        
        FormAction action = this.commands.get(actionType); 
        action.setData(formFields, id);
        boolean success = action.save();
        System.out.println("success " + success);
    }
    
}
