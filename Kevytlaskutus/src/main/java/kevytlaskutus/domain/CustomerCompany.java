/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kevytlaskutus.domain;

import java.util.List;

/**
 *
 * @author ilkka
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
