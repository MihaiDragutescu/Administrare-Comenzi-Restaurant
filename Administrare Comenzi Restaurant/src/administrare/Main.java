package administrare;

import comenzi.ServiciuAdministratorC;
import comenzi.ServiciuClientiComandaOnline;
import comenzi.ServiciuClientiComandaSimpla;
import persoana.Ospatar;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        Produs[] meniu = new Produs[]{new Produs("Frigarui", 30),
                new Produs("Paste", 15),
                new Produs("Pizza Quatro Stagioni", 23)};
        Arrays.sort(meniu, new SortareProduse());

        System.out.println("Cel mai scump produs din meniu este: " + meniu[meniu.length - 1].getDenumireProdus() + ", Pret: " + meniu[meniu.length - 1].getPretProdus()+" lei");
        System.out.println("");

        System.out.println("Meniul restaurantului:");
        for (int i = 0; i < meniu.length; i++) {
            System.out.println((i + 1) + ")Produs: " + meniu[i].getDenumireProdus() + ", Pret: " + meniu[i].getPretProdus()+" lei");
        }
        System.out.println("");

        List<Ospatar> ospatari = new ArrayList<>();
        ospatari.add(new Ospatar("Popescu Ion", "Strada Regina Maria", "1851021345131", 37));
        ospatari.add(new Ospatar("Ionescu Vasilica", "Strada Mihai Viteazu", "1920617149053", 29));
        ospatari.add(new Ospatar("Marinescu Alexandra", "Strada Carol I", "1920617141325", 42));

        int tip_utilizator = 0;
        do {

            System.out.println("Selectati numarul pentru tipul de utilizator:");
            System.out.println("1)Administrator");
            System.out.println("2)Client");
            System.out.println("3)Iesi");

            Scanner keyboard = new Scanner(System.in);
            tip_utilizator = keyboard.nextInt();

            if (tip_utilizator == 1) {

                String continua = "";

                do {
                    int tip_actiune;
                    ServiciuAdministratorC sa = new ServiciuAdministratorC();

                    System.out.println("Selectati numarul pentru tipul de actiune dorita: ");
                    System.out.println("1)Adauga produs");
                    System.out.println("2)Sterge produs");
                    System.out.println("3)Adauga ospatar");
                    System.out.println("4)Concediaza ospatar");
                    System.out.println("5)Profil ospatari");
                    System.out.println("6)Iesire");

                    tip_actiune = keyboard.nextInt();

                    if (tip_actiune == 6)
                        break;

                    switch (tip_actiune) {
                        case 1:
                            sa.adaugareProdus(meniu);
                            meniu = sa.getMeniu_actualizat();
                            break;
                        case 2:
                            sa.stergereProdus(meniu);
                            meniu = sa.getMeniu_actualizat();
                            break;
                        case 3:
                            sa.adaugareOspatar(ospatari);
                            ospatari = sa.getOspatari_actualizati();
                            break;
                        case 4:
                            sa.concediereOspatar(ospatari);
                            ospatari = sa.getOspatari_actualizati();
                            break;
                        case 5:
                            sa.afisareProfilOspatar(ospatari);
                            break;
                    }
                    System.out.println("Efectuati si alte actiuni?[y/n]");
                    continua = keyboard.next();

                } while (continua.equals("y"));

            } else if (tip_utilizator == 2) {

                String continua = "";

                System.out.println("Selectati numarul pentru tipul de comanda:");
                System.out.println("1)Comanda simpla.");
                System.out.println("2)Comanda speciala.(neimplementat)");
                System.out.println("3)Comanda online.");
                int option = keyboard.nextInt();

                switch (option) {
                    case 1:
                        ServiciuClientiComandaSimpla scs = new ServiciuClientiComandaSimpla();
                        scs.efectuareComanda(meniu, ospatari);
                        System.out.println("Costul total al comenzii: " + scs.getPret_total()+" lei");
                        System.out.println("");
                        ospatari = scs.getOspatari_actualizati();
                        break;

                    case 2:
                        break;

                    case 3:
                        ServiciuClientiComandaOnline sco = new ServiciuClientiComandaOnline();
                        sco.efectuareComanda(meniu, ospatari);
                        System.out.println("Costul total al comenzii + taxa transport: " + sco.getPret_total()+" lei");
                        System.out.println("");
                        break;
                }

            } else if (tip_utilizator == 3) {
                continue;
            } else {
                System.out.println("Optiunea selectata nu e valida.");
            }

        } while (tip_utilizator != 3);
    }
}