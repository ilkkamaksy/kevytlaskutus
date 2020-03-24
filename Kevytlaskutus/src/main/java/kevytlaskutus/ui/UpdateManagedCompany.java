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
public class UpdateManagedCompany extends FormAction{
    
    public UpdateManagedCompany(
            Company company,
            AppService appService
    ) {
        super(company, appService);
    }
    
    @Override
    public void save() {    
        super.appService.updateManagedCompany(super.company.getId(), (ManagedCompany)super.company);
    }
    
}
