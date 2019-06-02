package administrare;

import java.sql.*;

public class ServiciuBazaDeDate extends Thread {
    private String url, user, password;
    private Connection myConn;

    public ServiciuBazaDeDate(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        myConn = DriverManager.getConnection(url, user, password);
    }

    //Se citesc toate datele din tabela specificata prin parametrul metodei (numele tabelei)
    public ResultSet citireDate(String tableName) throws SQLException {
        Statement statement = myConn.createStatement();
        String sql = "select * from " + tableName;
        ResultSet resultSet = statement.executeQuery(sql);

        return resultSet;
    }

    public void inserareDateComandaOnline(String comanda, String numeClient, Double pretTotalComanda, Date data) {

        try {
            String query = "insert into restaurant.comenzi_online (Comanda,Client,Pret,Data) values(?,?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, comanda);
            preparedStatement.setString(2, numeClient);
            preparedStatement.setDouble(3, pretTotalComanda);
            preparedStatement.setDate(4, data);

            preparedStatement.execute();
            myConn.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void inserareDateComandaSimpla(String comanda, String numeOspatar, Double pretTotalComanda, Date date) {
        try {
            String query = "insert into restaurant.comenzi_simple (Comanda,Ospatar,Pret,Data) values(?,?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, comanda);
            preparedStatement.setString(2, numeOspatar);
            preparedStatement.setDouble(3, pretTotalComanda);
            preparedStatement.setDate(4, date);

            preparedStatement.execute();
            myConn.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void inserareDateClient(String numeClient, String adresa, String telefon) {

        try {
            String query = "insert into restaurant.clienti (Nume, Adresa, Telefon) values(?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, numeClient);
            preparedStatement.setString(2, adresa);
            preparedStatement.setString(3, telefon);

            preparedStatement.execute();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void inserareProdus(String denumire, Double pret) {

        try {
            String query = "insert into restaurant.meniu (Produs, Pret) values(?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, denumire);
            preparedStatement.setString(2, String.valueOf(pret));

            preparedStatement.execute();
            myConn.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void inserareAngajat(String numeAngajat, String adresaAngajat, String CNP, int varstaAngajat, int numarComanziPreluate) {
        try {
            String query = "insert into restaurant.ospatari (Nume, Adresa, CNP, Varsta, Comenzi) values(?,?,?,?,?)";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, numeAngajat);
            preparedStatement.setString(2, adresaAngajat);
            preparedStatement.setString(3, CNP);
            preparedStatement.setInt(4, varstaAngajat);
            preparedStatement.setInt(5, numarComanziPreluate);

            preparedStatement.execute();
            myConn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void stergereProdus(String denumire) {
        try {
            String query = "delete from restaurant.meniu where Produs=?";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, denumire);

            preparedStatement.execute();
            myConn.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void stergeAngajat(String numeAngajat) {
        try {
            String query = "delete from restaurant.ospatari where Nume=?";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, numeAngajat);

            preparedStatement.execute();
            myConn.close();

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    //Se incrementeaza numarul de comenzi preluate de catre ospatarul identificat prin numeOspatar
    public void actualizeazaComenziOspatar(String numeOspatar) {
        try {
            String query = "update restaurant.ospatari set Comenzi = Comenzi + 1 WHERE Nume = ?";
            PreparedStatement preparedStatement = myConn.prepareStatement(query);
            preparedStatement.setString(1, numeOspatar);

            preparedStatement.execute();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    //Se returneaza informatii legate de comanzile online efectuate de de un anumit client
    public ResultSet afiseazaComenziClient(String numeClient) throws SQLException {
        String sql = "select restaurant.comenzi_online.Comanda, restaurant.comenzi_online.Data from restaurant.comenzi_online, restaurant.clienti where restaurant.comenzi_online.Client = ? and restaurant.comenzi_online.Client = restaurant.clienti.Nume";
        PreparedStatement preparedStatement = myConn.prepareStatement(sql);
        preparedStatement.setString(1, numeClient);
        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet;

    }
}
