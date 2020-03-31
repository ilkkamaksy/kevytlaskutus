package kevytlaskutus.ui;

import java.util.HashMap;
import javafx.scene.control.TextField;
import kevytlaskutus.domain.*;

public class UpdateProduct extends FormAction {
    
    private Product product;
    
    public UpdateProduct(
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
        super.appService.updateProduct(this.product.getId(), this.product);
    }
    
}
