package rechtschreibtrainer.model;

public class BildFrage extends Frage{
    private String loesung;

    public BildFrage(String frage, String loesung) {
        super(frage);
        setLoesung(loesung);
    }

    public String getLoesung() {
        return loesung;
    }

    public void setLoesung(String loesung) {
        if(loesung != null && loesung.isEmpty()) this.loesung = loesung;
    }

    @Override
    public boolean check(String antwort) {
        return super.check(antwort);
    }
}
