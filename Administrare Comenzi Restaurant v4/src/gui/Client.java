package gui;

import com.sun.xml.internal.messaging.saaj.soap.JpegDataContentHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Client {
    private JFrame frame;

    public void creare() {
        frame = new JFrame("Client");
        frame.setSize(500, 650);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);
    }

    public void initializareComponente(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel("Selectati tipul comenzii:");
        label.setBounds(70, 75, 450, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 35));
        panel.add(label);

        JButton buttonComandaSimpla = new JButton("Comanda simpla");
        buttonComandaSimpla.setBounds(100, 200, 300, 100);
        buttonComandaSimpla.setFont(new Font("Arial", Font.PLAIN, 35));

        JButton buttonComandaOnline = new JButton("Comanda online");
        buttonComandaOnline.setBounds(100, 400, 300, 100);
        buttonComandaOnline.setFont(new Font("Arial", Font.PLAIN, 35));

        panel.add(buttonComandaSimpla);
        panel.add(buttonComandaOnline);

        comandaSimpla(buttonComandaSimpla);
        comandaOnline(buttonComandaOnline);
    }

    //Se deschide fereastra de efectuare a comenzilor simple la apasarea butonului
    public void comandaSimpla(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        ComandaSimpla comandaSimpla = null;
                        try {
                            comandaSimpla = new ComandaSimpla();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            comandaSimpla.creare();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
    }


    //Se deschide fereastra de efectuare a comenzilor online la apasarea butonului
    public void comandaOnline(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        ComandaOnline comandaOnline = null;
                        try {
                            comandaOnline = new ComandaOnline();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                        try {
                            comandaOnline.creare();
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
        );
    }
}
