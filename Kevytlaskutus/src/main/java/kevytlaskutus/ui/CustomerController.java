package kevytlaskutus.ui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.ManagedCompany;

/**
 * FXML Controller class
 *
 * @author ilkka
 */
public class CustomerController extends BaseController implements Initializable {

    @FXML
    private ListView<String> customerListView;

    
    public CustomerController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }
   
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.customerListView = new ListView<>();
        this.customerListView.getItems().add("eka");
        this.customerListView.getItems().add("toka");
        this.customerListView.getItems().add("ekolmska");
        
    }
   
    @FXML
    void addCustomerCompanyAction(ActionEvent event) {
        System.out.println("sfsaf");
    }
   
}
