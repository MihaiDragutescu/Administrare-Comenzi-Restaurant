package comenzi;

import administrare.Produs;
import persoana.Ospatar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public interface ServiciuClienti {
    void efectuareComanda(ArrayList<Produs> meniu, List<Ospatar> ospatari) throws IOException;
}
