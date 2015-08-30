/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eRekreacija;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import eRekreacijaDAO.UporabnikDAO;

@ManagedBean(name = "loginBean")
@SessionScoped
public class Login implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private Uporabnik noviUporabnik;

	public boolean isLogged = false;

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	public void logout() {
		System.out.println("Odjavljam!");
		HttpSession session = Util.getSession();
		session.invalidate();
		System.out.println("ODJAVA USPESNA: ID:" + session.getId());
		FacesMessage message = new FacesMessage("Odjava uspešna! ");
		FacesContext.getCurrentInstance().addMessage(null, message);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void prijaviUporabnika() throws Exception {
		try {
			UporabnikDAO uporDAO = new UporabnikDAO(baza);
			Uporabnik uporabnik = null;

			uporabnik = uporDAO.prijaviUporabnika(username, password);

			System.out.println("LB- Uporabnik:" + uporabnik.toString());

			if (!uporabnik.equals(null)) {
				// get Http Session and store username
				username = uporabnik.getEmail();
				noviUporabnik=uporabnik;

				HttpSession session = Util.getSession();
				session.setAttribute("username", uporabnik.getEmail());
				session.setAttribute("id_user", uporabnik.getId_Uporabnik());
				session.setAttribute("uporabnik", uporabnik);
				System.out.println("sesion id: " + session.getId());
				FacesMessage message = new FacesMessage("Prijava uspešna! ");

				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Neveljavna prijava!", "Poizkusite ponovno!"));

			}
		} catch (Exception e) {
			System.out.println("Napaka! Prijava Uporabnika!");

		}
	}

	public void autoLogin(String user, String geslo) throws Exception {
		try {
			UporabnikDAO uporDAO = new UporabnikDAO(baza);
			Uporabnik uporabnik = null;

			uporabnik = uporDAO.prijaviUporabnika(user, geslo);

			System.out.println("AUTOLOGIN- Uporabnik:" + uporabnik.toString());

			if (!uporabnik.equals(null)) {
				// get Http Session and store username
				username = uporabnik.getEmail();

				HttpSession session = Util.getSession();
				session.setAttribute("username", uporabnik.getEmail());
				session.setAttribute("id_user", uporabnik.getId_Uporabnik());
				session.setAttribute("uporabnik", uporabnik);
				System.out.println("sesion id: " + session.getId());
				FacesMessage message = new FacesMessage("Prijava uspešna! ");

				FacesContext.getCurrentInstance().addMessage(null, message);
				FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");

			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Neveljavna prijava!", "Poizkusite ponovno!"));

			}
		} catch (Exception e) {
			System.out.println("Napaka! Prijava Uporabnika!");

		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	public Uporabnik getNoviUporabnik() {
		return noviUporabnik;
	}

	public void setNoviUporabnik(Uporabnik noviUporabnik) {
		this.noviUporabnik = noviUporabnik;
	}
}
