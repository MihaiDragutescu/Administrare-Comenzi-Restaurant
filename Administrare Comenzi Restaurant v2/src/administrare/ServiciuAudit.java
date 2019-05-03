package administrare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class ServiciuAudit {
    private String path;

    public ServiciuAudit(String path) {
        this.path = path;
    }

    //Metoda care scrie in fisier de fiecare data când este executata o actiune (numele actiunii si data+ora)
    public void scrie(String actiune) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            bufferedWriter.write(actiune + "," + Timestamp.from(Instant.now()) + "\n");
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("A aparut o eroare la scrierea in fisier.");
        }
    }
}
