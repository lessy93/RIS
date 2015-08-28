package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import eRekreacija.Objekt;
import eRekreacija.RezervacijaTermina;
import eRekreacija.SportniCenter;
import eRekreacija.Termini;
import eRekreacija.TipSporta;
import eRekreacija.Uporabnik;

public class RezervacijaTerminaDAO {
	
	static DataSource baza;
	public RezervacijaTerminaDAO(DataSource pb){
		baza=pb;	
	}
	
	public void shraniRezervacijoTermina(RezervacijaTermina termin, Uporabnik uporabnik) throws Exception {
		Connection conn= null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = ("INSERT INTO rezervacijatermina (uporabnik_idUporabnik, termini_idTermini) VALUES(?,?)");
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, termin.getTermini().getId_Termini());
			st.setInt(2, uporabnik.getId_Uporabnik());


			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}
			st.executeUpdate();
			
		}catch (Exception e){
			e.printStackTrace();
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}
				
	}
}
/*
	public List<RezervacijaTermina> getRezervacijeByIdUpo(int idUporabnika) throws Exception{
		List<RezervacijaTermina> seznamRezervacij = new ArrayList<RezervacijaTermina>();
		Connection conn= null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM rezervacijatermina r, uporabnik u, termini t, objekt o, sportnicenter sc WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND o.idObjekta=t.objekt_idObjekt AND o.sportnicenter_idSportnicenter=sc.idSportniCenter AND r.uporabnik_idUporabnik=?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idUporabnika);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				RezervacijaTermina rezervacija = new RezervacijaTermina();
				SportniCenter sportniCenter = new SportniCenter();
				Termini termini = new Termini();
				Uporabnik uporabnik = new Uporabnik();
				Objekt tempObjekt =  new Objekt();
				
				
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
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				termini.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				termini.setKoncniCas(koncniC);				
				termini.setId_Termini(rs.getInt("idTermini"));
				termini.setDatum(dat);
				termini.setZasedenost(rs.getBoolean("zasedenost"));
				termini.setObjekt(tempObjekt); 
					
				rezervacija.setId_RezervacijaTermina(rs.getInt("idRezervacijaTermina"));
				rezervacija.setTermini(termini);
				rezervacija.setUporabnik(uporabnik);
				
				seznamRezervacij.add(rezervacija);
			}	
		}catch (Exception e){
			e.printStackTrace();
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}
		return seznamRezervacij;
	}
	
	
	public List<RezervacijaTermina> getRezervacijeByIdObjekt(int idObjekt) throws Exception{
		List<RezervacijaTermina> seznamRezervacij = new ArrayList<RezervacijaTermina>();
		Connection conn= null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM rezervacijatermina r, uporabnik u, termini t, objekt o, sportnicenter sc WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND o.idObjekta=t.objekt_idObjekt AND o.sportnicenter_idSportnicenter=sc.idSportniCenter AND sc.idSportniCenter = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, idObjekt);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				RezervacijaTermina rezervacija = new RezervacijaTermina();
				SportniCenter sportniCenter = new SportniCenter();
				Termini termini = new Termini();
				Uporabnik uporabnik = new Uporabnik();
				Objekt tempObjekt =  new Objekt();
				
				
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
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				termini.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				termini.setKoncniCas(koncniC);				
				termini.setId_Termini(rs.getInt("idTermini"));
				termini.setDatum(dat);
				termini.setZasedenost(rs.getBoolean("zasedenost"));
				termini.setObjekt(tempObjekt); 
					
				rezervacija.setId_RezervacijaTermina(rs.getInt("idRezervacijaTermina"));
				rezervacija.setTermini(termini);
				rezervacija.setUporabnik(uporabnik);
				
				seznamRezervacij.add(rezervacija);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return seznamRezervacij;
	}	
	
	public RezervacijaTermina getRezervacijeById(int id) throws Exception{
		RezervacijaTermina rezervacija = new RezervacijaTermina();
		Connection conn= null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM rezervacijaDvorane r, uporabnik u, termini t, dvorana d, sportniobjekt s, tipsporta tps WHERE r.uporabnik_idUporabnik=u.idUporabnik AND r.termini_idTermini=t.idTermini AND d.idDvorana=t.dvorana_idDvorana AND d.sportniobjekt_idSportniObjekt=s.idSportniObjekt AND d.tipsporta_idTipSporta = tps.idTipSporta AND r.idRezervacijaDvorane =?";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, id);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
					
				SportniCenter sportniCenter = new SportniCenter();
				Termini termin = new Termini();
				Uporabnik uporabnik = new Uporabnik();
				Objekt tempObjekt =  new Objekt();
				TipSporta tip = new TipSporta();
				
				uporabnik.setId_Uporabnik(rs.getInt("idUporabnik"));
				uporabnik.setIme(rs.getString("ime"));
				uporabnik.setPriimek(rs.getString("priimek"));
				uporabnik.setEmail(rs.getString("email"));
				uporabnik.setAktiven_Uporabnik(rs.getBoolean("aktiven"));
				
				sportniCenter.setId_SportniCenter(rs.getInt("idSportniCenter"));
				sportniCenter.setLokacija(rs.getString("lokacija_centra"));
				sportniCenter.setNaziv_SportniCenter(rs.getString("naziv_centra"));
				
				tip.setId_TipSporta(rs.getInt("d.tipsporta_idTipSporta"));
				tip.setNaziv_TipSporta(rs.getString("tps.naziv"));
							
				tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
				tempObjekt.setNaziv_Objekta(rs.getString("naziv_objekta"));
				tempObjekt.setTipObjekta(rs.getString("tipObjekta"));
				tempObjekt.setTipSporta(tip);
				tempObjekt.setSportniCenter(sportniCenter);
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
					
				termin.setZacetniCas(zacetniC);
					
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
					
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
					
				termin.setKoncniCas(koncniC);				
				termin.setId_Termini(rs.getInt("idTermini"));
				termin.setDatum(dat);
				termin.setZasedenost(rs.getBoolean("zasedenost"));
				termin.setObjekt(tempObjekt); 
						
				rezervacija.setId_RezervacijaTermina(rs.getInt("idRezervacijaTermina"));
				rezervacija.setTermini(termin);
				rezervacija.setUporabnik(uporabnik);

			}
			
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}		
		return rezervacija;
	}	
}
*/