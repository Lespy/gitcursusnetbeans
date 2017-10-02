<%@tag description='menuGenres' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<h2>Genres</h2>
<nav>
	<ul>
		<c:forEach var='genre' items='${genres}'>
			<c:url value='/index.htm' var='genreVoorstellingURL'>
				<c:param name='id' value="${genre.id}"/>
			</c:url>
			<li><a href='${genreVoorstellingURL}'>${genre.naam}</a></li>
		</c:forEach>
	</ul>
</nav>