package comenzi;

import administrare.Produs;
import administrare.ServiciuCitireFisier;
import administrare.ServiciuScriereFisier;
import persoana.Ospatar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class ServiciuClientiComandaSimpla implements ServiciuClienti {
    double pret_total;
    List<Ospatar> ospatari_actualizati;

    public List<Ospatar> getOspatari_actualizati() {
        return ospatari_actualizati;
    }

    public double getPret_total() {
        return pret_total;
    }

    public void efectuareComanda(ArrayList<Produs> meniu, List<Ospatar> ospatari) throws IOException {

        Scanner keyboard = new Scanner(System.in);
        int option;
        String option2;

        System.out.println("");
        System.out.println("Selectati numarul preparatului dorit din meniu:");
        for (int i = 0; i < meniu.size(); i++) {
            System.out.println(i + 1 + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
        }

        List<Comanda> comenzi = new ArrayList<>();

        option = keyboard.nextInt();
        System.out.println("Numarul de portii: ");
        int nr_portii = keyboard.nextInt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Comanda comanda = new Comanda(meniu.get(option - 1), nr_portii, dateFormat.format(date));
        comenzi.add(comanda);
        pret_total += meniu.get(option - 1).getPretProdus() * comanda.getNumarPortii();

        System.out.println("Doriti sa mai comandati altceva?[y/n]");
        option2 = keyboard.next();

        if (option2.equals("n")) {
            System.out.println("Comanda finala:");
            for (int i = 0; i < comenzi.size(); i++) {
                Produs produs_temp = comenzi.get(i).getProdusComandat();
                System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
            }

            //Se selecteaza un ospatar ales la intamplare din lista pentru a prelua comanda
            int random_number = ThreadLocalRandom.current().nextInt(0, ospatari.size());
            Ospatar ospatar = ospatari.get(random_number);
            System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
            ospatari.get(random_number).setComenzi(comenzi);
            ospatari_actualizati = ospatari;

            //Se adauga in fisier comanda efectuata si pretul total
            List<String> linie = new ArrayList<>();
            for (int i = 0; i < comenzi.size(); i++) {
                linie.add(comenzi.get(i).getProdusComandat().getDenumireProdus() + "," + comenzi.get(i).getNumarPortii());
            }
            linie.add(ospatar.getNume());
            linie.add(String.valueOf(pret_total));

            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/comenziSimple.csv");
            serviciuScriereFisier.scrieLinie(linie);

            //Se citeste din fisierul cu ospatari numarul de comenzi preluate de ospatarul selectat pana in acel moment pentru a fi actualizat
            ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier("files/ospatari.csv");

            int nrLinie = 0;
            int nrComenzi = 0;

            while (nrLinie <= random_number) {
                List<String> linie1 = serviciuCitireFisier.citireLinie();
                nrComenzi = Integer.valueOf(linie1.get(4)) + 1;
                nrLinie++;
            }

            //Se actualizeaza numarul de comenzi preluate de catre ospatarul selectat
            ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/ospatari.csv");
            serviciuScriereFisier1.actualizeazaLinie(random_number, 4, String.valueOf(nrComenzi));

        } else {
            do {
                System.out.println("Selectati numarul preparatului dorit din meniu:");
                for (int i = 0; i < meniu.size(); i++) {
                    System.out.println(i + 1 + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
                }

                option = keyboard.nextInt();
                System.out.println("Numarul de portii: ");
                nr_portii = keyboard.nextInt();
                dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                Comanda comanda2 = new Comanda(meniu.get(option - 1), nr_portii, dateFormat.format(date));
                comenzi.add(comanda2);
                pret_total += meniu.get(option - 1).getPretProdus() * comanda2.getNumarPortii();

                System.out.println("Doriti sa mai comandati altceva?[y/n]");
                option2 = keyboard.next();

                if (option2.equals("n")) {
                    System.out.println("Comanda finala:");
                    for (int i = 0; i < comenzi.size(); i++) {
                        Produs produs_temp = comenzi.get(i).getProdusComandat();
                        System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
                    }

                    //Se selecteaza un ospatar ales la intamplare din lista pentru a prelua comanda
                    int random_number = ThreadLocalRandom.current().nextInt(0, ospatari.size());
                    Ospatar ospatar = ospatari.get(random_number);
                    System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
                    ospatari.get(random_number).setComenzi(comenzi);
                    int nrComenzi = ospatari.get(random_number).getNumarComenziPreluate() + 1;
                    ospatari.get(random_number).setNumarComenziPreluate(nrComenzi);
                    ospatari_actualizati = ospatari;

                    //Se adauga in fisier comanda efectuata si pretul total
                    List<String> linie = new ArrayList<>();
                    for (int i = 0; i < comenzi.size(); i++) {
                        linie.add(comenzi.get(i).getProdusComandat().getDenumireProdus() + "," + comenzi.get(i).getNumarPortii());
                    }
                    linie.add(ospatar.getNume());
                    linie.add(String.valueOf(pret_total));

                    ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/comenziSimple.csv");
                    serviciuScriereFisier.scrieLinie(linie);

                    //Se citeste din fisierul cu ospatari numarul de comenzi preluate de ospatarul selectat pana in acel moment pentru a fi actualizat
                    ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier("files/ospatari.csv");

                    int nrLinie = 0;
                    nrComenzi = 0;

                    while (nrLinie <= random_number) {
                        List<String> linie1 = serviciuCitireFisier.citireLinie();
                        nrComenzi = Integer.valueOf(linie1.get(4)) + 1;
                        nrLinie++;
                    }

                    //Se actualizeaza numarul de comenzi preluate de catre ospatarul selectat
                    ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/ospatari.csv");
                    serviciuScriereFisier1.actualizeazaLinie(random_number, 4, String.valueOf(nrComenzi));

                    break;
                }

            } while (option2.equals("y"));
        }
    }
}
