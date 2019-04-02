<spring:htmlEscape defaultHtmlEscape="true" />
<link type="text/css"
	href="<c:url value="/resources/css/style.css"/>"
	rel="stylesheet">
	
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/style.css">
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/bootstrap.min.css">
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/jquery.dataTables.css">
	<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/bootstrap.min.js"></script>
	
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/manage") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/PSI/manage.form"><spring:message
				code="PSI.manage" /></a>
	</li>
	
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="PSI.title" />
</h2>
