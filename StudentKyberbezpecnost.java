
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class StudentKyberbezpecnost extends Student implements Skill {
    private static final long serialVersionUID = 1L;

    public StudentKyberbezpecnost(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustitDovednost() {
        System.out.println("Hash: " + naHash(jmeno + " " + prijmeni));
    }

    private String naHash(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return "Chyba hashování";
        }
    }
}
