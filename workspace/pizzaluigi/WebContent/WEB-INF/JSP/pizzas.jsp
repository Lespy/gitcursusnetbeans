<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%> 
<!doctype html>
<html lang='nl'>
<head>
<c:import url='/WEB-INF/JSP/head.jsp'>
<c:param name='title' value="Pizza's"/>
</c:import>
</head>
<body>
<c:import url="/WEB-INF/JSP/menu.jsp"/>
	<h1>Pizza's
		<c:forEach begin='1' end='5'>&#9733; <%-- de HTML code van een ster --%></c:forEach>
	</h1>
<ul class='zebra'>
	<c:forEach var='pizza' items='${pizzas}'>
		<li>${pizza.id}: 
			<c:out value='${pizza.naam}'/> ${pizza.prijs}&euro;
			${pizza.pikant ? "Hot" : "Not" }
			<c:url value='/pizzas/detail.htm' var='detailURL'>
				<c:param name='id' value='${pizza.id}'/>
			</c:url>
			<a href='${detailURL}' >detail</a>	
		</li>
	</c:forEach>
</ul>
</body>
</html>