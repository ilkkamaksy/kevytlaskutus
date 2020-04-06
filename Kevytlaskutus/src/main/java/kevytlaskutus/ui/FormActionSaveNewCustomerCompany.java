package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

/**
 * Action to save a new customer.
 */
public class FormActionSaveNewCustomerCompany extends FormAction {
    
    private CustomerCompany company; 
    
    public FormActionSaveNewCustomerCompany(
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.company = super.makeCustomerCompanyFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createCustomerCompany(this.company);
    }

}
