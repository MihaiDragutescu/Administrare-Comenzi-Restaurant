package administrare;

public class Produs implements Comparable<Produs> {

    private String denumireProdus;
    private double pretProdus;

    public Produs(String denumireProdus, double pretProdus) {
        this.denumireProdus = denumireProdus;
        this.pretProdus = pretProdus;
    }

    public String getDenumireProdus() {
        return denumireProdus;
    }

    public double getPretProdus() {
        return pretProdus;
    }

    public void setDenumireProdus(String denumireProdus) {
        this.denumireProdus = denumireProdus;
    }

    public void setPretProdus(double pretProdus) {
        this.pretProdus = pretProdus;
    }

    @Override
    //Metoda care compara pretul a doua produse
    public int compareTo(Produs o) {
        return Double.valueOf(this.getPretProdus()).compareTo(o.getPretProdus());
    }
}
