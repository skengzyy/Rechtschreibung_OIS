package rechtschreibtrainer.model;

public class Rechtschreibtrainer {
    private Frage[] fragenpool;
    private int countAbgefragt;
    private int countRichtige, countFalsche, anzahlVersuche;

    public Rechtschreibtrainer(Frage[] fragenpool, int anzahlVersuche) {
        setFragenpool(fragenpool);
        setAnzahlVersuche(anzahlVersuche);
    }

    public Frage[] getFragenpool() {
        return fragenpool;
    }

    public void setFragenpool(Frage[] fragenpool) {
        if(fragenpool != null) {
            this.fragenpool = fragenpool;
        }
    }

    public int getCountAbgefragt() {
        return countAbgefragt;
    }

    public void setCountAbgefragt(int countAbgefragt) {
        if(countAbgefragt >= 0) {
            this.countAbgefragt = countAbgefragt;
        }
    }

    public int getCountRichtige() {
        return countRichtige;
    }

    public void setCountRichtige(int countRichtige) {
        if(countRichtige >= 0) {
            this.countRichtige = countRichtige;
        }
    }

    public int getAnzahlVersuche() {
        return anzahlVersuche;
    }

    public void setAnzahlVersuche(int anzahlVersuche) {
        this.anzahlVersuche = anzahlVersuche;
    }

    public int getCountFalsche() {
        return getCountAbgefragt() - getCountRichtige();
    }
}
