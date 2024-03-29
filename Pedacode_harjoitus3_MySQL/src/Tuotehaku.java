

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class Tuotehaku {

  private Connection conn = null;
  private PreparedStatement hakuNimelläPst = null;

  public Tuotehaku(Connection conn) {
    this.conn = conn;
  }

  public Tuote[] haeNimellä(String haettavaNimi) {
    try {
      if (this.hakuNimelläPst == null) {
        String sql = "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tuoteryhmaid, tr.nimi "
                + "FROM tuote t, tuoteryhma tr "
                + "WHERE tr.id = t.tuoteryhmaid "
                + "AND t.nimi = ? "
                + "ORDER BY t.julkaisupvm ";
        this.hakuNimelläPst = this.conn.prepareStatement(sql);
      }

      hakuNimelläPst.setString(1, haettavaNimi);
      ResultSet rs = hakuNimelläPst.executeQuery();
      ArrayList tulokset = new ArrayList();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);

        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        tulokset.add(t);

      }

      Tuote[] tuotteet = new Tuote[tulokset.size()];
      for (int i = 0; i < tulokset.size(); i++) {
        tuotteet[i] = (Tuote) tulokset.get(i);
      }
      return tuotteet;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public Tuote haeNimellä(String haettavaNimi, int ID) {
    // metodin ylikuormittaminen
    try {
      if (this.hakuNimelläPst == null) {
        String sql = "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tuoteryhmaid, tr.nimi "
                + "FROM tuote t, tuoteryhma tr "
                + "WHERE tr.id = t.tuoteryhmaid "
                + "AND t.nimi = ? "
                + "ORDER BY t.julkaisupvm ";
        this.hakuNimelläPst = this.conn.prepareStatement(sql);
      }

      hakuNimelläPst.setString(1, haettavaNimi);
      ResultSet rs = hakuNimelläPst.executeQuery();
      //ArrayList tulokset = new ArrayList();
      Tuote apu = null;

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);

        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        apu = t;
      }
      return apu;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public void haeKaikkiNimikkeet() {
    try {
      String sNimi = "";

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi <> ? "
              + "ORDER BY tr.id ");

      s.setString(1, sNimi);
      ResultSet rs = s.executeQuery();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        int tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);
        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        System.out.println(t);
      }

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public int palautaTuotteenID(Tuote[] tuotteet, String sNimi) {
    int iId = 0;
    if (tuotteet != null) {
      for (int i = 0; i < tuotteet.length; i++) {
        if (tuotteet[i].getNimi().equalsIgnoreCase(sNimi)) {
          iId = tuotteet[i].getId();
          //System.out.println("id: " + tuotteet[i].getId());
        }
      }
    }
    return iId;
  }

  public int haeTuoteId(String sTuoteryhmanNimi) {
    try {
      int tuoteryhmaid = 0;

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi = ? ");

      s.setString(1, sTuoteryhmanNimi);
      ResultSet rs = s.executeQuery();

      while (rs.next()) {
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        tuoteryhmaid = rs.getInt(5);
        String tuoteryhmaNimi = rs.getString(6);
        Tuote t = new Tuote(id, nimi, hinta, pvm, tuoteryhmaid, tuoteryhmaNimi);
        System.out.println(t);
      }
      return tuoteryhmaid;

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public boolean lueKannastaTuoteryhmanNimi(String sNimi) {
    boolean loytyiko = false;
    try {

      PreparedStatement s =
              conn.prepareStatement("SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, t.tuoteryhmaid, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid "
              + "AND tr.nimi = ? "
              + "ORDER BY t.julkaisupvm ");

      s.setString(1, sNimi);

      ResultSet rs = s.executeQuery();
      while (rs.next()) {
        int id = rs.getInt(1);
        int tuoteId = id;
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        String tuoteryhmä = rs.getString(5);
        String tuoteryhmänimi = rs.getString(6);
        if (tuoteryhmänimi.equalsIgnoreCase(sNimi)) {
          loytyiko = true;
        }
      }
      return loytyiko;
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public void tuotteenHaku(Scanner lukija, Tuotehaku haku) {
    System.out.print("Mikä tuote haetaan: ");
    String sName = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimellä(sName);
    Tuotepaivitys.tulostaTuotteet(tuotteet);
  }

  public static void haeJaTulostaTiedot() throws ClassNotFoundException {
    try {
      // Muodostetaan yhteys tietokantaan
      Connection conn = Tietokantayhteydet.getConnection();
      Statement s = conn.createStatement();

      // Kysely, jolla tiedot haetaan
      ResultSet rs = s.executeQuery(
              "SELECT t.id, t.nimi, t.hinta, t.julkaisupvm, tr.nimi "
              + "FROM tuote t, tuoteryhma tr "
              + "WHERE tr.id = t.tuoteryhmaid");

      // Tulostetaan ruudulle eri kenttien nimet
      System.out.println("\n\n" + fill("ID", 4) + fill("Nimi", 50)
              + fill("Hinta", 8) + fill("Julkaisupvm", 12) + fill("Tuoteryhmä", 13));
      System.out.println("----------------------------------------------------------------------------------------");

      // Käydään läpi tietokannasta haetut tiedot rivi kerrallaan ja tulostetaan ne ruudulle
      while (rs.next()) {

        // Tallennetaan yhden rivin kentät muuttujiin
        int id = rs.getInt(1);
        String nimi = rs.getString(2);
        double hinta = rs.getDouble(3);
        java.util.Date pvm = rs.getDate(4);
        String tuoteryhmä = rs.getString(5);

        // Tulostetaan muuttujien arvot ruudulle. 
        System.out.println(fill("" + id, 4) + fill(nimi, 50)
                + fill("" + hinta, 8) + fill(pvm.toString(), 12) + fill(tuoteryhmä, 13));
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  private static String fill(String merkkijono, int kokonaispituus) {
    String s = null;
    if (merkkijono != null) {
      s = merkkijono;
      while (s.length() < kokonaispituus) {
        s = s.concat(" ");
      }
    }
    return s;
  }
}
