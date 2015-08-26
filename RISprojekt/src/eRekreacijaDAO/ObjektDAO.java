package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import eRekreacija.Objekt;
import eRekreacija.SportniCenter;
import eRekreacija.TipSporta;

public class ObjektDAO {
	
	static DataSource baza;
	public ObjektDAO(DataSource pb){
		baza=pb;	
	}
	
	public void shraniObjekt(Objekt objekt) throws Exception {
		Connection conn = null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = "insert into objekt (naziv_objekta, tipObjekta, opis_objekta, cena_objekta,tipsporta_idTipSporta ,sportnicenter_idSportnicenter) values(?, ?, ?, ?, ?,?)";
			PreparedStatement st=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, objekt.getNaziv_Objekta());
			st.setString(2, objekt.getTipObjekta());
			st.setString(3, objekt.getOpis_Objekta());
			st.setString(4, objekt.getCena_Objekta());
			st.setInt(5, objekt.getTipSporta().getId_TipSporta());
			st.setInt(6, objekt.getSportniCenter().getId_SportniCenter());
			st.executeUpdate();
			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}

		}catch(Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}			
	}

	public List<Objekt> getSeznamObjektov() throws Exception{
		List<Objekt> seznamObjektov = new ArrayList<Objekt>();
		Connection conn=null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM objekt o, tipsporta ts WHERE ts.idTipSporta=d.tipsporta_idTipSporta;";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				
				Objekt tempObjekt = new Objekt();
				SportniCenter sportniCenter = new SportniCenter();
				TipSporta tipSporta = new TipSporta();
				
				sportniCenter.setId_SportniCenter(rs.getInt("sportnicenter_idSportniCenter"));
				tipSporta.setId_TipSporta(rs.getInt("tipsporta_idTipSporta"));
				tipSporta.setNaziv_TipSporta(rs.getString("ts.naziv_sporta"));
				
				tempObjekt.setId_Objekta(rs.getInt("idObjekta"));
				tempObjekt.setNaziv_Objekta(rs.getString("naziv_objekta"));
				tempObjekt.setTipObjekta(rs.getString("tipObjekta"));
				tempObjekt.setOpis_Objekta(rs.getString("opis_objekta"));
				tempObjekt.setCena_Objekta(rs.getString("cena_objekta"));
				tempObjekt.setSportniCenter(sportniCenter);
				tempObjekt.setTipSporta(tipSporta);
				
				seznamObjektov.add(tempObjekt);		
			}
			
		}catch (Exception e){
			System.out.println("Napaka Dvorana list!");
		} finally {				
			conn.close();
		System.out.println("Connection CLOSED!");
		} 
		return seznamObjektov;
	}
	
	
	public List<Objekt> getObjektById(int idObjekt) throws Exception{
		List<Objekt> seznamObjektov = new ArrayList<Objekt>();
		Connection conn=null; 
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM objekt o, sportnicenter sc,tipsporta ts WHERE sportnicenter_idSportniCenter = ? AND sc.idSportnicenter=o.sportniCenter_idSportniCenter AND ts.idTipSporta=o.tipsporta_idTipsporta;";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, idObjekt);
			ResultSet rs = prst.executeQuery();
			while (rs.next()){
				Objekt tempObjekt = new Objekt();
				SportniCenter sportniCenter = new SportniCenter();
				TipSporta tipSporta = new TipSporta();
				
				sportniCenter.setId_SportniCenter(rs.getInt("sportniobjekt_idSportniObjekt"));
				tipSporta.setId_TipSporta(rs.getInt("tipsporta_idTipSporta"));
				tipSporta.setNaziv_TipSporta(rs.getString("ts.naziv_sporta"));
				
				tempObjekt.setId_Objekta(rs.getInt("idObjekta"));
				tempObjekt.setNaziv_Objekta(rs.getString("naziv_objekta"));
				tempObjekt.setTipObjekta(rs.getString("tipObjekta"));
				tempObjekt.setOpis_Objekta(rs.getString("opis_objekta"));
				tempObjekt.setCena_Objekta(rs.getString("cena_objekta"));
				tempObjekt.setSportniCenter(sportniCenter);
				tempObjekt.setTipSporta(tipSporta);
				
				seznamObjektov.add(tempObjekt);
			}
			
		} catch (Exception e){
			System.out.println("Napaka ListDvoranbyID");
			System.out.println(e.getStackTrace());
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		} 		
		return seznamObjektov; 
	}
	
	public boolean deleteDvoranaById(int id) throws Exception {
        boolean deleted = true;
        Connection conn = null;
        try {
        	try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
            String sql = "DELETE FROM objekt WHERE idObjekta = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            st.executeUpdate();

        } catch (Exception e) {
            deleted = false;
        } finally {				
			conn.close();
		System.out.println("Connection CLOSED!");
		}    
        return deleted;
    }
 public boolean updateObjekt (Objekt objekt) throws Exception{
		boolean updated = true;
		Connection conn = null;
        try {
        	try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}		
			String sql = "UPDATE objekt SET naziv_objekta =?, tipObjekta =?, opis_objekta =?, cena_objekta=?, sportnicenter_idSportniCenter=?, tipsporta_idTipsporta =?  WHERE idObjekta = ?";
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, objekt.getNaziv_Objekta());
			st.setString(2, objekt.getTipObjekta());
			st.setString(3, objekt.getOpis_Objekta());
			st.setString(4, objekt.getCena_Objekta());
			st.setInt(5, objekt.getSportniCenter().getId_SportniCenter());
			st.setInt(6, objekt.getTipSporta().getId_TipSporta());
			st.setInt(7, objekt.getId_Objekta());
			
			st.executeUpdate();
			
		}catch (Exception e){
			updated = false;
		} finally {				
			conn.close();
		System.out.println("Connection CLOSED!");
		}    		
		return updated;
	}
	
}