package eRekreacija;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

import eRekreacijaDAO.TipSportaDAO;

@ManagedBean(name = "sport")
@SessionScoped
public class TipSporta {

	private int id_TipSporta;
	private String naziv_TipSporta;
	private List<TipSporta> vsiTipi;

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	public TipSporta() {

	}

	@PostConstruct
	public void init() throws Exception {
		TipSportaDAO sportDAO = new TipSportaDAO(baza);
		vsiTipi = sportDAO.getTipSports();
	}

	public void pridobiVseSporte() throws Exception {
		TipSportaDAO sportDAO = new TipSportaDAO(baza);
		vsiTipi = sportDAO.getTipSports();
		System.out.println("Tip Sporta:");
		for (TipSporta tipSporta : vsiTipi) {
			System.out.println("Tip Sporta:" + tipSporta.toString());
		}

	}

	public List<TipSporta> getVsiTipi() {
		return vsiTipi;
	}

	public void setVsiTipi(List<TipSporta> vsiTipi) {
		this.vsiTipi = vsiTipi;
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
		return id_TipSporta + naziv_TipSporta;
	}
}
