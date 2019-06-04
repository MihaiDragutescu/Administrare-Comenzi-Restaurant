package administrare;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

public class ServiciuAudit {
    private static ServiciuAudit singleInstance = null;
    private String path;

    public ServiciuAudit(String path) {
        this.path = path;
    }

    public static ServiciuAudit getInstance(String path) {
        if (singleInstance == null)
            singleInstance = new ServiciuAudit(path);

        return singleInstance;
    }

    //Metoda care scrie in fisier de fiecare data când este executata o actiune (numele actiunii, data+ora si numele firului de executie(doar pentru versiunea 3))
    public void scrie(String actiune) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
            bufferedWriter.write(actiune + "," + Timestamp.from(Instant.now()) + "," + Thread.currentThread().getName() + "\n");
            bufferedWriter.close();
        } catch (IOException ex) {
            System.out.println("A aparut o eroare la scrierea in fisier.");
        }
    }
}
