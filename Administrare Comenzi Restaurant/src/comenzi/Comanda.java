package comenzi;

import administrare.Produs;

public class Comanda {

    public static int numarComanda = 0;
    protected Produs produsComandat;
    protected int numarPortii;
    protected String dataComenzii;

    public Comanda(Produs produsComandat, int numarPortii, String dataComenzii) {
        numarComanda++;
        this.produsComandat = produsComandat;
        this.numarPortii = numarPortii;
        this.dataComenzii = dataComenzii;
    }


    public Produs getProdusComandat() {
        return produsComandat;
    }

    public int getNumarPortii() {
        return numarPortii;
    }

    public String getDataComenzii() {
        return dataComenzii;
    }

    public void setProdusComandat(Produs produsComandat) {
        this.produsComandat = produsComandat;
    }

    public void setNumarPortii(int numarPortii) {
        this.numarPortii = numarPortii;
    }

    public void setDataComenzii(String dataComenzii) {
        this.dataComenzii = dataComenzii;
    }
}
