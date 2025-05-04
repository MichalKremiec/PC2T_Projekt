
import java.util.*;
import java.io.*;
import java.sql.*;

public class StudentDatabase {
    private List<Student> studenti = new ArrayList<>();
    private int dalsiId = 1;
    private Scanner sc = new Scanner(System.in);
    private final String DB_URL = "jdbc:sqlite:studenti.db";

    public StudentDatabase() {
        nactiZDatabaze();
    }

    public void start() {
        while (true) {
            System.out.println("\nMENU:");
            System.out.println("1 - Přidat studenta");
            System.out.println("2 - Přidat známku studentovi");
            System.out.println("3 - Propuštění studenta");
            System.out.println("4 - Najít studenta podle ID");
            System.out.println("5 - Spustit dovednost studenta");
            System.out.println("6 - Výpis všech studentů");
            System.out.println("7 - Výpis obecného průměru oborů");
            System.out.println("8 - Výpis počtu studentů v oborech");
            System.out.println("9 - Uložit studenta do souboru");
            System.out.println("10 - Načíst studenta ze souboru");
            System.out.println("0 - Konec a uložení databáze");
            System.out.print("Zadej volbu: ");
            String volba = sc.nextLine();
            switch (volba) {
                case "1" -> pridejStudenta();
                case "2" -> pridejZnamku();
                case "3" -> propustStudenta();
                case "4" -> najdiStudenta();
                case "5" -> spustDovednost();
                case "6" -> vypisVsechny();
                case "7" -> vypisPrumeryOboru();
                case "8" -> vypisPocty();
                case "9" -> ulozStudenta();
                case "10" -> nactiStudenta();
                case "0" -> { ulozDoDatabaze(); return; }
                default -> System.out.println("Neplatná volba, zkus to znovu.");
            }
        }
    }

    private void pridejStudenta() {
        System.out.println("Vyber skupinu studenta (1 - Telekomunikace, 2 - Kyberbezpečnost):");
        String typ = sc.nextLine();
        if (!typ.equals("1") && !typ.equals("2")) {
            System.out.println("Neplatný výběr skupiny.");
            return;
        }
        System.out.print("Zadej jméno: ");
        String jmeno = sc.nextLine();
        System.out.print("Zadej příjmení: ");
        String prijmeni = sc.nextLine();
        System.out.print("Zadej rok narození: ");
        int rok = Integer.parseInt(sc.nextLine());
        Student s = typ.equals("1") ?
            new StudentTelekomunikace(dalsiId++, jmeno, prijmeni, rok) :
            new StudentKyberbezpecnost(dalsiId++, jmeno, prijmeni, rok);
        studenti.add(s);
        System.out.println("Student přidán: " + s.getInfo());
    }

    private void pridejZnamku() {
        Student s = vyhledejStudent();
        if (s == null) return;
        System.out.print("Zadej známku (1-5): ");
        int znamka = Integer.parseInt(sc.nextLine());
        if (znamka < 1 || znamka > 5) {
            System.out.println("Neplatná známka.");
            return;
        }
        s.pridatZnamku(znamka);
        System.out.println("Známka přidána.");
    }

    private void propustStudenta() {
        Student s = vyhledejStudent();
        if (s == null) return;
        studenti.remove(s);
        System.out.println("Student byl odstraněn.");
    }

    private void najdiStudenta() {
        Student s = vyhledejStudent();
        if (s != null)
            System.out.println(s.getInfo());
    }

    private void spustDovednost() {
        Student s = vyhledejStudent();
        if (s != null)
            s.spustitDovednost();
    }

    private void vypisVsechny() {
        studenti.stream()
            .sorted(Comparator.comparing(Student::getPrijmeni))
            .forEach(s -> System.out.println(s.getInfo()));
    }

    private void vypisPrumeryOboru() {
        double prumerTele = studenti.stream().filter(s -> s instanceof StudentTelekomunikace).mapToDouble(Student::getPrumer).average().orElse(0);
        double prumerKyb = studenti.stream().filter(s -> s instanceof StudentKyberbezpecnost).mapToDouble(Student::getPrumer).average().orElse(0);
        System.out.printf("Telekomunikace: %.2f, Kyberbezpečnost: %.2f\n", prumerTele, prumerKyb);
    }

    private void vypisPocty() {
        long tele = studenti.stream().filter(s -> s instanceof StudentTelekomunikace).count();
        long kyb = studenti.stream().filter(s -> s instanceof StudentKyberbezpecnost).count();
        System.out.printf("Počet studentů - Telekomunikace: %d, Kyberbezpečnost: %d\n", tele, kyb);
    }

    private void ulozStudenta() {
        Student s = vyhledejStudent();
        if (s == null) return;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student_" + s.getId() + ".dat"))) {
            oos.writeObject(s);
            System.out.println("Student uložen.");
        } catch (IOException e) {
            System.out.println("Chyba při ukládání.");
        }
    }

    private void nactiStudenta() {
        System.out.print("Zadej název souboru (např. student_1.dat): ");
        String nazev = sc.nextLine();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nazev))) {
            Student s = (Student) ois.readObject();
            studenti.add(s);
            dalsiId = Math.max(dalsiId, s.getId() + 1);
            System.out.println("Student načten: " + s.getInfo());
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Chyba při načítání.");
        }
    }

    private Student vyhledejStudent() {
        System.out.print("Zadej ID studenta: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            return studenti.stream().filter(s -> s.getId() == id).findFirst().orElseThrow();
        } catch (Exception e) {
            System.out.println("Student s tímto ID nebyl nalezen.");
            return null;
        }
    }

    private void ulozDoDatabaze() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement st = conn.createStatement();
            st.executeUpdate("DROP TABLE IF EXISTS studenti");
            st.executeUpdate("CREATE TABLE studenti (id INTEGER, jmeno TEXT, prijmeni TEXT, rok INTEGER, typ TEXT, znamky TEXT)");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO studenti VALUES (?,?,?,?,?,?)");
            for (Student s : studenti) {
                ps.setInt(1, s.getId());
                ps.setString(2, s.jmeno);
                ps.setString(3, s.prijmeni);
                ps.setInt(4, s.rokNarozeni);
                ps.setString(5, s.getTyp());
                ps.setString(6, s.getZnamky().toString());
                ps.executeUpdate();
            }
            System.out.println("Data uložena do SQL databáze.");
        } catch (SQLException e) {
            System.out.println("Chyba při ukládání do DB: " + e.getMessage());
        }
    }

    private void nactiZDatabaze() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM studenti");
            while (rs.next()) {
                int id = rs.getInt("id");
                String jmeno = rs.getString("jmeno");
                String prijmeni = rs.getString("prijmeni");
                int rok = rs.getInt("rok");
                String typ = rs.getString("typ");
                String znamkyStr = rs.getString("znamky").replaceAll("[\[\] ]", "");
                Student s = typ.equals("Telekomunikace") ?
                    new StudentTelekomunikace(id, jmeno, prijmeni, rok) :
                    new StudentKyberbezpecnost(id, jmeno, prijmeni, rok);
                if (!znamkyStr.isEmpty()) {
                    for (String z : znamkyStr.split(",")) {
                        s.pridatZnamku(Integer.parseInt(z));
                    }
                }
                studenti.add(s);
                dalsiId = Math.max(dalsiId, id + 1);
            }
            System.out.println("Data načtena ze SQL databáze.");
        } catch (SQLException e) {
            System.out.println("Chyba při načítání DB: " + e.getMessage());
        }
    }
}
