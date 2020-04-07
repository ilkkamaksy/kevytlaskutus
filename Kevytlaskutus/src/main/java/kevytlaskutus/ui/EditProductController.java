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
        
        super.setupNotice();
        super.noticePane.getChildren().add(super.notice);
        
        this.actionFactory = new FormActionFactory(this.appService);
        this.setupForm();
        this.setButtonAction();
    }
   
    public void setupForm() {
        
        this.form = new Form();
        
        if (currentProduct.getName().isEmpty()) {
            this.form.addTextField("Name", "");
            this.form.addTextField("Price", "");
            this.form.addTextField("Price Unit", "");
            this.form.addTextField("Description", "");
        } else {
            this.form.getForm().getChildren().clear();
        
            this.form.addTextField("Name", currentProduct.getName());
            this.form.addTextField("Price", currentProduct.getPrice());
            this.form.addTextField("Price Unit", currentProduct.getPriceUnit());
            this.form.addTextField("Description", currentProduct.getDescription());
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
            this.viewFactory.showManageProductsView();
        });
    }
}
