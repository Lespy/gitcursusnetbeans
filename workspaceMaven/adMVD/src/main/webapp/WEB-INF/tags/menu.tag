<%@ tag language="java" pageEncoding="UTF-8"%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<nav>
	<ul>
		<li><a href="<c:url value='/'/>">&#8962;</a></li>
		<li><a href="#">Bar</a>
			<ul>
				<li><a href="<c:url value='/bar'/>">Lijst</a></li>
			</ul></li>
		<li><a href="#">Leden</a>
			<ul>
				<li><a href="<c:url value='/leden/lijst'/>">Lijst</a></li>
				<li><a href="<c:url value='/leden/toevoegen'/>">Toevoegen</a></li>
				<li><a href="<c:url value='/leden/verwijderen'/>">Verwijderen</a></li>
			</ul></li>

		<%-- 		<c:if test='${pageContext.response.locale.language != "nl"}'> --%>
		<%-- 			<c:url value='' var='nederlandsURL'> --%>
		<%-- 				<c:param name='locale' value='nl_be' /> --%>
		<%-- 			</c:url> --%>
		<%-- 			<li><a href='${nederlandsURL}'>Nederlands</a></li> --%>
		<%-- 		</c:if> --%>
	</ul>
</nav>
