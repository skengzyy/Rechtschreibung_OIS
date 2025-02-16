package rechtschreibtrainer.model;

public class Rechtschreibtrainer {
    private Frage[] fragenpool;
    private int countAbgefragt, countAbgefragtSpiel ;
    private int countRichtige, countRichtigeSpiel, countFalsche,countFalscheSpiel, anzahlVersuche;

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
    public void setCountAbgefragtSpiel(int countAbgefragt) {
        if(countAbgefragt >= 0) {
            this.countAbgefragtSpiel = countAbgefragt;
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
    public void setCountRichtigeSpiel(int countRichtige) {
        if(countRichtige >= 0) {
            this.countRichtigeSpiel = countRichtige;
        }
    }
    public void addCountAbgefragt() {
        this.countAbgefragt++;
    }
    public void addCountRichtige() {
        this.countRichtige++;
    }
    public void addCountAbgefragtSpiel() {
        this.countAbgefragtSpiel++;
    }

    public int getCountAbgefragtSpiel() {
        return countAbgefragtSpiel;
    }

    public int getCountRichtigeSpiel() {
        return countRichtigeSpiel;
    }

    public void addCountRichtigeSpiel() {
        this.countRichtigeSpiel++;
    }

    public int getAnzahlVersuche() {
        return anzahlVersuche;
    }

    public void setAnzahlVersuche(int anzahlVersuche) {
        this.anzahlVersuche = anzahlVersuche;
    }
    public void addAnzahlVersuche() {
        this.anzahlVersuche++;
    }

    public int getCountFalsche() {
        return Math.max(0, getCountAbgefragt() - getCountRichtige());
    }
    public int getCountFalscheSpiel() {
        return Math.max(0, getCountAbgefragtSpiel() - getCountRichtigeSpiel());
    }
    public void setCountFalsche(int countFalsche) {
        this.countFalsche = countFalsche;
    }

    public void setCountFalscheSpiel(int countFalsche) {
        this.countFalscheSpiel = countFalsche;
    }
}
