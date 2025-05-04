
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

abstract class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    protected int id;
    protected String jmeno;
    protected String prijmeni;
    protected int rokNarozeni;
    protected List<Integer> znamky = new ArrayList<>();

    public Student(int id, String jmeno, String prijmeni, int rokNarozeni) {
        this.id = id;
        this.jmeno = jmeno;
        this.prijmeni = prijmeni;
        this.rokNarozeni = rokNarozeni;
    }

    public void pridatZnamku(int znamka) {
        znamky.add(znamka);
    }

    public double getPrumer() {
        return znamky.stream().mapToInt(i -> i).average().orElse(0.0);
    }

    public abstract void spustitDovednost();

    public String getInfo() {
        String znamkyStr = znamky.toString();
        return String.format("ID: %d, Jméno: %s %s, Rok narození: %d, Průměr: %.2f, Známky: %s",
                id, jmeno, prijmeni, rokNarozeni, getPrumer(), znamkyStr);
    }

    public String getPrijmeni() {
        return prijmeni;
    }

    public int getId() {
        return id;
    }

    public String getTyp() {
        return this instanceof StudentTelekomunikace ? "Telekomunikace" : "Kyberbezpečnost";
    }

    public List<Integer> getZnamky() {
        return znamky;
    }
}
