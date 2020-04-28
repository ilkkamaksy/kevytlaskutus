package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.ManagedCompany;
import kevytlaskutus.domain.Product;


/**
 * Abstract class for form actions.
 */
public abstract class FormAction {
    
    AppService appService;
   
    public FormAction(AppService appService) {
        this.appService = appService;
    }
  
    public abstract boolean execute();
}
