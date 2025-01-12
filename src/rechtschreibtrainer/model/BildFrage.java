
package rechtschreibtrainer.model;

import java.net.MalformedURLException;
import java.net.URL;

public class BildFrage extends Frage {


    public BildFrage(String frage, String loesung) {
        setFrage(frage);
        super.setAntwort(loesung);
    }


    @Override
    public boolean check(String antwort) {
        return antwort != null && antwort.equals(super.getAntwort());
    }

    public boolean isUrlSafe(String urlString) {
        try {
            URL url = new URL(urlString);
            String protocol = url.getProtocol();
            String host = url.getHost();

            // Check for HTTPS protocol
            if (!protocol.equalsIgnoreCase("https")) {
                System.out.println("Unsafe URL: Must use HTTPS.");
                return false;
            }

            // Check for common unsafe domains
            if (host.endsWith(".ru") || host.endsWith(".cn")) {
                System.out.println("Unsafe URL: Domain not allowed by EU regulations.");
                return false;
            }

            // Check if domain contains suspicious patterns
            if (host.contains("--") || host.matches(".*\\d{5,}.*")) {
                System.out.println("Unsafe URL: Contains suspicious patterns.");
                return false;
            }

            return true;
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL format.");
            return false;
        }
    }
    public URL getFrage() throws MalformedURLException {
        return new URL(super.getFrageText());
    }

    public void setFrage(String frage) {
        if(isUrlSafe(frage)) {
            super.setFrageText(frage);
        };
    }

    @Override
    public String toString() {
        return super.getFrageText().concat(";").concat("Bild").concat(";").concat(getAntwort());
    }
}
