package rechtschreibtrainer.model;
import java.io.*;

public interface Speicherbar {
    int MAXFRAGEN = 30;
    void speichern(String filename, int anzahlVersuche);
    Rechtschreibtrainer laden(File filename);


}
