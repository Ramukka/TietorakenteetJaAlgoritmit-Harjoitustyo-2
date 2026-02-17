public class Ehdokas implements Comparable<Ehdokas>{
    private String etunimi;
    private String sukunimi;
    private String puolue;
    private int aanimaara;
    private double vertailuluku;

    public Ehdokas(String etunimi, String sukunimi, String puolue, int aanimaara) {
        this.etunimi = etunimi;
        this.sukunimi = sukunimi;
        this.puolue = puolue;
        this.aanimaara = aanimaara;
        this.vertailuluku = 0;
    }

    @Override
    public String toString() {
        return "Ehdokas [etunimi="+etunimi+", sukunimi="+sukunimi+", puolue="+puolue+", äänimäärä="+aanimaara+", vertailuluku="+vertailuluku+"]"; 
    }

    public void setVertailuluku(double vertailuluku) {
        this.vertailuluku = vertailuluku;
    }

    public String getNimi() {
        return etunimi + " " + sukunimi;
    }

    public String getPuolue() {
        return puolue;
    }

    public int getAanimaara() {
        return aanimaara;
    }

    public double getVertailuluku() {
        return vertailuluku;
    }

    @Override
    public int compareTo(Ehdokas toinen) {
        int result = Integer.compare(toinen.aanimaara, this.aanimaara);
        return result;
    }
}
