package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.*;

public class FormActionUpdateCustomerCompany extends FormCustomerCompanyAction {
    
    private CustomerCompany company;
    
    public FormActionUpdateCustomerCompany(AppService appService) {
        super(appService);
    }
    
    @Override
    public boolean execute() {
        return super.appService.updateCurrentCustomerCompany();
    }

}
