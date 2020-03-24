 # Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovelluksen avulla freelancerit ja yrittäjät voivat hallita yhden tai useamman yrityksen asiakastietoja, tuotteita, sekä hallita laskutustietoja. 

## Käyttäjät

Sovelluksessa on yksi käyttäjärooli, eli **normaali käyttäjä**. 

## Käyttöliittymäluonnos

Sovellus koostuu seuraavista näkymistä:
1. **Hallintapaneeli**, jossa käyttäjät voi valita toiminnoista: 
  - lisää, poista tai muokkaa hallittavia yrityksiä 
  - hallitse asiakkaita
  - hallitse tuotteita 
2. **Lisää hallittava yritys** käyttäjä voi lisätä uuden hallittavan yrityksen
3. **Muokkaa hallittavia yrityksiä** käyttäjä voi muokata hallittavan yrityksen tietoja
4. **Hallitse asiakkaita** käyttäjä voi selata, lisätä, muokata ja poistaa asiakkaita.
5. **Lisää asiakas** käyttäjä voi lisätä uuden asiakkaan.
6. **Muokkaa asiakasta**: käyttäjä voi muokata asiakkaan tietoja.
7. **Hallitse tuotteita**: käyttäjä voi selata, lisätä, muokata ja poistaa tuotteita.
8. **Lisää tuote** käyttäjä voi lisätä uuden tuotteen tai palvelun.
9. **Muokkaa tuotetta** käyttäjä voi muokata tuotteen tietoja.
10. **Hallitse yritystä** käyttäjä voi selata, lisätä, muokata ja poistaa yrityksen laskuja.
11. **Lisää lasku** käyttäjä voi lisätä yritykselle laskun.
12. **Muokkaa laskua** käyttäjä voi muokata laskua.

Sovellus aukeaa hallintapaneeliin käynnistymisen yhteydessä. 

## Perusversion tarjoama toiminnallisuus

- Käyttäjä voi lisätä uuden hallittavan yrityksen
  - yritykselle annetaan nimi, y-tunnus, yhteystiedot, OVT-tunnus, välittäjätunnus, sekä IBAN-tilinumero ja BIC-tunniste 

- Käyttäjä voi muokata hallittavien yritysten tietoja

- Käyttäjä voi poistaa hallittavia yrityksiä

- Käyttäjä voi lisätä uuden asiakkaan
  - asiakkaalle annetaan nimi, y-tunnus, yhteystiedot, sekä OVT-tunnus ja välittäjätunnus 
  - asiakkaalle luodaan asiakasnumero automaattisesti

- Käyttäjä voi muokata asiakkaiden tietoja

- Käyttäjä voi poistaa asiakkaita

- Käyttäjä voi lisätä uuden tuotteen
  - Tuotteelle annetaan nimi, hinta ja laskutusyksikkö (kpl, tunti, kk) 
  - tuotenumero luodaan automaattisesti

- Käyttäjä voi muokata tuotteita
- Käyttäjä voi poistaa tuotteita

- Käyttäjä voi lisätä hallitsemilleen yrityksille laskuja
  - Laskulle valitaan asiakas ja tuotteet
  - Laskulle annetaan numero ja eräpäivä
  - viitenumero luodaan automaattisesti 

- Käyttäjä voi tarkastella lisättyjä laskuja
- Käyttäjä voi tarkastella lisättyjä asiakkaita
- Käyttäjä voi tarkastella lisättyjä tuotteita

# Toimintaympäristön rajoitteet  

Ohjelmiston tulee toimia Linux- ja Windows 10-käyttöjärjestelmillä varustetuissa koneissa.
Asiakkaiden, tuotteiden ja laskujen tiedot talletetaan paikallisen koneen levylle.

## Jatkokehitysideoita

Jatkossa ohjelmaan lisätään seuraavia toiminnallisuuksia:

- Käyttäjä voi tallentaa laskuja pdf-tiedostona
- käyttäjä voi luoda hyvityslaskun
- tuotteita voi lisätä ryhmiin ja ryhmän voi suoraan lisätä laskulle
- uuden laskun voi luoda monistamalla jonkun valmiin laskun
- käyttäjä voi tarkastella yhteenvetoraportteja: laskutus per asiakas, tuote tai ajanjakso
- järjestelmä voidaan yhdistää kirjanpito-ohjelmistoihin rajapinnan kautta 
- sähköiset laskut voidaaan lähettää suoraan ohjelmasta 
- asiakas-, tuote- ja laskutiedot voidaan tallentaa pilveen, jonka avulla niihin pääsee käsiksi useammalta laitteelta






