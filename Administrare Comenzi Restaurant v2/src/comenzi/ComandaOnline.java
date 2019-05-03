package comenzi;

import administrare.Produs;

public class ComandaOnline extends Comanda {
    double taxaTransport;

    public ComandaOnline(Produs produsComandat, int numarPortii, String dataComenzii, double taxaTransport) {
        super(produsComandat, numarPortii, dataComenzii);
        this.taxaTransport = taxaTransport;
    }

    public double getTaxaTransport() {
        return taxaTransport;
    }

    public void setTaxaTransport(double taxaTransport) {
        this.taxaTransport = taxaTransport;
    }
}
