<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='v' uri='http://mvd.be/tags'%>
<!doctype html>
<html lang='nl'>
<head><v:head title='Lid toevoegen'/></head>
<body>
<body>
<h1 align="right">adMVD</h1>
<v:menu/>
<img alt='field' src='<c:url value="/images/headerIMG.jpg"/>' class='fullwidth'>
<h2>leden toevoegen</h2>
<form method="post" action="<c:url value="/filialen" />" ><input type='submit' value='toevoegen'></form>
</body>
</html>