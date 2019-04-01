package comenzi;

import administrare.Produs;

public class ComandaSpeciala extends Comanda {

    String denumireComandaSpeciala;
    double pretSuplimentar;

    public ComandaSpeciala(Produs produsComandat, int numarPortii, String dataComenzii, String denumireComandaSpeciala, double pretSuplimentar) {
        super(produsComandat, numarPortii, dataComenzii);
        this.denumireComandaSpeciala = denumireComandaSpeciala;
        this.pretSuplimentar = pretSuplimentar;
    }

    public String getDenumireComandaSpeciala() {
        return denumireComandaSpeciala;
    }

    public double getPretSuplimentar() {
        return pretSuplimentar;
    }

    public void setDenumireComandaSpeciala(String denumireComandaSpeciala) {
        this.denumireComandaSpeciala = denumireComandaSpeciala;
    }

    public void setPretSuplimentar(double pretSuplimentar) {
        this.pretSuplimentar = pretSuplimentar;
    }
}
