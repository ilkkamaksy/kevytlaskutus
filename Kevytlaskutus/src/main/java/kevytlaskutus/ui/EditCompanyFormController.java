/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Company;

/**
 *
 * @author ilkka
 */
public class EditCompanyFormController extends BaseController implements Initializable {
    
    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    private Form form;
    
    private FormActionFactory actionFactory;
    
    public EditCompanyFormController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
       
        Company currentCompany = this.appService.getCurrentCompany();
        
        if ( currentCompany.getName().isEmpty() ) {
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
            // this.form.addFormField("IBAN", this.currentManagedCompany.getIban());
            // this.form.addFormField("BIC", this.currentManagedCompany.getBic());
        }
   
        this.editFormContainerPane.getChildren().add(this.form.getForm());
    }
    
   
    private void setButtonAction() {
        this.saveFormButton.setOnAction(e->{
            this.actionFactory.execute(this.form.getFormFields(), this.appService.getCurrentCompany());
        });
    }
}
