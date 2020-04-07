package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

/**
 * Action to save a new managed company.
 */
public class FormActionSaveNewManagedCompany extends FormAction {
    
    private ManagedCompany company;
    
    public FormActionSaveNewManagedCompany(
            AppService appService
    ) {
        super(appService);
    }
    
    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.company = super.makeManagedCompanyFromFieldValues(formFields, id);
    }
    
    @Override
    public boolean save() {
        return super.appService.createManagedCompany(this.company);
    }
   
}
