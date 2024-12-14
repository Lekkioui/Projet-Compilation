public class Main {
    public static void main(String[] args) {
        String[] phrases = {
                "chaque matin le téléphone sonne",    "je charge mon téléphone",
                "chaque matin je charge le téléphone",    "la souris mange le fromage",
                "le livre lit un fromage",    "chaque soir il mange le fromage",
                "à midi charge le téléphone",    "en janvier le livre sonne",
                "mon ordinateur charge la souris",    "à midi le chat mange une pomme",
                "chaque soir il court",    "le lundi nous lisons un livre",
                "le chat saute sur le canapé",    "pendant la journée téléphone ",
                "le elle mange une pomme",    "nous écrivons des lettres en septembre",
                "en février il écrit un poème"
        };

        for (String phrase : phrases) {
            System.out.println("Phrase : \"" + phrase + "\"");
            TokenManager tm = new TokenManager(phrase);
            Parseur parseur = new Parseur(tm);

            if (parseur.estValide()) {
                System.out.println("=> La phrase est valide.\n");
            } else {
                System.out.println("=> La phrase n'est pas valide.\n");
            }
        }
    }
}
