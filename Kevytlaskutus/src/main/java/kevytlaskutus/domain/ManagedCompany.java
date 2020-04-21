package kevytlaskutus.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Managed company object class.
 */
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
public class ManagedCompany {
    
    private int id;
    
    private String name;
    private String regId;
    private String phone;
    private String street;
    private String postcode;
    private String commune;
    private String ovtId;
    private String provider;
    private String iban;
    private String bic;
       
    public ManagedCompany(
        String name, 
        String regId, 
        String phone, 
        String street, 
        String postcode, 
        String commune, 
        String ovtId, 
        String provider
    ) {
        this.name = name;
        this.regId = regId;
        this.phone = phone;
        this.street = street;
        this.postcode = postcode;
        this.commune = commune;
        this.ovtId = ovtId;
        this.provider = provider;        
    }
  
}
