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
    
    public void getAction(String action, HashMap<String, TextField> formFields, Company company) {
        
        FormAction save = null;
        
        if ( action.equals("newManagedCompany") ) {
            Company companyToSave = this.makeManagedCompanyFromFieldValues(formFields, company);
            save = new SaveNewManagedCompany(companyToSave, appService);
        } else if ( action.equals("updateManagedCompany")) {
            Company companyToSave = this.makeManagedCompanyFromFieldValues(formFields, company);
            save = new UpdateManagedCompany(companyToSave, appService);
        } else if ( action.equals("newCustomerCompany")) {
            Company companyToSave = this.makeCustomerCompanyFromFieldValues(formFields, company);
            save = new SaveNewCustomerCompany(companyToSave, appService);
        } else if ( action.equals("UpdateCustomerCompany")) {
            Company companyToSave = this.makeCustomerCompanyFromFieldValues(formFields, company);
            save = new UpdateCustomerCompany(companyToSave, appService);
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
        
        result.setIban(formFields.get("IBAN").getText());
        result.setBic(formFields.get("BIC").getText());
        
        if ( company != null ) {
            result.setId(company.getId());
        }
        
        return result;
    }
    
    private Company makeCustomerCompanyFromFieldValues(HashMap<String, TextField> formFields, Company company) {
        
        Company result = new CustomerCompany(
            formFields.get("Name").getText(), 
            formFields.get("Register Id").getText(),
            formFields.get("Phone").getText(),
            formFields.get("Street address").getText(),
            formFields.get("Postcode").getText(),
            formFields.get("Commune/City").getText(),
            formFields.get("OVT").getText(),
            formFields.get("Provider").getText()
        );
        
        if ( company != null ) {
            result.setId(company.getId());
        }
        
        return result;
    }
}
