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
    
    private Form form;
    
    private String actionType;
    
    private ManagedCompany currentCompany;
    
    private FormActionFactory actionFactory;
    
    public EditCompanyController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentCompany = this.appService.getCurrentManagedCompany();
        this.setActionType();
        this.form = new Form();
        super.setupNotice();
        super.noticePane.getChildren().add(super.notice);
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
            this.form.addTextField("BIC", "");
            this.form.addTextField("IBAN", "");
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
            this.form.addTextField("IBAN", currentCompany.getIban());
            this.form.addTextField("BIC", currentCompany.getBic());
        }
   
        
        
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
            super.setNoticeMessageText(success);
            super.toggleNoticeVisibility(success);
        });
    }
    
    
}
