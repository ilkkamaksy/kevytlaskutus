package kevytlaskutus.ui;

import java.sql.Date;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
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
    
    public abstract void setData(HashMap<String, Node> formFields, int id);
    
    public abstract void save();
    
    protected ManagedCompany makeManagedCompanyFromFieldValues(HashMap<String, Node> formFields, int id) {
    
        TextField name = (TextField) formFields.get("Name");
        TextField registerId = (TextField) formFields.get("Register Id");
        TextField phone = (TextField) formFields.get("Phone");
        TextField streetAddress = (TextField) formFields.get("Street address");
        TextField postcode = (TextField) formFields.get("Postcode");
        TextField city = (TextField) formFields.get("Commune/City");
        TextField ovt = (TextField) formFields.get("OVT");
        TextField provider = (TextField) formFields.get("Provider");
        TextField bic = (TextField) formFields.get("BIC");
        TextField iban = (TextField) formFields.get("IBAN");
        
        ManagedCompany result = new ManagedCompany(
            name.getText(), 
            registerId.getText(),
            phone.getText(),
            streetAddress.getText(),
            postcode.getText(),
            city.getText(),
            ovt.getText(),
            provider.getText()
        );
        
        result.setBic(bic.getText());
        result.setIban(iban.getText());
        result.setId(id);
       
        return result;
    }
    
    protected CustomerCompany makeCustomerCompanyFromFieldValues(HashMap<String, Node> formFields, int id) {
    
        TextField name = (TextField) formFields.get("Name");
        TextField registerId = (TextField) formFields.get("Register Id");
        TextField phone = (TextField) formFields.get("Phone");
        TextField streetAddress = (TextField) formFields.get("Street address");
        TextField postcode = (TextField) formFields.get("Postcode");
        TextField city = (TextField) formFields.get("Commune/City");
        TextField ovt = (TextField) formFields.get("OVT");
        TextField provider = (TextField) formFields.get("Provider");
        
        CustomerCompany result = new CustomerCompany(
            name.getText(), 
            registerId.getText(),
            phone.getText(),
            streetAddress.getText(),
            postcode.getText(),
            city.getText(),
            ovt.getText(),
            provider.getText()
        );
        
        result.setId(id);
       
        return result;
    }
    
    protected Product makeProductFromFieldValues(HashMap<String, Node> formFields, int id) {
    
        TextField name = (TextField) formFields.get("Name");
        TextField price = (TextField) formFields.get("Price");
        TextField priceUnit = (TextField) formFields.get("Price Unit");
        TextField description = (TextField) formFields.get("Description");
                
        Product result = new Product(
            name.getText(), 
            price.getText(),
            priceUnit.getText(),
            description.getText()
        );
        
        result.setId(id);
       
        return result;
    }
    
    protected Invoice makeInvoiceFromFieldValues(HashMap<String, Node> formFields, int id) {
        
        DatePicker createdDate = (DatePicker) formFields.get("Date");
        TextField customerId = (TextField) formFields.get("Customer Id");
        
        Invoice result = new Invoice(Date.valueOf(createdDate.getValue()));
        result.setId(id);
        result.setCustomerId(Integer.valueOf(customerId.getText()));
       
        return result;
        
    }
}
