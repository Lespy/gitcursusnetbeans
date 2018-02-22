<%@tag description='menu' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@attribute name='voorstellingen' type='java.lang.String'%>
<%@attribute name='reservatiemandje' type='java.lang.String'%>
<%@attribute name='bevestigen' type='java.lang.String'%>
<div id='menu'>
<nav>
	<ul>
		<li class='${voorstellingen}'><a href="<c:url value='/index.htm'/>">Voorstellingen</a></li>
		<c:if test='${not empty sessionScope.mandje}'>
			<li class='${reservatiemandje}'><a href="<c:url value='/reservatiemandje.htm'/>">Reservatiemandje</a></li>
			<li class='${bevestigen}'><a href="<c:url value='/bevestigen.htm'/>">Bevestiging reservatie</a></li>
		</c:if>
	</ul>
</nav>
</div>                         