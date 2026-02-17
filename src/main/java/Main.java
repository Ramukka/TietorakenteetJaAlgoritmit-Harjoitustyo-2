//run with mvn exec:java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.io.FileWriter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Main {
    
    public static void main(String[] args) {
        try {
            HashMap<String, Vaaliliitto> puolueet = new HashMap<>();
            InputStream is = Main.class.getClassLoader().getResourceAsStream("ehdokkaat.txt"); //Inputstream maven projektissa lukemista varten

            Scanner scanner = new Scanner(is, StandardCharsets.UTF_8);

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] parts = row.split("\\s+");

                Ehdokas ehdokas = new Ehdokas(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));

                // Jos puoluetta ei vielä löydy, lisätään se puolueet hashmappiin.
                if(!puolueet.containsKey(ehdokas.getPuolue())) {
                    puolueet.put(ehdokas.getPuolue(), new Vaaliliitto(ehdokas.getPuolue()));
                }

                puolueet.get(ehdokas.getPuolue()).lisaaEhdokas(ehdokas);
            }
            scanner.close();

            ArrayList<Ehdokas> kaikkiEhdokkaat = new ArrayList<>();

            // Lisätään kaikkien puolueiden ehdokkaat ArrayListiin.
            for (String puolue : puolueet.keySet()) {
                Vaaliliitto vl = puolueet.get(puolue);
                vl.laskeVertailuluvut();
                ArrayList<Ehdokas> puolueenEhdokkaat = vl.getEhdokkaat();
                for (Ehdokas ehdokas : puolueenEhdokkaat) {
                    kaikkiEhdokkaat.add(ehdokas);
                }
            }

            // Järjestetään vertailuluvun mukaan
            Comparator<Ehdokas> vertailuluvunMukaan = (e1, e2) -> Double.compare(e2.getVertailuluku(), e1.getVertailuluku());
            Collections.sort(kaikkiEhdokkaat, vertailuluvunMukaan);

            // tulostetaan ensimmäiset 51.
            for (int i = 0; i < 51; i++) {
                Ehdokas e = kaikkiEhdokkaat.get(i);
                System.out.printf("puolue: %s, äänimäärä: %d, nimi: %s\n", e.getPuolue(), e.getAanimaara(), e.getNimi());
            }

            // tulosteaan myös json-tiedostoon.
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            System.out.println("--- Exporting to JSON ---");
            FileWriter writer = new FileWriter("valtuusto.json");
            var valitut = kaikkiEhdokkaat.subList(0, Math.min(51, kaikkiEhdokkaat.size()));
            gson.toJson(valitut, writer);
            writer.flush();
            System.out.println("Created valtuusto.json");
           
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
