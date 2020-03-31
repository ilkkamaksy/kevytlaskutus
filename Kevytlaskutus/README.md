# KevytlaskutusApp

Sovelluksen avulla freelancerit ja pienyrittäjät voivat hallita yhden tai useamman yrityksensä asiakakkaita, tuotteita ja palveluita, sekä laskutustietoja. Sovellus ei vaadi rekisteröitymistä.

## Dokumentaatio

- [Vaatimusmäärittely](/dokumentaatio/vaatimusmaarittely.md)
- [Työaikakirjanpito](/dokumentaatio/tuntikirjanpito)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

`mvn test`

Testikattavuusraportti luodaan komennolla

`mvn jacoco:report`

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto target/site/jacoco/index.html

### Suoritettavan jarin generointi

Komento

`mvn package`

generoi hakemistoon target suoritettavan jar-tiedoston Kevytlaskutus-1.0-SNAPSHOT.jar

### JavaDoc

JavaDoc generoidaan komennolla

`mvn javadoc:javadoc`

JavaDocia voi tarkastella avaamalla selaimella tiedosto target/site/apidocs/index.html

### Checkstyle

Tiedostoon checkstyle.xml määrittelemät tarkistukset suoritetaan komennolla

`mvn jxr:jxr checkstyle:checkstyle`

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto target/site/checkstyle.html


