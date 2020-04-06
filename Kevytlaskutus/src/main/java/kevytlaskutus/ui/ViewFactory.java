package kevytlaskutus.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kevytlaskutus.domain.AppService;

/**
 * Class responsible for changing scenes.
 */
public class ViewFactory {
    
    private AppService appService;
    private Stage mainStage;
    
    public ViewFactory(AppService appService) {
        this.appService = appService;
        this.mainStage = new Stage();
        this.mainStage.setTitle("Kevytlaskutus");
    }
    
    public void showDashBoard() {
        BaseController controller = new DashboardController(this.appService, this, "/fxml/dashboard.fxml");
        this.initializeStage(controller);
    }
    
    public void showEditCompanyView() {
        BaseController controller = new EditCompanyController(this.appService, this, "/fxml/formEditCompany.fxml");
        this.initializeStage(controller);
    }
    
    public void showManageCustomerView() {
        BaseController controller = new ManageCustomerController(this.appService, this, "/fxml/manageCustomers.fxml");
        this.initializeStage(controller);
    }
    
    public void showEditCustomerView() {
        BaseController controller = new EditCustomerController(this.appService, this, "/fxml/formEditCustomer.fxml");
        this.initializeStage(controller);
    }
    
    public void showManageProductsView() {
        BaseController controller = new ManageProductsController(this.appService, this, "/fxml/manageProducts.fxml");
        this.initializeStage(controller);
    }

    public void showEditProductView() {
        BaseController controller = new EditProductController(this.appService, this, "/fxml/formEditProduct.fxml");
        this.initializeStage(controller);
    }
    
    public void showManageInvoicesView() {
        BaseController controller = new ManageInvoicesController(this.appService, this, "/fxml/manageInvoices.fxml");
        this.initializeStage(controller);
    }

    public void showEditInvoiceView() {
        BaseController controller = new EditInvoiceController(this.appService, this, "/fxml/formEditInvoice.fxml");
        this.initializeStage(controller);
    }
    
    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(parent);
        this.mainStage.setScene(scene);
        this.mainStage.show();
    }
   
}
