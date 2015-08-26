package eRekreacija;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.sql.DataSource;

import eRekreacijaDAO.SportniCenterDAO;
import eRekreacijaDAO.UporabnikDAO;

@ManagedBean(name = "center")
@SessionScoped
public class SportniCenter {
	
	private int id_SportniCenter;
	private String naziv_SportniCenter;
	private String opis_SportniCenter;
	private String lokacija;
	private float mapsLat;
	private float mapsLng;
	private List<Objekt> seznamObjektov;
	private Boolean aktiven__SportniCenter;
	private SportniCenter izbraniSportniCenter;
	private int  id_Uporabnik;
	private String ime;
	private String priimek;
	private String email;
	private String geslo;
	
	
	private List<SportniCenter> seznamVsehCentrov= new ArrayList<SportniCenter>();
	
	@Resource(lookup="java:jboss/datasources/eRekreacija/")
	DataSource baza;
	
	

	public SportniCenter dodajCenter() throws Exception{
		
		SportniCenter noviCenter = null;
		try{
		SportniCenterDAO centerDAO= new SportniCenterDAO(baza);
		
		System.out.println("Center:" + naziv_SportniCenter);
		noviCenter= new SportniCenter(id_SportniCenter, naziv_SportniCenter, opis_SportniCenter, lokacija, 1, 1, true);
		System.out.println("Center:"+ noviCenter.getNaziv_SportniCenter()+" "+ noviCenter.lokacija);
		centerDAO.shraniSportniCenter(noviCenter);
		
		
		System.out.println("Center dodan!");
		System.out.println(noviCenter.toString());
		
		UporabnikDAO uporDAO= new UporabnikDAO(baza);
		Uporabnik noviUporabnik= new Uporabnik(id_Uporabnik, ime, priimek, email, geslo, true, noviCenter );
		System.out.println("Uporabnik1111:"+ noviUporabnik.toString());
		uporDAO.shraniUporabnika(noviUporabnik);
		System.out.println("Uporabnik:"+ noviUporabnik);
		System.out.println("Uporabnik dodan!");
	
	
		FacesMessage message = new FacesMessage("Registracija uspešna! ");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			context.redirect("profile.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
     
		}catch(Exception e){
			System.out.println("Napaka! dodaj Center!"+ e.toString());
			FacesMessage message = new FacesMessage("Registracija ni uspela! Prosimo poskusite ponovno! ");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return noviCenter;
	}
	
	
	
	
	@PostConstruct
	public void init() throws Exception {
		SportniCenterDAO scDAO= new SportniCenterDAO(baza);
		seznamVsehCentrov= scDAO.getSeznamSportnihCentrov();
	}
	
	public void getSeznamCentrov() throws Exception{	
		SportniCenterDAO scDAO= new SportniCenterDAO(baza);
		seznamVsehCentrov= scDAO.getSeznamSportnihCentrov(); 
	}
	
	public void prikaziIzbraniSportniCenter(int id) throws Exception{
		SportniCenterDAO scDAO= new SportniCenterDAO(baza);
		izbraniSportniCenter= scDAO.getCenterById(id);
	}
	
	
	
	public SportniCenter(int id_SportniCenter, String naziv_SportniCenter,
			String opis_SportniCenter, String lokacija, float mapsLat,
			float mapsLng, Boolean aktiven__SportniCenter) {
		super();
		this.id_SportniCenter = id_SportniCenter;
		this.naziv_SportniCenter = naziv_SportniCenter;
		this.opis_SportniCenter = opis_SportniCenter;
		this.lokacija = lokacija;
		this.mapsLat = mapsLat;
		this.mapsLng = mapsLng;
		this.seznamObjektov = seznamObjektov;
		this.aktiven__SportniCenter = aktiven__SportniCenter;
	}
	public SportniCenter() {
		// TODO Auto-generated constructor stub
	}
	public SportniCenter(int id) {
		this.id_SportniCenter=id;
	}

	/**
	 * @return the id_SportniCenter
	 */
	public int getId_SportniCenter() {
		return id_SportniCenter;
	}
	/**
	 * @param id_SportniCenter the id_SportniCenter to set
	 */
	public void setId_SportniCenter(int id_SportniCenter) {
		this.id_SportniCenter = id_SportniCenter;
	}
	/**
	 * @return the naziv_SportniCenter
	 */
	public String getNaziv_SportniCenter() {
		return naziv_SportniCenter;
	}
	/**
	 * @param naziv_SportniCenter the naziv_SportniCenter to set
	 */
	public void setNaziv_SportniCenter(String naziv_SportniCenter) {
		this.naziv_SportniCenter = naziv_SportniCenter;
	}
	/**
	 * @return the opis_SportniCenter
	 */
	public String getOpis_SportniCenter() {
		return opis_SportniCenter;
	}
	/**
	 * @param opis_SportniCenter the opis_SportniCenter to set
	 */
	public void setOpis_SportniCenter(String opis_SportniCenter) {
		this.opis_SportniCenter = opis_SportniCenter;
	}
	/**
	 * @return the lokacija
	 */
	public String getLokacija() {
		return lokacija;
	}
	/**
	 * @param lokacija the lokacija to set
	 */
	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
	}
	/**
	 * @return the mapsLat
	 */
	public float getMapsLat() {
		return mapsLat;
	}
	/**
	 * @param mapsLat the mapsLat to set
	 */
	public void setMapsLat(float mapsLat) {
		this.mapsLat = mapsLat;
	}
	/**
	 * @return the mapsLng
	 */
	public float getMapsLng() {
		return mapsLng;
	}
	/**
	 * @param mapsLng the mapsLng to set
	 */
	public void setMapsLng(float mapsLng) {
		this.mapsLng = mapsLng;
	}
	/**
	 * @return the seznamObjektov
	 */
	public List<Objekt> getSeznamObjektov() {
		return seznamObjektov;
	}
	/**
	 * @param seznamObjektov the seznamObjektov to set
	 */
	public void setSeznamObjektov(List<Objekt> seznamObjektov) {
		this.seznamObjektov = seznamObjektov;
	}
	/**
	 * @return the aktiven__SportniCenter
	 */
	public Boolean getAktiven__SportniCenter() {
		return aktiven__SportniCenter;
	}
	/**
	 * @param aktiven__SportniCenter the aktiven__SportniCenter to set
	 */
	public void setAktiven__SportniCenter(Boolean aktiven__SportniCenter) {
		this.aktiven__SportniCenter = aktiven__SportniCenter;
	}
	
	public List<SportniCenter> getSeznamVsehCentrov() {
		return seznamVsehCentrov;
	}



	public void setSeznamVsehCentrov(List<SportniCenter> seznamVsehCentrov) {
		this.seznamVsehCentrov = seznamVsehCentrov;
	}



	public SportniCenter getIzbraniSportniCenter() {
		return izbraniSportniCenter;
	}



	public void setIzbraniSportniCenter(SportniCenter izbraniSportniCenter) {
		this.izbraniSportniCenter = izbraniSportniCenter;
	}



	@Override
	public String toString() {
		return "SportniObjekt [idSportniObjekt=" + id_SportniCenter + ", naziv="
				+ naziv_SportniCenter + ", opis=" + opis_SportniCenter + ", lokacija=" + lokacija
				+ ", lat=" + mapsLat + ", lng=" + mapsLng + ", aktiven=" + aktiven__SportniCenter + "]";
	}




	public int getId_Uporabnik() {
		return id_Uporabnik;
	}




	public void setId_Uporabnik(int id_Uporabnik) {
		this.id_Uporabnik = id_Uporabnik;
	}




	public String getIme() {
		return ime;
	}




	public void setIme(String ime) {
		this.ime = ime;
	}




	public String getPriimek() {
		return priimek;
	}




	public void setPriimek(String priimek) {
		this.priimek = priimek;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public String getGeslo() {
		return geslo;
	}




	public void setGeslo(String geslo) {
		this.geslo = geslo;
	}
	
	

}
