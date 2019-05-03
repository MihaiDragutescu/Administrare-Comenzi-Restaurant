package administrare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServiciuScriereFisier {
    private String path;

    public ServiciuScriereFisier(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //Metoda care adauga o linie de text la finalul fisierului
    public void scrieLinie(List<String> linie) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));

        for (int i = 0; i < linie.size(); i++) {

            if (i < linie.size() - 1) {
                bufferedWriter.write(linie.get(i) + ",");
            } else {
                bufferedWriter.write(linie.get(i));
            }
        }
        bufferedWriter.write("\n");

        bufferedWriter.flush();
        bufferedWriter.close();
    }

    //Metoda care adauga mai multe linii de text la finalul fisierului
    private void scrieLinii(List<List<String>> linii) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path));
        for (List<String> rand : linii) {
            for (int i = 0; i < rand.size(); i++) {
                if (i < rand.size() - 1) {
                    bufferedWriter.write(rand.get(i) + ",");
                } else {
                    bufferedWriter.write(rand.get(i));
                }
            }
            bufferedWriter.write("\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    //Metoda care sterge o linie in fisier rescriindu-le pe toate mai putin pe cea cu index-ul i
    public void StergeLinie(int i) throws IOException {
        ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier(path);
        int linieCurenta = 0;

        List<String> linie;
        List<List<String>> linii = new ArrayList<>();

        while ((linie = serviciuCitireFisier.citireLinie()) != null) {
            if (linieCurenta != i) {
                linii.add(linie);
            }
            linieCurenta++;
        }

        scrieLinii(linii);
    }

    /*Metoda care actualizeaza o linie in fisier. Se citesc toate datele din fisier, iar atunci cand intalnim
     linia ce dorim sa fie actualizata modificam campul dorit dupa indexCuvant cu cuvantActualizat*/
    public void actualizeazaLinie(int indexLinie, int indexCuvant, String cuvantActualizat) throws IOException {
        ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier(path);
        int linieCurenta = 0;

        List<String> linie;
        List<List<String>> linii = new ArrayList<>();

        while ((linie = serviciuCitireFisier.citireLinie()) != null) {
            if (linieCurenta == indexLinie) {
                linie.set(indexCuvant, cuvantActualizat);

            }
            linii.add(linie);
            linieCurenta++;
        }

        scrieLinii(linii);
    }
}
