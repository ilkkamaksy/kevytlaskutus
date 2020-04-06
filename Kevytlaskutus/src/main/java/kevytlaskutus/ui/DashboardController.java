package kevytlaskutus.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Company;
import kevytlaskutus.domain.ManagedCompany;

/**
 * FXML controller class for dashboard view.
 *
 * @author ilkka
 */
public class DashboardController extends BaseController implements Initializable {

    @FXML
    protected ListView<Node> managedCompaniesListView;
   
    public DashboardController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.setupManagedCompanyListView();
    }
    
    private void setupManagedCompanyListView() {
        this.managedCompaniesListView.getItems().clear();
       
        List<ManagedCompany> companies = super.appService.getManagedCompanies();
        if (companies.isEmpty()) {
            this.noContentMessage();
            return;
        }
        
        this.makeList(companies);
    }

    private void noContentMessage() {
        VBox container = new VBox(10);
        container.getChildren().add(new Text("You haven't added any companies yet. Add one to kick things off!"));
        this.managedCompaniesListView.getItems().add(container);
    }
    
    private void makeList(List<ManagedCompany> companies) {
        companies.forEach(company-> {
            this.createListNode(company);
        });
    }
    private void createListNode(ManagedCompany company) {
        
        HBox box = new HBox(10);
        Label label  = new Label(company.getName());
        label.setMinHeight(28);
        
        Button selectButton = new Button("Manage");
        selectButton.setOnAction(e-> {
            this.appService.setCurrentManagedCompany(company);
            this.viewFactory.showManageInvoicesView();
        });
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e-> {
            this.appService.setCurrentManagedCompany(company);
            this.viewFactory.showEditCompanyView();
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e-> {
            this.appService.deleteManagedCompany(company.getId());
            this.setupManagedCompanyListView();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));
        
        box.getChildren().addAll(
                label, 
                spacer, 
                selectButton,
                editButton,
                deleteButton
        );
        
        this.managedCompaniesListView.getItems().add(box);
    }
    
    @FXML
    void addNewManagedCompanyAction(ActionEvent event) {
        this.appService.setCurrentManagedCompany(new ManagedCompany());
        this.viewFactory.showEditCompanyView();
    }

}
