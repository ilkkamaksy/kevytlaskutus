package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

/**
 * Action to execute a new managed company.
 */
public class FormActionSaveManagedCompany extends FormAction {
   
    public FormActionSaveManagedCompany(AppService appService) {
        super(appService);
    }
   
    @Override
    public boolean execute() {
        return super.appService.saveCurrentManagedCompany();
    }
   
}
