package administrare;

import comenzi.ServiciuAdministratorC;
import comenzi.ServiciuClientiComandaOnline;
import comenzi.ServiciuClientiComandaSimpla;
import persoana.Ospatar;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<Produs> meniu=new ArrayList<Produs>();
        String denumireProdus;
        Double pretProdus;

        //Se incarca meniul din fisier la pornirea programului
        ServiciuCitireFisier serviciuCitireFisier =new ServiciuCitireFisier("files/meniu.csv");
        List<String> data= serviciuCitireFisier.citireLinie();

        while (data!=null){
            denumireProdus=data.get(0);
            pretProdus=Double.valueOf(data.get(1));
            Produs produs=new Produs(denumireProdus,pretProdus);
            meniu.add(produs);

            data= serviciuCitireFisier.citireLinie();
        }

        ArrayList<Produs> meniu_sortat=new ArrayList<Produs>(meniu);
        Collections.sort(meniu_sortat);

        System.out.println("\nCel mai scump produs din meniu este: " + meniu_sortat.get(meniu_sortat.size() - 1).getDenumireProdus() + ", Pret: " + meniu_sortat.get(meniu_sortat.size() - 1).getPretProdus()+" lei");
        System.out.println("");

        System.out.println("Meniul restaurantului:");
        for (int i = 0; i < meniu.size(); i++) {
            System.out.println((i + 1) + ")Produs: " + meniu.get(i).getDenumireProdus() + ", Pret: " + meniu.get(i).getPretProdus()+" lei");
        }
        System.out.println("");

        //Se incarca lista de ospatari din fisier la pornirea programului
        List<Ospatar> ospatari = new ArrayList<>();
        String numeOspatar,adresaOspatar,CNP;
        int varstaOspatar,nrComenzi;

        ServiciuCitireFisier serviciuCitireFisier1 =new ServiciuCitireFisier("files/ospatari.csv");
        data= serviciuCitireFisier1.citireLinie();

        while (data!=null){
            numeOspatar=data.get(0);
            adresaOspatar=data.get(1);
            CNP=data.get(2);
            varstaOspatar=Integer.valueOf(data.get(3));
            nrComenzi=Integer.valueOf(data.get(4));
            Ospatar ospatar=new Ospatar(numeOspatar,adresaOspatar,CNP,varstaOspatar,nrComenzi);
            ospatari.add(ospatar);

            data= serviciuCitireFisier1.citireLinie();
        }

        ServiciuAudit serviciuAudit=new ServiciuAudit("files/serviciuAudit.csv");

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
                            serviciuAudit.scrie(TipuriActiuni.AdaugaProdus);
                            meniu = sa.getMeniu_actualizat();
                            break;
                        case 2:
                            sa.stergereProdus(meniu);
                            serviciuAudit.scrie(TipuriActiuni.StergeProdus);
                            meniu = sa.getMeniu_actualizat();
                            break;
                        case 3:
                            sa.adaugareOspatar(ospatari);
                            serviciuAudit.scrie(TipuriActiuni.AdaugaOspatar);
                            ospatari = sa.getOspatari_actualizati();
                            break;
                        case 4:
                            sa.concediereOspatar(ospatari);
                            serviciuAudit.scrie(TipuriActiuni.ConcediazaOspatar);
                            ospatari = sa.getOspatari_actualizati();
                            break;
                        case 5:
                            sa.afisareProfilOspatar(ospatari);
                            serviciuAudit.scrie(TipuriActiuni.AfiseazaProfilOspatar);
                            break;
                    }
                    System.out.println("Efectuati si alte actiuni?[y/n]");
                    continua = keyboard.next();

                } while (continua.equals("y"));

            } else if (tip_utilizator == 2) {

                String continua = "";

                System.out.println("Selectati numarul pentru tipul de comanda:");
                System.out.println("1)Comanda simpla.");
                System.out.println("2)Comanda online.");
                int option = keyboard.nextInt();

                switch (option) {
                    case 1:
                        ServiciuClientiComandaSimpla scs = new ServiciuClientiComandaSimpla();
                        scs.efectuareComanda(meniu, ospatari);
                        System.out.println("Costul total al comenzii: " + scs.getPret_total()+" lei");
                        System.out.println("");
                        ospatari = scs.getOspatari_actualizati();
                        serviciuAudit.scrie(TipuriActiuni.ComandaSimpla);
                        break;

                    case 2:
                        ServiciuClientiComandaOnline sco = new ServiciuClientiComandaOnline();
                        sco.efectuareComanda(meniu, ospatari);
                        System.out.println("Costul total al comenzii + taxa transport: " + sco.getPret_total()+" lei");
                        System.out.println("");
                        serviciuAudit.scrie(TipuriActiuni.ComandaOnline);
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