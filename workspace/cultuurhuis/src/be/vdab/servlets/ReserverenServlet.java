package be.vdab.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import be.vdab.repositories.VoorstellingRepository;
import be.vdab.util.StringUtils;

@WebServlet("/reserveren.htm")
public class ReserverenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reserveren.jsp";
	private static final String REDIRECT_URL = "/reservatiemandje.htm";
	private static final String MANDJE = "mandje";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idString = request.getParameter("id");
		if(StringUtils.isLong(idString)) {
			Long id = Long.parseLong(idString);
			if(voorstellingRepository.read(id).isPresent()) {
				request.setAttribute("voorstelling", voorstellingRepository.read(id).get());
				HttpSession session = request.getSession(false);
				if(session != null) {
					@SuppressWarnings("unchecked")
					Map<Long, Integer> mandje = ((Map<Long, Integer>)session.getAttribute(MANDJE));
					if(mandje != null && mandje.containsKey(id)) {
						request.setAttribute("aantalPlaatsenGereserveerd", mandje.get(id));
					}
				}
			}
		}else {
			request.setAttribute("fout", "Id niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String aantalString = request.getParameter("aantal");
		String idString = request.getParameter("id");
		voorstellingRepository.read(Long.parseLong(idString)).ifPresent(
				voorstelling -> request.setAttribute("voorstelling", voorstelling));
		Map<String, String> fouten = new HashMap<>();
		if(aantalString == null) {
			fouten.put("aantalLeeg", "Verplicht");
		}
		if(!StringUtils.isInt(aantalString)) {
			fouten.put("aantal", "Tik een cijfer tussen 1 en ");
		}else {
			if(Integer.parseInt(aantalString) < 1) {
				fouten.put("aantal", "Tik een getal tussen 1 en ");
			} else if(Integer.parseInt(aantalString) > voorstellingRepository.read(
					Long.parseLong(idString)).get().getVrijePlaatsen()) {
				fouten.put("aantal", "Tik een getal tussen 1 en ");
			}
		}
		if(fouten.isEmpty()) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Long, Integer> mandje = ((Map<Long, Integer>)session.getAttribute(MANDJE));
			if(mandje == null) {
				mandje = new HashMap<>();
			}
			mandje.put(Long.parseLong(idString),
					Integer.parseInt(aantalString));
			session.setAttribute(MANDJE, mandje);
			response.sendRedirect(request.getContextPath() + REDIRECT_URL);
		}else {
			request.setAttribute("fouten", fouten);
			request.getRequestDispatcher(VIEW).forward(request, response);
		}
	}
}
