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
    
    public FormActionFactory(AppService appService) {
        this.appService = appService;
    }
    
    public void execute(HashMap<String, TextField> formFields, Company company) {
        
        System.out.println(company.getClass());
        FormAction save = null;
        Company companyToSave = this.makeManagedCompanyFromFieldValues(formFields, company);
        
        if ( companyToSave.getId() == 0 ) {
            save = new SaveNewManagedCompany(companyToSave, appService);
        } else if ( company.getId() != 0 ) {
            save = new UpdateManagedCompany(companyToSave, appService);
        } 
       
        save.save();

    }
    
    private ManagedCompany makeManagedCompanyFromFieldValues(HashMap<String, TextField> formFields, Company company) {
    
        ManagedCompany result = new ManagedCompany(
            formFields.get("Name").getText(), 
            formFields.get("Register Id").getText(),
            formFields.get("Phone").getText(),
            formFields.get("Street address").getText(),
            formFields.get("Postcode").getText(),
            formFields.get("Commune/City").getText(),
            formFields.get("OVT").getText(),
            formFields.get("Provider").getText()
        );
        
        if ( company.getId() != 0 ) {
            result.setId(company.getId());
        } else {
            result.setId(0);
        }
        
        return result;
    }
   
}
