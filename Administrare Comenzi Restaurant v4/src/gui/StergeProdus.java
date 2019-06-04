package gui;

import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;
import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class StergeProdus {
    private JFrame frame;
    private JPanel panel;
    private ServiciuBazaDeDate serviciuBazaDeDate;

    public StergeProdus() throws SQLException {
        serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
    }

    public void creare() throws SQLException {

        frame = new JFrame("Sterge Produs");
        frame.setSize(500, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.StergeProdus);
    }

    public void initializareComponente(JPanel panel) throws SQLException {
        panel.setLayout(null);

        JLabel label = new JLabel("Selectati numele produsului:");
        label.setBounds(25, 75, 450, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(label);

        ArrayList<String> produse = new ArrayList<>();

        ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.meniu");

        while (resultSet.next()) {
            produse.add(resultSet.getString("Produs"));
        }

        String[] listaOspatari = produse.toArray(new String[produse.size()]);

        //Se salveaza denumirile produselor si preturile din tabela "meniu" intr-un comboBox
        JComboBox jComboBox = new JComboBox(listaOspatari);
        jComboBox.setBounds(25, 200, 450, 50);
        jComboBox.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(jComboBox);

        JButton buttonSterge = new JButton("Sterge");
        buttonSterge.setBounds(75, 325, 150, 50);
        buttonSterge.setFont(new Font("Arial", Font.PLAIN, 30));

        JButton buttonInapoi = new JButton("Inapoi");
        buttonInapoi.setBounds(buttonSterge.getX() + 175, 325, 150, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 30));

        panel.add(buttonSterge);
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        stergeProdus(buttonSterge, jComboBox);
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

    //Se sterge din tabela "meniu" produsul selectat in comboBox
    public void stergeProdus(JButton button, JComboBox jComboBox) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String denumireProdus = String.valueOf(jComboBox.getSelectedItem());
                        serviciuBazaDeDate.stergereProdus(denumireProdus);
                        JOptionPane.showMessageDialog(panel, "Produsul a fost sters cu succes.");
                    }
                }
        );
    }
}
