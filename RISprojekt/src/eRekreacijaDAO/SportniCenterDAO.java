package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import eRekreacija.Objekt;
import eRekreacija.SportniCenter;
import eRekreacija.TipSporta;

public class SportniCenterDAO {
	
static DataSource baza;
	
	public SportniCenterDAO(DataSource pb) throws Exception{
		baza = pb;
	
	}
	//VNOS CENTRA
	public void shraniSportniCenter(SportniCenter sportniCenter) throws Exception {
		Connection conn= null; 
		
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN center!");
			}catch(Exception e){
				System.out.println("Napaka Connection Center!"+ e);
			}
			String sql = ("INSERT INTO sportniCenter (naziv_centra, opis_centra, lokacija_centra, aktiven, mapsLat, mapsLng) VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement st=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, sportniCenter.getNaziv_SportniCenter());
			st.setString(2, sportniCenter.getOpis_SportniCenter());
			st.setString(3, sportniCenter.getLokacija());
			st.setBoolean(4, sportniCenter.getAktiven__SportniCenter());
			st.setFloat(5, sportniCenter.getMapsLat());
			st.setFloat(6, sportniCenter.getMapsLng());
			
		
			st.executeUpdate();
		}catch(Exception e){
			System.out.println("Napaka! ShraniSportniCenter!" + e.toString());
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}	
			
	}
	// ZA SEZNAM VSEH CENTROV
	public List<SportniCenter> getSeznamSportnihCentrov() throws Exception{
		List<SportniCenter> seznamSportniCenter = new ArrayList<SportniCenter>();
		Connection conn= null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			
			String sql ="SELECT * FROM sportnicenter where aktiven= 1;";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs1 = st.executeQuery();
			while (rs1.next()){
				SportniCenter tempSportniCenter = new SportniCenter();
				
				List<Objekt> listObjektov = new ArrayList<>();
				
				tempSportniCenter.setId_SportniCenter(rs1.getInt("idSportniCenter"));
				tempSportniCenter.setNaziv_SportniCenter(rs1.getString("naziv_centra"));
				tempSportniCenter.setOpis_SportniCenter(rs1.getString("opis_centra"));
				tempSportniCenter.setLokacija(rs1.getString("lokacija_centra"));
				tempSportniCenter.setMapsLat(rs1.getFloat("mapsLat"));
				tempSportniCenter.setMapsLng(rs1.getFloat("mapsLng"));
				
				String sql1 ="SELECT * FROM objekt  WHERE sportniCenter_idSportniCenter= " + tempSportniCenter.getId_SportniCenter();
				PreparedStatement st1 = conn.prepareStatement(sql1);
				ResultSet rs2 = st1.executeQuery();
				while (rs2.next()){
					Objekt tempSportniObjekt = new Objekt();
					tempSportniObjekt.setId_Objekta(rs2.getInt("idObjekta"));
					tempSportniObjekt.setNaziv_Objekta(rs2.getString("naziv_objekta"));
					tempSportniObjekt.setOpis_Objekta(rs2.getString("opis_objekta"));
					tempSportniObjekt.setCena_Objekta(rs2.getString("cena_objekta"));
					tempSportniObjekt.setTipObjekta(rs2.getString("tipObjekta"));
			
					listObjektov.add(tempSportniObjekt);
				}

				tempSportniCenter.setSeznamObjektov(listObjektov);
				seznamSportniCenter.add(tempSportniCenter);
			}
			
		}catch (Exception e){ 
			System.out.println("Napaka! getSeznamSportnihCentrov!"+ e.toString());
			
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}		
		return seznamSportniCenter;
	}
	//ZA ISKANJE PO NAZIVU
	public List<SportniCenter> getSportniCenterByNaziv(SportniCenter sc) throws Exception{
		List<SportniCenter> seznamSportniCenter = new ArrayList<SportniCenter>();
		Connection conn=null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT DISTINCT sc.IdSportniCenter, sc.naziv_centra, sc.opis_centra, sc.lokacija_centra, sc.mapsLat, sc.mapsLng FROM sportnicenter sc WHERE naziv_centra LIKE ? AND aktiven=1";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, sc.getNaziv_SportniCenter());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniCenter tempSportniCenter = new SportniCenter();		
				tempSportniCenter.setId_SportniCenter(rs.getInt("idSportniObjekt"));
				tempSportniCenter.setLokacija(rs.getString("lokacija_centra"));
				tempSportniCenter.setNaziv_SportniCenter(rs.getString("naziv_centra"));
				tempSportniCenter.setOpis_SportniCenter(rs.getString("opis_centra"));
				tempSportniCenter.setMapsLat(rs.getFloat("mapsLat"));
				tempSportniCenter.setMapsLng(rs.getFloat("mapsLng"));			
				seznamSportniCenter.add(tempSportniCenter);
			}
			if(!rs.first()){        
				System.out.println("NI ZADETKOV!");
			}
			
		}catch (Exception e){ 
			e.printStackTrace();
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}
		return seznamSportniCenter;
	}
	
	
	public List<SportniCenter> getSportniCenterByTipSporta(TipSporta sport) throws Exception{
		List<SportniCenter> seznamSportniObjekt = new ArrayList<SportniCenter>();
		Connection conn=null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
	
			String sql ="SELECT DISTINCT sc.idSportniCenter, sc.naziv_centra, sc.opis_centra, sc.lokacija_centra, sc.mapsLat, sc.mapsLng FROM sportnicenter sc, objekt o, tipsporta s WHERE s.naziv_sporta LIKE ? AND o.sportniCenter_idSportniCenter=sc.idSportniCenter  AND o.tipSporta_idTipSporta= s.idTipSporta; ";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, sport.getNaziv_TipSporta());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				SportniCenter tempSportniCenter = new SportniCenter();
				
				List<Objekt> listObjektov = new ArrayList<>();
				
				tempSportniCenter.setId_SportniCenter(rs.getInt("idSportniObjekt"));
				tempSportniCenter.setLokacija(rs.getString("lokacija"));
				tempSportniCenter.setNaziv_SportniCenter(rs.getString("naziv"));
				tempSportniCenter.setOpis_SportniCenter(rs.getString("opis"));
				tempSportniCenter.setMapsLat(rs.getFloat("Lat"));
				tempSportniCenter.setMapsLng(rs.getFloat("Lng"));
				
				String sql1 ="SELECT * FROM  objekt  WHERE sportnicenter_idSportnicenter = " + tempSportniCenter.getId_SportniCenter();
				PreparedStatement st1 = conn.prepareStatement(sql1);
				ResultSet rs1 = st1.executeQuery();
				while (rs1.next()){
					Objekt tempObjekt = new Objekt();
					tempObjekt.setId_Objekta(rs1.getInt("idObjekta"));
					tempObjekt.setNaziv_Objekta(rs1.getString("naziv_objekta"));
					tempObjekt.setOpis_Objekta(rs1.getString("opis_objekta"));
					tempObjekt.setTipObjekta(rs1.getString("tipObjekta"));
					tempObjekt.setCena_Objekta(rs.getString("cena_objekta"));
					listObjektov.add(tempObjekt);
				}
				
				tempSportniCenter.setSeznamObjektov(listObjektov);
				seznamSportniObjekt.add(tempSportniCenter);				
			}
			if(!rs.first()){        
				System.out.println("NI ZADETKOV!");
			}
			
		}catch(Exception e){
			System.out.println("Napaka! getSportniCenterByTipSporta!" + e.toString());
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		} 
		return seznamSportniObjekt;
	}
	
	public SportniCenter getCenterById(int id) throws Exception {
		SportniCenter center = new SportniCenter();
		Connection conn =null;
        try {
        	try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}      
            String sql = "SELECT * FROM sportnicenter WHERE idSportniCenter =?";
            PreparedStatement prst = conn.prepareStatement(sql);
            prst.setInt(1, id);

            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
             
            	center.setId_SportniCenter(rs.getInt("idSportniCenter"));
            	center.setLokacija(rs.getString("lokacija_centra"));
            	center.setNaziv_SportniCenter(rs.getString("naziv_centra"));
            	center.setOpis_SportniCenter(rs.getString("opis_centra"));
            	center.setMapsLat(rs.getFloat("mapsLat"));
            	center.setMapsLng(rs.getFloat("mapsLng"));
            	center.setAktiven__SportniCenter(rs.getBoolean("aktiven"));
            }
    	}catch(Exception e){
			System.out.println("Napaka! getCenterByID!" + e.toString());
    	} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}  
		return center;
	}
	
	public boolean deleteCenterById(int idCenter) throws Exception {
        boolean deleted = true;
        Connection conn = null;
        try {
        	try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}  

            String sql = "UPDATE sportnicenter SET aktiven=0 WHERE idSportniCenter = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, idCenter);
            st.executeUpdate();

        } catch (SQLException e) {
            deleted = false;
        } finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}  
        return deleted;
    }
	
	 public boolean updateCenter(SportniCenter center) throws Exception{
			boolean updated = true;
			Connection conn = null;
			try{
				try {
					conn= baza.getConnection();
					System.out.println("Connection OPEN!");
				}catch(Exception e){
					System.out.println("Napaka Connection!");
				}  
				
				String sql = "UPDATE sportnicenter SET naziv_centra =?, opis_centra =?, lokacija_centra =?, mapsLat =?, mapsLng =?, aktiven =? WHERE idSportniCenter = ?";
				PreparedStatement st = conn.prepareStatement(sql);
				
				st.setString(1, center.getNaziv_SportniCenter());
				st.setString(2, center.getOpis_SportniCenter());
				st.setString(3, center.getLokacija());
				st.setFloat(4, center.getMapsLat());
				st.setFloat(5,center.getMapsLng());
				st.setBoolean(6, center.getAktiven__SportniCenter());
				st.setInt(7, center.getId_SportniCenter());

				st.executeUpdate();
				
			}catch (SQLException e){
				updated = false;
			}finally{
				System.out.println("SHRANJENO!" +center);
				conn.close();
			}		
			return updated;
		}
 
	
	
}
