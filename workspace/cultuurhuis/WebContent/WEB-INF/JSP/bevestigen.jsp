<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
	<vdab:head title='Reservatie bevestigen'/>
</head>
<body>
	<h1>Het Cultuurhuis:bevestiging reservaties
		<img src="<c:url value='/images/bevestig.png'/>" alt='Bevestig'>
	</h1>
	<vdab:menu bevestigen='hidden'/>
	<form method='post'>
		<h2>Stap 1:Wie ben je?</h2>
		<label>Gebruikersnaam:
		<input name='gebruikersnaam' autofocus required
			<c:if test='${not empty klant}'>disabled</c:if>></label>
		<label>Paswoord:
		<input name='paswoord' type='password' required
			<c:if test='${not empty klant}'>disabled</c:if>></label>
		${fout}
		<input name='zoekMeOp' type='submit' value='Zoek me op'
			<c:if test='${not empty klant}'>disabled</c:if>>
	</form>
	<form action='<c:url value="/nieuweklant.htm"/>'>	
		<input type='submit' value='Ik ben nieuw'
			<c:if test='${not empty klant}'>disabled</c:if>>
	</form>
	<c:if test='${not empty klant}'>
		<c:out value='${klant.voornaam} ${klant.familienaam} ${klant.adres.straat}
		${klant.adres.huisNr} ${klant.adres.postcode} ${klant.adres.gemeente}'/>
	</c:if>
	<h2>Stap 2:Bevestigen</h2>
	<form method='post'>
		<input name='bevestigen' type='submit' value='Bevestigen'
			<c:if test='${empty klant}'>disabled</c:if>>
	</form>
</body>
</html>