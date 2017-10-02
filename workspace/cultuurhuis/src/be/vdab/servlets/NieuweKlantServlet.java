package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.entities.Adres;
import be.vdab.entities.Klant;
import be.vdab.repositories.KlantRepository;
import be.vdab.util.StringUtils;

@WebServlet("/nieuweklant.htm")
public class NieuweKlantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "WEB-INF/JSP/nieuweklant.jsp";
	private static final String REDIRECT_URL = "/bevestigen.htm";
	private final transient KlantRepository klantRepository = new KlantRepository();
	@Resource(name = KlantRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		klantRepository.setDataSource(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String,String> fouten = new HashMap<>();
		Optional<Klant> optionalKlant = klantRepository.read(request.getParameter("gebruikersnaam"));
		if(optionalKlant.isPresent()) {
			fouten.put("gebruikersnaamInGebruik", "Gebruikersnaam bestaat al. Kies een andere.");
		}
		String voornaam = request.getParameter("voornaam");
		if(voornaam == null || !Klant.isStringValid(voornaam)) {
			fouten.put("voornaamLeeg", "Voornaam niet ingevuld");
		}
		String familienaam = request.getParameter("familienaam");
		if(familienaam == null || !Klant.isStringValid(familienaam)) {
			fouten.put("familienaamLeeg", "Familienaam niet ingevuld");
		}
		String straat = request.getParameter("straat");
		if(straat == null || !Adres.isStringValid(straat)) {
			fouten.put("straatLeeg", "Straat niet ingevuld");
		}
		String huisnr = request.getParameter("huisnr");
		if(!StringUtils.isInt(huisnr) || !Adres.isIntValid(Integer.parseInt(huisnr))) {
			fouten.put("huisnrLeeg", "Huisnr. niet ingevuld");
		}
		String postcode = request.getParameter("postcode");
		if(!StringUtils.isInt(postcode) || !Adres.isIntValid(Integer.parseInt(postcode))) {
			fouten.put("postcodeLeeg", "Postcode niet ingevuld");
		}
		String gemeente = request.getParameter("gemeente");
		if(gemeente == null || !Adres.isStringValid(gemeente)) {
			fouten.put("gemeenteLeeg", "Gemeente niet ingevuld");
		}
		String gebruikersnaam = request.getParameter("gebruikersnaam");
		if(gebruikersnaam == null || !Klant.isStringValid(gebruikersnaam)) {
			fouten.put("gebruikersnaamLeeg", "Gebruikersnaam niet ingevuld");
		}
		String paswoord = request.getParameter("paswoord");
		if(paswoord == null || !Klant.isStringValid(paswoord)) {
			fouten.put("paswoord", "Paswoord niet ingevuld");
		}
		String herhaalPaswoord = request.getParameter("herhaalpaswoord");
		if(herhaalPaswoord == null || !Klant.isStringValid(herhaalPaswoord)) {
			fouten.put("herhaalpaswoordLeeg", "Herhaal paswoord niet ingevuld");
		}
		if(!(request.getParameter("paswoord")).equals(request.getParameter("herhaalpaswoord"))) {
			fouten.put("paswoordFout", "Paswoorden komen niet overeen");
		}
		if(fouten.isEmpty()) {
			Klant nieuweKlant = new Klant(request.getParameter("voornaam"), request.getParameter("familienaam"), 
				new Adres(request.getParameter("straat"), Integer.parseInt(request.getParameter("huisnr")), 
						Integer.parseInt(request.getParameter("postcode")), request.getParameter("gemeente")), 
					request.getParameter("gebruikersnaam"), request.getParameter("paswoord"));
			klantRepository.create(nieuweKlant);
			HttpSession session = request.getSession();
			session.setAttribute("gebruikersnaamSession", request.getParameter("gebruikersnaam"));
			session.setAttribute("paswoordSession", request.getParameter("paswoord"));
			response.sendRedirect(request.getContextPath() + REDIRECT_URL);
		} else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}

}
