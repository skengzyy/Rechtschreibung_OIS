
package rechtschreibtrainer.model;

import java.net.MalformedURLException;
import java.net.URL;

public class BildFrage extends Frage {
    private String loesung;

    // Constructor to initialize BildFrage object
    public BildFrage(String frage, String loesung) {
        super(frage);
        setLoesung(loesung);
    }

    public String getLoesung() {
        return loesung;
    }

    // Setter method to assign loesung with null-check
    public void setLoesung(String loesung) {
        if (loesung != null && !loesung.isEmpty()) {
            this.loesung = loesung;
        }
    }

    @Override
    public boolean check(String antwort) {
        return antwort != null && antwort.equals(this.loesung);
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

    @Override
    public String toString() {
        return super.getFrageText().concat(";").concat("Bild").concat(";").concat(getLoesung());
    }
}
