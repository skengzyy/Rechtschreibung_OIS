package rechtschreibtrainer.model;
import java.io.*;

public interface Speicherbar {
    int MAXFRAGEN = 20;
    void speichern(String pfad, String filename);
    Fragenpool laden(File filename);


}
