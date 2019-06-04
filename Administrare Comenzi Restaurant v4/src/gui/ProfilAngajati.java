package gui;

import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class ProfilAngajati {
    private JFrame frame;
    private JPanel panel;
    private DefaultListModel<String> model;
    private JList<String> jList;
    private ServiciuBazaDeDate serviciuBazaDeDate;
    private String[] ospatariArray;
    private int indexAngajat;

    public void creare() {
        frame = new JFrame("Profil angajati");
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
        serviciuAudit.scrie(TipuriActiuni.ProfilAngajati);
    }

    public void initializareComponente(JPanel panel) throws SQLException {
        panel.setLayout(null);
        ArrayList<String> ospatari = new ArrayList<>();

        serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
        ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.ospatari");

        while (resultSet.next()) {
            String linie = "";
            linie = linie + "Nume: " + resultSet.getString("Nume") + "; Adresa: " + resultSet.getString("Adresa") + "; CNP: " + resultSet.getString("CNP") + "; Varsta: " + resultSet.getInt("Varsta") + "; Numar comenzi: " + resultSet.getInt("Comenzi");
            ospatari.add(linie);
        }

        ospatariArray = ospatari.toArray(new String[ospatari.size()]);

        model = new DefaultListModel<>();

        for (int i = 0; i < ospatariArray.length; i++) {
            String[] informatiiAngajat = ospatariArray[i].split(";");
            String numeAngajat = informatiiAngajat[0];
            model.addElement((i + 1) + ". " + numeAngajat);
        }

        //Cream lista cu angajati pe care o adaugam intr-un JScrollPane
        jList = new JList<>(model);
        jList.setSelectedIndex(0);
        jList.setFont(new Font("Arial", Font.PLAIN, 30));
        JScrollPane listScroller = new JScrollPane(jList);
        listScroller.setBounds(5, 5, 510, 350);
        panel.add(listScroller);

        JButton detaliiAngajat = new JButton("Informatii");
        detaliiAngajat.setFont(new Font("Arial", Font.PLAIN, 35));
        detaliiAngajat.setBounds(50, 400, 175, 50);
        panel.add(detaliiAngajat);

        JButton buttonInapoi = new JButton("Inapoi");
        buttonInapoi.setBounds(detaliiAngajat.getX() + 250, 400, 150, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        informatiiAngajat(detaliiAngajat);

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

    //Se afiseaza informatii suplimentare pentu angajatul selectat din lista la apasarea butonului
    public void informatiiAngajat(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        indexAngajat = jList.getSelectedIndex();
                        JOptionPane.showMessageDialog(panel, ospatariArray[indexAngajat]);
                    }
                }
        );
    }
}
