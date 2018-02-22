<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='Het Cultuurhuis' />
</head>
<body>
	<h1>
		Het Cultuurhuis:voorstellingen <img
			src=<c:url value='/images/voorstellingen.png'/> alt='voorstellingen'>
	</h1>
	<vdab:menu voorstellingen='hidden' />
	<vdab:menuGenres />
	<c:choose>
		<c:when test='${empty param}'>
		</c:when>
		<c:when test='${not empty fout}'>
			<div class='fout'>${fout}</div>
		</c:when>
		<c:when test='${empty genreVoorstellingen}'>
			<div class='fout'>Geen voorstellingen gevonden</div>
		</c:when>
		<c:otherwise>
			<table>
				<caption>${genre.naam}voorstellingen</caption>
				<tr>
					<th>Datum</th>
					<th>Titel</th>
					<th>Uitvoerders</th>
					<th>Prijs</th>
					<th>Vrije plaatsen</th>
					<th>Reserveren</th>
				</tr>
				<c:forEach var='genreVoorstelling' items='${genreVoorstellingen}'>
					<tr>
						<td><fmt:formatDate value='${genreVoorstelling.datumTijd}'
								type='both' dateStyle='short' timeStyle='short' /></td>
						<td><c:out value='${genreVoorstelling.titel}'/></td>
						<td><c:out value='${genreVoorstelling.uitvoerders}'/></td>
						<td>&euro;${genreVoorstelling.prijs}</td>
						<td>${genreVoorstelling.vrijePlaatsen}</td>
						<td><c:if test='${genreVoorstelling.vrijePlaatsen > 0}'>
								<c:url value='/reserveren.htm' var='reserveringsURL'>
									<c:param name='id' value='${genreVoorstelling.id}' />
								</c:url>
								<a href='${reserveringsURL}'>Reserveren</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
</body>
</html>