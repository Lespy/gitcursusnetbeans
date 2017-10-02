package be.vdab.servlets;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import be.vdab.entities.Genre;
import be.vdab.repositories.GenreRepository;
import be.vdab.repositories.VoorstellingRepository;
import be.vdab.util.StringUtils;

@WebServlet("/index.htm")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String VIEW = "/WEB-INF/JSP/index.jsp";
	private final transient GenreRepository genreRepository = new GenreRepository();
	private final transient VoorstellingRepository voorstellingRepository = new VoorstellingRepository();
	@Resource(name = GenreRepository.JNDI_NAME)
	void setDataSource(DataSource dataSource) {
		genreRepository.setDataSource(dataSource);
		voorstellingRepository.setDataSource(dataSource);
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Genre> genres = genreRepository.findAll();
		request.setAttribute("genres", genres);
		LocalDateTime vandaag = LocalDateTime.now();
		String idString = request.getParameter("id");
		if(StringUtils.isLong(idString)) {
			Long id = Long.parseLong(idString);
			genreRepository.findById(id).ifPresent(
					genre -> request.setAttribute("genre", genre));
			request.setAttribute("genreVoorstellingen",
					voorstellingRepository.findFuturePerformancesByGenreId(id, vandaag));
		}else {
			request.setAttribute("fout", "Id niet correct");
		}
		request.getRequestDispatcher(VIEW).forward(request,response);
	}
}
