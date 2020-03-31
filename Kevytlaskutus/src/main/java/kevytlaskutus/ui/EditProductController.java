package kevytlaskutus.ui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Product;

/**
 * FXML controller class for editing product view.
 */
public class EditProductController extends BaseController implements Initializable {
    
    @FXML
    private Pane editFormContainerPane;
   
    @FXML 
    private Button saveFormButton;
    
    private Form form;
    
    private String actionType;
    
    private Product currentProduct;
    
    private FormActionFactory actionFactory;
    
    public EditProductController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.currentProduct = this.appService.getCurrentProduct();
        this.setActionType();
        this.form = new Form();
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
   
        if (currentProduct.getName().isEmpty()) {
            this.form.addFormField("Name", "");
            this.form.addFormField("Price", "");
            this.form.addFormField("Price Unit", "");
            this.form.addFormField("Description", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addFormField("Name", currentProduct.getName());
            this.form.addFormField("Price", currentProduct.getPrice());
            this.form.addFormField("Price Unit", currentProduct.getPriceUnit());
            this.form.addFormField("Description", currentProduct.getDescription());
        }
   
        this.editFormContainerPane.getChildren().add(this.form.getForm());
    }
  
    public void setActionType() {
        if (this.currentProduct.getId() > 0) {
            this.actionType = "UpdateProduct";
        } else {
            this.actionType = "NewProduct";
        }
    }
    
    private void setButtonAction() {
        this.saveFormButton.setOnAction(e-> {
            this.actionFactory.execute(this.actionType, this.form.getFormFields(), this.currentProduct.getId());
        });
    }
}
