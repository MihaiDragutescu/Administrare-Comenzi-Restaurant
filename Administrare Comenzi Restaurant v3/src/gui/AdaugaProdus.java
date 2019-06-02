package gui;

import administrare.Produs;
import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class AdaugaProdus {
    private JFrame frame;
    JTextField textFieldPret;
    JTextField textFieldDenumire;
    JPanel panel;

    public void creare() throws SQLException {
        frame = new JFrame("Adauga produs");
        frame.setSize(500, 525);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.AdaugaProdus);
    }

    public void initializareComponente(JPanel panel) {
        panel.setLayout(null);

        JLabel dateProdus = new JLabel("Date produs");
        dateProdus.setBounds(135, 50, 300, 100);
        dateProdus.setFont(new Font("Arial", Font.PLAIN, 40));

        panel.add(dateProdus);

        JLabel numeProdus = new JLabel("Denumire:");
        numeProdus.setBounds(20, 175, 150, 50);
        numeProdus.setFont(new Font("Arial", Font.PLAIN, 30));

        textFieldDenumire = new JTextField();
        textFieldDenumire.setBounds(175, numeProdus.getY(), 300, 50);
        textFieldDenumire.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(textFieldDenumire);

        JLabel pretProdus = new JLabel("Pret:");
        pretProdus.setBounds(20, 275, 150, 50);
        pretProdus.setFont(new Font("Arial", Font.PLAIN, 30));

        textFieldPret = new JTextField();
        textFieldPret.setBounds(175, pretProdus.getY(), 300, 50);
        textFieldPret.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(textFieldPret);

        panel.add(numeProdus);
        panel.add(pretProdus);

        JButton buttonSalveaza = new JButton("Salveaza");
        JButton buttonInapoi = new JButton("Inapoi");

        buttonSalveaza.setBounds(30, pretProdus.getY() + 100, 175, 50);
        buttonSalveaza.setFont(new Font("Arial", Font.PLAIN, 30));

        buttonInapoi.setBounds(buttonSalveaza.getX() + 250, pretProdus.getY() + 100, 175, 50);
        buttonInapoi.setFont(new Font("Arial", Font.PLAIN, 30));

        panel.add(buttonSalveaza);
        panel.add(buttonInapoi);

        inapoiLaMeniu(buttonInapoi);
        salveazaProdus(buttonSalveaza);
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

    //Se insereaza un produs nou in tabela "meniu" din baza de date
    public void salveazaProdus(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            Produs produs = new Produs(textFieldDenumire.getText(), Double.valueOf(textFieldPret.getText()));

                            ServiciuBazaDeDate serviciuBazaDeDate = new ServiciuBazaDeDate("jdbc:mysql://localhost:3306/restaurant?autoReconnect=true", "root", "root");
                            serviciuBazaDeDate.inserareProdus(produs.getDenumireProdus(), produs.getPretProdus());

                            JOptionPane.showMessageDialog(panel, "Produsul a fost adaugat cu succes.");
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(panel, "Completarea campurilor este obligatorie!");
                        }

                    }
                }
        );
    }
}
