package eRekreacija;

import java.io.IOException;

import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import eRekreacijaDAO.ObjektDAO;

@ManagedBean(name = "objekt")
@SessionScoped
public class Objekt {
	
	private int id_Objekta;
	private int id_TipSporta;//test
	private String naziv_Objekta;
	private String tipObjekta; //igrisca
	private String opis_Objekta;
	private String cena_Objekta;
	private SportniCenter sportniCenter= new SportniCenter();
	private TipSporta tipSporta= new TipSporta();
	
	@Resource(lookup="java:jboss/datasources/eRekreacija/")
	DataSource baza;
	

	public Objekt(int id_Objekta, String naziv_Objekta, String tipObjekta, String opis_Objekta, String cena_Objekta,
			SportniCenter sportniCenter, TipSporta tipSporta) {
		super();
		this.id_Objekta = id_Objekta;
		this.naziv_Objekta = naziv_Objekta;
		this.tipObjekta = tipObjekta;
		this.opis_Objekta = opis_Objekta;
		this.cena_Objekta = cena_Objekta;
		this.sportniCenter = sportniCenter;
		this.tipSporta = tipSporta;
	}
	

	public Objekt() {
		// TODO Auto-generated constructor stub
	}


	public void dodajObjekt() throws Exception{
		Objekt objekt=null;
		try{
		ObjektDAO objDAO= new ObjektDAO(baza);
		HttpSession session = Util.getSession();
      
        System.out.println("sesion id: "+session.getId());
		Uporabnik up=(Uporabnik) session.getAttribute("uporabnik");
		
		sportniCenter= up.getSportniCenter();
		System.out.println("up!"+ up.toString());
		
		
		System.out.println("tip sporta id!"+ id_TipSporta);
		System.out.println("naziv! "+ naziv_Objekta);
		System.out.println("opis! "+ opis_Objekta);
		System.out.println("tip objekta! "+ tipObjekta);
		
		objekt = new Objekt(id_Objekta, naziv_Objekta, tipObjekta, opis_Objekta, cena_Objekta, sportniCenter, tipSporta);
		
		System.out.println("Objekt:!"+ objekt.toString());
		
		objDAO.shraniObjekt(objekt);
		System.out.println("Objekt:"+ objekt.toString());
		System.out.println("Objekt dodan!");
		FacesMessage message = new FacesMessage("Objekt uspešno dodan! ");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			context.redirect("profile.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		}catch(Exception e){
			System.out.println("Napaka! dodaj Objekt!"+ e.toString());
			FacesMessage message = new FacesMessage("Objekt ni dodan! Prosimo poskusite ponovno! ");
	        FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}
	
	
	
	public int getId_Objekta() {
		return id_Objekta;
	}

	public void setId_Objekta(int id_Objekta) {
		this.id_Objekta = id_Objekta;
	}
	public String getNaziv_Objekta() {
		return naziv_Objekta;
	}
	public void setNaziv_Objekta(String naziv_Objekta) {
		this.naziv_Objekta = naziv_Objekta;
	}
	public String getTipObjekta() {
		return tipObjekta;
	}
	public void setTipObjekta(String tipObjekta) {
		this.tipObjekta = tipObjekta;
	}
	public String getOpis_Objekta() {
		return opis_Objekta;
	}
	public void setOpis_Objekta(String opis_Objekta) {
		this.opis_Objekta = opis_Objekta;
	}
	public String getCena_Objekta() {
		return cena_Objekta;
	}
	public void setCena_Objekta(String cena_Objekta) {
		this.cena_Objekta = cena_Objekta;
	}
	public SportniCenter getSportniCenter() {
		return sportniCenter;
	}
	public void setSportniCenter(SportniCenter sportniCenter) {
		this.sportniCenter = sportniCenter;
	}
	public TipSporta getTipSporta() {
		return tipSporta;
	}
	public void setTipSporta(TipSporta tipSporta) {
		this.tipSporta = tipSporta;
	}
	
	@Override
	public String toString() {
		return id_Objekta + naziv_Objekta + opis_Objekta + tipObjekta + tipSporta + sportniCenter;
	}



	public int getId_TipSporta() {
		return id_TipSporta;
	}



	public void setId_TipSporta(int id_TipSporta) {
		this.id_TipSporta = id_TipSporta;
	}
	
}
