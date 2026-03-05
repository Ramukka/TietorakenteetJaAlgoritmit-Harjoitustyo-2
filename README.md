# TietorakenteetJaAlgoritmit-Harjoitustyö 2
Harjoitustyö, jossa lasketaan vertailulukujen perusteella valtuustoon valitut ehdokkaat.

# Raportti

## Johdanto
Harjoitustyön tavoitteena oli hakea vaaliehdokkaiden tiedot ehdokkat.txt-tiedostosta, 
ja laskea ehdokkaille vertailuluvut, joiden avulla ehdokkaat valitaan valtuustoon.
Lopuksi valitut 51 ehdokasta tuli tulostaa valtuusto.json tiedostoon.

Gson-riippuvuutta hallittiin Mavenin avulla.
Ohjelman voi suorittaa mavenilla käyttämällä komentoa: `mvn exec:java`.

## Ohjelman toiminta
Alla käydään läpi ohjelman luokkia, metodeita ja yleisesti toimintaa.

### Java-luokat
Ohjelmassa on kolme luokaa: Ehdokas, Vaaliliitto ja Main. Tässä osiossa esitellään ne lyhyesti.

**Ehdokas**  
Ehdokasluokka pitää sisällään String-tyyppiset muuttujat etunimi, sukunimi ja puolue. Sekä int äänimäärä ja double-tyyppinen vertailuluku.
Luokasta löytyy metodeina konstruktori, toString, set/get-metodeita ja compareTo-implementaatio.

**Vaaliliitto**  
Vaaliliitto-luokassa on String-tyyppinen puolue, sekä ArrayList Ehdokas-luokan olioista.
Metodeina konstruktori, getPuolue, getEhdokkaat, LisaaEhdokas, sekä laskeVertailuluvut, josta lisää myöhemmin.

**Main**  
Main-luokassa, eli pääohjelmassa luetaan Ehdokkaat.txt tiedostoa Scannerin avulla, ja tiedostosta puretut tiedot lisätään Ehdokas-olioon, joka tallennetaan puolueet-HashMapin sisällä olevaan Vaaliliitto-olioon oman puolueensa alle.
Mainissa myös suoritetaan Ehdokkaiden järjestäminen, vertailuluvun laskeminen, vertailuluvun mukaan järjestäminen ja JSON-tulostus.

### Tekstitiedoston purku
Ehdokkaat.txt tiedot purettiin Scannerilla.
While-loopissa tekstitiedoston rivien tiedot purettiin osiin String[] taulukkoon, laskien whitespace regexiksi.
```java
String[] parts = row.split("\\s+");
```
tämän jälkeen osista kasataan Ehdokas-olio
```java
Ehdokas ehdokas = new Ehdokas(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
```
lopuksi lisätään ehdokas oman puolueensa vaaliliittoon. Mikäli puoluetta ei ennestään löydy, lisätään puolueet-HashMapiin uusi vaaliliitto-olio ja puolueavain.
```java
if(!puolueet.containsKey(ehdokas.getPuolue())) {
      puolueet.put(ehdokas.getPuolue(), new Vaaliliitto(ehdokas.getPuolue()));
}

puolueet.get(ehdokas.getPuolue()).lisaaEhdokas(ehdokas);
```

### Vertailuluvun laskeminen
Vertailuluku lasketaan D'Hondtin menetelmällä Vaaliliitto-luokassa, joka pitää sisällään Ehdokas-luokan olioita.

Vertailuluvun laskeminen tapahtuu kaavalla: $$V = \frac{S}{n}$$
Missä S on vaaliliiton kokonaisäänimäärä ja n on ehdokkaan sijoitus listalla.

Ensin ehdokkaat järjestetään äänimäärän mukaan. Tämä toteutetaan kutsumalla `Collections.sort(ehdokkaat);`, käyttämällä Ehdokas-luokkaan rakennettua compareTo-implementaatiota 
```java
@Override
public int compareTo(Ehdokas toinen) {
      int result = Integer.compare(toinen.aanimaara, this.aanimaara);
      return result;
}
```
Tämän jälkeen tallennetaan kaikkien ehdokkaiden yhteenlaskettu äänimäärä for-loopissa muuttujaan `int kokonaisAanimaara`. 
```java
for (Ehdokas ehdokas : ehdokkaat) {
      kokonaisAanimaara+= ehdokas.getAanimaara();
}
``` 
Lopuksi annetaan for-loopissa jokaiselle ehdokkaalle vertailuluku, joka lasketaan jakamalla kokonaisäänimäärä ehdokkaan sijoituksella listassa (äänimäärän mukaan).
```java
for (int i = 0; i < ehdokkaat.size(); i++) {
      ehdokkaat.get(i).setVertailuluku((double)kokonaisAanimaara/(i+1));
}
``` 

### JSON-tulostus
JSON-tulostukseen käytin Googlen Gson kirjastoa ja javan FIleWriteriä.
Otin valitut 51 ehdokasta sublistiin.
```java
Gson gson = new GsonBuilder().setPrettyPrinting().create();
System.out.println("--- Exporting to JSON ---");
FileWriter writer = new FileWriter("valtuusto.json");
var valitut = kaikkiEhdokkaat.subList(0, Math.min(51, kaikkiEhdokkaat.size()));
gson.toJson(valitut, writer);
writer.flush();
System.out.println("Created valtuusto.json");
```

## Yhteenveto
Harjoitustyössä hyödynnettiin paljon tuntiharjoitteista tutuksi tulleita toimintoja. D'Hondtin menetelmä tuli minulle uutena asiana, ja sen toetuttaminen oli suhteellisen vaivatonta kun sen sisäisti.

Googlen Gson-kirjasto osoittautui näppäräksi tavaksi hyödyntää JSON:ia Java-ympäristössä. Se oli kevyt ja helppo käyttää.

Kaikenkaikkiaan harjoitustyön toteutus sujui mutkattomasti, ja sain ohjelman toimimaan luotettavasti ja nopeasti.