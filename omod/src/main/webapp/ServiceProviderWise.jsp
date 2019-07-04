<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
	  <thead>
	        <tr>
	            <th>Category</th>	            
	            <th>Item</th>
	            <th>Code</th>
	            <th>Number of Service</th>	            
	            <th>Total</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="report" items="${ reports }">
	        <tr>
	            <td>${ report.category }</td>	            
	        	<td>${ report.code }</td>
	        	<td>${ report.item }</td>
	            <td>${ report.serviceCount }</td>
	            <td>${ report.total }</td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>

