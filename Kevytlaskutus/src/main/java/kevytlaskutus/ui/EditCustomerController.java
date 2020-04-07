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
 * FXML controller class for editing customers view.
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
        super.setupNotice();
        super.noticePane.getChildren().add(super.notice);
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
   
        if (currentCompany.getName().isEmpty()) {
            this.form.addTextField("Name", "");
            this.form.addTextField("Register Id", "");
            this.form.addTextField("Phone", "");
            this.form.addTextField("Street address", "");
            this.form.addTextField("Postcode", "");
            this.form.addTextField("Commune/City", "");
            this.form.addTextField("OVT", "");
            this.form.addTextField("Provider", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addTextField("Name", currentCompany.getName());
            this.form.addTextField("Register Id", currentCompany.getRegId());
            this.form.addTextField("Phone", currentCompany.getPhone());
            this.form.addTextField("Street address", currentCompany.getStreet());
            this.form.addTextField("Postcode", currentCompany.getPostcode());
            this.form.addTextField("Commune/City", currentCompany.getCommune());
            this.form.addTextField("OVT", currentCompany.getOvtId());
            this.form.addTextField("Provider", currentCompany.getProvider());
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
            boolean success = this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentCompany.getId());
            super.setNoticeMessageText(success);
            super.toggleNoticeVisibility(success);
        });
    }
}
