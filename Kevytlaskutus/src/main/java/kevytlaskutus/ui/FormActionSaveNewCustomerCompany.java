package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

/**
 * Action to execute a new customer.
 */
public class FormActionSaveNewCustomerCompany extends FormCustomerCompanyAction {
   
    public FormActionSaveNewCustomerCompany(AppService appService) {
        super(appService);
    }

    @Override
    public boolean execute() {
        return super.appService.saveCurrentCustomerCompany();
    }

}
