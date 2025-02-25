package rechtschreibtrainer.model;

public class BooleanFrage extends Frage{
    private boolean loesung;

    public BooleanFrage(String frage, boolean loesung) {
        super(frage);
        setLoesung(loesung);
    }

    public boolean isLoesung() {
        return loesung;
    }

    public void setLoesung(boolean loesung) {
         this.loesung = loesung;
    }


    public boolean check(String antwort) {
        antwort = antwort.toLowerCase();
        if(antwort.equals("true") || antwort.equals("false")) {
            return antwort.equals(getLoesung());
        }
        return false;
    }
    public String getTyp() {
        return "Boolean";
    }
    public String getLoesung() {
        if(loesung) {
            return "true";
        }
        return "false";
    }

    @Override
    public String toString() {
        return getFrageText().concat(";").concat("Boolean").concat(";").concat(Boolean.toString(isLoesung()));
    }


}
