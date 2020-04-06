package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.Node;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Product;

/**
 * Action to save a new Product.
 */
public class FormActionSaveNewProduct extends FormAction {
    
    private Product product;
    
    public FormActionSaveNewProduct(
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, Node> formFields, int id) {
        this.product = super.makeProductFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createProduct(this.product);
    }
}
