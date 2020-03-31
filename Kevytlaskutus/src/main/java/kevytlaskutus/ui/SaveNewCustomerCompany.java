package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.*;

/**
 * Action to save a new customer.
 */
public class SaveNewCustomerCompany extends FormAction {
    
    private CustomerCompany company; 
    
    public SaveNewCustomerCompany(
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, TextField> formFields, int id) {
        this.company = super.makeCustomerCompanyFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createCustomerCompany(this.company);
    }

}
