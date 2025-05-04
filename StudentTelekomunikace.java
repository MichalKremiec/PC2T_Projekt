
import java.util.HashMap;
import java.util.Map;

public class StudentTelekomunikace extends Student implements Skill {
    private static final long serialVersionUID = 1L;

    public StudentTelekomunikace(int id, String jmeno, String prijmeni, int rokNarozeni) {
        super(id, jmeno, prijmeni, rokNarozeni);
    }

    @Override
    public void spustitDovednost() {
        System.out.println("Morseova abeceda: " + naMorse(jmeno + " " + prijmeni));
    }

    private String naMorse(String text) {
        Map<Character, String> morse = new HashMap<>();
        morse.put('A', ".-"); morse.put('B', "-..."); morse.put('C', "-.-.");
        morse.put('D', "-.."); morse.put('E', "."); morse.put('F', "..-.");
        morse.put('G', "--."); morse.put('H', "...."); morse.put('I', "..");
        morse.put('J', ".---"); morse.put('K', "-.-"); morse.put('L', ".-..");
        morse.put('M', "--"); morse.put('N', "-."); morse.put('O', "---");
        morse.put('P', ".--."); morse.put('Q', "--.-"); morse.put('R', ".-.");
        morse.put('S', "..."); morse.put('T', "-"); morse.put('U', "..-");
        morse.put('V', "...-"); morse.put('W', ".--"); morse.put('X', "-..-");
        morse.put('Y', "-.--"); morse.put('Z', "--.."); morse.put(' ', "/");

        StringBuilder sb = new StringBuilder();
        for (char c : text.toUpperCase().toCharArray()) {
            sb.append(morse.getOrDefault(c, "?")).append(" ");
        }
        return sb.toString();
    }
}
