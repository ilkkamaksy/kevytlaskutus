package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

public class FormActionUpdateManagedCompany extends FormAction {
    
    private ManagedCompany company;
    
    public FormActionUpdateManagedCompany(
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
        return super.appService.updateManagedCompany(this.company.getId(), this.company);
    }
    
}
