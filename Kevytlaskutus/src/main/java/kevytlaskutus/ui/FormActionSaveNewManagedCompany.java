package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

/**
 * Action to execute a new managed company.
 */
public class FormActionSaveNewManagedCompany extends FormManagedCompanyAction {
   
    public FormActionSaveNewManagedCompany(AppService appService) {
        super(appService);
    }
   
    @Override
    public boolean execute() {
        return super.appService.saveCurrentManagedCompany();
    }
   
}
