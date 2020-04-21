package kevytlaskutus.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Product object class.
 */
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class Product {
    
    private int id;
    
    private List<Invoice> invoices = new ArrayList<>();
    
    private String name;
    private String price;
    private String priceUnit;
    private String description;

    public Product(String name, String price, String priceUnit, String description) {
        this.name = name;
        this.price = price;
        this.priceUnit = priceUnit;
        this.description = description;
    }
}
