package kevytlaskutus.domain;

import kevytlaskutus.domain.Company;

/**
 * Managed company object class.
 */
public class ManagedCompany extends Company {
    
    private String iban;
    private String bic;
    
    public ManagedCompany() {
        this("", "", "", "", "", "", "", "");
    }
    
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
        super(name, regId, phone, street, postcode, commune, ovtId, provider);
    }
    
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
    
    
   
}
