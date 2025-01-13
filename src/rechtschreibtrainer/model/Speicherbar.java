package rechtschreibtrainer.model;
import java.io.*;

public interface Speicherbar {
    int MAXFRAGEN = 20;
    void speichern(String filename);
    Rechtschreibtrainer laden(File filename);


}
