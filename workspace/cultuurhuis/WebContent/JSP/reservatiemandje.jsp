<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
	<vdab:head title='Reservatiemandje'/>
</head>
<body>
	<h1>Het Cultuurhuis:reservatiemandje
		<img src="<c:url value='/images/mandje.png'/>" alt='mandje'>
	</h1>
	<vdab:menu reservatiemandje='hidden'/>
	<form method='post'>
		<table>
			<tr>
				<th>Datum</th>
				<th>Titel</th>
				<th>Uitvoerders</th>
				<th>Prijs</th>
				<th>Plaatsen</th>
				<th><input type='submit' value='Verwijderen'/></th>
			</tr>
			<c:forEach var='reservatieInMandje' items='${reservatiesInMandje}'>
				<tr>
					<td><fmt:formatDate value='${reservatieInMandje.voorstelling.datumTijd}' 
						type='both' dateStyle='short' timeStyle='short'/></td>
					<td><c:out value='${reservatieInMandje.voorstelling.titel}'/></td>
					<td><c:out value='${reservatieInMandje.voorstelling.uitvoerders}'/></td>
					<td>&euro;${reservatieInMandje.voorstelling.prijs}</td>
					<td>${reservatieInMandje.aantalPlaatsen}</td>
					<td>
						<input type='checkbox' name='id' value='${reservatieInMandje.voorstelling.id}'>	
					</td>
				</tr>
			</c:forEach>
		</table>
	</form>
	Te betalen: &euro;${totalePrijs}
</body>
</html>