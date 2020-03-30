/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.CustomerCompany;

/**
 *
 * @author ilkka
 */
public class EditCustomerController extends BaseController implements Initializable {
    
    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    private Form form;
    
    private String actionType;
    
    private CustomerCompany currentCompany;
    
    private FormActionFactory actionFactory;
    
    public EditCustomerController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentCompany = (CustomerCompany) this.appService.getCurrentCustomerCompany();
        this.setActionType();
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
   
        if (currentCompany.getName().isEmpty()) {
            this.form.addFormField("Name", "");
            this.form.addFormField("Register Id", "");
            this.form.addFormField("Phone", "");
            this.form.addFormField("Street address", "");
            this.form.addFormField("Postcode", "");
            this.form.addFormField("Commune/City", "");
            this.form.addFormField("OVT", "");
            this.form.addFormField("Provider", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addFormField("Name", currentCompany.getName());
            this.form.addFormField("Register Id", currentCompany.getRegId());
            this.form.addFormField("Phone", currentCompany.getPhone());
            this.form.addFormField("Street address", currentCompany.getStreet());
            this.form.addFormField("Postcode", currentCompany.getPostcode());
            this.form.addFormField("Commune/City", currentCompany.getCommune());
            this.form.addFormField("OVT", currentCompany.getOvtId());
            this.form.addFormField("Provider", currentCompany.getProvider());
        }
   
        this.editFormContainerPane.getChildren().add(this.form.getForm());
    }
  
    public void setActionType() {
        if (this.currentCompany.getId() > 0) {
            this.actionType = "UpdateCustomerCompany";
        } else {
            this.actionType = "NewCustomerCompany";
        }
    }
    
    private void setButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentCompany.getId());
        });
    }
}
