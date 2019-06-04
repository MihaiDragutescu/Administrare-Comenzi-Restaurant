package gui;

import administrare.Produs;
import administrare.ServiciuAudit;
import administrare.ServiciuBazaDeDate;
import administrare.TipuriActiuni;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

public class ComandaOnline {
    private JFrame frame;
    private JTextField portii;
    private JComboBox comboBox;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> listaComanda = new JList<>(model);
    private ArrayList<comenzi.ComandaOnline> comandaOnline = new ArrayList<>();
    private Double pretTotalComanda = 0.0;
    private double taxaTransport = 2.0;
    private int index = 0;
    private String numeClient;
    private String adresaClient;
    private String telefonClient;
    private JTextField[] textFields = new JTextField[4];
    private ServiciuBazaDeDate serviciuBazaDeDate;

    public ComandaOnline() throws SQLException {
        serviciuBazaDeDate = ServiciuBazaDeDate.getInstance();
    }

    JTabbedPane tabbedPane;

    JPanel panel, panelDateClient;

    public void creare() throws SQLException {


        frame = new JFrame("Comanda online");
        frame.setSize(500, 700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        panelDateClient = new JPanel();
        panel = new JPanel();

        tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 1380, 450);

        tabbedPane.add("Date client", panelDateClient);
        tabbedPane.add("Date comanda", panel);

        frame.add(tabbedPane);

        initializareComponenteDateComanda(panel);
        initializareComponenteDateClient(panelDateClient);
        frame.setVisible(true);

        tabbedPane.addChangeListener(
                new ChangeListener() {
                    @Override

                    public void stateChanged(ChangeEvent e) {
                        if (index % 2 == 0) {
                            frame.setSize(1380, 450);
                        } else {
                            frame.setSize(500, 700);
                        }
                        index++;

                    }
                }
        );

        ServiciuAudit serviciuAudit = ServiciuAudit.getInstance("files/serviciuAudit.csv");
        serviciuAudit.scrie(TipuriActiuni.ComandaOnline);
    }

    //Se initializeaza componentele din tab-ul cu informatiile clientului
    public void initializareComponenteDateClient(JPanel panelDateClient) {
        panelDateClient.setLayout(null);

        JLabel dateClient = new JLabel("Date client");
        dateClient.setBounds(135, 40, 300, 100);
        dateClient.setFont(new Font("Arial", Font.PLAIN, 40));

        JLabel[] labels = new JLabel[3];

        for (int i = 0; i < 3; i++) {
            labels[i] = new JLabel();
            labels[i].setBounds(20, (i + 3) * 50 + 50 * i, 150, 100);
            labels[i].setFont(new Font("Arial", Font.PLAIN, 30));
        }

        labels[0].setText("Nume:");
        labels[1].setText("Adresa:");
        labels[2].setText("Telefon:");

        for (int i = 0; i < 3; i++) {
            panelDateClient.add(labels[i]);
        }

        for (int i = 0; i < 3; i++) {
            textFields[i] = new JTextField();
            textFields[i].setBounds(150, labels[i].getY() + 30, 300, 50);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 30));
            panelDateClient.add(textFields[i]);
        }

        panelDateClient.add(dateClient);

        JButton buttonSalveaza = new JButton("Salveaza");
        buttonSalveaza.setBounds(150, labels[2].getY() + 150, 175, 50);
        buttonSalveaza.setFont(new Font("Arial", Font.PLAIN, 30));
        panelDateClient.add(buttonSalveaza);

        salveazaDateClient(buttonSalveaza);

    }

    //Se salveaza in baza de date informatiile clientului care urmeaza sa efectueze comanda
    public void salveazaDateClient(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        numeClient = textFields[0].getText();
                        adresaClient = textFields[1].getText();
                        telefonClient = textFields[2].getText();

                        serviciuBazaDeDate.inserareDateClient(numeClient, adresaClient, telefonClient);

                        button.setEnabled(false);
                        JOptionPane.showMessageDialog(panelDateClient, "Datele dumneavoastra au fost salvate in baza de date.");
                        tabbedPane.setSelectedIndex(1);
                    }
                }
        );
    }

    //Se initializeaza componentele tab-ului in care se efectueaza comanda
    public void initializareComponenteDateComanda(JPanel panel) throws SQLException {
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

        JLabel info = new JLabel("* Pentru fiecare produs se percepe o taxa de transport de " + taxaTransport + " lei");
        info.setBounds(buttonAdaugaProdus.getX() + 105, listaComanda.getY() + 320, 600, 50);
        info.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(info);

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
                        comenzi.ComandaOnline comanda = new comenzi.ComandaOnline(produs, numarPortii, sqlDate, taxaTransport);
                        comandaOnline.add(comanda);
                        listaComanda.setSelectedIndex(comandaOnline.size() - 1);

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
                            comandaOnline.remove(comandaOnline.get(index));
                            listaComanda.setSelectedIndex(comandaOnline.size() - 1);
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(panel, "Nu puteti sterge un produs dintr-o lista goala de produse.");
                        }

                    }
                }
        );
    }

    /*La apasarea butonului de finalizare, comanda se salveaza in tabela "comanda_online" informatii referitoare la comanda
    efectuata precum denumirea produselor comandate, numarul de portii, numele clientului, data si
    pretul total al comenzii*/
    public void finalizareComanda(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        try {
                            String comanda = "";

                            for (int i = 0; i < comandaOnline.size(); i++) {
                                comanda = comanda + comandaOnline.get(i).getProdusComandat().getDenumireProdus() + " X " + comandaOnline.get(i).getNumarPortii() + "; ";
                                pretTotalComanda += comandaOnline.get(i).getProdusComandat().getPretProdus() * comandaOnline.get(i).getNumarPortii() + 2 * comandaOnline.get(i).getNumarPortii();
                            }
                            serviciuBazaDeDate.inserareDateComandaOnline(comanda, numeClient, pretTotalComanda, comandaOnline.get(0).getDataComenzii());

                            JOptionPane.showMessageDialog(panel, "Comanda finalizata! Pretul total: " + pretTotalComanda + " lei.");
                            frame.dispose();
                            Autentificare autentificare = new Autentificare();
                            autentificare.start();

                        } catch (Exception e1) {
                            if (numeClient.equals("")) {
                                JOptionPane.showMessageDialog(panel, "Nu ati completat datele de contact!");
                            } else {
                                JOptionPane.showMessageDialog(panel, "Lista de produse este goala");
                            }
                        }
                    }
                }
        );
    }
}
