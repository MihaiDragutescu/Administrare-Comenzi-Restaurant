package gui;

import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;
import persoana.Ospatar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AdaugaOspatar {
    private JFrame frame;
    private JPanel panel;
    JTextField[] textFields = new JTextField[4];


    public void creare() {
        frame = new JFrame("Adauga Ospatar");
        frame.setSize(500, 800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.AdaugaOspatar);
    }

    public void initializareComponente(JPanel panel) {
        panel.setLayout(null);

        JLabel dateAngajat = new JLabel("Date angajat");
        dateAngajat.setBounds(135, 50, 300, 100);
        dateAngajat.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel[] labels = new JLabel[4];

        for (int i = 0; i < 4; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(20, (i + 3) * 50 + 50 * i, 150, 100);
            labels[i].setFont(new Font("Arial", Font.PLAIN, 30));
        }

        labels[0].setText("Nume:");
        labels[1].setText("Adresa:");
        labels[2].setText("CNP:");
        labels[3].setText("Varsta:");

        for (int i = 0; i < 4; i++) {
            panel.add(labels[i]);
        }

        for (int i = 0; i < 4; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds(150, labels[i].getY() + 30, 300, 50);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 30));
            panel.add(textFields[i]);
        }

        panel.add(dateAngajat);

        JButton buttonSalveaza = new JButton("Salveaza");
        JButton buttonInapoi = new JButton("Inapoi");

        buttonSalveaza.setBounds(30, labels[3].getY() + 150, 175, 50);
        buttonSalveaza.setFont(new Font("Arial", Font.PLAIN, 30));

        buttonInapoi.setBounds(buttonSalveaza.getX() + 250, labels[3].getY() + 150, 175, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 30));

        panel.add(buttonSalveaza);
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        salveazaAngajat(buttonSalveaza);
    }

    //Redirectionare la meniul principal la apasarea butonului
    public void inapoiLaMeniu(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        Administrator administrator = new Administrator();
                        administrator.creare();
                    }
                }
        );
    }

    //Se insereaza un angajat nou in tabela "ospatari" din baza de date
    public void salveazaAngajat(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Ospatar ospatar;
                        try {
                            ospatar = new Ospatar(textFields[0].getText(), textFields[1].getText(), textFields[2].getText(), Integer.valueOf(textFields[3].getText()), 0);
                            ServiciuBazaDeDate serviciuBazaDeDate = new ServiciuBazaDeDate("jdbc:mysql://localhost:3306/restaurant?autoReconnect=true", "root", "root");
                            serviciuBazaDeDate.inserareAngajat(ospatar.getNume(), ospatar.getAdresa(), ospatar.getCNP(), ospatar.getVarsta(), ospatar.getNumarComenziPreluate());

                            JOptionPane.showMessageDialog(panel, "Angajatul a fost adaugat cu succes.");
                        } catch (NumberFormatException e1) {
                            JOptionPane.showMessageDialog(panel, "Completarea campurilor este obligatorie!");
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
    }
}
