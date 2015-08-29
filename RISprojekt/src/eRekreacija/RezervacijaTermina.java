package eRekreacija;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import eRekreacijaDAO.RezervacijaTerminaDAO;

@ManagedBean(name = "rezervacija")
@ViewScoped
public class RezervacijaTermina {

	private int id_RezervacijaTermina;
	private Termini termini;
	private Uporabnik uporabnik;

	private List<RezervacijaTermina> seznamRezervacijZaUporabnika;
	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	public void pridobiSez() throws IOException {
		RezervacijaTerminaDAO rezervacijaDAO = new RezervacijaTerminaDAO(baza);
		seznamRezervacijZaUporabnika = new ArrayList<RezervacijaTermina>();
		Uporabnik upor = new Uporabnik();
		HttpSession session = Util.getSession();
		upor = (Uporabnik) session.getAttribute("uporabnik");// uporabnika
																// pridobimo iz
																// seje
		System.out.println("Uporabnik" + upor.toString());

		if (upor != null) {

			try {
				seznamRezervacijZaUporabnika = rezervacijaDAO.getRezervacijeByIdUpo(upor.getId_Uporabnik());
			} catch (Exception e) {
				System.out.println("Napaka! pridobi rezervacije");
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the id_RezervacijaTermina
	 */
	public int getId_RezervacijaTermina() {
		return id_RezervacijaTermina;
	}

	/**
	 * @param id_RezervacijaTermina
	 *            the id_RezervacijaTermina to set
	 */
	public void setId_RezervacijaTermina(int id_RezervacijaTermina) {
		this.id_RezervacijaTermina = id_RezervacijaTermina;
	}

	/**
	 * @return the termini
	 */
	public Termini getTermini() {
		return termini;
	}

	/**
	 * @param termini
	 *            the termini to set
	 */
	public void setTermini(Termini termini) {
		this.termini = termini;
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

	public List<RezervacijaTermina> getSeznamRezervacijZaUporabnika() {
		return seznamRezervacijZaUporabnika;
	}

	public void setSeznamRezervacijZaUporabnika(List<RezervacijaTermina> seznamRezervacijZaUporabnika) {
		this.seznamRezervacijZaUporabnika = seznamRezervacijZaUporabnika;
	}

	@Override
	public String toString() {
		return id_RezervacijaTermina + "" + uporabnik + "";
	}
}
