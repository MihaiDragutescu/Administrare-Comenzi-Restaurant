package gui;

import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VeziComenziOnline extends Thread {
    private JFrame frame;
    private JPanel panel;
    private DefaultListModel<String> model;
    private JList<String> jList;
    private ServiciuBazaDeDate serviciuBazaDeDate;
    private String[] clientiArray;


    //Folosim un alt fir de executie pentru fereastra curenta
    public void run() {
        frame = new JFrame("Vezi comenzi online");
        frame.setSize(525, 550);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        try {
            initializareComponente(panel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frame.setVisible(true);

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.VeziComenziOnline);
    }

    public void initializareComponente(JPanel panel) throws SQLException {
        panel.setLayout(null);
        ArrayList<String> clienti = new ArrayList<>();

        serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
        ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.clienti");

        while (resultSet.next()) {
            String linie = "";
            linie = linie + resultSet.getString("Nume");
            clienti.add(linie);
        }

        clientiArray = clienti.toArray(new String[clienti.size()]);

        model = new DefaultListModel<>();

        for (int i = 0; i < clientiArray.length; i++) {
            String numeClient = clientiArray[i];
            model.addElement((i + 1) + ". " + numeClient);
        }

        //Cream lista cu clienti pe care o adaugam intr-un JScrollPane
        jList = new JList<>(model);
        jList.setSelectedIndex(0);
        jList.setFont(new Font("Arial", Font.PLAIN, 30));
        JScrollPane listScroller = new JScrollPane(jList);
        listScroller.setBounds(5, 5, 510, 350);
        panel.add(listScroller);

        JButton afiseazaComenzi = new JButton("Informatii");
        afiseazaComenzi.setFont(new Font("Arial", Font.PLAIN, 35));
        afiseazaComenzi.setBounds(50, 400, 175, 50);
        panel.add(afiseazaComenzi);

        JButton buttonInapoi = new JButton("Inapoi");
        buttonInapoi.setBounds(afiseazaComenzi.getX() + 250, 400, 150, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        informatiiComenzi(afiseazaComenzi);
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

    //Se afiseaza informatii legate de comenzile efectuate de catre clientul selectat
    public void informatiiComenzi(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        String detaliiComanda = "";
                        String linieLista = jList.getSelectedValue();
                        String[] numeClient = linieLista.split(" ");
                        String numeClient1 = numeClient[1] + " " + numeClient[2];
                        try {
                            ResultSet resultSet = serviciuBazaDeDate.afiseazaComenziClient(numeClient1);

                            while (resultSet.next()) {
                                detaliiComanda = detaliiComanda + "Comanda: " + resultSet.getString("Comanda") + " Data comenzii: " + resultSet.getDate("Data");
                            }
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }

                        JOptionPane.showMessageDialog(panel, detaliiComanda);
                    }
                }
        );
    }
}
