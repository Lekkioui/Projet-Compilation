import java.util.Arrays;
import java.util.List;

public class Parseur {
    private TokenManager tm;
    private String tc; // Token courant

    // Vocabulaire enrichi
    private final List<String> articles = Arrays.asList("le", "la", "les", "un", "une", "des", "mon", "ton", "son", "notre",
            "votre", "leur", "ma", "ta", "sa");
    private final List<String> noms = Arrays.asList("souris", "fromage","pomme", "téléphone","lettres", "livre", "ordinateur",
            "chat", "voiture", "chatte","poème", "table","journée", "porte", "maison", "fleur", "oiseau", "matin", "soir", "midi");
    private final List<String> verbes = Arrays.asList("mange", "mangent", "charge", "lit", "sonne", "court", "saute", "vol",
            "écrit","écrivons", "écoute", "chante", "danse");
    private final List<String> pronoms = Arrays.asList("je", "tu", "il", "elle", "nous", "vous", "ils", "elles", "on");
    private final List<String> complementsTempsP1 = Arrays.asList("chaque", "à", "pendant", "en", "ce", "au", "dès", "depuis");
    private final List<String> complementsTempsP2 = Arrays.asList("matin", "soir", "midi", "heures", "semaine", "mois", "année",
            "lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche", "jour", "janvier", "février", "septembre");

    public Parseur(TokenManager tm) {
        this.tm = tm;
        this.tc = tm.suivant();
    }

    private void consommer(String attendu) {
        if (tc.equals(attendu)) {
            avance();
        } else {
            throw new RuntimeException("Token attendu : '" + attendu + "', trouvé : '" + tc + "'");
        }
    }

    private void avance() {
        tc = tm.suivant();
    }

    // Règle principale : S → CC? (N V N | P V N) CC?
    private void S() {
        CC();

        if (pronoms.contains(tc)) {
            P(); // Pronom sujet
            V(); // Verbe
            N2(); // Complément
        } else {
            N1(); // Sujet (article + nom ou possessif)
            V(); // Verbe
            N2(); // Complément
        }
        CC();
    }

    private void CC() {
        if (complementsTempsP1.contains(tc)) {
            avance();
            if (complementsTempsP2.contains(tc)) {
                avance();
            }
        }
    }

    // Règle : N1 → article + nom
    private void N1() {
        if (articles.contains(tc)) {
            avance(); // Consomme l'article
            if (noms.contains(tc)) {
                avance(); // Consomme le nom
            } else {
                throw new RuntimeException("Nom attendu après l'article, trouvé : '" + tc + "'");
            }
        } else {
            throw new RuntimeException("Article ou déterminant attendu, trouvé : '" + tc + "'");
        }
    }

    // Règle : N2 → article + nom || epsilon
    private void N2() {
        if (articles.contains(tc)) {
            avance(); // Consomme l'article
            if (noms.contains(tc)) {
                avance(); // Consomme le nom
            } else {
                throw new RuntimeException("Nom attendu après l'article, trouvé : '" + tc + "'");
            }
        }
    }

    // Règle : V → verbe
    private void V() {
        if (verbes.contains(tc)) {
            avance(); // Consomme le verbe
        } else {
            throw new RuntimeException("Verbe attendu, trouvé : '" + tc + "'");
        }
    }

    // Règle : P → pronom
    private void P() {
        if (pronoms.contains(tc)) {
            avance(); // Consomme le pronom
        } else {
            throw new RuntimeException("Pronom attendu, trouvé : '" + tc + "'");
        }
    }

    // Méthode principale pour valider la phrase
    public void parse() {
        S();
        if (!tc.equals("#")) {
            throw new RuntimeException("Fin de phrase inattendue, token restant : '" + tc + "'");
        }
    }

    public boolean estValide() {
        try {
            parse();
            return true; // Si la phrase est valide, retourne vrai
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
