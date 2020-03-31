package kevytlaskutus.domain;

/**
 * Abstract class that works as a blueprint for managed and customer company objects 
 */
public abstract class Company {
    
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
    
    public Company() {
        this("", "", "", "", "", "", "", "");
    }
    
    public Company(
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
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
     
    public String getPhone() {
        return this.phone;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }
    
    public String getRegId() {
        return regId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCommune() {
        return commune;
    }

    public void setOvtId(String ovtId) {
        this.ovtId = ovtId;
    }
    
    public String getOvtId() {
        return ovtId;
    }

    public String getProvider() {
        return provider;
    }
    
}
