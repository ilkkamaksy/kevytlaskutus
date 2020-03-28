/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.List;
import kevytlaskutus.domain.Company;

/**
 *
 * @author ilkka
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
