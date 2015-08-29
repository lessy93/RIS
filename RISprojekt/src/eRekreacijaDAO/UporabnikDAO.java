package eRekreacijaDAO;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import eRekreacija.SportniCenter;
import eRekreacija.Uporabnik;
import eRekreacija.Util;

public class UporabnikDAO {
	static DataSource baza;

	public UporabnikDAO(DataSource pb) throws Exception {
		baza = pb;

	}

	public UporabnikDAO() {
	}

	// kodiranje gesla v SHA1
	public static String sha1(String geslo) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(geslo.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public void shraniUporabnika(Uporabnik upor) throws Exception {
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}

			PreparedStatement st = conn.prepareStatement(
					"INSERT INTO uporabnik(ime, priimek, email, geslo, aktiven, Sportnicenter_idSportnicenter, admin) VALUES (?,?,?,?,?,?,?)");
			st.setString(1, upor.getIme());
			st.setString(2, upor.getPriimek());
			st.setString(3, upor.getEmail());
			st.setString(4, sha1(upor.getGeslo())); // kodiranje gesla v SHA1
			st.setBoolean(5, upor.getAktiven_Uporabnik());
			st.setInt(6, upor.getSportniCenter().getId_SportniCenter());
			st.setBoolean(7, upor.isAdmin());
			st.executeUpdate();
			
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
	}

	public Uporabnik prijaviUporabnika(String email, String geslo) throws Exception {
		Uporabnik upor = new Uporabnik();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM uporabnik u, sportnicenter s WHERE u.email=? AND u.geslo=? AND u.SportniCenter_idSportniCenter=s.idSportnicenter AND u.aktiven='1'";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setString(1, email);
			prst.setString(2, sha1(geslo)); // kodiranje gesla v SHA1

			ResultSet rs = prst.executeQuery();
			if (rs.next()) // found
			{
				SportniCenter tempCenter = new SportniCenter();
				upor.setIme(rs.getString("ime"));
				upor.setPriimek(rs.getString("priimek"));
				upor.setEmail(rs.getString("email"));
				upor.setGeslo(rs.getString("geslo"));
				upor.setAktiven_Uporabnik(rs.getBoolean("aktiven"));
				upor.setId_Uporabnik(rs.getInt("idUporabnik"));
				upor.setAktiven_Uporabnik(rs.getBoolean("admin"));
				tempCenter.setId_SportniCenter(rs.getInt("sportniCenter_idSportnicenter"));
				tempCenter.setNaziv_SportniCenter(rs.getString("naziv_centra"));
				tempCenter.setOpis_SportniCenter(rs.getString("opis_centra"));
				tempCenter.setLokacija(rs.getString("lokacija_centra"));
				tempCenter.setMapsLat(rs.getFloat("mapsLat"));
				tempCenter.setMapsLng(rs.getFloat("mapsLng"));
				tempCenter.setAktiven__SportniCenter(rs.getBoolean("aktiven"));

				upor.setSportniCenter(tempCenter);

				return upor;
			} else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println("NAPAKA! Prijavi uporabnika DAO" + ex.toString());
			return null;

		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
	}

	// Pridobi vse aktivne uporabnike
	public List<Uporabnik> getUporabniks() throws Exception {
		List<Uporabnik> seznamUporabnikov = new ArrayList<Uporabnik>();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = "SELECT * FROM uporabnik WHERE aktiven='da';";
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Uporabnik tempUporabnik = new Uporabnik();
				tempUporabnik.setId_Uporabnik(rs.getInt("idUporabnik"));
				tempUporabnik.setIme(rs.getString("ime"));
				tempUporabnik.setPriimek(rs.getString("priimek"));
				tempUporabnik.setEmail(rs.getString("email"));
				tempUporabnik.setAktiven_Uporabnik(rs.getBoolean("aktiven"));
				seznamUporabnikov.add(tempUporabnik);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return seznamUporabnikov;
	}

	public void deleteUporabnikById(int idUporabnik) throws Exception {

		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = "UPDATE uporabnik SET aktiven='0' WHERE idUporabnik = 1";
			PreparedStatement st = conn.prepareStatement(sql);
			// st.setInt(1, idUporabnik);
			st.executeUpdate();

		} catch (SQLException e) {
			System.out.println("Napaka!" + e.toString());
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}

	}

	public Uporabnik getUporabnikById(int idUporabnika) throws Exception {
		Uporabnik up = new Uporabnik();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}

			String sql = "SELECT * FROM uporabnik WHERE idUporabnik = ? ";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, idUporabnika);

			ResultSet rs = prst.executeQuery();
			while (rs.next()) {
				SportniCenter objekt = new SportniCenter();
				objekt.setId_SportniCenter(rs.getInt("sportniobjekt_idSportniObjekt"));

				up.setId_Uporabnik(rs.getInt("idUporabnik"));
				up.setIme(rs.getString("ime"));
				up.setPriimek(rs.getString("priimek"));
				up.setEmail(rs.getString("email"));
				up.setGeslo(rs.getString("geslo"));
				up.setSportniCenter(objekt);
				up.setAktiven_Uporabnik(rs.getBoolean("aktiven"));
				up.setAdmin(rs.getBoolean("admin"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}

		System.out.print(up.toString());
		return up;
	}

	public boolean updateUporabnik(Uporabnik up) throws Exception {
		boolean updated = true;
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection!");
			}
			String sql = "UPDATE uporabnik SET ime =?, priimek =?, email =?, geslo =?, aktiven =? WHERE idUporabnik = ?";
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, up.getIme());
			st.setString(2, up.getPriimek());
			st.setString(3, up.getEmail());
			st.setString(4, sha1(up.getGeslo()));
			st.setBoolean(5, up.getAktiven_Uporabnik());
			st.setInt(6, up.getId_Uporabnik());

			st.executeUpdate();
			System.out.println("Urejanje profila uspešno!");

		} catch (SQLException e) {
			System.out.println("Napaka! Uredi uporabnika DAO" + e.toString());
			updated = false;
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");

		}
		return updated;
	}

	public List<SportniCenter> vrniMojCenter() throws SQLException {
		List<SportniCenter> centerVrni = new ArrayList<SportniCenter>();
		Connection conn = null;
		try {
			try {
				conn = baza.getConnection();
				System.out.println("Connection OPEN!");
			} catch (Exception e) {
				System.out.println("Napaka Connection-!!!!");
			}

			Uporabnik up = new Uporabnik();
			HttpSession session = Util.getSession();
			up = (Uporabnik) session.getAttribute("uporabnik");
			int id = up.getId_Uporabnik();
			System.out.println("Id- je moj?" + id);

			String sql = "SELECT * FROM sportnicenter sc, uporabnik u WHERE u.idUporabnik=? AND  u.Sportnicenter_idSportniCenter=sc.idSportnicenter;";
			PreparedStatement prst = conn.prepareStatement(sql);
			prst.setInt(1, id);
			ResultSet rs = prst.executeQuery();

			while (rs.next()) {
				// Objekt center = new Objekt();
				SportniCenter sportniCenter = new SportniCenter();
				// TipSporta tipSporta = new TipSporta();

				/*
				 * up.setId_Uporabnik(rs.getInt("idUporabnik"));
				 * up.setIme(rs.getString("ime"));
				 * up.setPriimek(rs.getString("priimek"));
				 * up.setEmail(rs.getString("email"));
				 * up.setGeslo(rs.getString("geslo"));
				 * up.setAktiven_Uporabnik(rs.getBoolean("aktiven"));
				 * up.setSportniCenter(sportniCenter);
				 */

				sportniCenter.setId_SportniCenter(rs.getInt("idSportnicenter"));
				System.out.println("id center" + sportniCenter.getId_SportniCenter());
				sportniCenter.setNaziv_SportniCenter(rs.getString("naziv_centra"));
				sportniCenter.setOpis_SportniCenter(rs.getString("opis_cenra"));
				sportniCenter.setLokacija(rs.getString("lokacija_centra"));
				sportniCenter.setAktiven__SportniCenter(rs.getBoolean("aktiven"));
				sportniCenter.setMapsLat(rs.getInt("mapsLat"));
				sportniCenter.setMapsLng(rs.getInt("mapsLng"));

				centerVrni.add(sportniCenter);

			}
			if (!rs.first()) {
				System.out.println("NI ZADETKOV!");
			}
		} catch (Exception e) {
			System.out.println("Napaka! vrniMojCenter" + e.toString());
		} finally {
			conn.close();
			System.out.println("Connection CLOSED!");
		}
		return centerVrni;
	}
}
