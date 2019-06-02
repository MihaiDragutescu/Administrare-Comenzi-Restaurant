package gui;

import administrare.Produs;
import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;
import comenzi.Comanda;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class ComandaSimpla {
    private JFrame frame;
    private JTextField portii;
    private JComboBox comboBox;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> listaComanda = new JList<>(model);
    private ArrayList<Comanda> comandaSimpla = new ArrayList<>();
    private Double pretTotalComanda = 0.0;
    JPanel panel;
    private ServiciuBazaDeDate serviciuBazaDeDate;

    public ComandaSimpla() throws SQLException {
        serviciuBazaDeDate = new ServiciuBazaDeDate("jdbc:mysql://localhost:3306/restaurant?autoReconnect=true", "root", "root");
    }

    public void creare() throws SQLException {
        frame = new JFrame("Comanda simpla");
        frame.setSize(1380, 450);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.ComandaSimpla);
    }

    public void initializareComponente(JPanel panel) throws SQLException {
        panel.setLayout(null);

        ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.meniu");

        ArrayList<String> meniu = new ArrayList<>();
        while (resultSet.next()) {
            String produs = "";
            produs = produs + resultSet.getString("Produs") + " " + resultSet.getDouble("Pret");
            meniu.add(produs);
        }
        String[] meniuArray = meniu.toArray(new String[meniu.size()]);

        //Se salveaza denumirile produselor si preturile din tabela "meniu" intr-un comboBox
        comboBox = new JComboBox(meniuArray);
        comboBox.setBounds(50, 75, 550, 50);
        comboBox.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(comboBox);

        JButton buttonAdaugaProdus = new JButton("+");
        buttonAdaugaProdus.setBounds(comboBox.getX() + 590, 150, 75, 75);
        buttonAdaugaProdus.setFont(new Font("Arial", Font.PLAIN, 55));
        panel.add(buttonAdaugaProdus);

        JLabel numarPortii = new JLabel("Numar portii: ");
        numarPortii.setBounds(50, 150, 200, 50);
        numarPortii.setFont(new Font("Arial", Font.PLAIN, 30));
        panel.add(numarPortii);

        portii = new JTextField();
        portii.setBounds(numarPortii.getX() + 200, numarPortii.getY(), 40, 50);
        portii.setFont(new Font("Arial", Font.PLAIN, 35));
        portii.setText("1");
        panel.add(portii);

        listaComanda.setFont(new Font("Arial", Font.PLAIN, 30));
        listaComanda.setSelectedIndex(0);
        JScrollPane listScroller = new JScrollPane(listaComanda);
        listScroller.setBounds(buttonAdaugaProdus.getX() + 115, 75, 550, 250);
        panel.add(listScroller);

        JButton buttonSterge = new JButton("Sterge");
        buttonSterge.setBounds(comboBox.getX() + 30, portii.getY() + 100, 205, 50);
        buttonSterge.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(buttonSterge);

        JButton buttonFinalizeaza = new JButton("Finalizeaza");
        buttonFinalizeaza.setBounds(buttonSterge.getX() + 275, portii.getY() + 100, 205, 50);
        buttonFinalizeaza.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(buttonFinalizeaza);

        adaugaProdus(buttonAdaugaProdus);
        stergeProdus(buttonSterge);
        finalizareComanda(buttonFinalizeaza);
    }

    //Se adauga produsul selectat din comboBox si numarul de portii intr-o lista la apasarea butonului
    public void adaugaProdus(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String[] produsSplited = String.valueOf(comboBox.getSelectedItem()).split(" ");
                        Double pretProdus = Double.valueOf(produsSplited[produsSplited.length - 1]);
                        String denumireProdus = String.valueOf(comboBox.getSelectedItem()).replace(produsSplited[produsSplited.length - 1], "");
                        Produs produs = new Produs(denumireProdus, pretProdus);

                        int numarPortii = Integer.valueOf(portii.getText());
                        String randLista = denumireProdus + String.valueOf(pretProdus) + " X " + numarPortii;
                        model.addElement(randLista);

                        java.sql.Date sqlDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                        Comanda comanda = new Comanda(produs, numarPortii, sqlDate);
                        comandaSimpla.add(comanda);
                        listaComanda.setSelectedIndex(comandaSimpla.size() - 1);

                    }
                }
        );
    }

    //Se sterge produsul selectat din lista la apasarea butonului
    public void stergeProdus(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int index = listaComanda.getSelectedIndex();
                            ((DefaultListModel) listaComanda.getModel()).remove(index);
                            comandaSimpla.remove(comandaSimpla.get(index));
                            listaComanda.setSelectedIndex(comandaSimpla.size() - 1);
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(panel, "Nu puteti sterge un produs dintr-o lista goala de produse.");
                        }
                    }
                }
        );
    }

    /*La apasarea butonului de finalizare comanda, se salveaza in tabela "comanda_simpla" informatii referitoare la comanda
    efectuata precum denumirea produselor comandate, numarul de portii, numele ospatarului ce a preluat comanda, data si
    pretul total al comenzii*/
    public void finalizareComanda(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {

                            ArrayList<String> ospatari = new ArrayList<>();
                            String numeOspatar;
                            String comanda = "";

                            for (int i = 0; i < comandaSimpla.size(); i++) {
                                comanda = comanda + comandaSimpla.get(i).getProdusComandat().getDenumireProdus() + " X " + comandaSimpla.get(i).getNumarPortii() + "; ";
                                pretTotalComanda += comandaSimpla.get(i).getProdusComandat().getPretProdus() * comandaSimpla.get(i).getNumarPortii();
                            }

                            ResultSet resultSet = serviciuBazaDeDate.citireDate("restaurant.ospatari");

                            while (true) {
                                try {
                                    if (!resultSet.next()) break;
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                                try {
                                    ospatari.add(resultSet.getString("Nume"));
                                } catch (SQLException e1) {
                                    e1.printStackTrace();
                                }
                            }

                            //Se genereaza la intamplare un index care va determina ospatarul ce preia comanda
                            int randomNumber = ThreadLocalRandom.current().nextInt(0, ospatari.size());
                            numeOspatar = ospatari.get(randomNumber);
                            serviciuBazaDeDate.actualizeazaComenziOspatar(numeOspatar);

                            serviciuBazaDeDate.inserareDateComandaSimpla(comanda, numeOspatar, pretTotalComanda, comandaSimpla.get(0).getDataComenzii());
                            JOptionPane.showMessageDialog(panel, "Comanda finalizata cu succes va fi preluata de ospatarul " + numeOspatar + ". Pretul total: " + pretTotalComanda + " lei.");

                            frame.dispose();
                            Autentificare autentificare = new Autentificare();
                            autentificare.creare();

                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(panel, "Lista de produse este goala");
                        }
                    }
                }
        );
    }
}
