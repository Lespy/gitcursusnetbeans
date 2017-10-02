package be.vdab.servlets;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

import be.vdab.entities.Reservatie;
import be.vdab.repositories.VoorstellingRepository;

@WebServlet("/reservatiemandje.htm")
public class ReservatiemandjeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/reservatiemandje.jsp";
	private static final String MANDJE = "mandje";
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	@Resource(name = VoorstellingRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		voorstellingRepository.setDataSource(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session != null) {
			@SuppressWarnings("unchecked")
			Map<Long, Integer> mandje = (Map<Long, Integer>)session.getAttribute(MANDJE);
			if(mandje != null) {
				List<Reservatie> reservatiesInMandje = new ArrayList<>();
				List<BigDecimal> subtotaal = new ArrayList<>(); //ArrayList met subtotaal per voorstelling in mandje
				mandje.keySet().stream()
						.map(voorstellingRepository::read)
						.filter(Optional::isPresent)
						.map(Optional::get)
						.map(voorstelling -> new Reservatie(voorstelling, mandje.get(voorstelling.getId())))
						.forEach(reservatie -> {
							reservatiesInMandje.add(reservatie);
							subtotaal.add(reservatie.getVoorstelling().getPrijs().multiply(
									BigDecimal.valueOf(reservatie.getAantalPlaatsen())));
						});
				request.setAttribute("totalePrijs",
						subtotaal.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
				request.setAttribute("reservatiesInMandje", reservatiesInMandje);
			}
		}
		request.getRequestDispatcher(VIEW).forward(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameterValues("id") != null) {
			HttpSession session = request.getSession();
			@SuppressWarnings("unchecked")
			Map<Long, Integer> mandje = (Map<Long, Integer>)session.getAttribute(MANDJE);
			Arrays.stream(request.getParameterValues("id"))
				.forEach(id -> mandje.remove(Long.parseLong(id)));
			session.setAttribute(MANDJE, mandje);
		}
		response.sendRedirect(request.getRequestURI());
	}
}

