/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Invoice;

/**
 *
 * @author ilkka
 */
public class FormActionSaveNewInvoice extends FormAction {
    private Invoice invoice; 
    
    public FormActionSaveNewInvoice (
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.invoice = super.makeInvoiceFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createInvoice(this.invoice);
    }
}
