package persoana;

import comenzi.Comanda;

import java.util.List;

public class Ospatar extends Persoana {
    private List<Comanda> comenzi;
    private int numarComenziPreluate;
    private String CNP;
    private int varsta;

    public Ospatar(String nume, String adresa, List<Comanda> comenzi, int numarComenziPreluate, String CNP, int varsta) {
        super(nume, adresa);
        this.comenzi = comenzi;
        this.numarComenziPreluate = numarComenziPreluate;
        this.CNP = CNP;
        this.varsta = varsta;
    }

    public Ospatar(String nume, String adresa,String CNP, int varsta) {
        super(nume, adresa);
        this.CNP = CNP;
        this.varsta = varsta;
    }

    public String getCNP() {
        return CNP;
    }

    public List<Comanda> getComenzi() {
        return comenzi;
    }

    public int getNumarComenziPreluate() {
        return numarComenziPreluate;
    }

    public int getVarsta() {
        return varsta;
    }

    public void setComenzi(List<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public void setNumarComenziPreluate(int numarComenziPreluate) {
        this.numarComenziPreluate = numarComenziPreluate;
    }

    public void setVarsta(int varsta) {
        this.varsta = varsta;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}
