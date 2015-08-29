package eRekreacija;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import eRekreacijaDAO.RezervacijaTerminaDAO;
import eRekreacijaDAO.TerminiDAO;

@ManagedBean(name = "termini")
@ViewScoped
public class Termini {

	private int id_Termini;
	private Date datum;
	private Date zacetniCas;
	private Date koncniCas;
	private double cenaTermina;
	private Boolean zasedenost;
	private Objekt objekt;

	private List<Termini> seznamTerminovObjekt;
	private List<Termini> seznamTerminovCenter;
	private List<Termini> seznamTerminovUporabnik;

	public Termini(Date datum, Date zacetniCas, Date koncniCas, Boolean zasedenost, Objekt objekt) {
		super();
		this.datum = datum;
		this.zacetniCas = zacetniCas;
		this.koncniCas = koncniCas;
		this.zasedenost = zasedenost;
		this.objekt = objekt;

	}

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;
	
	public void pridobiSez(){
		TerminiDAO terminiDAO= new TerminiDAO(baza);
		seznamTerminovUporabnik= new ArrayList<Termini>();
		Uporabnik upor= new Uporabnik();
		HttpSession session = Util.getSession();	
		upor = (Uporabnik) session.getAttribute("uporabnik");// uporabnika pridobimo iz seje
		System.out.println("Uporabnik"+ upor.toString());
		
		try {
			seznamTerminovUporabnik=terminiDAO.getTermineByIdUpo(upor.getId_Uporabnik());
		} catch (Exception e) {
			System.out.println("Napaka! pridobi rezervacije");
			e.printStackTrace();
		}
	}


	public Termini(int id_Termini, Date datum, Date zacetniCas, Date koncniCas, Boolean zasedenost, Objekt objekt) {
		super();
		this.id_Termini = id_Termini;
		this.datum = datum;
		this.zacetniCas = zacetniCas;
		this.koncniCas = koncniCas;
		this.zasedenost = zasedenost;
		this.objekt = objekt;
	}
	
	public void pridobiSeznamTerminovUporabnika(){
		
		
	}

	public Termini() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * public void pridobiSeznamTerminovZaObjekt(int id_objekta) {
	 * seznamTerminovObjekt= new ArrayList<Termini>(); TerminiDAO terminDAO= new
	 * TerminiDAO(baza); try { seznamTerminovObjekt=
	 * terminDAO.getTerminiBYidObjekt(id_objekta); System.out.println(
	 * "Velikost seznama- TERMINI:"+ seznamTerminovObjekt.size());
	 * 
	 * } catch (Exception e) { System.out.println("Napaka! Termini!"); }
	 */

	public List<Termini> getSeznamTerminovObjekt() {
		return seznamTerminovObjekt;
	}

	public void setSeznamTerminovObjekt(ArrayList<Termini> seznamTerminovObjekt) {
		this.seznamTerminovObjekt = seznamTerminovObjekt;
	}

	public Termini(Date zacetniCas2, Date koncniCas2, Boolean zasedenost, Objekt objekt) {
		super();
		this.zacetniCas = zacetniCas2;
		this.koncniCas = koncniCas2;
		this.zasedenost = zasedenost;
		this.objekt = objekt;
	}

	/**
	 * @return the id_Termini
	 */
	public int getId_Termini() {
		return id_Termini;
	}

	/**
	 * @param id_Termini
	 *            the id_Termini to set
	 */
	public void setId_Termini(int id_Termini) {
		this.id_Termini = id_Termini;
	}

	/**
	 * @return the datum
	 */
	public Date getDatum() {
		return datum;
	}

	/**
	 * @param datum
	 *            the datum to set
	 */
	public void setDatum(Date datum) {
		this.datum = datum;
	}

	/**
	 * @return the zacetniCas
	 */
	public Date getZacetniCas() {
		return zacetniCas;
	}

	/**
	 * @param zacetniCas
	 *            the zacetniCas to set
	 */
	public void setZacetniCas(Date zacetniCas) {
		this.zacetniCas = zacetniCas;
	}

	/**
	 * @return the koncniCas
	 */
	public Date getKoncniCas() {
		return koncniCas;
	}

	/**
	 * @param koncniCas
	 *            the koncniCas to set
	 */
	public void setKoncniCas(Date koncniCas) {
		this.koncniCas = koncniCas;
	}

	/**
	 * @return the zasedenost
	 */
	public Boolean getZasedenost() {
		return zasedenost;
	}

	/**
	 * @param zasedenost
	 *            the zasedenost to set
	 */
	public void setZasedenost(Boolean zasedenost) {
		this.zasedenost = zasedenost;
	}

	/**
	 * @return the objekt
	 */
	public Objekt getObjekt() {
		return objekt;
	}

	/**
	 * @param objekt
	 *            the objekt to set
	 */
	public void setObjekt(Objekt objekt) {
		this.objekt = objekt;
	}

	public List<Termini> getSeznamTerminovCenter() {
		return seznamTerminovCenter;
	}

	public void setSeznamTerminovCenter(List<Termini> seznamTerminovCenter) {
		this.seznamTerminovCenter = seznamTerminovCenter;
	}

	public List<Termini> getSeznamTerminovUporabnik() {
		return seznamTerminovUporabnik;
	}

	public void setSeznamTerminovUporabnik(List<Termini> seznamTerminovUporabnik) {
		this.seznamTerminovUporabnik = seznamTerminovUporabnik;
	}

	@Override
	public String toString() {
		return "Termini [id_Termini=" + id_Termini + ", datum=" + datum + ", zacetniCas=" + zacetniCas + ", koncniCas="
				+ koncniCas + ", zasedenost=" + zasedenost + ", objekt=" + objekt + "]";
	}

	public void setSeznamTerminovObjekt(List<Termini> seznamTerminovObjekt) {
		this.seznamTerminovObjekt = seznamTerminovObjekt;
	}


	public double getCenaTermina() {
		return cenaTermina;
	}


	public void setCenaTermina(double cenaTermina) {
		this.cenaTermina = cenaTermina;
	}

}
