package comenzi;

import administrare.Produs;
import persoana.Ospatar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ServiciuAdministrator {

    void adaugareProdus(ArrayList<Produs> meniu) throws IOException;

    void stergereProdus(ArrayList<Produs> meniu) throws IOException;

    void adaugareOspatar(List<Ospatar> ospatari) throws IOException;

    void concediereOspatar(List<Ospatar> ospatari) throws IOException;

    void afisareProfilOspatar(List<Ospatar> ospatari) throws IOException;
}
