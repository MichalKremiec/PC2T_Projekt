Projekt: StudentDatabaseApp (PC2T)
==================================

Autor: Michal Kremiec
Akademický rok: 2024/2025
Předmět: PC2T – Programování 2

Popis:
------
Tento projekt simuluje databázi studentů dvou oborů technické univerzity:
- Telekomunikace
- Kyberbezpečnost

Uživatel může přidávat studenty, zadávat jim známky, zobrazovat jejich informace,
spouštět oborové dovednosti (Morseova abeceda nebo SHA-256 hash), pracovat s databází
i soubory.

Program je vytvořen jako **konzolová aplikace** – bez zbytečné režie GUI,
aby byla maximalizována srozumitelnost, přenositelnost a čistý návrh podle zadání.

Ovládání:
---------
Po spuštění `Main.java` se otevře interaktivní textové menu s nabídkou funkcí:

1 - Přidat studenta
2 - Přidat známku studentovi
3 - Propuštění studenta
4 - Najít studenta podle ID
5 - Spustit dovednost studenta
6 - Výpis všech studentů (včetně známek)
7 - Výpis obecného průměru oborů
8 - Výpis počtu studentů v oborech
9 - Uložit studenta do souboru
10 - Načíst studenta ze souboru
0 - Konec a uložení databáze

OOP a technické principy:
--------------------------
✔️ Dědičnost (StudentTelekomunikace, StudentKyberbezpecnost dědí z abstraktní třídy Student)  
✔️ Abstraktní třída (Student)  
✔️ Rozhraní (Skill)  
✔️ Dynamická struktura (ArrayList<Student>)  
✔️ Serializace objektů  
✔️ Práce s SQLite databází  
✔️ Ošetření vstupů a výjimek  
✔️ Optimalizovaný návrh bez zbytečné složitosti

Speciální poznámky:
-------------------
✔️ Známky jsou uloženy jako seznam a zobrazeny při výpisu studenta  
✔️ Morseovka se používá pro studenty Telekomunikací  
✔️ Hash funkce (SHA-256) se používá pro studenty Kyberbezpečnosti  
✔️ Program obsahuje předvyplněná data pro rychlé testování funkcí (studenti.db)

Spuštění:
---------
1. Otevři projekt v Eclipse (doporučeno JDK 8 až 17)
2. Spusť `Main.java` pravým tlačítkem jako Java aplikaci
3. Nebo:
   > cd src  
   > javac *.java  
   > java Main

GitHub:
-------
Zdrojový kód a archiv ZIP je dostupný na:
https://github.com/MichalKremiec/PC2T_Projekt
