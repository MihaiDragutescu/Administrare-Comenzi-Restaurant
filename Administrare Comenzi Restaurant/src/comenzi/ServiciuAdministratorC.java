package comenzi;

import administrare.Produs;
import persoana.Ospatar;

import java.util.*;

public class ServiciuAdministratorC implements ServiciuAdministrator {
    Produs[] meniu_actualizat;
    List<Ospatar> ospatari_actualizati;

    @Override
    public void afisareProfilOspatar(List<Ospatar> ospatari) {

        for (int i = 0; i < ospatari.size(); i++) {
            System.out.println("Profilul ospatarului " + ospatari.get(i).getNume());
            System.out.println("Adresa: " + ospatari.get(i).getAdresa());
            System.out.println("CNP: " + ospatari.get(i).getCNP());
            System.out.println("Numar total de comenzi preluate astazi: " + ospatari.get(i).getNumarComenziPreluate());
            System.out.println("");

        }
    }

    public Produs[] getMeniu_actualizat() {
        return meniu_actualizat;
    }

    public List<Ospatar> getOspatari_actualizati() {
        return ospatari_actualizati;
    }

    @Override
    public void adaugareProdus(Produs[] meniu) {
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

            meniu = Arrays.copyOf(meniu, meniu.length + 1);
            meniu[meniu.length - 1] = new Produs(denumire, pret);

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.length; i++) {
                System.out.println((i + 1) + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus());
            }
            System.out.println("");

            meniu_actualizat = Arrays.copyOf(meniu, meniu.length);

            System.out.println("Doriti sa mai adaugati si alte produse?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }

    @Override
    public void stergereProdus(Produs[] meniu) {

        int numar_produs;
        String continua = "";

        do {
            int k = -1;

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.length; i++) {
                System.out.println((i + 1) + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus());
            }
            System.out.println("");

            System.out.println("Introduceti numarul produsului pe care vreti sa il elminati: ");

            Scanner keyboard = new Scanner(System.in);
            numar_produs = keyboard.nextInt();

            Produs[] meniu_nou = new Produs[meniu.length - 1];

            for (int j = 0; j < meniu.length; j++) {

                if (j != numar_produs - 1) {
                    meniu_nou[++k] = meniu[j];
                }
            }
            meniu = Arrays.copyOf(meniu_nou, meniu_nou.length);

            System.out.println("Meniul restaurantului:");
            for (int i = 0; i < meniu.length; i++) {
                System.out.println((i + 1) + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus());
            }
            System.out.println("");

            meniu_actualizat = Arrays.copyOf(meniu_nou, meniu_nou.length);

            System.out.println("Doriti sa mai stergeti si alt produs?[y/n]");
            continua = keyboard.next();

        } while (continua.equals("y"));

    }

    @Override
    public void adaugareOspatar(List<Ospatar> ospatari) {

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

            Ospatar ospatar = new Ospatar(nume, adresa, CNP, varsta);
            ospatari.add(ospatar);

            System.out.println("Lista cu ospatarii restaurantului:");
            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta());
            }
            System.out.println("");

            ospatari_actualizati = new ArrayList<>(ospatari);

            System.out.println("Doriti sa mai adaugati si alti angajati?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }

    @Override
    public void concediereOspatar(List<Ospatar> ospatari) {

        String nume_ospatar;
        String continua = "";

        Scanner keyboard = new Scanner(System.in);

        do {
            System.out.println("Lista cu ospatarii restaurantului:");

            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta());
            }
            System.out.println("");

            System.out.println("Introduceti numele ospatarului pe care il concediati:");
            nume_ospatar = keyboard.nextLine();

            for (int i = 0; i < ospatari.size(); i++) {
                if (ospatari.get(i).getNume().equals(nume_ospatar)) {
                    ospatari.remove(ospatari.get(i));
                    break;
                }
            }

            for (int i = 0; i < ospatari.size(); i++) {
                System.out.println((i + 1) + ")Nume: " + ospatari.get(i).getNume() + ", Adresa: " + ospatari.get(i).getAdresa() + ", CNP: " + ospatari.get(i).getCNP() + ", varsta: " + ospatari.get(i).getVarsta());
            }
            System.out.println("");

            ospatari_actualizati = new ArrayList<>(ospatari);

            System.out.println("Doriti sa mai concediati alti angajati?[y/n]");
            continua = keyboard.next();
            keyboard.nextLine();

        } while (continua.equals("y"));

    }
};
