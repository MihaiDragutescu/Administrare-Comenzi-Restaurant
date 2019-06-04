package gui;

import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;
import com.sun.crypto.provider.JceKeyStore;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class StergeOspatar extends Thread {
    private JFrame frame;
    private JPanel panel;
    private ServiciuBazaDeDate serviciuBazaDeDate;

    public StergeOspatar() throws SQLException {
        serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
    }

    public void creare() {
        frame = new JFrame("Concediaza Ospatar");
        frame.setSize(500, 500);
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
        serviciuAudit.scrie(TipuriActiuni.ConcediazaOspatar);
    }

    public void initializareComponente(JPanel panel) throws SQLException {
        panel.setLayout(null);

        JLabel label = new JLabel("Selectati numele angajatului:");
        label.setBounds(25, 75, 450, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(label);

        ArrayList<String> ospatari = new ArrayList<>();

        ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.ospatari");

        while (resultSet.next()) {
            ospatari.add(resultSet.getString("Nume"));
        }

        String[] listaOspatari = ospatari.toArray(new String[ospatari.size()]);

        //Se salveaza numele angajatilor din tabela "ospatari" intr-un comboBox
        JComboBox jComboBox = new JComboBox(listaOspatari);
        jComboBox.setBounds(50, 200, 400, 50);
        jComboBox.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(jComboBox);

        JButton buttonSterge = new JButton("Sterge");
        buttonSterge.setBounds(75, 325, 150, 50);
        buttonSterge.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton buttonInapoi = new JButton("Inapoi");
        buttonInapoi.setBounds(buttonSterge.getX() + 200, 325, 150, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 30));

        panel.add(buttonSterge);
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        stergeAngajat(buttonSterge, jComboBox);

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

    //Se sterge din tabela "ospatari" angajatul cu numele selectat in comboBox
    public void stergeAngajat(JButton button, JComboBox jComboBox) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String numeOspatar = String.valueOf(jComboBox.getSelectedItem());

                        serviciuBazaDeDate.stergeAngajat(numeOspatar);
                        JOptionPane.showMessageDialog(panel, "Angajatul a fost sters cu succes.");
                    }
                }
        );
    }
}
