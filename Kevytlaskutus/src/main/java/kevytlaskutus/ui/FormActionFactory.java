package kevytlaskutus.ui;

import java.util.HashMap;
import java.util.List;
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
        this.commands.put("NewInvoice", new FormActionSaveNewInvoice(appService));
        this.commands.put("UpdateInvoice", new FormActionUpdateInvoice(appService));
    }
    
    public boolean execute(String actionType, HashMap<String, Node> formFields, int id) {        
        FormAction action = this.commands.get(actionType); 
        return action.execute();
    }
    
    public boolean execute(String actionType, HashMap<String, Node> formFields, int id, List<Product> products) {        
        FormAction action = this.commands.get(actionType); 
        return action.execute();
    }
    
}
