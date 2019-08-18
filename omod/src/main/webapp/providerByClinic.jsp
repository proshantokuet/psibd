<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>	  
 	 <option value=""></option>
	<c:forEach var="user" items="${ psiClinicUsers }">
		<option value="${user.username }">${user.userRole }</option>							  
	</c:forEach>				             
 

