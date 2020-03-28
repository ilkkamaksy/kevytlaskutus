/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import kevytlaskutus.domain.*;


/**
 *
 * @author ilkka
 */
public class FormActionFactory {
    
    AppService appService;
    HashMap<String, FormAction> commands;
    
    public FormActionFactory(AppService appService) {
        this.appService = appService;
        this.commands = new HashMap<>();
        this.commands.put("NewManagedCompany", new SaveNewManagedCompany(appService));
        this.commands.put("UpdateManagedCompany", new UpdateManagedCompany(appService));
        this.commands.put("NewCustomerCompany", new SaveNewCustomerCompany(appService));
        this.commands.put("UpdateCustomerCompany", new UpdateCustomerCompany(appService));
        this.commands.put("NewProduct", new SaveNewProduct(appService));
        this.commands.put("UpdateProduct", new UpdateProduct(appService));
    }
    
    public void execute(String actionType, HashMap<String, TextField> formFields, int id) {        
        FormAction action = this.commands.get(actionType); 
        action.setData(formFields, id);
        action.save();
    }
    
}
