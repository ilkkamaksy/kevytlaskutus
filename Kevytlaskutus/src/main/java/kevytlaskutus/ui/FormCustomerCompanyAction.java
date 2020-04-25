/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;

/**
 *
 * @author ilkka
 */
public abstract class FormCustomerCompanyAction extends FormAction {
   
    public FormCustomerCompanyAction(AppService appService) {
        super(appService);
    }
    
    public abstract boolean execute();
    
    public CustomerCompany makeCustomerCompanyFromFieldValues(HashMap<String, Node> formFields, int id) {

        CustomerCompany result = new CustomerCompany(
            this.dataExtractor.getValueFromTextField("Name", formFields), 
            this.dataExtractor.getValueFromTextField("Register Id", formFields),
            this.dataExtractor.getValueFromTextField("Phone", formFields),
            this.dataExtractor.getValueFromTextField("Street address", formFields),
            this.dataExtractor.getValueFromTextField("Postcode", formFields),
            this.dataExtractor.getValueFromTextField("Commune/City", formFields),
            this.dataExtractor.getValueFromTextField("OVT", formFields),
            this.dataExtractor.getValueFromTextField("Provider", formFields)
        );
        
        result.setId(id);
       
        return result;
    }
    
}
