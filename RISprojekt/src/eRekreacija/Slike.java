package eRekreacija;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.primefaces.model.UploadedFile;

@ManagedBean(name = "slike")
@SessionScoped
public class Slike {

	private int idSlike;
	private String naziv_slike;
	private Blob slika;
	private SportniCenter sportniCenter;
	private Uporabnik uporabnik;
	private UploadedFile file;

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	public void upload() throws SQLException {
		Connection conn = null;
		if (file != null) {
			try {
				try {
					conn = baza.getConnection();
					System.out.println("Connection OPEN!");
				} catch (Exception e) {
					System.out.println("Napaka Connection!");
				}

				HttpSession session = Util.getSession();
				Uporabnik user = (Uporabnik) session.getAttribute("uporabnik");
				System.out.println("User id:" + user.getId_Uporabnik());
				System.out.println("User id:" + user.getSportniCenter().getId_SportniCenter());
				// Create the statement object
				PreparedStatement st = conn.prepareStatement(
						"INSERT INTO slike(naziv_slike, slika,Sportnicenter_idSportnicenter, Uporabnik_idUporabnik)  VALUES (?,?,?,?)");
				// Set file data
				st.setString(1, "profilna");
				st.setBinaryStream(2, file.getInputstream());
				st.setInt(3, user.getSportniCenter().getId_SportniCenter());
				st.setInt(4, user.getId_Uporabnik());

				// Insert data to the database
				st.executeUpdate();

				FacesMessage message = new FacesMessage("Nalaganje datoteke uspešno! Datoteka ",
						file.getFileName() + "je naložena!");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e) {
				e.printStackTrace();
				// Add error message
				FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka pri nalaganju datoteke!",
						e.getMessage());
				FacesContext.getCurrentInstance().addMessage(null, errorMsg);
			} finally {
				conn.close();
				System.out.println("Connection CLOSED!");
			}
		}
	}

	public void getSlike() throws SQLException {
		Connection conn = null;
		if (file != null) {
			try {
				try {
					conn = baza.getConnection();
					System.out.println("Connection OPEN!");
				} catch (Exception e) {
					System.out.println("Napaka Connection!");
				}

				HttpSession session = Util.getSession();
				Uporabnik user = (Uporabnik) session.getAttribute("uporabnik");

				String sql = "SELECT * FROM slike WHERE Uporabnik_idUporabnik=?";
				PreparedStatement prst = conn.prepareStatement(sql);
				prst.setInt(1, user.getId_Uporabnik());

				ResultSet rs = prst.executeQuery();
				while (rs.next()) {
					Slike tempSlika = new Slike();
					tempSlika.setIdSlike(rs.getInt("idSlike"));
					tempSlika.setNaziv_slike(rs.getString("naziv_slike"));
					tempSlika.setSlika(rs.getBlob("slika"));

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				conn.close();
				System.out.println("Connection CLOSED!");
			}
		}
	}

	/**
	 * @return the idSlike
	 */
	public int getIdSlike() {
		return idSlike;
	}

	/**
	 * @param idSlike
	 *            the idSlike to set
	 */
	public void setIdSlike(int idSlike) {
		this.idSlike = idSlike;
	}

	/**
	 * @return the naziv_slike
	 */
	public String getNaziv_slike() {
		return naziv_slike;
	}

	/**
	 * @param naziv_slike
	 *            the naziv_slike to set
	 */
	public void setNaziv_slike(String naziv_slike) {
		this.naziv_slike = naziv_slike;
	}

	/**
	 * @return the slika
	 */
	public Blob getSlika() {
		return slika;
	}

	/**
	 * @param slika
	 *            the slika to set
	 */
	public void setSlika(Blob slika) {
		this.slika = slika;
	}

	/**
	 * @return the sportniCenter
	 */
	public SportniCenter getSportniCenter() {
		return sportniCenter;
	}

	/**
	 * @param sportniCenter
	 *            the sportniCenter to set
	 */
	public void setSportniCenter(SportniCenter sportniCenter) {
		this.sportniCenter = sportniCenter;
	}

	/**
	 * @return the uporabnik
	 */
	public Uporabnik getUporabnik() {
		return uporabnik;
	}

	/**
	 * @param uporabnik
	 *            the uporabnik to set
	 */
	public void setUporabnik(Uporabnik uporabnik) {
		this.uporabnik = uporabnik;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the baza
	 */
	public DataSource getBaza() {
		return baza;
	}

	/**
	 * @param baza
	 *            the baza to set
	 */
	public void setBaza(DataSource baza) {
		this.baza = baza;
	}
}
