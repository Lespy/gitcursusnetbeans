<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://mvd.be/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<v:head title='adMVD - leden' />
</head>
<body>
	<h1 align="right">adMVD - Leden</h1>
	<v:menu />
	<img alt='field' src='<c:url value="/images/headerIMG.jpg"/>'
		class='fullwidth'>
		<h2>Ledenlijst</h2>	
		<form method='post'>
		<input style='display: inline; ' type='submit' value='Importeer'
			name='importBTN'> <input
			style='display: inline; font-family: monospace;' type='submit'
			value='Lid toevoegen' name='lidToevoegenBTN'> <input
			style='display: inline;' type='submit'
			value='Lid verwijderen' name='lidVerwijderenBTN'> <input
			style='display: inline; ' type='submit'
			value='Exporteer' name='exportBTN'>
	</form>
	<p>hier komt een lijst van de leden</p>
</body>
</html>