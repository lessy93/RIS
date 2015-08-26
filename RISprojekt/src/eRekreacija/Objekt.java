package eRekreacija;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.sql.DataSource;

@ManagedBean(name = "objekt")
@SessionScoped
public class Objekt {
	
	private int id_Objekta;
	private String naziv_Objekta;
	private String tipObjekta; //igrisca
	private String opis_Objekta;
	private String cena_Objekta;
	private SportniCenter sportniCenter;
	private TipSporta tipSporta;
	
	public int getId_Objekta() {
		return id_Objekta;
	}
	@Resource(lookup="java:jboss/datasources/eRekreacija/")
	DataSource baza;
	
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
	
}
