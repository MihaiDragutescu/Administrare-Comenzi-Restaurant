package administrare;

public class Produs {

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

}
