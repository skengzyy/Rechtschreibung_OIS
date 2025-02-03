package rechtschreibtrainer.model;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Fragenpool {
    private int size;
    private Frage[] fragenpool;

    protected Fragenpool(int size){
        this.fragenpool = new Frage[0];
        /*
        if(size > 0){

            this.size = size;
            fragenpool = new Frage[size];
        }else{
            System.err.println("given size too small");
        }
         */

    }

    public boolean addFrage(Frage frage){
        if (frage == null || fragenpool.length >= Speicherbar.MAXFRAGEN) {
            return false;
        }
        Frage[] temp = new Frage[fragenpool.length + 1];
        System.arraycopy(fragenpool, 0, temp, 0, fragenpool.length);
        temp[fragenpool.length] = frage;
        fragenpool = temp;
        return true;
    }

    public Frage[] getFragenpool(){
        return this.fragenpool;
    }

    public void deleteFrage(int index){
        if (index < 0 || index >= fragenpool.length) {
            return;
        }
        Frage[] temp = new Frage[fragenpool.length - 1];
        int j = 0;
        for (int i = 0; i < fragenpool.length; i++) {
            if (i != index) {
                temp[j++] = fragenpool[i];
            }
        }
        fragenpool = temp;
    }

    protected Frage getRandomFrage(){
        for(Frage fr : this.fragenpool){
            if(fr != null){
                while(true){
                    Frage rnd = this.fragenpool[ThreadLocalRandom.current().nextInt(this.fragenpool.length)];
                    if(rnd != null){
                        return rnd;
                    }
                }
            }
        }
        System.err.println("Array only contains null");
        return null;
    }

    public int getSize(){
        int trueSize = 0;
        for(int i=0; i<this.fragenpool.length;i++) {
            if(this.fragenpool[i] != null) {
                trueSize++;
            }
        }
        return trueSize;
    }
    public boolean isEmpty() {
        boolean empty = true;
        for(int i=0; i<this.fragenpool.length;i++) {
            if(this.fragenpool[i] != null) {
                empty = false;
            }
        }
        return empty;
    }
    public String poolToString() {
        String text = "";
        for(int i=0; i<this.fragenpool.length;i++) {
            text += "Nr. "+ (i+1) + "\t" + fragenpool[i].toString() + "\n";
        }
        return text;
    }


}
