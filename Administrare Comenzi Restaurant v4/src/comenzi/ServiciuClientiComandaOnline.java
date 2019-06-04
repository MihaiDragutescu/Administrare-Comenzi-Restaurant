package comenzi;

import administrare.Produs;
import administrare.ServiciuScriereFisier;
import persoana.Client;
import persoana.Ospatar;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ServiciuClientiComandaOnline implements ServiciuClienti {
    private static ServiciuClientiComandaOnline single_instance = null;
    double pretTotal;

    public static ServiciuClientiComandaOnline getInstance()
    {
        if (single_instance == null)
            single_instance = new ServiciuClientiComandaOnline();

        return single_instance;
    }

    public double getPretTotal() {
        return pretTotal;
    }

    public void efectuareComanda(ArrayList<Produs> meniu, List<Ospatar> ospatari) throws IOException {

        Scanner keyboard = new Scanner(System.in);
        int option;
        String option2;

        List<ComandaOnline> comenziOnline = new ArrayList<>();
        String numeClient;
        String adresaClient;
        String numarTelefon;

        int nrPortii;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        System.out.println("Introduceti datele de contact:");
        System.out.println("Nume: ");
        numeClient = keyboard.nextLine();

        System.out.println("Adresa: ");
        adresaClient = keyboard.nextLine();

        System.out.println("Numar de telefon: ");
        numarTelefon = keyboard.nextLine();

        Client client = new Client(numeClient, adresaClient, numarTelefon);

        //Se adauga in fisier datele clientilor care au efectuat comenzi online
        List<String> linie = new ArrayList<>();
        linie.add(numeClient);
        linie.add(adresaClient);
        linie.add(numarTelefon);

        ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/clienti.csv");
        serviciuScriereFisier.scrieLinie(linie);

        System.out.println("");
        System.out.println("Selectati numarul preparatului dorit din meniu:");
        for (int i = 0; i < meniu.size(); i++) {
            System.out.println(i + 1 + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus() + " lei");
        }

        option = keyboard.nextInt();
        System.out.println("Numarul de portii: ");
        nrPortii = keyboard.nextInt();
        java.sql.Date sqlDate=new java.sql.Date(Calendar.getInstance().getTime().getTime());

        ComandaOnline comandaOnline = new ComandaOnline(meniu.get(option - 1), nrPortii,sqlDate, 2);
        comenziOnline.add(comandaOnline);
        pretTotal += meniu.get(option - 1).getPretProdus() * comandaOnline.getNumarPortii();

        System.out.println("Doriti sa mai comandati altceva?[y/n]");
        option2 = keyboard.next();
        pretTotal += comenziOnline.get(0).getTaxaTransport() * comandaOnline.getNumarPortii();

        if (option2.equals("n")) {
            System.out.println("Comanda finala:");
            for (int i = 0; i < comenziOnline.size(); i++) {
                Produs produsTemp = comenziOnline.get(i).getProdusComandat();
                System.out.println("Produs: " + produsTemp.getDenumireProdus() + "; Numar portii: " + comenziOnline.get(i).getNumarPortii());
            }

            //Se adauga in fisier comanda efectuata, pretul total si numele clientului
            List<String> linie1 = new ArrayList<>();

            for (int i = 0; i < comenziOnline.size(); i++) {
                linie1.add(comenziOnline.get(i).getProdusComandat().getDenumireProdus());
                linie1.add(String.valueOf(comenziOnline.get(i).getNumarPortii()));
            }

            linie1.add(String.valueOf(pretTotal));
            linie1.add(numeClient);

            ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/comenziOnline.csv");
            serviciuScriereFisier1.scrieLinie(linie1);

        } else {
            do {
                System.out.println("Selectati numarul preparatului dorit din meniu:");
                for (int i = 0; i < meniu.size(); i++) {
                    System.out.println(i + 1 + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
                }

                option = keyboard.nextInt();
                System.out.println("Numarul de portii: ");
                nrPortii = keyboard.nextInt();
                //sqlDate=new java.sql.Date(Calendar.getInstance().getTime().getTime());

                ComandaOnline comanda2 = new ComandaOnline(meniu.get(option - 1), nrPortii, sqlDate, 2);
                comenziOnline.add(comanda2);
                pretTotal += meniu.get(option - 1).getPretProdus() * comanda2.getNumarPortii();

                System.out.println("Doriti sa mai comandati altceva?[y/n]");
                option2 = keyboard.next();
                pretTotal += comenziOnline.get(comenziOnline.size() - 1).getTaxaTransport() * comanda2.getNumarPortii();

                if (option2.equals("n")) {
                    System.out.println("Comanda finala:");
                    for (int i = 0; i < comenziOnline.size(); i++) {
                        Produs produsTemp = comenziOnline.get(i).getProdusComandat();
                        System.out.println("Produs: " + produsTemp.getDenumireProdus() + "; Numar portii: " + comenziOnline.get(i).getNumarPortii());
                    }

                    //Se adauga in fisier comanda efectuata, pretul total si numele clientului
                    linie = new ArrayList<>();

                    for (int i = 0; i < comenziOnline.size(); i++) {
                        linie.add(comenziOnline.get(i).getProdusComandat().getDenumireProdus());
                        linie.add(String.valueOf(comenziOnline.get(i).getNumarPortii()));
                    }

                    linie.add(String.valueOf(pretTotal));
                    linie.add(numeClient);

                    ServiciuScriereFisier serviciuScriereFisier1 = new ServiciuScriereFisier("files/comenziOnline.csv");
                    serviciuScriereFisier1.scrieLinie(linie);

                    break;
                }

            } while (option2.equals("y"));
        }
    }
}

