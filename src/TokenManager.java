public class TokenManager {
    private String[] tokens;
    private int iCourant;

    public TokenManager(String entree) {
        this.tokens = entree.split("\\s+");
        this.iCourant = 0;
    }

    public String suivant() {
        if (iCourant < tokens.length) {
            return tokens[iCourant++];
        } else {
            return "#";
        }
    }
}
