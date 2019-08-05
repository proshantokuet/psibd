<spring:htmlEscape defaultHtmlEscape="true" />

	
	

				
	<li>
		<c:if test='<%= request.getRequestURI().contains("/dashboard") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/PSI/dashboard.form"><spring:message
				code="PSI.dahboard" /></a>
	</li>
	
	
