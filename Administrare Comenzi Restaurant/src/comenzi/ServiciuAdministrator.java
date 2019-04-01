package comenzi;

import administrare.Produs;
import persoana.Ospatar;

import java.util.List;

public interface ServiciuAdministrator {

    void adaugareProdus(Produs[] meniu);
    void stergereProdus(Produs[] meniu);

    void adaugareOspatar(List<Ospatar> ospatari);
    void concediereOspatar(List<Ospatar> ospatari);

    void afisareProfilOspatar(List<Ospatar> ospatari);
}
