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


    public boolean check(String antwort) {
        int intAntwort = 0;
        try {
            intAntwort = Integer.parseInt(antwort);
        } catch(NumberFormatException ex) {
            return false;
        }
        return intAntwort == loesung;
    }
    public String getTyp() {
        return "Integer";
    }

    @Override
    public String toString() {
        return super.getFrageText().concat(";").concat("Integer").concat(";").concat(Integer.toString(getLoesung()));
    }


}
