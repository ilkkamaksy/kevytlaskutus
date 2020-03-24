/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;

import kevytlaskutus.domain.Company;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.ManagedCompany;


/**
 *
 * @author ilkka
 */
public abstract class FormAction {
    
    Company company;
    AppService appService;
    
    public FormAction(
            Company company, 
            AppService appService
    ) {
        this.company = company;
        this.appService = appService;
    }
    
    public abstract void save();
    
}
