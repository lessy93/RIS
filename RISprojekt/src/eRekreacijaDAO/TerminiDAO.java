package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import eRekreacija.Email;
import eRekreacija.Objekt;
import eRekreacija.RezervacijaTermina;
import eRekreacija.SportniCenter;
import eRekreacija.Termini;
import eRekreacija.Uporabnik;
import eRekreacija.Util;

public class TerminiDAO {
	static DataSource baza;

	public TerminiDAO(DataSource pb) {
		baza = pb;
	}

	public TerminiDAO() {
	}

	public Termini shraniTermin(Termini termin) throws Exception {

		int id = 0;
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = ("INSERT INTO termini (datum, zacetniCas, koncniCas, zasedenost, Objekt_idObjekt, cenaTermina) VALUES(?,?,?,?,?,?)");
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("TERMINDAO zac cas:" + new java.sql.Date(termin.getZacetniCas().getTime()));

			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String formatedZacetniCas = sdf.format(termin.getZacetniCas());
			String formateKoncniCas = sdf.format(termin.getKoncniCas());

			Objekt ob = new Objekt();
			ob = termin.getObjekt();
			Email posljiEmail = new Email();
			Uporabnik upor = new Uporabnik();
			HttpSession session = Util.getSession();
			double cena = izracunajCeno(termin, ob);// metoda za izracun cene
													// rezervacije
			upor = (Uporabnik) session.getAttribute("uporabnik");// uporabnika
																	// pridobimo
																	// iz seje
			
			st.setDate(1, new java.sql.Date(termin.getZacetniCas().getTime()));
			st.setString(2, formatedZacetniCas);
			st.setString(3, formateKoncniCas);
			st.setBoolean(4, termin.getZasedenost());
			st.setInt(5, termin.getObjekt().getId_Objekta());
			st.setDouble(6, cena);

			st.executeUpdate();
			
			try {
				posljiEmail.posljiEmailRezervacija(upor, termin, ob, cena);// pošiljanje
																			// email-a
																			// uporabniku
			} catch (Exception e) {
				System.out.println("Napaka! shraniTermin- POŠILJANJE MAILA");
			}
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				id = rs.getInt(1);
				System.out.println("Id vnosa: " + id);
				termin.setId_Termini(id);
			}

		} catch (Exception e) {
			System.out.println("Napaka! shraniTermin!");
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return termin;
	}

	// izracun cene za rezervacijo
	private double izracunajCeno(Termini ptermin, Objekt noviObjekt) {
		int izracunajMinute = (int) Math
				.round(((ptermin.getKoncniCas().getTime() - ptermin.getZacetniCas().getTime()) / (1000 * 60)));// dobimo
																												// minute
		Double cenaObjekta = noviObjekt.getCena_Objekta();
		System.out.println("____________________________");
		System.out.println("Izracunan Cas: " + izracunajMinute);

		System.out.println("Cena objekta:" + noviObjekt.getCena_Objekta());
		Double cenaRezervacije = (izracunajMinute / 60) * cenaObjekta;

		System.out.println("Cena Rezervacije:" + cenaRezervacije);
		System.out.println("____________________________");

		return cenaRezervacije;
	}
	
	public List<Termini> getTermineByIdUpo(int idUporabnika) throws Exception {
		List<Termini> seznamRezervacij = new ArrayList<Termini>();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}

			String sql = "SELECT * FROM rezervacijatermina r, uporabnik u, termini t, objekt o, sportnicenter sc WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND o.idObjekta=t.Objekt_idObjekt AND o.sportnicenter_idSportnicenter=sc.idSportniCenter AND r.uporabnik_idUporabnik=? ORDER BY t.zacetniCas";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idUporabnika);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {

				RezervacijaTermina rezervacija = new RezervacijaTermina();
				SportniCenter sportniCenter = new SportniCenter();
				Termini termini = new Termini();
				Uporabnik uporabnik = new Uporabnik();
				Objekt tempObjekt = new Objekt();

				uporabnik.setId_Uporabnik(rs.getInt("idUporabnik"));
				uporabnik.setIme(rs.getString("ime"));
				uporabnik.setPriimek(rs.getString("priimek"));
				uporabnik.setEmail(rs.getString("email"));
				uporabnik.setAktiven_Uporabnik(rs.getBoolean("aktiven"));

				sportniCenter.setId_SportniCenter(rs.getInt("idSportnicenter"));
				sportniCenter.setLokacija(rs.getString("lokacija_centra"));
				sportniCenter.setNaziv_SportniCenter(rs.getString("naziv_centra"));

				tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
				tempObjekt.setNaziv_Objekta(rs.getString("naziv_objekta"));
				tempObjekt.setTipObjekta(rs.getString("tipObjekta"));
				tempObjekt.setSportniCenter(sportniCenter);
				
				Date zacetniCas=rs.getTimestamp("zacetniCas");
				
				termini.setZacetniCas(zacetniCas);
				java.sql.Date datum = rs.getDate("datum");
				Date koncniCas = rs.getTimestamp("koncniCas");

		
			
				termini.setKoncniCas(koncniCas);
				termini.setId_Termini(rs.getInt("idTermini"));
				termini.setDatum(datum);
				termini.setCenaTermina(rs.getDouble("cenaTermina"));
				termini.setZasedenost(rs.getBoolean("zasedenost"));
				termini.setObjekt(tempObjekt);

				rezervacija.setId_RezervacijaTermina(rs.getInt("idRezervacijaTermina"));
				rezervacija.setTermini(termini);
				rezervacija.setUporabnik(uporabnik);

				seznamRezervacij.add(termini);
			}
		} catch (Exception e) {
			System.out.println("Napaka! rezervacija termina!!");
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return seznamRezervacij;
	}


	public ArrayList<Termini> getTerminiBYidObjekt(int idObjekta) throws Exception {
		ArrayList<Termini> seznamTerminov = new ArrayList<Termini>();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM termini WHERE objekt_idObjekt = ? ";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, idObjekta);
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				Termini tempTermin = new Termini();
				Objekt tempObjekt = new Objekt();

				tempObjekt.setId_Objekta(rs.getInt("Objekt_idObjekt"));
				Date zacetniCas = rs.getTimestamp("zacetniCas");
				tempTermin.setZacetniCas(zacetniCas);
				java.sql.Date datum = rs.getDate("datum");
				Date koncniCas = rs.getTimestamp("koncniCas");

				tempTermin.setKoncniCas(koncniCas);
				tempTermin.setId_Termini(rs.getInt("idTermini"));
				tempTermin.setDatum(datum);
				tempTermin.setZasedenost(rs.getBoolean("zasedenost"));
				tempTermin.setObjekt(tempObjekt);
				seznamTerminov.add(tempTermin);
			}

		} catch (Exception e) {
			System.out.println("Napaka! getTerminByIdObjekt!");
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}

		return seznamTerminov;
	}
	/*
	 * public Termini getTerminByIDDvorInCas(int idObjekt,Calendar date,
	 * Calendar cas) throws Exception { Connection conn = null; Termini temp =
	 * new Termini();
	 * 
	 * SimpleDateFormat sf = new SimpleDateFormat("HH:mm"); SimpleDateFormat df
	 * = new SimpleDateFormat("yyyy-MM-dd");
	 * 
	 * String cs = sf.format(cas.getTime()); String ds=
	 * df.format(date.getTime());
	 * 
	 * try{ try { conn= baza.getConnection(); System.out.println(
	 * "Connection OPEN!"); }catch(Exception e){ System.out.println(
	 * "Napaka Connection!"); } String sql =
	 * "SELECT * FROM termini WHERE objekt_idObjekt=? AND zacetniCas =? AND datum=?"
	 * ; PreparedStatement prst = conn.prepareStatement(sql); prst.setInt(1,
	 * idObjekt); prst.setString(2,cs); prst.setString(3,ds);
	 * 
	 * 
	 * ResultSet rs = prst.executeQuery(); while (rs.next()) {
	 * 
	 * Objekt tempObjekt = new Objekt();
	 * 
	 * tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
	 * 
	 * java.sql.Time zacetniCas =rs.getTime("zacetniCas"); Calendar zacetniC =
	 * Calendar.getInstance(); zacetniC.setTime(zacetniCas);
	 * 
	 * temp.setZacetniCas(zacetniC);
	 * 
	 * java.sql.Time koncniCas =rs.getTime("koncniCas"); Calendar koncniC =
	 * Calendar.getInstance(); koncniC.setTime(koncniCas);
	 * 
	 * java.sql.Date datum =rs.getDate("datum"); Calendar dat =
	 * Calendar.getInstance(); dat.setTime(datum);
	 * 
	 * temp.setKoncniCas(koncniC);
	 * 
	 * temp.setId_Termini(rs.getInt("idTermini")); temp.setDatum(dat);
	 * temp.setZasedenost(rs.getBoolean("zasedenost"));
	 * temp.setObjekt(tempObjekt); } }catch (Exception e){ e.printStackTrace();
	 * } finally { conn.close(); System.out.println("Connection CLOSED!"); }
	 * return temp; }
	 * 
	 * 
	 * public List<Termini> getTerminis() throws Exception{ List<Termini>
	 * seznamTerminov = new ArrayList<Termini>(); Connection conn= null; try{
	 * try { conn= baza.getConnection(); System.out.println("Connection OPEN!");
	 * }catch(Exception e){ System.out.println("Napaka Connection!"); } String
	 * sql ="SELECT * FROM termini ORDER BY datum;"; PreparedStatement st =
	 * conn.prepareStatement(sql); ResultSet rs = st.executeQuery(); while
	 * (rs.next()){ Termini tempTermin = new Termini(); Objekt tempObjekt = new
	 * Objekt();
	 * 
	 * tempObjekt.setId_Objekta(rs.getInt("dvorana_idDvorana"));
	 * 
	 * java.sql.Time zacetniCas =rs.getTime("zacetniCas"); Calendar zacetniC =
	 * Calendar.getInstance(); zacetniC.setTime(zacetniCas);
	 * 
	 * tempTermin.setZacetniCas(zacetniC);
	 * 
	 * java.sql.Time koncniCas =rs.getTime("koncniCas"); Calendar koncniC =
	 * Calendar.getInstance(); koncniC.setTime(koncniCas);
	 * 
	 * java.sql.Date datum =rs.getDate("datum"); Calendar dat =
	 * Calendar.getInstance(); dat.setTime(datum);
	 * 
	 * tempTermin.setKoncniCas(koncniC);
	 * 
	 * tempTermin.setId_Termini(rs.getInt("idTermini"));
	 * tempTermin.setDatum(dat);
	 * tempTermin.setZasedenost(rs.getBoolean("zasedenost"));
	 * tempTermin.setObjekt(tempObjekt);
	 * 
	 * 
	 * seznamTerminov.add(tempTermin); }
	 * 
	 * }catch (Exception e){ e.printStackTrace(); } finally { conn.close();
	 * System.out.println("Connection CLOSED!"); } return seznamTerminov; }
	 */

	/*
	 * public Termini getTerminById(int id) throws Exception { Termini ter = new
	 * Termini(); Connection conn = null; try { try { conn=
	 * baza.getConnection(); System.out.println("Connection OPEN!");
	 * }catch(Exception e){ System.out.println("Napaka Connection!"); } String
	 * sql = "SELECT * FROM Termini WHERE idTermini= ?;"; PreparedStatement prst
	 * = conn.prepareStatement(sql); prst.setInt(1, id);
	 * 
	 * ResultSet rs = prst.executeQuery(); while (rs.next()) {
	 * 
	 * Objekt tempObjekt = new Objekt();
	 * tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
	 * 
	 * java.sql.Time zacetniCas =rs.getTime("zacetniCas"); Calendar zacetniC =
	 * Calendar.getInstance(); zacetniC.setTime(zacetniCas);
	 * 
	 * ter.setZacetniCas(zacetniC);
	 * 
	 * java.sql.Time koncniCas =rs.getTime("koncniCas"); Calendar koncniC =
	 * Calendar.getInstance(); koncniC.setTime(koncniCas);
	 * 
	 * java.sql.Date datum =rs.getDate("datum"); Calendar dat =
	 * Calendar.getInstance(); dat.setTime(datum);
	 * 
	 * ter.setKoncniCas(koncniC); ter.setId_Termini(rs.getInt("idTermini"));
	 * ter.setDatum(dat); ter.setZasedenost(rs.getBoolean("zasedenost"));
	 * ter.setObjekt(tempObjekt); } } catch (Exception e) { e.printStackTrace();
	 * } finally { conn.close(); System.out.println("Connection CLOSED!"); }
	 * return ter; }
	 * 
	 */
}
