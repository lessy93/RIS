package eRekreacija;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import eRekreacijaDAO.UporabnikDAO;

@ManagedBean(name = "uporabnik")
@SessionScoped
public class Uporabnik {

	private int id_Uporabnik;
	private String ime;
	private String priimek;
	private String email;
	private String geslo;
	private Boolean aktiven_Uporabnik;
	private SportniCenter sportniCenter;
	private boolean admin;
	private List<SportniCenter> vrniMojCenter;

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	public void test() throws Exception {

		UporabnikDAO pd = new UporabnikDAO(baza);
		SportniCenter sc = new SportniCenter(1, "ad", "opis", "lokacija", 1, 1, true);
		Uporabnik n = new Uporabnik(5, "miha", "leskovaR", "email@EMAIL.NET", "geslo", true, sc);
		System.out.println(sc);
		System.out.println(n);
		pd.shraniUporabnika(n);
		System.out.println("to tui");
	}

	public void dodajUporabnika() throws Exception {
		try {
			UporabnikDAO uporDAO = new UporabnikDAO(baza);
			Email posljiEmail = new Email();
			SportniCenter noviSportniCenter = new SportniCenter();
			
			noviSportniCenter.setId_SportniCenter(1);

			Uporabnik upor = new Uporabnik(id_Uporabnik, ime, priimek, email, geslo, true, noviSportniCenter, false);
			System.out.println("REGISTRACIJA:"+ upor.toString());
			uporDAO.shraniUporabnika(upor);

			System.out.println("REGISTRACIJA: Uporabnik dodan!");

			try {
				posljiEmail.posljiEmailRegister(email);
			} catch (Exception e) {
				System.out.println("Napaka! Pošiljanje email-a!" + e.toString());
				FacesMessage message = new FacesMessage("Registracija ni uspela! Prosimo poskusite ponovno! ");
				FacesContext.getCurrentInstance().addMessage(null, message);
			}

			FacesMessage message = new FacesMessage("Registracija uspešna! ");

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect("login.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			System.out.println("Napaka! dodaj Uporabnika!" + e.toString());
			FacesMessage message = new FacesMessage("Registracija ni uspela! Prosimo poskusite ponovno! ");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public void shraniSpremembe() throws Exception {
		UporabnikDAO upDAO = new UporabnikDAO();
		Uporabnik up = new Uporabnik(id_Uporabnik, ime, priimek, email, geslo, aktiven_Uporabnik);
		try {
			upDAO.updateUporabnik(up);
			FacesMessage message = new FacesMessage("Urejanje profila uspešno! ");

			FacesContext.getCurrentInstance().addMessage(null, message);
			FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
		} catch (Exception e) {
			System.out.println("Napaka! uredi Uporabnika!" + e.toString());
		}

	}

	public List<SportniCenter> vrniMojCenter() throws Exception {
		UporabnikDAO uDAO = new UporabnikDAO(baza);
		System.out.println("lalala" + uDAO.toString());

		// tak imamo tu pri drugih funkcijah
		// vrniMojCenter = (List<SportniCenter>) uDAO.vrniMojCenter();
		// tak smo imeli mi na Praktikumu 2
		List<SportniCenter> vrniMojCenter = uDAO.vrniMojCenter();
		System.out.println(vrniMojCenter.toString());
		return vrniMojCenter;
	}

	public void izbrisiUporabnika() throws Exception {
		try {
			UporabnikDAO uporDAO = new UporabnikDAO(baza);

			HttpSession session = Util.getSession();
			int id = (int) session.getAttribute("id_user");
			System.out.println("Napaka! izbrisi Uporabnika z id-jem: " + id);
			uporDAO.deleteUporabnikById(id);
			FacesMessage message = new FacesMessage("Napaka! izbris ni uspel! ");

			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			try {
				context.redirect("profile.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}

			FacesContext.getCurrentInstance().addMessage(null, message);
		} catch (Exception e) {
			System.out.println("Napaka! izbrisi Uporabnika!" + e.toString());
			FacesMessage message = new FacesMessage("izbris ni uspela! Prosimo poskusite ponovno! ");
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	public Uporabnik() {
		super();
	}

	public Uporabnik(int id_Uporabnik, String ime, String priimek, String email, String geslo,
			Boolean aktiven_Uporabnik) {
		super();
		this.id_Uporabnik = id_Uporabnik;
		this.ime = ime;
		this.priimek = priimek;
		this.email = email;
		this.geslo = geslo;
		this.aktiven_Uporabnik = aktiven_Uporabnik;
	}

	public Uporabnik(int id_Uporabnik, String ime, String priimek, String email, String geslo,
			Boolean aktiven_Uporabnik, SportniCenter sportniCenter) {
		super();
		this.id_Uporabnik = id_Uporabnik;
		this.ime = ime;
		this.priimek = priimek;
		this.email = email;
		this.geslo = geslo;
		this.aktiven_Uporabnik = aktiven_Uporabnik;
		this.sportniCenter = sportniCenter;
	}

	/**
	 * @param id_Uporabnik
	 * @param ime
	 * @param priimek
	 * @param email
	 * @param geslo
	 * @param aktiven_Uporabnik
	 * @param sportniCenter
	 * @param admin
	 * @param vrniMojCenter
	 */
	public Uporabnik(int id_Uporabnik, String ime, String priimek, String email, String geslo,
			Boolean aktiven_Uporabnik, SportniCenter sportniCenter, boolean admin) {
		super();
		this.id_Uporabnik = id_Uporabnik;
		this.ime = ime;
		this.priimek = priimek;
		this.email = email;
		this.geslo = geslo;
		this.aktiven_Uporabnik = aktiven_Uporabnik;
		this.sportniCenter = sportniCenter;
		this.admin = admin;
	}

	/**
	 * @return the id_Uporabnik
	 */
	public int getId_Uporabnik() {
		return id_Uporabnik;
	}

	/**
	 * @param id_Uporabnik
	 *            the id_Uporabnik to set
	 */
	public void setId_Uporabnik(int id_Uporabnik) {
		this.id_Uporabnik = id_Uporabnik;
	}

	/**
	 * @return the ime
	 */
	public String getIme() {
		return ime;
	}

	/**
	 * @param ime
	 *            the ime to set
	 */
	public void setIme(String ime) {
		this.ime = ime;
	}

	/**
	 * @return the priimek
	 */
	public String getPriimek() {
		return priimek;
	}

	/**
	 * @param priimek
	 *            the priimek to set
	 */
	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the geslo
	 */
	public String getGeslo() {
		return geslo;
	}

	/**
	 * @param geslo
	 *            the geslo to set
	 */
	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}

	/**
	 * @return the aktiven_Uporabnik
	 */
	public Boolean getAktiven_Uporabnik() {
		return aktiven_Uporabnik;
	}

	/**
	 * @param aktiven_Uporabnik
	 *            the aktiven_Uporabnik to set
	 */
	public void setAktiven_Uporabnik(Boolean aktiven_Uporabnik) {
		this.aktiven_Uporabnik = aktiven_Uporabnik;
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
	 * @return the admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	/**
	 * @param admin
	 *            the admin to set
	 */
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * @return the vrniMojCenter
	 */
	public List<SportniCenter> getVrniMojCenter() {
		return vrniMojCenter;
	}

	/**
	 * @param vrniMojCenter
	 *            the vrniMojCenter to set
	 */
	public void setVrniMojCenter(List<SportniCenter> vrniMojCenter) {
		this.vrniMojCenter = vrniMojCenter;
	}

	@Override
	public String toString() {
		return "Uporabnik [id=" + id_Uporabnik + ", ime=" + ime + ", priimek=" + priimek + ", email=" + email
				+ ", geslo=" + geslo + ", aktiven=" + aktiven_Uporabnik + ",admin=" + admin + ", sportniObjekt=" + sportniCenter + "]";
	}

}
