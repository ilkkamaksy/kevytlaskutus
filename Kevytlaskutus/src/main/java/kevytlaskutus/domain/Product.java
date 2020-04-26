package kevytlaskutus.domain;

import java.math.BigDecimal;
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
    
    private Integer invoiceId;
    
    private String name;
    private BigDecimal price;
    private String priceUnit;
    private String description;

    public Product(String name, BigDecimal price, String priceUnit, String description) {
        this.name = name;
        this.price = price;
        this.priceUnit = priceUnit;
        this.description = description;
    }
    
    
}
