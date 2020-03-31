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
import kevytlaskutus.domain.Product;

/**
 * FXML controller class for Products view.
 */
public class ProductsController extends BaseController implements Initializable {
    
    @FXML
    private ListView<Node> productListView;

    public ProductsController(AppService appService, ViewFactory viewFactory, String fxmlName) {
        super(appService, viewFactory, fxmlName);
    }
   
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.setupProductListView();
    }
    
    private void setupProductListView() {
        this.productListView.getItems().clear();
        
        List<Product> products = super.appService.getProducts();
        if (products.isEmpty()) {
            this.noContentMessage();
            return;
        }
        
        this.makeList(products);
    }
    
    private void noContentMessage() {
        VBox container = new VBox(10);
        container.getChildren().add(new Text("No products yet. Add one to kick things off!"));
        this.productListView.getItems().add(container);
    }
    
    private void makeList(List<Product> products) {
        products.forEach(product-> {
            this.createListNode(product);
        });
    }
    
    private void createListNode(Product product) {
        
        HBox box = new HBox(10);
        Label label  = new Label(product.getName());
        label.setMinHeight(28);
        
        Button selectButton = new Button("Manage");
        selectButton.setOnAction(e-> {
        });
        
        Button editButton = new Button("Edit");
        editButton.setOnAction(e-> {
            this.appService.setCurrentProduct(product);
            this.viewFactory.showEditProductForm();
        });
        
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e-> {
            this.appService.deleteProduct(product.getId());
            this.setupProductListView();
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
        
        this.productListView.getItems().add(box);
    }
  
    @FXML
    void addNewProductAction() {
        this.appService.setCurrentProduct(new Product());
        this.viewFactory.showEditProductForm();
    }

}
