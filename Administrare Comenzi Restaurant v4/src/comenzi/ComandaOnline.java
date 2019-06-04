package comenzi;

import administrare.Produs;

import java.util.Date;

public class ComandaOnline extends Comanda {
    double taxaTransport;

    public ComandaOnline(Produs produsComandat, int numarPortii, Date dataComenzii, double taxaTransport) {
        super(produsComandat, numarPortii, (java.sql.Date) dataComenzii);
        this.taxaTransport = taxaTransport;
    }

    public double getTaxaTransport() {
        return taxaTransport;
    }

    public void setTaxaTransport(double taxaTransport) {
        this.taxaTransport = taxaTransport;
    }
}
