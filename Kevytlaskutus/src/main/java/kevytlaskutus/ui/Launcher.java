/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.ProductDaoImpl;
import kevytlaskutus.domain.AppService;

/**
 *
 * @author ilkka
 */
public class Launcher extends Application {
    
    private AppService appService;
    
    public static void run(String [] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        ManagedCompanyDao managedCompanyDao = new ManagedCompanyDao();
        CustomerCompanyDao customerCompanyDao = new CustomerCompanyDao();
        ProductDaoImpl productDao = new ProductDaoImpl();
        this.appService = new AppService(managedCompanyDao, customerCompanyDao, productDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(this.appService);
        viewFactory.showDashBoard();
    }
}
