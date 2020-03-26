/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kevytlaskutus.domain.AppService;

/**
 *
 * @author ilkka
 */
public class ProductsController extends BaseController {

    public ProductsController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }
       
    @FXML
    private ListView<?> productListView;

    @FXML
    void manageCompaniesAction(ActionEvent event) {

    }

    @FXML
    void manageCustomersAction(ActionEvent event) {

    }

    @FXML
    void manageProductsAction(ActionEvent event) {

    }
    
}
