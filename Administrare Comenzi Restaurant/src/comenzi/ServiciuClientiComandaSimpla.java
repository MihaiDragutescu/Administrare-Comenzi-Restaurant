package comenzi;

import administrare.Produs;
import persoana.Client;
import persoana.Ospatar;

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

    public void efectuareComanda(Produs[] meniu, List<Ospatar> ospatari) {

        Scanner keyboard = new Scanner(System.in);
        int option;
        String option2;

        System.out.println("");
        System.out.println("Selectati numarul preparatului dorit din meniu:");
        for (int i = 0; i < meniu.length; i++) {
            System.out.println(i + 1 + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus());
        }

        List<Comanda> comenzi = new ArrayList<>();

        option = keyboard.nextInt();
        System.out.println("Numarul de portii: ");
        int nr_portii = keyboard.nextInt();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Comanda comanda = new Comanda(meniu[option - 1], nr_portii, dateFormat.format(date));
        comenzi.add(comanda);
        pret_total += meniu[option - 1].getPretProdus() * comanda.getNumarPortii();

        System.out.println("Doriti sa mai comandati altceva?[y/n]");
        option2 = keyboard.next();

        if (option2.equals("n")) {
            System.out.println("Comanda finala:");
            for (int i = 0; i < comenzi.size(); i++) {
                Produs produs_temp = comenzi.get(i).getProdusComandat();
                System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
            }

            int random_number = ThreadLocalRandom.current().nextInt(0, ospatari.size());
            Ospatar ospatar = ospatari.get(random_number);
            System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
            ospatari.get(random_number).setComenzi(comenzi);
            ospatari.get(random_number).setNumarComenziPreluate(ospatari.get(random_number).getNumarComenziPreluate() + 1);
            ospatari_actualizati = ospatari;

        } else {
            do {
                System.out.println("Selectati numarul preparatului dorit din meniu:");
                for (int i = 0; i < meniu.length; i++) {
                    System.out.println(i + 1 + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus());
                }

                option = keyboard.nextInt();
                System.out.println("Numarul de portii: ");
                nr_portii = keyboard.nextInt();
                dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                Comanda comanda2 = new Comanda(meniu[option - 1], nr_portii, dateFormat.format(date));
                comenzi.add(comanda2);
                pret_total += meniu[option - 1].getPretProdus() * comanda2.getNumarPortii();

                System.out.println("Doriti sa mai comandati altceva?[y/n]");
                option2 = keyboard.next();

                if (option2.equals("n")) {
                    System.out.println("Comanda finala:");
                    for (int i = 0; i < comenzi.size(); i++) {
                        Produs produs_temp = comenzi.get(i).getProdusComandat();
                        System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi.get(i).getNumarPortii());
                    }

                    int random_number = ThreadLocalRandom.current().nextInt(0, ospatari.size());
                    Ospatar ospatar = ospatari.get(random_number);
                    System.out.println("Comanda a fost preluata de ospatarul " + ospatar.getNume());
                    ospatari.get(random_number).setComenzi(comenzi);
                    ospatari.get(random_number).setNumarComenziPreluate(ospatari.get(random_number).getNumarComenziPreluate() + 1);
                    ospatari_actualizati = ospatari;

                    break;
                }

            } while (option2.equals("y"));
        }
    }
}
