# Arkkitehtuurikuvaus 

## Rakenne

Ohjelman rakenne noudattelee kolmitasoista kerrosarkkitehtuuria, ja koodin pakkausrakenne on seuraava:

![pakkauskaavio](pakkauskaavio.png)

Pakkaukset:

- kevytlaskutus.ui: JavaFX-käyttöliittymä 
- kevytlaskutus.domain sovelluslogiikka 
- kevytlaskutus.dao tietojen tallennus

## Käyttöliittymä

Käyttöliittymä sisältää seuraavat näkymät:

- Yritysten hallintanäkymä, jossa käyttäjä voi hallita yrityksiensä tietoja
- Yritysten muokkaus- ja tallennusnäkymä
- Asiakkaiden hallintanäkymä, jossa käyttäjä voi hallita asiakkaidensa tietoja
- Asiakkaiden muokkaus- ja tallennusnäkymä
- Yrityksen laskujen hallintanäkymä, jossa käyttäjä voi hallita yrityksensä laskuja
- Laskujen muokkaus- ja tallennusnäkymä

Jokainen näkymä on toteutettu Scene-oliona, jonka asettelun pohjana on fmxl-malli. Näkymät sijoitetaan yksi kerrallaan sovelluksen Stageen, josta huolehtii käyttöliittymän ViewController-luokka. 

Jokaisella näkymällä on taas oma kontrollerinsa, kuten esimerkiksi ManageCustomerController asiakkaiden hallintanäkymälle ja EditCustomerController asiakastietojen muokkausnäkymälle. Näkymäkohtaiset kontrollerit perivät BaseController-luokan, joka sisältää mm. globaalin navigaation toiminnallisuudet. 

Käyttöliittymä on eriytetty kokonaan sovelluslogiikasta. Käyttöliittymä saa kaiken datan kutsumalla sovelluslogiikan luokan AppService metodeja ja se myös käyttää AppServicen metodeja käyttäjän syöttämän datan välittämiseksi sovelluslogiikan käsiteltäväksi.

Kun käyttäjä lisää onnistuneesti uuden yrityksen, asiakkaan tai laskun, kyseisen näkymän kontrolleri kutsuu ViewFactoryn soveltuvaa metodia, joka vaihtaaa näkymäksi yritysten, asiakkaiden tai laskujen hallintanäkymän. Koska jokainen näkymä piirretään uudelleen aina näkymää vaihdettaessa, käyttäjä näkee lisäämänsä tietueen heti hallintanäkymässä. Mikäli tietueen lisäys ei onnistu puuttuvista tiedoista johtuen, tästä ilmoitetaan käyttäjälle suoraan muokkausnäkymässä. 

Muokkausnäkymien lomakkeet rakennetaan kutsumalla näkymän kontrollerista Form-luokan metodeja, kuten addTextField, addDecimalField tai addDatePicker. Form-luokan metodit huolehtivat lomakkeiden kenttien rakentamisesta käyttäjän antamien parametrien avulla ja lisää kentät näkymään. 

Form-luokka hyödyntää lomakekenttien rakentamisessa FormField-rajapinnan toteuttavia luokkia, kuten FormFieldText- ja FormFieldDatePicker-luokkia. FormField-rajapinnan toteuttavilla luokilla on metodit, joiden avulla voidaan määritellä FormField-olioille tapahtumakuuntelijan ja ns. callBack-metodin dynaamisesti. Tapahtumakuuntelijoiden ja callBack-metodien kutsumat luokat ja metodien nimet välitetään parametreinä näille metodeille. 

- Muokkausnäkymien lomakkeiden kentät välittävät syötetyn datan välittömästi AppService-luokalle tapahtumakuuntelijoiden avulla. 
- CallBack-metodien avulla kutsutaan pääasiassa käyttöliittymän luokkien metodeja näkymien tietojen päivittämiseksi. 

Kun käyttäjä klikkaa muokkausnäkymässä tallenna-nappia, kutsutaan FormActionFactory-luokan metodia execute, jolle välitetään String-tyyppisenä parametrina "actionType", joka voi olla esimerkiksi "SaveManagedCompany" tai "SaveCustomerCompany". Tämän parametrin perusteella FormActionFactoryn poimii ja suorittaa soveltuvan FormAction-luokan execute-metodin command-suunnittelumallin mukaisesti. 

FormAction-luokkien metodit kutsuvat puolestaan AppService-luokan parametrittomia metodeja saveCurrentManagedCompany tai saveCurrentCustomerCompany. Nämä tallentavat sovelluksen tilassa olevat ManagedCompany-, CustomerCompany, Product ja Invoice-oliot tietokantaan.

## Sovelluslogiikka ja datamalli

Luokka AppService tarjoaa metodit kaikille käyttöliittymän toiminnoille ja hallitsee sovelluslogiikkaa kokonaisuutena. Sovelluslogiikka sisältää myös sovelluksen datamalliin kuuluvat luokat ja lisäksi palveluluokkia, jotka vastaavat näiden data-luokkien ilmentymien tallennuksesta ja noutamisesta tietokannasta. 

Tämän lisäksi sovelluslogiikka sisältää apuluokkia laskujen viitenumeroiden tuottamiseen, loppusummien laskemiseen ja käyttäjälle näytettävien ilmoitusten luomiseen ja hallintaan.

### Datamalli

Sovelluksen datamallin muodostavat luokat CustomerCompany, ManagedCompany, Invoice ja Product. 

- ManagedCompany kuvaa käyttäjän hallitsemia yrityksiä
- CustomerCompany kuvaa asiakasyrityksiä
- Invoice kuvaa yritykselle lisätty laskuja
- Product kuvaa tuotteita, joita käyttäjä voi lisätä laskuihin

Data-olioita ovat lisäksi Notice-rajapinnan toteuttamat NoticeSuccess- ja NoticeError-luokat, jotka ilmentävät käyttäjälle välitettäviä ilmoituksia. Notice-luokan ilmentymät ovat staattisia, eikä niitä tallenneta tietokantaan.

### Sovelluslogiikka

AppService hyödyntää tietokannan tietojen noutamisessa ja tallennuksessa palveluluokkia 

- productService, 
- invoiceService, 
- managedCompanyService ja 
- customerCompanyService. 

AppService tarjoaa käyttöliittymälle metodit kaikkia sen tarvitsemia toiminnallisuuksia ja tietoja varten. Näitä ovat esimerkiksi

- List<Invoice> getInvoices()
- Invoice getInvoiceById(int id)
- boolean saveCurrentInvoice()
- boolean deleteInvoice(int id)

Samaan tapaan AppService tarjoaa vastaavia metodeja myös muille datamallin luokille. AppService pääsee käsiksi kaikkiin olioihin palveluluokkien - esimerkiksi luokat ProductService, InvoiceService tai CustomerCompanyService - välityksellä, jotka puolestaan pääsevät olioihin käsiksi pakkauksen kevytlaskutus.dao luokkien kautta.  

Lisäksi AppService tarjoaa metodit:

- Integer updateCurrentInvoiceReferenceNumber() - päivitä valitun laskun viitenumero
- BigDecimal updateCurrentInvoiceTotal() - päivitä valitun laskun loppusumma
- boolean hasNoticePending() - onko käyttäjälle esitettäviä ilmoituksia?
- Notice getPendingNotice() - hae esittävien ilmoituksien jonosta ensimmäinen ilmoitus

Kaikki riippuvuudet injektoidaan sovelluslogiikalle konstruktorikutsun yhteydessä.

Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:

 ![pakkausluokkakaavio](pakkausluokkakaavio-revisio.png)

### Tietojen pysyväistallennus

Sovelluslogiikan palveluluokat käyttävät tietokannan tietojen hakuun ja tallennukseen pakkauksen kevytlaskutus.dao luokkia

- ManagedCompanyDao
- CustomerCompanyDao
- InvoiceDaoImpl
- ProductDaoImpl

Nämä luokat toteuttavat CompanyDao, InvoiceDao ja ProductDao-rajapinnat. Ainoastaan nämä luokat huolehtivat tietojen tallentamisesta tietokantaan ja tietojen noutamisesta tietokannasta.

Kevytlaskutus.dao -pakkauksen Populate-luokka puolestaan käsittelee sekä tietokannasta noudetut tiedot, että sinne tallennettavat tiedot. Toisaalta kaikki tietokannasta noudetut tiedot välitetään Populate-luokalle - joka tuottaa tiedoista olioita, ja toisaalta kaikki tallennettavat tiedot välitetään ensin Populate-luokalle, joka lisää tiedot tietokantakutsuun. 

Koska kaikki dao-Luokat noudattavat Data Access Object -suunnittelumallia ja ne voidaan korvata uusilla toteutuksilla, jos tallennusmenetelmiä halutaan vaihtaa. Luokkien sisäinen logiikka on häivytetty rajapintojen taakse, jolloin sovelluslogiikkan käyttäjän ei tarvitse tietää niiden yksityiskohdista. 

Sovelluslogiikan testaus käyttää tietokantaa, joka on keskusmuistissa, kun taas itse sovellus tallentaa tietokannan käyttäjän laitteelle massamuistiin.

## Päätoiminnallisuudet

Alla oleva sekvenssikaavio kuvaa, miten sovelluksen kontrolli etenee kun käyttäjä lisää uuden hallittavan yrityksen.

![sekvenssikaavio yrityksen tallennus](sekvenssikaavio-savemanagedcompany.png)

Tapahtumakäsittelijä kutsuu AppService-luokan metodia saveCurrentManagedCompany ja AppService tarkistaa ensin, että sovelluksen tilassa on valittuna ManagedCompany-olio ja että tällä on vähintäänkin nimi. Jos nämä ovat kunnossa, AppService kutsuu ManagedCompanyService-luokan metodia createManagedCompany, lähettäen parametrina nykyisen ManagedCompany-olion. 

ManagedCompanyService kutsuu puolestaan ManagedCompanyDao-luokan metodia create ja välittää ManagedCompany-olion edelleen parametrina. ManagedCompanyDao-luokka tallentaa tiedot tietokantaan ja palauttaa true, jos operaatio onnistuu ja false, jos se epäonnistuu.

Jos operaatio onnistuu, ManagedCompanyDao palauttaa true ManagedCompanyServicelle, joka välittää vastauksen eteen päin AppService-luokalle. Tämän jälkeen appService lisää notifikaation NoticeQueue-jonoon. Lisättävässä Notice-oliossa on indikaattori onnistuneesta operaatiosta ja viesti käyttäjälle. Indikaattoreiden true ja false avulla määritellään, onko kyseessä NoticeSuccess- vai NoticeError-luokka. 

Tämän jälkeen AppService välittää tiedon operaation onnistumisesta tai epäonnistumisesta käyttöliittymälle, joka onnistumisen yhteydessä siirtää käyttäjän dashboard-näkymään. Dashboard puolestaan kysyy välittömästi AppService-luokalta, onko NoticeQueue-jonossa uusia ilmoituksia ja jos on, se pyytää ensimmäisenä jonossa olevan Notice-olion tältä ja esittää sen käyttäjälle.

### Muut toiminnallisuudet

Sama periaate toistuu myös tallennettaessa ja päivitettäessä ManagedCompany, CustomerCompany ja Invoice-olioita. 