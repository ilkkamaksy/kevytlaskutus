package kevytlaskutus.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import kevytlaskutus.dao.CustomerCompanyDao;
import kevytlaskutus.dao.InvoiceDaoImpl;
import kevytlaskutus.dao.ManagedCompanyDao;
import kevytlaskutus.dao.ProductDaoImpl;
import kevytlaskutus.domain.AppService;

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
        InvoiceDaoImpl invoiceDao = new InvoiceDaoImpl();
        this.appService = new AppService(managedCompanyDao, customerCompanyDao, productDao, invoiceDao);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(this.appService);
        viewFactory.showDashBoard();
    }
}
