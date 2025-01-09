package rechtschreibtrainer.model;

public class Rechtschreibtrainer {
    private MyFragenpool[] fragenpool;
    private int countAbgefragt;
    private int countRichtige;
    private int countFalsche;
    private Spielmodus spielmodus;

    public Rechtschreibtrainer(MyFragenpool[] fragenpool) {
        setFragenpool(fragenpool);
    }

    public MyFragenpool[] getFragenpool() {
        return fragenpool;
    }

    public void setFragenpool(MyFragenpool[] fragenpool) {
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
    public int getCountFalsche() {
        return getCountAbgefragt() - getCountRichtige();
    }
}
