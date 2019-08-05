<spring:htmlEscape defaultHtmlEscape="true" />

	
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/style.css">
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/bootstrap.min.css">
	<link rel="stylesheet" href="/openmrs/moduleResources/PSI/css/jquery.dataTables.css">
	<script type="text/javascript" src="/openmrs/moduleResources/PSI/js/bootstrap.min.js"></script>
	
	
<ul id="menu">
	<%-- <li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li> --%>
	<c:if test="${hasDashboardPermission}">			
	<li
		<c:if test='<%= request.getRequestURI().contains("/dashboard") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/PSI/dashboard.form"><spring:message
				code="PSI.dahboard" /></a>
	</li>	
	</c:if>
	<c:if test="${hasClinicPermission}">
	<li
		<c:if test='<%= request.getRequestURI().contains("/PSIClinicList") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/PSI/PSIClinicList.form"><spring:message
				code="PSI.psiclinic" /></a>
	</li>
	</c:if>
	<%-- <li
		<c:if test='<%= request.getRequestURI().contains("/PSIClinicServiceList") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/PSI/PSIClinicServiceList.form"><spring:message
				code="PSI.psiservicelist" /></a>
	</li> --%>
	<li>		
		<a href="/bahmni/home/#/dashboard">Back to Main</a>
	</li>
	
	
	<!-- Add further links here -->
</ul>
<h2>
	<p>${pre }</p> <p>${report }</p>
</h2>
