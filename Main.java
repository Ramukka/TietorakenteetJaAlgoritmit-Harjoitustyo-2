import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            HashMap<String, Vaaliliitto> puolueet = new HashMap<>();

            Scanner scanner = new Scanner(Paths.get("ehdokkaat.txt"));

            while (scanner.hasNextLine()) {
                String row = scanner.nextLine();
                String[] parts = row.split("\\s+");

                Ehdokas ehdokas = new Ehdokas(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));

                if(!puolueet.containsKey(ehdokas.getPuolue())) {
                    puolueet.put(ehdokas.getPuolue(), new Vaaliliitto(ehdokas.getPuolue()));
                }

                puolueet.get(ehdokas.getPuolue()).lisaaEhdokas(ehdokas);
            }
            scanner.close();

            ArrayList<Ehdokas> kaikkiEhdokkaat = new ArrayList<>();

            for (String puolue : puolueet.keySet()) {
                Vaaliliitto vl = puolueet.get(puolue);
                vl.laskeVertailuluvut();
                ArrayList<Ehdokas> puolueenEhdokkaat = vl.getEhdokkaat();
                for (Ehdokas ehdokas : puolueenEhdokkaat) {
                    kaikkiEhdokkaat.add(ehdokas);
                }
            }

            Comparator<Ehdokas> vertailuluvunMukaan = (e1, e2) -> Double.compare(e2.getVertailuluku(), e1.getVertailuluku());
            Collections.sort(kaikkiEhdokkaat, vertailuluvunMukaan);

            for (int i = 0; i < 51; i++) {
                Ehdokas e = kaikkiEhdokkaat.get(i);
                System.out.printf("puolue: %s, äänimäärä: %d, nimi: %s vertluku: %f \n", e.getPuolue(), e.getAanimaara(), e.getNimi());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
