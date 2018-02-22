<%@page contentType='text/html' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
	<vdab:head title='Nieuwe klant'/>
</head>
<body>
	<h1>Het Cultuurhuis:nieuwe klant
		<img src="<c:url value='/images/nieuweklant.png'/>" alt='Nieuwe klant'>
	</h1>
	<vdab:menu/>
	<form method='post'>
		<label>Voornaam:
			<input name='voornaam' autofocus>
		</label>
		<label>Familienaam:
			<input name='familienaam'>
		</label>
		<label>Straat:
			<input name='straat'>
		</label>
		<label>Huisnr.:
			<input type='number' name='huisnr'>
		</label>
		<label>Postcode:
			<input type='number' name='postcode'>
		</label>
		<label>Gemeente:
			<input name='gemeente'>
		</label>
		<label>Gebruikersnaam:
			<input name='gebruikersnaam'>
		</label>
		<label>Paswoord:
			<input name='paswoord' type='password'>
		</label>
		<label>Herhaal paswoord:
			<input name='herhaalpaswoord' type='password'>
		</label>
		<input type='submit' value='OK'>
	</form>
	<ul>
		<c:forEach var='fout' items='${fouten}'>
			<li>${fout.value}</li>
		</c:forEach>
	</ul>
</body>
</html>