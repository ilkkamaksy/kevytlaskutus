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
        this.commands.put("SaveManagedCompany", new FormActionSaveManagedCompany(appService));
        this.commands.put("SaveCustomerCompany", new FormActionSaveCustomerCompany(appService));
        this.commands.put("SaveInvoice", new FormActionSaveInvoice(appService));
    }
    
    public boolean execute(String actionType) {        
        FormAction action = this.commands.get(actionType); 
        return action.execute();
    }
   
}
