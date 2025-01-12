package rechtschreibtrainer.model;

public class Rechtschreibtrainer {
    private Frage[] fragenpool;
    private int countAbgefragt;
    private int countRichtige, countFalsche, anzahlVersuche;
    private Spielmodus spielmodus;

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
            this.spielmodus = spielmodus;
        }
    }

    public Spielmodus getSpielmodus() {
        return spielmodus;
    }

    public void setSpielmodus(Spielmodus spielmodus) {
        if(spielmodus != null) {
            this.spielmodus = spielmodus;
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
