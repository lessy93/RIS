package eRekreacija;

public class RezervacijaTermina {

	private int id_RezervacijaTermina;
	private Termini termini;
	private Uporabnik uporabnik;

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

	@Override
	public String toString() {
		return id_RezervacijaTermina + "" + uporabnik + "";
	}
}
