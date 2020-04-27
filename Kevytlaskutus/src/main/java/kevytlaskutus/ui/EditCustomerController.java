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
    
    @FXML 
    private Button cancelButton;
    
    private Form form;
    
    private String actionType;
    
    private CustomerCompany currentCompany;
    
    private FormActionFactory actionFactory;
    
    public EditCustomerController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentCompany = this.appService.getCurrentCustomerCompany();
        this.setActionType();
        
        super.primaryNotice.setupNotice();
        super.noticePane.getChildren().add(super.primaryNotice.notice);
        super.primaryNotice.showPendingNoticeMessage();
        
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
        this.setCancelButtonAction();
    }
   
    public void setupForm() {
   
        this.form = new Form(this.appService);
        
        if (currentCompany == null) {
            this.appService.setCurrentCustomerCompany(new CustomerCompany());
        }
        
        this.form.addTextField("Name", currentCompany.getName(), currentCompany, "Name");
        this.form.addTextField("Register Id", currentCompany.getRegId(), currentCompany, "RegId");
        this.form.addTextField("Phone", currentCompany.getPhone(), currentCompany, "Phone");
        this.form.addTextField("Street address", currentCompany.getStreet(), currentCompany, "Street");
        this.form.addTextField("Postcode", currentCompany.getPostcode(), currentCompany, "Postcode");
        this.form.addTextField("Commune/City", currentCompany.getCommune(), currentCompany, "Commune");
        this.form.addTextField("OVT", currentCompany.getOvtId(), currentCompany, "OvtId");
        this.form.addTextField("Provider", currentCompany.getProvider(), currentCompany, "Provider");
        
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
            if (success) {
                this.viewFactory.showManageCustomerView();
            } else {
                this.viewFactory.showEditCustomerView();
            }
        });
    }
    
    private void setCancelButtonAction() {
        this.cancelButton.setOnAction(e-> {
            this.viewFactory.showDashBoard();
        });
    }
}
