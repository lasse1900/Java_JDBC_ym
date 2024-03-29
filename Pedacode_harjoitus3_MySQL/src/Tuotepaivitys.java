

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Tuotepaivitys {

  private Connection conn = null;
  private PreparedStatement lisääTuotePst = null;
  private PreparedStatement päivitäTuotePst = null;
  private PreparedStatement päivitäTuoteryhmäPst = null;
  private PreparedStatement luoUusiTuoteryhmäPst = null;
  private PreparedStatement haeTuoteryhmäPst = null;
  Tuotehaku haku = new Tuotehaku(Tietokantayhteydet.getConnection());

  public Tuotepaivitys(Connection conn) {
    this.conn = conn;
  }

  public void lisääTuote(Tuote t) {
    try {
      if (this.lisääTuotePst == null) {
        String sql = "INSERT INTO tuote (id, nimi, hinta, julkaisupvm, tuoteryhmaid) "
                + "VALUES (?, ?, ?, ?, ?)";
        this.lisääTuotePst = this.conn.prepareStatement(sql);
      }

      lisääTuotePst.setInt(1, t.getId());
      lisääTuotePst.setString(2, t.getNimi());
      lisääTuotePst.setDouble(3, t.getHinta());
      lisääTuotePst.setDate(4, new java.sql.Date(t.getJulkaisupvm().getTime()));
      lisääTuotePst.setInt(5, t.getTuoteryhmaid());

      lisääTuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen lisäys tietokantaan epäonnistui", e);
    }
  }

  public void poistaKannasta(int poistettavaID) throws SQLException {
    try {
      int iId = poistettavaID;
      PreparedStatement pst = conn.prepareStatement("DELETE FROM tuote WHERE id = ?");
      pst.setInt(1, iId); // nimi

      pst.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Tietojen haku tietokannasta epäonnistui", e);
    }
  }

  public void päivitäTuote(Tuote t) {
    try {

      if (this.päivitäTuotePst == null) {
        String sql = "UPDATE tuote "
                + "SET nimi = ?, hinta = ?, julkaisupvm = ?, tuoteryhmaid = ?"
                + "WHERE id = ?";
        this.päivitäTuotePst = conn.prepareStatement(sql);
      }

      päivitäTuotePst.setString(1, t.getNimi());
      päivitäTuotePst.setDouble(2, t.getHinta());
      päivitäTuotePst.setDate(3, new java.sql.Date(t.getJulkaisupvm().getTime()));
      päivitäTuotePst.setInt(4, t.getTuoteryhmaid());
      päivitäTuotePst.setInt(5, t.getId());

      päivitäTuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen tietojen päivittäminen tietokantaan epäonnistui", e);
    }
  }

  public void päivitäTuoteRyhmä(Tuote t) {
    try {

      if (this.päivitäTuotePst == null) {
        String sql = "UPDATE tuote "
                + "SET nimi = ?, hinta = ?, julkaisupvm = ?, tuoteryhmaid = ?"
                + "WHERE id = ?";
        this.päivitäTuotePst = conn.prepareStatement(sql);
      }

      päivitäTuotePst.setString(1, t.getNimi());
      päivitäTuotePst.setDouble(2, t.getHinta());
      päivitäTuotePst.setDate(3, new java.sql.Date(t.getJulkaisupvm().getTime()));
      päivitäTuotePst.setInt(4, t.getTuoteryhmaid());
      päivitäTuotePst.setInt(5, t.getId());

      päivitäTuotePst.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Tuotteen tietojen päivittäminen tietokantaan epäonnistui", e);
    }
  }

  public void päivitäTuoteryhmäTuotteille(Tuote[] tuotteet, String tuoteryhmäNimi) {
    try {

      // Asetetaan aluksi autocommit-asetus pois päältä. Haluamme, että 
      // kaikki operaatiot kuuluvat samaan transaktioon. 
      this.conn.setAutoCommit(false);

      // Kysely on tallennettu luokkatason muuttujaan ja 
      // kysely täytyy alustaa siten vain, jos sitä ei ole 
      // vielä alustettu. 
      if (this.päivitäTuoteryhmäPst == null) {
        String sql = "UPDATE tuote SET tuoteryhmaid = ? WHERE id = ?";
        this.päivitäTuoteryhmäPst = this.conn.prepareStatement(sql);
      }

      // Tutkitaan onko haluttu tuoteryhmä jo olemassa. Jos ei ole, 
      // luodaan kantaan uusi tuoteryhmä. 
      int tuoteryhmänId = palautaTuoteryhmäId(tuoteryhmäNimi);
      if (tuoteryhmänId == -1) {
        // Tuoteryhmän tietoja ei löytynyt. Luodaan kantaan uusi tuoteryhmä
        tuoteryhmänId = lisääTuoteryhmä(tuoteryhmäNimi);
      }

      // Käydään läpi kaikki päivitettävät tuotteet ja päivitetään
      // niille haluttu tuoteryhmää
      for (int i = 0; i < tuotteet.length; i++) {
        päivitäTuoteryhmäPst.setInt(1, tuoteryhmänId);
        päivitäTuoteryhmäPst.setInt(2, tuotteet[i].getId());
        päivitäTuoteryhmäPst.executeUpdate();
      }

      // Hyväksytään kaikki muutokset
      this.conn.commit();

    } catch (Exception e) {

      // Perutaan kaikki tehdyt muutokset
      try {
        this.conn.rollback();
      } catch (SQLException sqle) {
        throw new RuntimeException("Rollback-operaatio epäonnistui", sqle);
      }

      // Tulostetaan virheen tiedot ruudulle
      e.printStackTrace();
      throw new RuntimeException("Tuotteen lisäys tietokantaan epäonnistui", e);

    } finally {
      // Palautetaan autocommit-asetus alkuperäiseen arvoon
      try {
        this.conn.setAutoCommit(true);
      } catch (SQLException sqle) {
        throw new RuntimeException("Autocommit-asetus epäonnistui", sqle);
      }
    }
  }
  
  
   private int palautaTuoteryhmäId(String tuoteryhmänNimi)
    {
      try {

        int tuoteryhmänId = 1;
        
        // Kysely on tallennettu luokkatason muuttujaan ja 
        // kysely täytyy alustaa siten vain, jos sitä ei ole 
        // vielä alustettu. 
        if (this.haeTuoteryhmäPst == null) {
          String sql = "SELECT id from tuoteryhma WHERE nimi = ?";
          this.haeTuoteryhmäPst = conn.prepareStatement(sql);
        }
        
        // Asetetaan sql-lauseeseen poistettavan tuotteen id
        haeTuoteryhmäPst.setString(1, tuoteryhmänNimi);

        // Haetaan tuoteryhmän tiedot
          ResultSet rs = haeTuoteryhmäPst.executeQuery();
          
          // Tutkitaan tulos
          if (rs.next() == false) {
            // Mitään tietoja ei löytynyt
            tuoteryhmänId = -1;
          } else {
            // Löytyi jotain tietoja, haetaan tiedot ensimmäiseltä riviltä
            tuoteryhmänId = rs.getInt("id");
          }
          
          return tuoteryhmänId;
          
      } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Tuoteryhmän tietojen haku epäonnistui", e);
      }
    }


    private int lisääTuoteryhmä(String tuoteryhmänNimi)
    {
      try {

        if (this.luoUusiTuoteryhmäPst == null) {
          String sql = "INSERT INTO tuoteryhma (id, nimi)" + 
        "VALUES (?, ?)";
          this.luoUusiTuoteryhmäPst = conn.prepareStatement(sql);
        }
        
        // Uusi tuoteryhmän id
        int id = luoUusiAvain();
        
        // Annetaan kyselylle vaadittavat tiedot
        luoUusiTuoteryhmäPst.setInt(1, id);
        luoUusiTuoteryhmäPst.setString(2, tuoteryhmänNimi);

        // Suoritetaan päivitys
        luoUusiTuoteryhmäPst.executeUpdate();
        
        return id;

      } catch (SQLException e) {
        e.printStackTrace();
        throw new RuntimeException("Uuden tuoteryhmän luominen epäonnistui", e);
      }
    }

  
  

  public void luoUusiTuote(Scanner lukija, Tuotepaivitys paivitys) throws NumberFormatException {
    System.out.print("Anne tuotteen nimi: ");
    String sName = lukija.nextLine();
    System.out.print("Anna hinta ");
    double dHinta = Double.parseDouble(lukija.nextLine());
    System.out.print("Anna päiväys xx.mm.yyyy: ");
    String sPvm = lukija.nextLine();
    System.out.print("Anna tuoteryhmä ID: ");
    int iTuoteryhma = Integer.parseInt(lukija.nextLine());
    System.out.print("Anna tuoteryhmän nimi: ");
    String sTuoteryhmanNimi = lukija.nextLine();
    Tuote t = new Tuote(Tuotepaivitys.luoUusiAvain(), sName, dHinta, Tuotepaivitys.stringToDate(sPvm), iTuoteryhma, sTuoteryhmanNimi);
    paivitys.lisääTuote(t);
  }

  public void tuotteenPaivitys(Scanner lukija, Tuotehaku haku, Tuotepaivitys paivitys) throws NumberFormatException {
    int id = 0; // metodin ylikuormittaminen
    System.out.print("Mikä tuote haetaan: ");
    String sName = lukija.nextLine();
    Tuote[] tuotteet = haku.haeNimellä(sName);
    Tuotepaivitys.tulostaTuotteet(tuotteet);
    Tuote t = haku.haeNimellä(sName, id);
    System.out.println("Päivitä uusi tuoteryhma, anna ryhmän ID: ?");
    int iTuoteryhma = Integer.parseInt(lukija.nextLine());
    t.setTuoteryhmaid(iTuoteryhma);
    paivitys.päivitäTuote(t);
    System.out.println(t);
  }

  public static void tulostaTuotteet(Tuote[] tuotteet) {
    if (tuotteet != null) {
      for (int i = 0; i < tuotteet.length; i++) {
        System.out.println(tuotteet[i]);
      }
    }
  }

  public static java.sql.Date stringToDate(String s) {
    SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    try {
      return new java.sql.Date(formatter.parse(s).getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  public static int luoUusiAvain() {
    return new Integer(new Long(System.currentTimeMillis()).intValue()).intValue() * (-1);
  }
}
