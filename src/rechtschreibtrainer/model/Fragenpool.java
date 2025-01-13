package rechtschreibtrainer.model;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Fragenpool {
    private int size;
    private Frage[] fragenpool;

    protected Fragenpool(int size){
        if(size > 0){
            this.size = size;
        }else{
            System.err.println("given size too small");
        }

    }

    protected boolean addFrage(Frage frage){
        if(frage != null){
            for(int i = 0; i < this.fragenpool.length; ++i) {
                if (this.fragenpool[i] == null) {
                    this.fragenpool[i] = frage;
                    return true;
                }
            }
        }
        return false;
    }

    protected Frage[] getFragenpool(){
        return this.fragenpool;
    }

    protected void deleteFrage(int frage){
        this.fragenpool[frage] = null;
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

    protected int getSize(){
        return this.fragenpool.length;
    }


}
