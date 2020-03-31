package kevytlaskutus.domain;

/**
 *
 * Customer object class.
 */
public class CustomerCompany extends Company {
   
    public CustomerCompany() {
        this("", "", "", "", "", "", "", "");
    }
    
    public CustomerCompany(
            String name, 
            String regId, 
            String phone, 
            String street, 
            String postcode, 
            String commune, 
            String ovtId, 
            String provider
    ) {
        super(name, regId, phone, street, postcode, commune, ovtId, provider);
    }

}
