import java.util.ArrayList;
import java.util.Collections;

public class Vaaliliitto {
    private String puolue;
    private ArrayList<Ehdokas> ehdokkaat; 

    public Vaaliliitto(String puolue) {
        this.puolue = puolue;
        this.ehdokkaat = new ArrayList<>();
    }

    public String getPuolue() {
        return puolue;
    }

    public ArrayList<Ehdokas> getEhdokkaat() {
        return ehdokkaat;
    }

    public void lisaaEhdokas(Ehdokas ehdokas) {
        ehdokkaat.add(ehdokas);
    }

    // Vertailuluvun laskeminen D'Hondtin menetelmällä
    public void laskeVertailuluvut() {
        int kokonaisAanimaara = 0;
        Collections.sort(ehdokkaat);
        for (Ehdokas ehdokas : ehdokkaat) {
            kokonaisAanimaara+= ehdokas.getAanimaara();
        }

        for (int i = 0; i < ehdokkaat.size(); i++) {
            ehdokkaat.get(i).setVertailuluku((double)kokonaisAanimaara/(i+1));
        }


    }
}
