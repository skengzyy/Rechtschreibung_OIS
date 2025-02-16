package rechtschreibtrainer.model;
import javax.swing.*;
import java.io.*;
import java.net.URL;

public class MyFragenpool extends Fragenpool implements Speicherbar{


    public MyFragenpool(int size) {
        super(size);
    }

    @Override
    public void speichern(String filename, int anzahlVersuche) {
        if(filename == null) return;
        File f = new File(filename);
        Frage[] pool = super.getFragenpool();
        Rechtschreibtrainer trainer = new Rechtschreibtrainer(pool,1);
        try(PrintWriter outputStream = new PrintWriter(f)) {
            if(pool == null) return;
            outputStream.println(this.getSize());
            outputStream.println("========");
            for(int i = 0; i < pool.length;i++) {
                if(pool[i]!= null) {
                    outputStream.println(pool[i].toString());
                }
            }
            outputStream.println("========");
            outputStream.println(trainer.getAnzahlVersuche());

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Rechtschreibtrainer laden(File file) {
        if (file == null) return null;

        try (BufferedReader inputStream = new BufferedReader(new FileReader(file))) {
            String line = inputStream.readLine();
            if (line == null || line.isEmpty()) return null;

            int fragenAnzahl = Integer.parseInt(line);

            if(fragenAnzahl > MAXFRAGEN) return null;
            Frage[] fragen = new Frage[fragenAnzahl];
            inputStream.readLine();
            Frage text = null;
            for (int i = 0; i < fragenAnzahl; i++) {
                String frageZeile = inputStream.readLine();
                if (frageZeile != null) {
                    String[] question = frageZeile.split(";");

                    if(question[0] == null || question[1] == null || question[2] == null) return null;
                    if(question[1].equals("String")) {
                        text = new Frage(question[2],question[0]);
                    }if(question[1].equals("Integer")) {
                        text = new IntegerFrage(question[0],Integer.parseInt(question[2]));
                    }if(question[1].equals("Boolean")) {
                        text = new BooleanFrage(question[0],Boolean.parseBoolean(question[2]));
                    }if(question[1].equals("Bild")) {
                        text = new BildFrage(question[0], question[2]);
                    }if(question[1].equals("Wortsalat")) {
                        text = new WortsalatFrage(question[2], question[0]);
                    }
                    fragen[i] = text;
                }
            }

            inputStream.readLine();

            int anzahlVersuche = Integer.parseInt(inputStream.readLine()); // Anzahl der Versuche
            Rechtschreibtrainer trainer = new Rechtschreibtrainer(fragen, anzahlVersuche);

            // Rückgabe des geladenen Fragenpools
            //return new Fragenpool(fragen, anzahlVersuche);
            return trainer;

        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Fehler beim Laden der Datei: " + e.getMessage(), e);
        }
    }






}
