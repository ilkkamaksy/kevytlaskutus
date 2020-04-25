/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.ManagedCompany;

/**
 *
 * @author ilkka
 */
public abstract class FormManagedCompanyAction extends FormAction {
    
    public FormManagedCompanyAction(AppService appService) {
        super(appService);
    }
    
    public abstract boolean execute();
    
    protected ManagedCompany makeManagedCompanyFromFieldValues(HashMap<String, Node> formFields, int id) {

        ManagedCompany result = new ManagedCompany(
            this.dataExtractor.getValueFromTextField("Name", formFields), 
            this.dataExtractor.getValueFromTextField("Register Id", formFields),
            this.dataExtractor.getValueFromTextField("Phone", formFields),
            this.dataExtractor.getValueFromTextField("Street address", formFields),
            this.dataExtractor.getValueFromTextField("Postcode", formFields),
            this.dataExtractor.getValueFromTextField("Commune/City", formFields),
            this.dataExtractor.getValueFromTextField("OVT", formFields),
            this.dataExtractor.getValueFromTextField("Provider", formFields)
        );
        
        result.setBic(this.dataExtractor.getValueFromTextField("BIC", formFields));
        result.setIban(this.dataExtractor.getValueFromTextField("IBAN", formFields));
        result.setId(id);
       
        return result;
    }
}
