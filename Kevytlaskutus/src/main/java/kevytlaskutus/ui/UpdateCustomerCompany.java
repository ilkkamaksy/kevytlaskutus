/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kevytlaskutus.domain.*;

/**
 *
 * @author ilkka
 */
public class UpdateCustomerCompany extends FormAction{
    
    private CustomerCompany company;
    
    public UpdateCustomerCompany(
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, TextField> formFields, int id) {
        this.company = super.makeCustomerCompanyFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
      super.appService.updateCustomerCompany(company.getId(), this.company);
    }

}
