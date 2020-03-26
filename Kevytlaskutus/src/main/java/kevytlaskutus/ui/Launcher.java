/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.domain.AppService;

/**
 *
 * @author ilkka
 */
public class Launcher extends Application {
    
    private AppService appService;
    
    public static void main(String [] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        ManagedCompanyDao managedCompanyDao = new ManagedCompanyDao();
        CustomerCompanyDao customerCompanyDao = new CustomerCompanyDao();
        this.appService = new AppService(managedCompanyDao, customerCompanyDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(this.appService);
        viewFactory.showDashBoard();
    }
}
