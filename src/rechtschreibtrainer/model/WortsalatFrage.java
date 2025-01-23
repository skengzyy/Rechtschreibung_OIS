package rechtschreibtrainer.model;

import java.util.Arrays;
import java.util.Random;

public class WortsalatFrage extends Frage{
    protected String text;
    private String antwort;

    public WortsalatFrage(String antwort, String text) {
        super(antwort, text);
        this.setAntwort(antwort);
        this.setFrageText(text);
        this.text = scramble(text);
    }

    public WortsalatFrage(String text) {
        super(text);
        this.setFrageText(text);
        this.text = scramble(text);
    }


    public String scramble(String text){
        Random rand = new Random();
        char[] new_frage = new char[(text.length() * 2) - 1];

        System.arraycopy(text.toCharArray(), 0, new_frage, 0, text.length());

        for(int i = 0; i < new_frage.length - text.length(); ++i){
            new_frage[i] = Character.toUpperCase((char) ('a' + rand.nextInt(26)));
        }

        shuffleArr(new_frage);

        return Arrays.toString(new_frage);
    }

    public char[] shuffleArr(char[] array) {
        Random rand = new Random();
        for (int i = 0; i < array.length; i++) {
            int randomIndexToSwap = rand.nextInt(array.length);
            char temp = array[randomIndexToSwap];
            array[randomIndexToSwap] = array[i];
            array[i] = temp;
        }

        return array;
    }

    public String getFrage(){
        return text;
    }

    public String getAntwort(){
        return antwort;
    }
}
