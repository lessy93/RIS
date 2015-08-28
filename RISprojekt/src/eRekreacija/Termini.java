package eRekreacija;

import java.util.Calendar;
import java.util.Date;

public class Termini {

	private int id_Termini;
	private Calendar datum;
	Date zacetniCas;
	private Date koncniCas;
	private Boolean zasedenost;
	private Objekt objekt;
	
	
	
	
	public Termini(int id_Termini, Calendar datum, Date zacetniCas, Date koncniCas, Boolean zasedenost,
			Objekt objekt) {
		super();
		this.id_Termini = id_Termini;
		this.datum = datum;
		this.zacetniCas = zacetniCas;
		this.koncniCas = koncniCas;
		this.zasedenost = zasedenost;
		this.objekt = objekt;
	}
	public Termini() {
		// TODO Auto-generated constructor stub
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
	 * @param id_Termini the id_Termini to set
	 */
	public void setId_Termini(int id_Termini) {
		this.id_Termini = id_Termini;
	}
	/**
	 * @return the datum
	 */
	public Calendar getDatum() {
		return datum;
	}
	/**
	 * @param datum the datum to set
	 */
	public void setDatum(Calendar datum) {
		this.datum = datum;
	}
	/**
	 * @return the zacetniCas
	 */
	public Date getZacetniCas() {
		return zacetniCas;
	}
	/**
	 * @param zacetniCas the zacetniCas to set
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
	 * @param koncniCas the koncniCas to set
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
	 * @param zasedenost the zasedenost to set
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
	 * @param objekt the objekt to set
	 */
	public void setObjekt(Objekt objekt) {
		this.objekt = objekt;
	}
	
	
}
