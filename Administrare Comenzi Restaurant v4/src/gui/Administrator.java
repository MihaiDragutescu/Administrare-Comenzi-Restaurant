package gui;

import administrare.ServiciuAudit;
import administrare.TipuriActiuni;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;
import comenzi.ServiciuAdministratorC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Administrator {
    private JFrame frame;
    private JButton[] buttons = new JButton[6];
    private ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");

    public void creare() {
        frame = new JFrame("Administrator");
        frame.setSize(500, 935);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);
    }

    public void initializareComponente(JPanel panel) {
        panel.setLayout(null);

        for (int i = 0; i < 6; i++) {
            buttons[i] = new JButton();
            buttons[i].setBounds(75, 42 * (i + 1) + 100 * i, 350, 100);
            buttons[i].setFont(new Font("Aria", Font.PLAIN, 35));
        }

        buttons[0].setText("Adauga produs");
        buttons[1].setText("Sterge produs");
        buttons[2].setText("Adauga ospatar");
        buttons[3].setText("Concediaza ospatar");
        buttons[4].setText("Profil angajati");
        buttons[5].setText("Vezi comenzi online");

        for (int i = 0; i < 6; i++) {
            panel.add(buttons[i]);
        }

        adaugaProdus(buttons[0]);
        stergeProdus(buttons[1]);
        adaugaOspatar(buttons[2]);
        stergeOspatar(buttons[3]);
        profilOspatari(buttons[4]);
        veziComenziOnline(buttons[5]);

    }

    //Redirectionare catre fereastra de adaugare a noi produse la apasarea butonului
    public void adaugaProdus(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        AdaugaProdus adaugaProdus = new AdaugaProdus();
                        try {
                            adaugaProdus.creare();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
    }

    //Redirectionare catre fereastra de adaugare a noi angajati la apasarea butonului
    public void adaugaOspatar(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        AdaugaOspatar adaugaOspatar = new AdaugaOspatar();
                        adaugaOspatar.creare();
                    }
                }
        );
    }

    //Redirectionare catre fereastra de stergere a angajatilor la apasarea butonului
    public void stergeOspatar(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        StergeOspatar stergeOspatar = null;
                        try {
                            stergeOspatar = new StergeOspatar();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        stergeOspatar.creare();
                    }
                }
        );
    }

    //Redirectionare catre fereastra de stergere a produselor la apasarea butonului
    public void stergeProdus(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        frame.dispose();
                        StergeProdus stergeProdus = null;
                        try {
                            stergeProdus = new StergeProdus();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            stergeProdus.creare();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
    }

    //Redirectionare catre fereastra in care putem vizualiza informatii despre angajati
    public void profilOspatari(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        ProfilAngajati profilAngajati = new ProfilAngajati();
                        profilAngajati.creare();

                    }
                }
        );
    }

    //Redirectionare catre fereastra in care putem vizualiza comenzile online pe care le-au efectuat anumiti clienti
    public void veziComenziOnline(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        VeziComenziOnline veziComenziOnline = new VeziComenziOnline();
                        veziComenziOnline.start();
                    }
                }
        );
    }
}
