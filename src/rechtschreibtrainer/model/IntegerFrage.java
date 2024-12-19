package rechtschreibtrainer.model;

public class IntegerFrage extends Frage{
    private int loesung;

    public IntegerFrage(String frage, int loesung) {
        super(frage);
        setLoesung(loesung);
    }

    public int getLoesung() {
        return loesung;
    }

    public void setLoesung(int loesung) {
        if (loesung >= 0) this.loesung = loesung;
    }

    @Override
    public boolean check(int antwort){

    }
}
