/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kevytlaskutus.domain.AppService;

/**
 *
 * @author ilkka
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
    
    public void showEditCompanyForm() {
        BaseController controller = new EditCompanyController(this.appService, this, "/fxml/companyForm.fxml");
        this.initializeStage(controller);
    }
    
    public void showCustomerView() {
        BaseController controller = new CustomerController(this.appService, this, "/fxml/manageCustomers.fxml");
        this.initializeStage(controller);
    }
    
    public void showEditCustomerForm() {
        BaseController controller = new EditCustomerController(this.appService, this, "/fxml/customerForm.fxml");
        this.initializeStage(controller);
    }
    
    public void showProductsView() {
        BaseController controller = new ProductsController(this.appService, this, "/fxml/manageProducts.fxml");
        this.initializeStage(controller);
    }

    public void showEditProductForm() {
        BaseController controller = new EditProductController(this.appService, this, "/fxml/productForm.fxml");
        this.initializeStage(controller);
    }
    
    private void initializeStage(BaseController controller) {
        FXMLLoader fxmlLoader =new FXMLLoader(getClass().getResource(controller.getFxmlName()));
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
