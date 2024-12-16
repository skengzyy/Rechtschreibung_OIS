package rechtschreibtrainer.model;

public interface Speicherbar {
    void speichern(String pfad);
    void laden(String pfad);
    void speichern();
    void laden();
}
