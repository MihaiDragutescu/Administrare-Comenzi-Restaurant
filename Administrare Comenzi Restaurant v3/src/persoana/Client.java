package persoana;

import comenzi.Comanda;

public class Client extends Persoana {
    Comanda comandaEfectuata;
    String numarDeTelefon;

    public Client(String nume, String adresa, Comanda comanda_efectuata, String numar_de_telefon) {
        super(nume, adresa);
        this.comandaEfectuata = comanda_efectuata;
        this.numarDeTelefon = numar_de_telefon;
    }

    public Client(String nume, String adresa, String numar_de_telefon) {
        super(nume, adresa);
        this.numarDeTelefon = numar_de_telefon;
    }

    public Comanda getComanda_efectuata() {
        return comandaEfectuata;
    }

    public String getNumar_de_telefon() {
        return numarDeTelefon;
    }

    public void setComanda_efectuata(Comanda comanda_efectuata) {
        this.comandaEfectuata = comanda_efectuata;
    }

    public void setNumar_de_telefon(String numar_de_telefon) {
        this.numarDeTelefon = numar_de_telefon;
    }
}
