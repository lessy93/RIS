package eRekreacija;

public class TipSporta {

	private int id_TipSporta;
	private String naziv_TipSporta;
	
	public TipSporta(){
		
	}



	public TipSporta(int id_TipSporta, String naziv_TipSporta) {
		super();
		this.id_TipSporta = id_TipSporta;
		this.naziv_TipSporta = naziv_TipSporta;
	}



	public int getId_TipSporta() {
		return id_TipSporta;
	}



	public void setId_TipSporta(int id_TipSporta) {
		this.id_TipSporta = id_TipSporta;
	}



	public String getNaziv_TipSporta() {
		return naziv_TipSporta;
	}



	public void setNaziv_TipSporta(String naziv_TipSporta) {
		this.naziv_TipSporta = naziv_TipSporta;
	}



	@Override
	public String toString() {
		return  id_TipSporta + naziv_TipSporta;
	}
}
