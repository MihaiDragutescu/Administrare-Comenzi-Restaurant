package administrare;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiciuCitireFisier {
    private BufferedReader bufferedReader;
    private String path;

    public ServiciuCitireFisier(String path) {
        this.path = path;

        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException ex) {
            System.out.println("Calea specificata nu este valida");
            bufferedReader = null;
        }
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public String getPath() {
        return path;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //Metoda citeste si returneaza o linie din fisier
    public List<String> citireLinie() throws IOException {
        if (bufferedReader != null) {
            String linie = bufferedReader.readLine();

            if (linie == null) {
                return null;
            }

            String[] data = linie.split(",");
            return new ArrayList<>(Arrays.asList(data));
        }
        bufferedReader.close();

        return null;
    }
}