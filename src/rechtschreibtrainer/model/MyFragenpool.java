package rechtschreibtrainer.model;
import java.io.*;

public class MyFragenpool extends Fragenpool implements Speicherbar{

    public MyFragenpool(int size, Frage[] fragenpool){
        super(size, fragenpool);

    }

    public void speichern(String pfad){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(pfad));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public Fragenpool laden(String pfad){

    }

    public void speichern(){

    }

    public Fragenpool laden(){

    }

    public boolean check(){

    }
}
