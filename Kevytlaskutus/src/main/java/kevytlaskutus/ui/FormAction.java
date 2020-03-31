package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;

import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;


/**
 * Abstract class for form actions.
 */
public abstract class FormAction {
    
    AppService appService;
    
    public FormAction(
            AppService appService
    ) {
        this.appService = appService;
    }
    
    public abstract void setData(HashMap<String, TextField> formFields, int id);
    
    public abstract void save();
    
    protected ManagedCompany makeManagedCompanyFromFieldValues(HashMap<String, TextField> formFields, int id) {
    
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
        
        result.setBic(formFields.get("BIC").getText());
        result.setIban(formFields.get("IBAN").getText());
        result.setId(id);
       
        return result;
    }
    
    protected CustomerCompany makeCustomerCompanyFromFieldValues(HashMap<String, TextField> formFields, int id) {
    
        CustomerCompany result = new CustomerCompany(
            formFields.get("Name").getText(), 
            formFields.get("Register Id").getText(),
            formFields.get("Phone").getText(),
            formFields.get("Street address").getText(),
            formFields.get("Postcode").getText(),
            formFields.get("Commune/City").getText(),
            formFields.get("OVT").getText(),
            formFields.get("Provider").getText()
        );
        
        result.setId(id);
       
        return result;
    }
    
    protected Product makeProductFromFieldValues(HashMap<String, TextField> formFields, int id) {
    
        Product result = new Product(
            formFields.get("Name").getText(), 
            formFields.get("Price").getText(),
            formFields.get("Price Unit").getText(),
            formFields.get("Description").getText()
        );
        
        result.setId(id);
       
        return result;
    }
    
}
