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

public class ServiciuClientiComandaOnline implements ServiciuClienti {

    double pret_total;

    public double getPret_total() {
        return pret_total;
    }

    public void efectuareComanda(Produs[] meniu, List<Ospatar> ospatari) {

        Scanner keyboard = new Scanner(System.in);
        int option;
        String option2;

        List<ComandaOnline> comenzi_online = new ArrayList<>();
        String nume_client;
        String adresa_client;
        String numar_telefon;

        int nr_portii;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("Introduceti datele de contact:");
        System.out.println("Nume: ");
        nume_client = keyboard.nextLine();

        System.out.println("Adresa: ");
        adresa_client = keyboard.nextLine();

        System.out.println("Numar de telefon: ");
        numar_telefon = keyboard.nextLine();

        Client client = new Client(nume_client, adresa_client, numar_telefon);

        System.out.println("");
        System.out.println("Selectati numarul preparatului dorit din meniu:");
        for (int i = 0; i < meniu.length; i++) {
            System.out.println(i + 1 + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus()+" lei");
        }

        option = keyboard.nextInt();
        System.out.println("Numarul de portii: ");
        nr_portii = keyboard.nextInt();
        dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date = new Date();

        ComandaOnline comanda_online = new ComandaOnline(meniu[option - 1], nr_portii, dateFormat.format(date), 2);
        comenzi_online.add(comanda_online);
        pret_total += meniu[option - 1].getPretProdus() * comanda_online.getNumarPortii();

        System.out.println("Doriti sa mai comandati altceva?[y/n]");
        option2 = keyboard.next();
        pret_total += comenzi_online.get(0).getTaxaTransport() * comanda_online.getNumarPortii();

        if (option2.equals("n")) {
            System.out.println("Comanda finala:");
            for (int i = 0; i < comenzi_online.size(); i++) {
                Produs produs_temp = comenzi_online.get(i).getProdusComandat();
                System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi_online.get(i).getNumarPortii());
            }

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

                ComandaOnline comanda2 = new ComandaOnline(meniu[option - 1], nr_portii, dateFormat.format(date), 2);
                comenzi_online.add(comanda2);
                pret_total += meniu[option - 1].getPretProdus() * comanda2.getNumarPortii();

                System.out.println("Doriti sa mai comandati altceva?[y/n]");
                option2 = keyboard.next();
                pret_total += comenzi_online.get(comenzi_online.size() - 1).getTaxaTransport() * comanda2.getNumarPortii();

                if (option2.equals("n")) {
                    System.out.println("Comanda finala:");
                    for (int i = 0; i < comenzi_online.size(); i++) {
                        Produs produs_temp = comenzi_online.get(i).getProdusComandat();
                        System.out.println("Produs: " + produs_temp.getDenumireProdus() + "; Numar portii: " + comenzi_online.get(i).getNumarPortii());
                    }
                    break;
                }

            } while (option2.equals("y"));
        }
    }
}

