package eRekreacijaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import eRekreacija.SportniCenter;
import eRekreacija.TipSporta;

public class TipSportaDAO {
	static DataSource baza;
	
	public TipSportaDAO(DataSource pb){
		baza = pb;
	}
	
	
	public void shraniTipSporta(TipSporta tip) throws Exception {
		Connection conn= null;		
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql = ("INSERT INTO tipsporta (naziv_sporta) VALUES(?)");
			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, tip.getNaziv_TipSporta());

			ResultSet rs = st.getGeneratedKeys();
			if (rs != null && rs.next()) {
				System.out.println("Id vnosa: " + rs.getLong(1));
			}
			st.executeUpdate();			
		}catch(SQLException e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}	
	}
	
	public List<TipSporta> getTipSports()throws Exception{
		List<TipSporta> seznamTipSporta = new ArrayList<TipSporta>();
		Connection conn= null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT * FROM tipsporta;";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				TipSporta tempTipSporta = new TipSporta();
				
				tempTipSporta.setId_TipSporta(rs.getInt("idTipsporta"));
				tempTipSporta.setNaziv_TipSporta(rs.getString("naziv_sporta"));			
				seznamTipSporta.add(tempTipSporta);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		} finally {				
			conn.close();
			System.out.println("Connection CLOSED!");
		}	
	
		return seznamTipSporta;
	}
	
	//za izpis vseh tipov sportov ki jih ponuja sportni objekt
	public List<TipSporta> getSportiZaCenter(SportniCenter center) throws Exception{
		List<TipSporta> seznamTipovSporta = new ArrayList<TipSporta>();
		Connection conn= null;
		try{
			try {
				conn= baza.getConnection();
				System.out.println("Connection OPEN!");
			}catch(Exception e){
				System.out.println("Napaka Connection!");
			}
			String sql ="SELECT DISTINCT s.naziv_sporta, s.idTipsporta FROM tipsporta s, objekt o, sportnicenter sc WHERE o.tipsporta_idtipsporta= s.idTipSporta AND o.sportniCenter_idSportniCenter=sc.idSportniCenter  AND sc.idSportniCenter= ?;";
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, center.getId_SportniCenter());
			ResultSet rs = st.executeQuery();
			while (rs.next()){
				TipSporta tempTipSporta = new TipSporta();
				
				tempTipSporta.setId_TipSporta(rs.getInt("idTipSporta"));
				tempTipSporta.setNaziv_TipSporta(rs.getString("naziv_sporta"));
				
				seznamTipovSporta.add(tempTipSporta);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		} finally{				
			conn.close();
		System.out.println("Connection CLOSED!");
		}
	
		return seznamTipovSporta;
	}
	
	

}
