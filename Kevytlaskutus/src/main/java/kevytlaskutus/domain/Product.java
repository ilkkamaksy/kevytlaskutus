package kevytlaskutus.domain;

/**
 * Product object class.
 */
public class Product {
    
    private int id;
    private String name;
    private String price;
    private String priceUnit;
    private String description;

    public Product() {
        this("", "", "", "");
    }
    
    public Product(String name, String price, String priceUnit, String description) {
        this.name = name;
        this.price = price;
        this.priceUnit = priceUnit;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
   
}
