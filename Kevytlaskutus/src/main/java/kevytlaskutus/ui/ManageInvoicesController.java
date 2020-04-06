package kevytlaskutus.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
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
import kevytlaskutus.domain.Invoice;
import kevytlaskutus.domain.Product;

/**
 * FXML controller class for Products view.
 */
public class ManageInvoicesController extends BaseController implements Initializable {
    
    @FXML
    private ListView<Node> invoiceListView;

    public ManageInvoicesController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }
   
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.setupInvoiceListView();
    }
    
    private void setupInvoiceListView() {
        this.invoiceListView.getItems().clear();
        
        List<Invoice> invoices = super.appService.getInvoices();
        if (invoices.isEmpty()) {
            this.noContentMessage();
            return;
        }
        
        this.makeList(invoices);
    }
    
    private void noContentMessage() {
        VBox container = new VBox(10);
        container.getChildren().add(new Text("No invoices yet. Add one to kick things off!"));
        this.invoiceListView.getItems().add(container);
    }
    
    private void makeList(List<Invoice> invoices) {
        invoices.forEach(invoice-> {
            this.createListNode(invoice);
        });
    }
    
    private void createListNode(Invoice invoice) {
        
        HBox box = new HBox(10);
        Label label  = new Label(invoice.getCreatedDate().toString());
        label.setMinHeight(28);
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e-> {
            this.appService.setCurrentInvoice(invoice);
             this.viewFactory.showEditInvoiceView();
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e-> {
            this.appService.deleteInvoice(invoice.getId());
            this.setupInvoiceListView();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));
        
        box.getChildren().addAll(
                label, 
                spacer, 
                editButton,
                deleteButton
        );
        
        this.invoiceListView.getItems().add(box);
    }
  
    @FXML
    void addNewInvoiceAction() {
        this.appService.setCurrentInvoice(new Invoice());
         this.viewFactory.showEditInvoiceView();
    }

}
