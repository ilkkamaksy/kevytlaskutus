/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

/**
 *
 * @author ilkka
 */
public class FormActionSaveInvoice extends FormAction {
    
    private Invoice invoice; 
    
    public FormActionSaveInvoice(AppService appService) {
        super(appService);
    }
    
    @Override
    public boolean execute() {
        return super.appService.saveCurrentInvoice();
    }
}
