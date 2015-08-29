package eRekreacija;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	public void posljiEmailRegister(String emailUporabnika) throws UnsupportedEncodingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("erekreacija.inforis", "erekreacija2015");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("erekreacija@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailUporabnika));
			message.setSubject("eRekreacija- Dobrodošli");
			message.setText("Pozdravljeni na strani eRekreacija! " + "\n\n Vaša registracija je bila uspešna! "
					+ "\n\n  " + "\n\nLep pozdrav, \n\n eRekreacija");

			Transport.send(message);

			System.out.println("Email ob Registraciji poslan!");

		} catch (MessagingException e) {
			System.out.println("NAPAKA! Pošiljanje email-a ni uspelo!");
			throw new RuntimeException(e);
		}

	}

	public void posljiEmailRezervacija(Uporabnik noviUporabnik, Termini noviTermin, Objekt novaObjekt, double cena)
			throws UnsupportedEncodingException {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("erekreacija.inforis", "erekreacija2015");
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("erekreacija@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(noviUporabnik.getEmail()));
			message.setSubject("Rezervacija termina uspešna");
			message.setText("Pozdravljeni " + noviUporabnik.getIme() + " " + noviUporabnik.getPriimek() + "!"
					+ "\n\nVaša rezervacija objekta " + novaObjekt.getNaziv_Objekta() + " za termin je bila uspešna!"
					+ "\n\nOd:" + noviTermin.getZacetniCas() + " " + "\n\nDo:" + noviTermin.getKoncniCas() + " "
					+ "\n\nCena:" + cena + "€ " + "\n\n " + "\n\nLep pozdrav! \n\neRekreacija");

			Transport.send(message);

			System.out.println("Email ob rezervaciji poslan!");

		} catch (MessagingException e) {
			System.out.println("NAPAKA! Pošiljanje email-a ni uspelo!");
			throw new RuntimeException(e);
		}

	}

}
