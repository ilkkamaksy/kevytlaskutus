/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.*;

/**
 *
 * @author ilkka
 */
public class UpdateManagedCompany extends FormAction {
    
    private ManagedCompany company;
    
    public UpdateManagedCompany(
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
        super.appService.updateManagedCompany(this.company.getId(), this.company);
    }
    
}
