 # Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla freelancerit ja yrittäjät voivat hallita yhden tai useamman yrityksen asiakastietoja, tuotteita, sekä hallita laskutustietoja. 

## Käyttäjät

Sovelluksessa on yksi käyttäjärooli, eli **normaali käyttäjä**. 

## Käyttöliittymäluonnos

Sovellus koostuu seuraavista näkymistä:
1. **Hallintapaneeli**, jossa käyttäjä voi selata, lisätä, poistaa tai muokata hallittavia yrityksiä 
2. **Muokkaa yritystä** käyttäjä voi muokata hallittavan yrityksen tietoja
3. **Hallitse asiakkaita** käyttäjä voi selata, lisätä, muokata ja poistaa asiakkaita.
4. **Muokkaa asiakasta**: käyttäjä voi muokata asiakkaan tietoja.
5. **Hallitse yritystä** käyttäjä voi selata, lisätä, muokata ja poistaa yrityksen laskuja.
6. **Muokkaa laskua** käyttäjä voi muokata laskun tietoja.

Sovellus aukeaa hallintapaneeliin käynnistymisen yhteydessä. 

## Perusversion toiminnallisuudet

### Yritysten hallinta:

- Käyttäjä voi lisätä uuden hallittavan yrityksen
  - yritykselle annetaan nimi, y-tunnus, yhteystiedot, OVT-tunnus, välittäjätunnus, sekä IBAN-tilinumero ja BIC-tunniste 
- Käyttäjä voi muokata hallittavien yritysten tietoja
- Käyttäjä voi poistaa hallittavia yrityksiä

### Asiakashallinta:

- Käyttäjä voi lisätä uuden asiakkaan
  - asiakkaalle annetaan nimi, y-tunnus, yhteystiedot, sekä OVT-tunnus ja välittäjätunnus 
- Käyttäjä voi muokata asiakkaiden tietoja
- Käyttäjä voi poistaa asiakkaita

### Laskujen hallinta:

- Käyttäjä voi lisätä hallitsemilleen yrityksille laskuja.
  - Laskun vastaanottajaksi valitaan jokin tallennettu asiakas. 
  - Laskulle lisätään laskunumero, jonka perusteella generoidaan viitenumero automaattisesti.
  - Laskulle voidaan lisätä luontipäivämäärä ja maksuaika päivissä, eräpäivä generoidaan automaattisesti näiden perusteella.
  - Laskulle voidaan antaa arvonlisäveron prosentti
  - Laskulle voidaan lisätä alennusprosentti
  - Laskulle lisätään tuotteita, joille annetaan vähintään nimi. Lisäksi tuotteille voidaan lisätä hinta, laskutusyksikkö (kpl, tunti, kk, ym.) ja kuvaus.
  - Laskun summa lasketaan automaattisesti laskulle lisättyjen tuotteiden hintojen, alennusprosentin ja verojen perusteella.
  - Käyttäjä voi poistaa tuotteita laskulta.
  - Käyttäjälle ilmoitetaan, jos laskulta puuttuu tarvittavia tietoja

- Käyttäjä voi tarkastella ja muokata lisättyjä laskuja

- Käyttäjä voi navigoida näkymien välillä helposti
 - muokkausnäkymistä pääsee palaamaan takaisin hallintanäkymään joko globaalin navigaation kautta tai cancel- tai save-napeilla.

# Toimintaympäristön rajoitteet  

Ohjelmiston tulee toimia Linux- ja Windows 10-käyttöjärjestelmillä varustetuissa koneissa.
Asiakkaiden, tuotteiden ja laskujen tiedot talletetaan paikallisen koneen levylle.

## Jatkokehitysideoita

Jatkossa ohjelmaan lisätään seuraavia toiminnallisuuksia:

- Käyttäjä voi tallentaa laskuja pdf-tiedostona
- käyttäjä voi luoda hyvityslaskun
- käyttäjä voi hakea luotuja tuotteita ja lisätä niitä useammille laskuille
- käyttäjä voi lisätä tuotteita ryhmiin ja ryhmän voi suoraan lisätä laskulle
- käyttäjä voi luoda uuden laskun monistamalla jonkun vanhan laskun
- käyttäjä voi tarkastella yhteenvetoraportteja: laskutus per asiakas, tuote tai ajanjakso
- järjestelmä voidaan yhdistää kirjanpito-ohjelmistoihin rajapinnan kautta 
- käyttäjä voi lähettää sähköisiä laskuja suoraan ohjelmasta 
- asiakas-, tuote- ja laskutiedot voidaan tallentaa pilveen, jonka avulla niihin pääsee käsiksi useammalta laitteelta


