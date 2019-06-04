package comenzi;

import administrare.Produs;
import administrare.ServiciuCitireFisier;
import administrare.ServiciuScriereFisier;
import persoana.Ospatar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ServiciuClientiComandaSimpla implements ServiciuClienti {
    private static ServiciuClientiComandaSimpla single_instance = null;
    double pretTotal;
    List<Ospatar> ospatariActualizati;

    public static ServiciuClientiComandaSimpla getInstance()
    {
        if (single_instance == null)
            single_instance = new ServiciuClientiComandaSimpla();

        return single_instance;
    }

    public List<Ospatar> getOspatariActualizati() {
        return ospatariActualizati;
    }

    public double getPretTotal() {
        return pretTotal;
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
        int nrPortii = keyboard.nextInt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        java.sql.Date sqlDate=new java.sql.Date(Calendar.getInstance().getTime().getTime());
        Comanda comanda = new Comanda(meniu.get(option - 1), nrPortii, sqlDate);
        comenzi.add(comanda);
        pretTotal += meniu.get(option - 1).getPretProdus() * comanda.getNumarPortii();

        System.out.println("Doriti sa mai comandati altceva?[y/n]");
        option2 = keyboard.next();

        if (option2.equals("n")) {
            System.out.println("Comanda finala:");
            for (int i = 0; i < comenzi.size(); i++) {
                Produs produsTemp = comenzi.get(i).getProdusComandat();
                System.out.println("Produs: " + produsTemp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
            }

            //Se selecteaza un ospatar ales la intamplare din lista pentru a prelua comanda
            int randomNumber = ThreadLocalRandom.current().nextInt(0, ospatari.size());
            Ospatar ospatar = ospatari.get(randomNumber);
            System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
            ospatari.get(randomNumber).setComenzi(comenzi);
            ospatariActualizati = ospatari;

            //Se adauga in fisier comanda efectuata si pretul total
            List<String> linie = new ArrayList<>();
            for (int i = 0; i < comenzi.size(); i++) {
                linie.add(comenzi.get(i).getProdusComandat().getDenumireProdus() + "," + comenzi.get(i).getNumarPortii());
            }
            linie.add(ospatar.getNume());
            linie.add(String.valueOf(pretTotal));

            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/comenziSimple.csv");
            serviciuScriereFisier.scrieLinie(linie);

            //Se citeste din fisierul cu ospatari numarul de comenzi preluate de ospatarul selectat pana in acel moment pentru a fi actualizat
            ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier("files/ospatari.csv");

            int nrLinie = 0;
            int nrComenzi = 0;

            while (nrLinie <= randomNumber) {
                List<String> linie1 = serviciuCitireFisier.citireLinie();
                nrComenzi = Integer.valueOf(linie1.get(4)) + 1;
                nrLinie++;
            }

            //Se actualizeaza numarul de comenzi preluate de catre ospatarul selectat
            ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/ospatari.csv");
            serviciuScriereFisier1.actualizeazaLinie(randomNumber, 4, String.valueOf(nrComenzi));

        } else {
            do {
                System.out.println("Selectati numarul preparatului dorit din meniu:");
                for (int i = 0; i < meniu.size(); i++) {
                    System.out.println(i + 1 + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
                }

                option = keyboard.nextInt();
                System.out.println("Numarul de portii: ");
                nrPortii = keyboard.nextInt();
               // sqlDate=new java.sql.Date(Calendar.getInstance().getTime().getTime());

                Comanda comanda2 = new Comanda(meniu.get(option - 1), nrPortii, sqlDate);
                comenzi.add(comanda2);
                pretTotal += meniu.get(option - 1).getPretProdus() * comanda2.getNumarPortii();

                System.out.println("Doriti sa mai comandati altceva?[y/n]");
                option2 = keyboard.next();

                if (option2.equals("n")) {
                    System.out.println("Comanda finala:");
                    for (int i = 0; i < comenzi.size(); i++) {
                        Produs produsTemp = comenzi.get(i).getProdusComandat();
                        System.out.println("Produs: " + produsTemp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
                    }

                    //Se selecteaza un ospatar ales la intamplare din lista pentru a prelua comanda
                    int randomNumber = ThreadLocalRandom.current().nextInt(0, ospatari.size());
                    Ospatar ospatar = ospatari.get(randomNumber);
                    System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
                    ospatari.get(randomNumber).setComenzi(comenzi);
                    int nrComenzi = ospatari.get(randomNumber).getNumarComenziPreluate() + 1;
                    ospatari.get(randomNumber).setNumarComenziPreluate(nrComenzi);
                    ospatariActualizati = ospatari;

                    //Se adauga in fisier comanda efectuata si pretul total
                    List<String> linie = new ArrayList<>();
                    for (int i = 0; i < comenzi.size(); i++) {
                        linie.add(comenzi.get(i).getProdusComandat().getDenumireProdus() + "," + comenzi.get(i).getNumarPortii());
                    }
                    linie.add(ospatar.getNume());
                    linie.add(String.valueOf(pretTotal));

                    ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/comenziSimple.csv");
                    serviciuScriereFisier.scrieLinie(linie);

                    //Se citeste din fisierul cu ospatari numarul de comenzi preluate de ospatarul selectat pana in acel moment pentru a fi actualizat
                    ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier("files/ospatari.csv");

                    int nrLinie = 0;
                    nrComenzi = 0;

                    while (nrLinie <= randomNumber) {
                        List<String> linie1 = serviciuCitireFisier.citireLinie();
                        nrComenzi = Integer.valueOf(linie1.get(4)) + 1;
                        nrLinie++;
                    }

                    //Se actualizeaza numarul de comenzi preluate de catre ospatarul selectat
                    ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/ospatari.csv");
                    serviciuScriereFisier1.actualizeazaLinie(randomNumber, 4, String.valueOf(nrComenzi));

                    break;
                }

            } while (option2.equals("y"));
        }
    }
}
