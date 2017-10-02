<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='fmt' uri='http://java.sun.com/jsp/jstl/fmt'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='Reserveren' />
</head>
<body>
	<h1>
		Het Cultuurhuis:reserveren <img
			src=<c:url value='/images/reserveren.png'/> alt='reserveren'>
	</h1>
	<vdab:menu />
	<c:choose>
		<c:when test='${not empty fout}'>
			<div class='fout'>${fout}</div>
		</c:when>
		<c:when test='${empty voorstelling}'>
			<div class='fout'>Voorstelling niet gevonden</div>
		</c:when>
		<c:otherwise>
			<dl>
				<dd>Voorstelling:</dd>
				<dt><c:out value='${voorstelling.titel}'/></dt>
				<dd>Uitvoerders:</dd>
				<dt><c:out value='${voorstelling.uitvoerders}'/></dt>
				<dd>Datum:</dd>
				<dt>
					<fmt:formatDate value='${voorstelling.datumTijd}' type='both'
						dateStyle='short' timeStyle='short' />
				</dt>
				<dd>Prijs:</dd>
				<dt>&euro;${voorstelling.prijs}</dt>
				<dd>Vrije plaatsen:</dd>
				<dt>${voorstelling.vrijePlaatsen}</dt>
			</dl>
			<form method='post'>
				<label>Plaatsen: <input name='aantal' type='number'
					value='${aantalPlaatsenGereserveerd}' required/>
					<c:if test='${not empty fouten}'>
						<span>${fouten.aantal}${voorstelling.vrijePlaatsen}</span>
						<span>${fouten.aantalLeeg}</span>
					</c:if>
				</label> <input type='submit' value='Reserveren' />
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>