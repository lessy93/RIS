package eRekreacija;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import eRekreacija.Termini;
import eRekreacijaDAO.ObjektDAO;
import eRekreacijaDAO.RezervacijaTerminaDAO;
import eRekreacijaDAO.TerminiDAO;

@ManagedBean(name = "urnikBean")
@ViewScoped
public class Urnik implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduleModel eventModel;

	private ScheduleModel lazyEventModel;

	private ScheduleEvent event = new DefaultScheduleEvent();

	private List<Termini> seznamTerminovZaObjekt;

	@Resource(lookup = "java:jboss/datasources/eRekreacija/")
	DataSource baza;

	@PostConstruct
	public void init() {
		eventModel = new DefaultScheduleModel();

		TerminiDAO terminDAO = new TerminiDAO(baza);
		System.out.println("Pridobivanje vseh rezervacij za objekt:");
		// seznamTerminovZaObjekt = termin.getSeznamTerminovObjekt();
		seznamTerminovZaObjekt = new ArrayList<Termini>();
		try {
			HttpSession session = Util.getSession();
			int id = (int) session.getAttribute("izbraniObjekt");

			seznamTerminovZaObjekt = terminDAO.getTerminiBYidObjekt(id);
			// seznamTerminovZaObjekt= termin.getSeznamTerminovObjekt();
			for (Termini termini : seznamTerminovZaObjekt) {

				eventModel.addEvent(
						new DefaultScheduleEvent("Zasedeno", termini.getZacetniCas(), termini.getKoncniCas()));
			}

		} catch (Exception e) {
			System.out.println("Napaka! Neuspešno pridobivanje rezerviranih terminov!" + e.toString());
		}

	}

	public Date getRandomDate(Date base) {
		Calendar date = Calendar.getInstance();
		date.setTime(base);
		date.add(Calendar.DATE, ((int) (Math.random() * 30)) + 1); // set random
																	// day of
																	// month

		return date.getTime();
	}

	public Date getInitialDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), Calendar.FEBRUARY, calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar.getTime();
	}

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public ScheduleModel getLazyEventModel() {
		return lazyEventModel;
	}

	private Calendar today() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

		return calendar;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public void addEvent(ActionEvent actionEvent) throws Exception {
		if (event.getId() == null) {
			eventModel.addEvent(event);
			Date zacetniCas = event.getStartDate();
			Date koncniCas = event.getEndDate();
			Date datum = event.getStartDate();
			RezervacijaTerminaDAO rezterDAO = new RezervacijaTerminaDAO(baza);

			HttpSession session = Util.getSession();
			Uporabnik upor = new Uporabnik();
			upor = (Uporabnik) session.getAttribute("uporabnik");
			int id = (int) session.getAttribute("izbraniObjekt");

			ObjektDAO objektDAO = new ObjektDAO(baza);
			Objekt noviObjekt = new Objekt();
			noviObjekt = objektDAO.getObjektIfo(id);
			Boolean zasedenost = true;

			Termini noviTermin = new Termini(datum, zacetniCas, koncniCas, zasedenost, noviObjekt);
			TerminiDAO terminDAO = new TerminiDAO(baza);
			terminDAO.shraniTermin(noviTermin);
			rezterDAO.shraniRezervacijoTermina(noviTermin, upor);
			
		
		} else {
			eventModel.updateEvent(event);
		}
		event = new DefaultScheduleEvent();
	}

	public void onEventSelect(SelectEvent selectEvent) {
		event = (ScheduleEvent) selectEvent.getObject();
	}

	public void onDateSelect(SelectEvent selectEvent) {
		event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject());
	}

	public void onEventMove(ScheduleEntryMoveEvent event) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved",
				"Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	public void onEventResize(ScheduleEntryResizeEvent event) {

		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized",
				"Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

		addMessage(message);
	}

	private void addMessage(FacesMessage message) {
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
