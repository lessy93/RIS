package eRekreacija;

import java.util.Calendar;

public class Termini {

	private int id_Termini;
	private Calendar datum;
	private Calendar zacetniCas, koncniCas;
	private Boolean zasedenost;
	private Objekt objekt;
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
	public Calendar getZacetniCas() {
		return zacetniCas;
	}
	/**
	 * @param zacetniCas the zacetniCas to set
	 */
	public void setZacetniCas(Calendar zacetniCas) {
		this.zacetniCas = zacetniCas;
	}
	/**
	 * @return the koncniCas
	 */
	public Calendar getKoncniCas() {
		return koncniCas;
	}
	/**
	 * @param koncniCas the koncniCas to set
	 */
	public void setKoncniCas(Calendar koncniCas) {
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
