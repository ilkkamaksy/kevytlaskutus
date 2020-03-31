package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.AppService;
import kevytlaskutus.domain.Product;

/**
 * Action to save a new Product.
 */
public class SaveNewProduct extends FormAction {
    
    private Product product;
    
    public SaveNewProduct(
            AppService appService
    ) {
        super(appService);
    }

    @Override
    public void setData(HashMap<String, TextField> formFields, int id) {
        this.product = super.makeProductFromFieldValues(formFields, id);
    }
    
    @Override
    public void save() {
        super.appService.createProduct(this.product);
    }
}
