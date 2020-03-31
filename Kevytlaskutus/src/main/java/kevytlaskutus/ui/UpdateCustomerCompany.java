package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.*;

public class UpdateCustomerCompany extends FormAction {
    
    private CustomerCompany company;
    
    public UpdateCustomerCompany(
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
        super.appService.updateCustomerCompany(company.getId(), this.company);
    }

}
