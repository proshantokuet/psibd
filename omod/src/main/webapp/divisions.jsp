<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>	  
 	<option value="">Please Select<option/>
	<c:forEach items="${locations}" var="location">	                  				 
			<option value="${location.id}">${location.name}</option>						             
	</c:forEach>				             
 

