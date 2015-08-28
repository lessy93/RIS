package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import eRekreacija.Objekt;
import eRekreacija.Termini;

public class TerminiDAO {
	static DataSource baza;
	public TerminiDAO(DataSource pb){
		baza=pb;	
	}
	
	public TerminiDAO() {
		// TODO Auto-generated constructor stub
	}

	public Termini shraniTermin(Termini termin) throws Exception {
	
		int id =0;
		Connection conn= null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = ("INSERT INTO termini (datum, zacetniCas, koncniCas, zasedenost, Dvorana_idDvorana) VALUES(?,?,?,?,?)");
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println("ble"+ new java.sql.Date(termin.getZacetniCas().getTime()) );
			st.setDate(1, new java.sql.Date(termin.getZacetniCas().getTime()));
			
			
			System.out.println("ble2"+ termin.getZacetniCas() );
			System.out.println("ble3"+ termin.getZacetniCas().toString() );
			System.out.println("ble2"+ termin.getZacetniCas() );
			
			
		
			
			st.setDate(2, new java.sql.Date(termin.getZacetniCas().getTime()));
			st.setDate(3, new java.sql.Date(termin.getKoncniCas().getTime()));
			st.setBoolean(4, termin.getZasedenost());
			st.setInt(5, termin.getObjekt().getId_Objekta());
			
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				id = rs.getInt(1);
				System.out.println("Id vnosa: " + id);
				termin.setId_Termini(id);
			}
			
			
		}catch (Exception e){
			e.printStackTrace();
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}
		return termin;
	}
	
/*	public Termini getTerminByIDDvorInCas(int idObjekt,Calendar date, Calendar cas) throws Exception {
		Connection conn = null;
		Termini temp = new Termini();
		
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		
		String cs = sf.format(cas.getTime());
		String ds= df.format(date.getTime());
		
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM termini WHERE objekt_idObjekt=? AND zacetniCas =? AND datum=?";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, idObjekt);
			prst.setString(2,cs);
			prst.setString(3,ds);
	            
	            
			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
	             
				Objekt tempObjekt =  new Objekt();
					
				tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
					
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar   zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
					
				temp.setZacetniCas(zacetniC);
					
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
					
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
					
				temp.setKoncniCas(koncniC);
					
				temp.setId_Termini(rs.getInt("idTermini"));
				temp.setDatum(dat);
				temp.setZasedenost(rs.getBoolean("zasedenost"));
				temp.setObjekt(tempObjekt);
			}							
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
		System.out.println("Connection CLOSED!");
		}		
		return temp;
	}	
	
	
	public List<Termini> getTerminis() throws Exception{
		List<Termini> seznamTerminov = new ArrayList<Termini>();
		Connection conn= null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM termini ORDER BY datum;";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				Termini tempTermin = new Termini();
				Objekt tempObjekt =  new Objekt();
				
				tempObjekt.setId_Objekta(rs.getInt("dvorana_idDvorana"));
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar   zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				tempTermin.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				tempTermin.setKoncniCas(koncniC);
				
				tempTermin.setId_Termini(rs.getInt("idTermini"));
				tempTermin.setDatum(dat);
				tempTermin.setZasedenost(rs.getBoolean("zasedenost"));
				tempTermin.setObjekt(tempObjekt);
				
				
				seznamTerminov.add(tempTermin);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return seznamTerminov;
	}
	
	
	public List<Termini> getTerminiBYidObjekt(int idObjekta) throws Exception{
		List<Termini> seznamTerminov = new ArrayList<Termini>();
		Connection conn=null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM termini WHERE objekt_idObjekt = ? ";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, idObjekta);
			ResultSet rs = prst.executeQuery();
			while (rs.next()){
				Termini tempTermin = new Termini();
				Objekt tempObjekt =  new Objekt();
				
				tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar   zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				tempTermin.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				tempTermin.setKoncniCas(koncniC);
				
				tempTermin.setId_Termini(rs.getInt("idTermini"));
				tempTermin.setDatum(dat);
				tempTermin.setZasedenost(rs.getBoolean("zasedenost"));
				tempTermin.setObjekt(tempObjekt);
				
				
				seznamTerminov.add(tempTermin);
			}
		
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}
	
		return seznamTerminov;
	}
	
	public Termini getTerminById(int id) throws Exception {
		Termini ter = new Termini();
		Connection conn = null;
        try {
        	try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
            String sql = "SELECT * FROM Termini WHERE idTermini= ?;";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setInt(1, id);

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
            	
				Objekt tempObjekt =  new Objekt();		
				tempObjekt.setId_Objekta(rs.getInt("objekt_idObjekt"));
				
				java.sql.Time zacetniCas =rs.getTime("zacetniCas");
				Calendar zacetniC = Calendar.getInstance();  
				zacetniC.setTime(zacetniCas);
				
				ter.setZacetniCas(zacetniC);
				
				java.sql.Time koncniCas =rs.getTime("koncniCas");
				Calendar   koncniC = Calendar.getInstance();  
				koncniC.setTime(koncniCas);
				
				java.sql.Date datum =rs.getDate("datum");
				Calendar   dat = Calendar.getInstance();  
				dat.setTime(datum);
				
				ter.setKoncniCas(koncniC);				
				ter.setId_Termini(rs.getInt("idTermini"));
				ter.setDatum(dat);
				ter.setZasedenost(rs.getBoolean("zasedenost"));
				ter.setObjekt(tempObjekt);           	
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}
        return ter;
    }
	
	*/
}
