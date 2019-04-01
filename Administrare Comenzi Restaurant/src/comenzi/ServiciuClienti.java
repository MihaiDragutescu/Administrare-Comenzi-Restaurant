package comenzi;

import administrare.Produs;
import persoana.Ospatar;

import java.util.List;

public interface ServiciuClienti {
    void efectuareComanda(Produs[] meniu, List<Ospatar> ospatari);
}
