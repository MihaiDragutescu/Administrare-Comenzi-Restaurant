package comenzi;

import administrare.Produs;
import administrare.ServiciuCitireFisier;
import administrare.ServiciuScriereFisier;
import persoana.Ospatar;

import java.io.IOException;
import java.util.*;

public class ServiciuAdministratorC implements ServiciuAdministrator {
    private static ServiciuAdministratorC single_instance = null;
    ArrayList<Produs> meniuActualizat;
    List<Ospatar> ospatariActualizati;

    public static ServiciuAdministratorC getInstance()
    {
        if (single_instance == null)
            single_instance = new ServiciuAdministratorC();

        return single_instance;
    }

    @Override
    public void afisareProfilOspatar(List<Ospatar> ospatari) throws IOException {

        for (int i = 0; i < ospatari.size(); i++) {
            System.out.println("Profilul ospatarului " + ospatari.get(i).getNume());
            System.out.println("Adresa: " + ospatari.get(i).getAdresa());
            System.out.println("CNP: " + ospatari.get(i).getCNP());

            //Se citeste din fisier numarul de comenzi preluate petru fiecare ospatar
            ServiciuCitireFisier serviciuCitireFisier = new ServiciuCitireFisier("files/ospatari.csv");
            int linieCurenta=0;

            List<String> linie=new ArrayList<>();
            while (linieCurenta-1<i){
                linie=serviciuCitireFisier.citireLinie();
                linieCurenta++;
            }

            System.out.println("Numar total de comenzi preluate astazi: " + linie.get(4));
            System.out.println("");

        }
    }

    //Metoda care returneaza meniul actualizat dupa actiuni precum adaugarea sau stergerea unui produs
    public ArrayList<Produs> getMeniuActualizat() {
        return meniuActualizat;
    }

    //Metoda care returneaza lista actualizata cu ospatari dupa actiuni precum adaugarea sau concedierea unui angajat
    public List<Ospatar> getOspatariActualizati() {
        return ospatariActualizati;
    }

    @Override
    public void adaugareProdus(ArrayList<Produs> meniu) throws IOException {
        String denumire;
        String continua;
        double pret;
        Scanner keyboard = new Scanner(System.in);

        do {

            System.out.println("Introduceti denumirea noului produs: ");
            denumire = keyboard.nextLine();

            System.out.println("Introduceti pretul noului produs: ");
            pret = keyboard.nextDouble();
            keyboard.nextLine();

            Produs produs = new Produs(denumire, pret);
            meniu.add(produs);

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.size(); i++) {
                System.out.println((i + 1) + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
            }
            System.out.println("");

            //Se adauga noul produs in fisierul ce contine meniul
            List<String> linie = new ArrayList<>();
            linie.add(denumire);
            linie.add(String.valueOf(pret));

            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/meniu.csv");
            serviciuScriereFisier.scrieLinie(linie);

            meniuActualizat = new ArrayList<Produs>(meniu);

            System.out.println("Doriti sa mai adaugati si alte produse?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }

    @Override
    public void stergereProdus(ArrayList<Produs> meniu) throws IOException {

        int numarProdus;
        String continua = "";

        do {
            int k = -1;

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.size(); i++) {
                System.out.println((i + 1) + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
            }
            System.out.println("");

            System.out.println("Introduceti numarul produsului pe care vreti sa il elminati: ");

            Scanner keyboard = new Scanner(System.in);
            numarProdus = keyboard.nextInt();

            meniu.remove(numarProdus - 1);

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.size(); i++) {
                System.out.println((i + 1) + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus());
            }
            System.out.println("");

            //Se sterge produsul din fisierul ce contine meniul dupa index-ul introdus de utilizator
            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/meniu.csv");
            serviciuScriereFisier.StergeLinie(numarProdus - 1);

            meniuActualizat = new ArrayList<Produs>(meniu);

            System.out.println("Doriti sa mai stergeti si alt produs?[y/n]");
            continua = keyboard.next();

        } while (continua.equals("y"));

    }

    @Override
    public void adaugareOspatar(List<Ospatar> ospatari) throws IOException {

        String CNP;
        int varsta;
        String nume;
        String adresa;
        String continua;

        Scanner keyboard = new Scanner(System.in);

        do {

            System.out.println("Introduceti numele noului angajat: ");
            nume = keyboard.nextLine();

            System.out.println("Introduceti CNP-ul noului angajat: ");
            CNP = keyboard.nextLine();

            System.out.println("Introduceti adresa noului angajat: ");
            adresa = keyboard.nextLine();

            System.out.println("Introduceti varsta noului angajat: ");
            varsta = keyboard.nextInt();
            keyboard.nextLine();

            Ospatar ospatar = new Ospatar(nume, adresa, CNP, varsta, 0);
            ospatari.add(ospatar);

            System.out.println("Lista cu ospatarii restaurantului:");
            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta());
            }
            System.out.println("");

            //Se adauga noul angajat in fisierul ce contine lista cu ospatari
            List<String> linie = new ArrayList<>();
            linie.add(nume);
            linie.add(adresa);
            linie.add(CNP);
            linie.add(String.valueOf(varsta));
            linie.add(String.valueOf(0));

            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/ospatari.csv");
            serviciuScriereFisier.scrieLinie(linie);

            ospatariActualizati = new ArrayList<>(ospatari);

            System.out.println("Doriti sa mai adaugati si alti angajati?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }

    @Override
    public void concediereOspatar(List<Ospatar> ospatari) throws IOException {

        String numeOspatar;
        String continua = "";

        Scanner keyboard = new Scanner(System.in);

        do {
            System.out.println("Lista cu ospatarii restaurantului:");

            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta() + ", numar comenzi preluate: " + ospatari.get(i).getNumarComenziPreluate());
            }
            System.out.println("");

            System.out.println("Introduceti numele ospatarului pe care il concediati:");
            numeOspatar = keyboard.nextLine();

            /*Se sterge din lista ospatarul care corespunde numelui introdus de utilizator si se identifica
             numarul de ordine al acestuia pentru a putea fi sters din fisierul cu ospatari dupa index*/
            int numarLinie = 0;
            for (int i = 0; i < ospatari.size(); i++) {
                if (ospatari.get(i).getNume().equals(numeOspatar)) {
                    ospatari.remove(ospatari.get(i));
                    break;
                }
                numarLinie++;
            }

            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta() + ", numar comenzi preluate: " + ospatari.get(i).getNumarComenziPreluate());
            }
            System.out.println("");

            //Se sterge angajatul din fisierul ce contine lista cu ospatari
            ServiciuScriereFisier serviciuScriereFisier = new ServiciuScriereFisier("files/ospatari.csv");
            serviciuScriereFisier.StergeLinie(numarLinie);

            ospatariActualizati = new ArrayList<>(ospatari);

            System.out.println("Doriti sa mai concediati alti angajati?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }
};
