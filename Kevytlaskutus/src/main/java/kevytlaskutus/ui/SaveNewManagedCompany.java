package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.*;

/**
 * Action to save a new managed company.
 */
public class SaveNewManagedCompany extends FormAction {
    
    private ManagedCompany company;
    
    public SaveNewManagedCompany(
            AppService appService
    ) {
        super(appService);
    }
    
    @Override
    public void setData(HashMap<String, TextField> formFields, int id) {
        this.company = super.makeManagedCompanyFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createManagedCompany(this.company);
    }
   
}
