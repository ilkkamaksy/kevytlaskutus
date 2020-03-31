package kevytlaskutus.ui;

import javafx.fxml.FXML;
import kevytlaskutus.domain.AppService;

/**
 * Base controller class for views.
 */
public abstract class BaseController {
    
    protected AppService appService;
    protected ViewFactory viewFactory;
    protected String fxmlName;

    public BaseController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        this.appService = appService;
        this.viewFactory = viewFactory;
        this.fxmlName = fxmlName;
    }
    
    public String getFxmlName() {
        return this.fxmlName;
    }
    
    @FXML
    void manageCompaniesAction() {
        this.viewFactory.showDashBoard();
    }

    @FXML
    void manageCustomersAction() {
        this.viewFactory.showCustomerView();
    }

    @FXML
    void manageProductsAction() {
        this.viewFactory.showProductsView();
    }    
}
