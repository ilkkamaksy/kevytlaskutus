package kevytlaskutus.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import static javax.swing.text.StyleConstants.Background;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.ManagedCompany;

/**
 * FXML controller class for editing managed company view.
 */
public class EditCompanyController extends BaseController implements Initializable {
   
    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    @FXML 
    private Button cancelButton;
    
    private Form form;
    
    private String actionType;
    
    private ManagedCompany currentCompany;
    
    private FormActionFactory actionFactory;
    
    public EditCompanyController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.actionFactory = new FormActionFactory(this.appService);
        this.currentCompany = this.appService.getCurrentManagedCompany();
        this.setActionType();
        
        super.primaryNotice.setupNotice();
        super.noticePane.getChildren().add(super.primaryNotice.notice);
        super.primaryNotice.showPendingNoticeMessage();
       
        this.setupForm();
        this.setButtonAction();
        this.setCancelButtonAction();
    }
   
    public void setupForm() {

        this.form = new Form(this.appService);

        if (this.appService.getCurrentManagedCompany() == null) {
            this.appService.setCurrentManagedCompany(new ManagedCompany());
        }
        
        this.form.addTextField("Name", currentCompany.getName(), currentCompany, "Name");
        this.form.addTextField("Register Id", currentCompany.getRegId(), currentCompany, "RegId");
        this.form.addTextField("Phone", currentCompany.getPhone(), currentCompany, "Phone");
        this.form.addTextField("Street address", currentCompany.getStreet(), currentCompany, "Street");
        this.form.addTextField("Postcode", currentCompany.getPostcode(), currentCompany, "Postcode");
        this.form.addTextField("Commune/City", currentCompany.getCommune(), currentCompany, "Commune");
        this.form.addTextField("OVT", currentCompany.getOvtId(), currentCompany, "OvtId");
        this.form.addTextField("Provider", currentCompany.getProvider(), currentCompany, "Provider");
        this.form.addTextField("IBAN", currentCompany.getIban(), currentCompany, "Iban");
        this.form.addTextField("BIC", currentCompany.getBic(), currentCompany, "Bic");
   
        this.editFormContainerPane.getChildren().add(this.form.getForm());
    }
  
    public void setActionType() {
        if (this.currentCompany.getId() > 0) {
            this.actionType = "UpdateManagedCompany";
        } else {
            this.actionType = "NewManagedCompany";
        }
    }
    
    private void setButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            boolean success = this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentCompany.getId());
            if (success) {
                this.viewFactory.showDashBoard();
            } else {
                this.viewFactory.showEditCompanyView();
            }
            
        });
    }
    
    private void setCancelButtonAction() {
        this.cancelButton.setOnAction(e-> {
            this.viewFactory.showDashBoard();
        });
    }
}
