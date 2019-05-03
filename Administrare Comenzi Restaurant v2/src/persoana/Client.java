package persoana;

import comenzi.Comanda;

public class Client extends Persoana {
    Comanda comanda_efectuata;
    String numar_de_telefon;

    public Client(String nume, String adresa, Comanda comanda_efectuata, String numar_de_telefon) {
        super(nume, adresa);
        this.comanda_efectuata = comanda_efectuata;
        this.numar_de_telefon = numar_de_telefon;
    }

    public Client(String nume, String adresa, String numar_de_telefon) {
        super(nume, adresa);
        this.numar_de_telefon = numar_de_telefon;
    }

    public Comanda getComanda_efectuata() {
        return comanda_efectuata;
    }

    public String getNumar_de_telefon() {
        return numar_de_telefon;
    }

    public void setComanda_efectuata(Comanda comanda_efectuata) {
        this.comanda_efectuata = comanda_efectuata;
    }

    public void setNumar_de_telefon(String numar_de_telefon) {
        this.numar_de_telefon = numar_de_telefon;
    }
}
