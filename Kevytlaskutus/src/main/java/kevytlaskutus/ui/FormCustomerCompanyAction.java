/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;

/**
 *
 * @author ilkka
 */
public abstract class FormCustomerCompanyAction extends FormAction {
   
    public FormCustomerCompanyAction(AppService appService) {
        super(appService);
    }
    
    public abstract boolean execute();
   
}
