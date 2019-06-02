package gui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Autentificare {
    private JFrame frame;
    JPanel panel;
    JTextField textField;

    public void creare() {
        frame = new JFrame("Autentificare");
        frame.setSize(500, 650);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);

        initializareComponente(panel);
        frame.setVisible(true);
    }

    public void initializareComponente(JPanel panel) {
        panel.setLayout(null);

        JLabel label = new JLabel();
        label.setText("Tip utilizator:");
        label.setBounds(140, 70, 500, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 40));

        JButton button1 = new JButton("Administrator");
        button1.setBounds(100, 210, 300, 100);
        button1.setFont(new Font("Arial", Font.PLAIN, 35));

        JButton button3 = new JButton("Client");
        button3.setBounds(100, 410, 300, 100);
        button3.setFont(new Font("Arial", Font.PLAIN, 35));

        panel.add(button1);
        panel.add(button3);
        panel.add(label);

        //La apasarea butonului de conectare pentru administrator, frame-ul va fi reconstruit sub forma unei ferestre de login
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button3.setVisible(false);

                JLabel label1Autentificare = new JLabel("Autentificare");
                label1Autentificare.setBounds(150, 100, 250, 50);
                label1Autentificare.setFont(new Font("Arial", Font.PLAIN, 40));
                panel.add(label1Autentificare);

                label.setText("ID:");
                label.setBounds(20, 250, 50, 50);
                label.setFont(new Font("Arial", Font.PLAIN, 40));

                button1.setVisible(false);

                textField = new JTextField();
                textField.setBounds(100, 250, 350, 50);
                textField.setFont(new Font("Arial", Font.PLAIN, 35));
                textField.setText("admin123");

                JButton button2 = new JButton("Conectare");
                button2.setBounds(175, 400, 150, 50);
                button2.setFont(new Font("Arial", Font.PLAIN, 25));

                panel.add(button2);
                panel.add(textField);

                conectareAdministrator(button2);

            }
        });

        conectareClient(button3);
    }

    //Redirectionare catre meniul de navigare pentru administrator daca autentificarea se realizeaza cu success (parola corecta)
    public void conectareAdministrator(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!textField.getText().trim().equals("admin123")) {
                    JOptionPane.showMessageDialog(panel, "Id-ul introdus nu este valid.");

                } else {
                    JOptionPane.showMessageDialog(panel, "Conectarea a fost efectuata cu succes.");
                    frame.dispose();
                    Administrator administrator = new Administrator();
                    administrator.creare();

                }
            }
        });
    }

    //Redirectionare catre meniul de navigare pentru clienti la apasarea butonului
    public void conectareClient(JButton button) {
        button.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.dispose();
                        Client client = new Client();
                        client.creare();
                    }
                }
        );
    }
}
