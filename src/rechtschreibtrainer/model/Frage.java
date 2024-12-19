package rechtschreibtrainer.model;

public class Frage {
    protected String text;
    private String antwort;

    public Frage(String antwort, String loesung) {
        setAntwort(antwort);
        setFrageText(loesung);
    }

    public Frage(String text) {
        setFrageText(text);
    }

    public String getFrageText() {
        return text;
    }

    public void setFrageText(String text) {
        if (text != null && !text.isEmpty()) this.text = text;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
       if(antwort != null && !antwort.isEmpty()) this.antwort = antwort;
    }

    @Override
    public String toString() {
        return getFrageText().concat(";").concat("String").concat(";").concat(getAntwort());
    }

    public boolean check(String antwort){
        if(this.text != null && !this.text.isEmpty()){
            antwort = antwort.toLowerCase();
            return antwort.contains(this.antwort);
        }
        return false;
    }
}
