package rechtschreibtrainer.model;

public interface Speicherbar {
    void speichern(String pfad);
    Fragenpool laden(String pfad);
    void speichern();
    Fragenpool laden();
}
