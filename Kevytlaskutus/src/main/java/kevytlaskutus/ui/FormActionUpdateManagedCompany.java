package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

public class FormActionUpdateManagedCompany extends FormAction {
   
    public FormActionUpdateManagedCompany(AppService appService) {
        super(appService);
    }
    
    @Override
    public boolean execute() {    
        return super.appService.updateCurrentManagedCompany();
    }
    
}
