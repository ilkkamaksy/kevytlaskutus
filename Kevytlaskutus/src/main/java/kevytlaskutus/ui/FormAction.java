package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;


/**
 * Abstract class for form actions.
 */
public abstract class FormAction {
    
    AppService appService;
    FormFieldDataExtractor dataExtractor = new FormFieldDataExtractor();
    
    public FormAction(
            AppService appService
    ) {
        this.appService = appService;
    }
    
    public abstract void setData(HashMap<String, Node> formFields, int id);
    
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
    
    protected CustomerCompany makeCustomerCompanyFromFieldValues(HashMap<String, Node> formFields, int id) {

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
    
    protected Product makeProductFromFieldValues(HashMap<String, Node> formFields, int id) {

        Product result = new Product(
            this.dataExtractor.getValueFromTextField("Name", formFields),
            this.dataExtractor.getValueFromTextField("Price", formFields),
            this.dataExtractor.getValueFromTextField("Price Unit", formFields),
            this.dataExtractor.getValueFromTextField("Description", formFields)
        );
        
        result.setId(id);
       
        return result;
    }

}
