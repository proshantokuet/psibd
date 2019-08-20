<%-- ${reportr } --%>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 --%>
 <div class="form-content">
        <div class="row">
            <div class="col-md-4">
                <div class="form-group">                							
						<label> ${dashboard.servedPatient } </label>  &nbsp;&nbsp; Patients Served						
                 </div>
                  	
             </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.earned }</label>
						&nbsp;&nbsp; Revenue Earned                  			
				</div>
                  	
              </div>
              <div class="col-md-4">
               	<div class="form-group">
                  	<label for="Service Code">${dashboard.newPatient }</label>  &nbsp;&nbsp; New Registration                 			
				</div>                  	
              </div>              	
       </div>          	
</div>

<div class="form-content" id="servicePointWiseReport">	</div>  
 <table id="servicePoint" class="display">
 	<thead>
	        <tr>
	            <th>Category</th>
	            <th>Code</th>
	            <th>Item</th>
	            <th>Static</th>
	            <th>Satellite</th>
	            <th>CSP</th>
	            <th>Total</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach var="report" items="${ reports }">
	        <tr>
	            <td>${ report.category }</td>
	        	<td>${ report.code }</td>
	            <td>${ report.item }</td>
	            <td>${ report.clinic }</td>
	            <td>${ report.satelite }</td>
	            <td>${ report.csp }</td>
	            <td>${ report.total }</td>
	        </tr>
	       </c:forEach>
	        
	    </tbody>

</table>